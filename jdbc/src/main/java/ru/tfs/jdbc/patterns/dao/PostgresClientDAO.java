package ru.tfs.jdbc.patterns.dao;

import ru.tfs.jdbc.connection_pool.ApplicationConnectionPool;
import ru.tfs.jdbc.connection_pool.ConnectionPool;
import ru.tfs.jdbc.patterns.OptimisticLockException;
import ru.tfs.jdbc.patterns.model.ClientVO;
import ru.tfs.jdbc.patterns.model.OrdersVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostgresClientDAO implements VoDao<ClientVO> {

    private static final ConnectionPool connectionPool = ApplicationConnectionPool.getInstance();

    private static final String SELECT_QUERY = "select client_id, name, address, email, password from CLIENT where client_id = ?";

    private static final String UPDATE_QUERY = "update CLIENT set name = ?, address = ?, email = ?, password = ?  where client_id = ?";

    private static final String INSERT_QUERY = "insert into CLIENT (name, address, email, password) values (?, ?, ?, ?) ";

    private static final String DELETE_QUERY = "delete from CLIENT where client_id = ?";

    //Можно сделать с ленивой инициализацие, но не хочется, все равно потом все на Spring переделывать
    public static final PostgresClientDAO dao = new PostgresClientDAO();

    private PostgresClientDAO() {
    }

    @Override
    public ClientVO getById(Long id) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            ) {

                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                connectionPool.releaseConnection(connection);
                if (!resultSet.next()) {
                    return null;
                }

                return createVO(resultSet, IntStream.range(1, 6).boxed().collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ClientVO save(ClientVO client) {
        return save(client, null);
    }

    ClientVO save(ClientVO client, Connection connection) {
        try {
            if (connection != null && connection.isValid(2)) {
                innerSave(client, connection);
            } else {
                Connection newConnection = connectionPool.getConnection();
                newConnection.setAutoCommit(false);
                innerSave(client, newConnection);
                newConnection.commit();
                connectionPool.releaseConnection(newConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    private void innerSave(ClientVO client, Connection connection) throws SQLException {

        try (
                PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY);
                PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY);
        ) {

            if (client.getClientId() != null) {
                updateStatement.setString(1, client.getName());
                updateStatement.setString(2, client.getAddress());
                updateStatement.setString(3, client.getEmail());
                updateStatement.setString(4, client.getPassword());
                updateStatement.setLong(5, client.getClientId());
                int count = updateStatement.executeUpdate();
                if (count == 0) {
                    connection.rollback();
                    connectionPool.releaseConnection(connection);
                    throw new OptimisticLockException("Ошибка при обновлении записи с id ", client.getClientId());
                }
            } else {
                insertStatement.setString(1, client.getName());
                insertStatement.setString(2, client.getAddress());
                insertStatement.setString(3, client.getEmail());
                insertStatement.setString(4, client.getPassword());
                insertStatement.executeUpdate();

                PreparedStatement statement = connection.prepareStatement("select currval('client_client_id_seq')");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                Long id = resultSet.getLong(1);
                client.setClient_id(id);
            }
        }


    }

    @Override
    public void delete(ClientVO client) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY);
            ) {

                deleteStatement.setLong(1, client.getClientId());
                deleteStatement.executeUpdate();
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ClientVO createVO(ResultSet resultSet, List<Integer> indexes) throws SQLException {
        ClientVO clientVO = new ClientVO();
        clientVO.setClient_id(resultSet.getLong(indexes.get(0)));
        clientVO.setName(resultSet.getString(indexes.get(1)));
        clientVO.setAddress(resultSet.getString(indexes.get(2)));
        clientVO.setEmail(resultSet.getString(indexes.get(3)));
        clientVO.setPassword(resultSet.getString(indexes.get(4)));

        return clientVO;
    }
}
