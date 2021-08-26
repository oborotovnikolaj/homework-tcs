package ru.tfs.jdbc.patterns.dao;

import ru.tfs.jdbc.connection_pool.ApplicationConnectionPool;
import ru.tfs.jdbc.connection_pool.ConnectionPool;
import ru.tfs.jdbc.patterns.OptimisticLockException;
import ru.tfs.jdbc.patterns.model.*;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostgresOrdersDAO implements SoftDeletableDao<OrdersVO> {

    private static final ConnectionPool connectionPool = ApplicationConnectionPool.getInstance();

    private static final String SELECT_QUERY = "select  " +
            "       O.order_id, O.status, O.address, O.creation_time, O.end_time, O.is_delete, " +
            "       C.client_id, C.name, C.address, C.email, C.password " +
            "from ORDERS O  " +
            "left join CLIENT C on C.client_id = O.client_id " +
            "where order_id = ?";

    private static final String UPDATE_QUERY = "update ORDERS set status = ?, address = ?, creation_time = ?, end_time = ?, is_delete = ?, client_id = ? where order_id = ?";

    private static final String INSERT_QUERY = "insert into ORDERS(status, address, creation_time, end_time, is_delete, client_id) values (?,?,?,?,?,?)";

    private static final String SOFT_DELETE_QUERY = "update ORDERS set is_delete = current_timestamp where order_id = ?";

    private static final String DELETE_QUERY = "delete from ORDERS where order_id = ?";

    //Можно сделать с ленивой инициализацие, но не хочется, все равно потом все на Spring переделывать
    public static final PostgresOrdersDAO dao = new PostgresOrdersDAO();

    private PostgresOrdersDAO() {
    }

    ;

    @Override
    public OrdersVO getById(Long id) {
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

                return createVO(resultSet, IntStream.range(1, 12).boxed().collect(Collectors.toList()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public OrdersVO save(OrdersVO order) {
        return save(order, null);
    }

    OrdersVO save(OrdersVO ordersVO, Connection connection) {
        try {
            if (connection != null && connection.isValid(2)) {
                innerSave(ordersVO, connection);
            } else {
                Connection newConnection = connectionPool.getConnection();
                newConnection.setAutoCommit(false);
                innerSave(ordersVO, newConnection);
                newConnection.commit();
                connectionPool.releaseConnection(newConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersVO;
    }

    private void innerSave(OrdersVO order, Connection connection) throws SQLException {

        try (
                PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY);
                PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY);
        ) {

            PostgresClientDAO.dao.save(order.getClient(), connection);

            if (order.getOrderId() != null) {
                updateStatement.setString(1, order.getStatus());
                updateStatement.setString(2, order.getAddress());
                //очень плохо
                updateStatement.setTimestamp(3, order.getCreationTime() == null ? null : new Timestamp(order.getCreationTime().getTime()));
                updateStatement.setTimestamp(4, order.getEndTime() == null ? null : new Timestamp(order.getEndTime().getTime()));
                updateStatement.setTimestamp(5, order.getIsDelete() == null ? null : new Timestamp(order.getIsDelete().getTime()));
                updateStatement.setLong(6, order.getClient().getClientId());
                updateStatement.setLong(7, order.getOrderId());
                int count = updateStatement.executeUpdate();
                if (count == 0) {
                    connection.rollback();
                    connectionPool.releaseConnection(connection);
                    throw new OptimisticLockException("Ошибка при обновлении записи с id ", order.getOrderId());
                }
            } else {
                insertStatement.setString(1, order.getStatus());
                insertStatement.setString(2, order.getAddress());
                insertStatement.setTimestamp(3, order.getCreationTime() == null ? null : new Timestamp(order.getCreationTime().getTime()));
                insertStatement.setTimestamp(4, order.getEndTime() == null ? null : new Timestamp(order.getEndTime().getTime()));
                insertStatement.setTimestamp(5, order.getIsDelete() == null ? null : new Timestamp(order.getIsDelete().getTime()));
                insertStatement.setLong(6, order.getClient().getClientId());
                insertStatement.executeUpdate();

                //            PreparedStatement statement = connection.prepareStatement("select currval('orders_order_id_seq')");
                PreparedStatement statement = connection.prepareStatement("SELECT last_value FROM orders_order_id_seq");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                Long id = resultSet.getLong(1);
                order.setOrder_id(id);
            }
        }
    }


    @Override
    public void delete(OrdersVO order) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY);
            ) {

                deleteStatement.setLong(1, order.getOrderId());
                deleteStatement.executeUpdate();

                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void softDelete(OrdersVO order) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement deleteStatement = connection.prepareStatement(SOFT_DELETE_QUERY);
            ) {

                deleteStatement.setLong(1, order.getOrderId());
                deleteStatement.executeUpdate();
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static OrdersVO createVO(ResultSet resultSet, List<Integer> indexes) throws SQLException {
        OrdersVO order = new OrdersVO();

        order.setOrder_id(resultSet.getLong(indexes.get(0)));
        order.setStatus(resultSet.getString(indexes.get(1)));
        order.setAddress(resultSet.getString(indexes.get(2)));
        order.setCreationTime(resultSet.getTimestamp(indexes.get(3)));
        order.setEndTime(resultSet.getTimestamp(indexes.get(4)));
        order.setIsDelete(resultSet.getTimestamp(indexes.get(5)));

        ClientVO clientVO = PostgresClientDAO.createVO(resultSet, indexes.subList(6, 11));
        order.setClient(clientVO);

        return order;
    }
}
