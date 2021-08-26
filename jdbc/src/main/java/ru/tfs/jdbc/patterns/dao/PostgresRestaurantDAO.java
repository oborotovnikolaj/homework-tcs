package ru.tfs.jdbc.patterns.dao;

import ru.tfs.jdbc.connection_pool.ApplicationConnectionPool;
import ru.tfs.jdbc.connection_pool.ConnectionPool;
import ru.tfs.jdbc.patterns.OptimisticLockException;
import ru.tfs.jdbc.patterns.model.MenuItemVO;
import ru.tfs.jdbc.patterns.model.RestaurantVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostgresRestaurantDAO implements VoDao<RestaurantVO> {

    private static final ConnectionPool connectionPool = ApplicationConnectionPool.getInstance();

    private static final String SELECT_QUERY = "select restaurant_id, name, address, rating from RESTAURANT where restaurant_id = ?;";

    private static final String UPDATE_QUERY = "update RESTAURANT set name = ?, address = ?, rating = ?  where restaurant_id = ?";

    private static final String INSERT_QUERY = "insert into RESTAURANT ( name, address, rating) values (?, ?, ?)";

    private static final String DELETE_QUERY = "delete from RESTAURANT where restaurant_id = ?";

    @Override
    public RestaurantVO getById(Long id) {
        try {
            Connection connection = connectionPool.getConnection();
            try(
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            ) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                connectionPool.releaseConnection(connection);
                if (!resultSet.next()) {
                    return null;
                }

                return createVO(resultSet, IntStream.range(1, 5).boxed().collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Можно сделать с ленивой инициализацие, но не хочется, все равно потом все на Spring переделывать
    public static final PostgresRestaurantDAO dao = new PostgresRestaurantDAO();

    private PostgresRestaurantDAO() {
    }

    ;

    @Override
    public RestaurantVO save(RestaurantVO restaurant) {
        return save(restaurant, null);
    }

    RestaurantVO save(RestaurantVO restaurant, Connection connection) {
        try {
            if (connection != null && connection.isValid(2)) {
                innerSave(restaurant, connection);
            } else {
                Connection newConnection = connectionPool.getConnection();
                newConnection.setAutoCommit(false);
                innerSave(restaurant, newConnection);
                newConnection.commit();
                connectionPool.releaseConnection(newConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    private void innerSave(RestaurantVO restaurant, Connection connection) throws SQLException {
        try(
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY);
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY);
        ) {

            if (restaurant.getRestaurantId() != null) {
                updateStatement.setString(1, restaurant.getName());
                updateStatement.setString(2, restaurant.getAddress());
                updateStatement.setLong(3, restaurant.getRating());
                updateStatement.setLong(4, restaurant.getRestaurantId());
                int count = updateStatement.executeUpdate();
                if (count == 0) {
                    connection.rollback();
                    connectionPool.releaseConnection(connection);
                    throw new OptimisticLockException("Ошибка при обновлении записи с id ", restaurant.getRestaurantId());
                }
            } else {
                insertStatement.setString(1, restaurant.getName());
                insertStatement.setString(2, restaurant.getAddress());
                insertStatement.setLong(3, restaurant.getRating());
                insertStatement.executeUpdate();

                PreparedStatement statement = connection.prepareStatement("select currval('restaurant_id_seq')");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                Long id = resultSet.getLong(1);
                restaurant.setRestaurant_id(id);
            }
        }
    }

    @Override
    public void delete(RestaurantVO restaurant) {
        try {
            Connection connection = connectionPool.getConnection();
            try(
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY);
            ) {

                deleteStatement.setLong(1, restaurant.getRestaurantId());
                deleteStatement.executeUpdate();

                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantVO createVO(ResultSet resultSet, List<Integer> indexes) throws SQLException {
        RestaurantVO restaurantVO = new RestaurantVO();

        restaurantVO.setRestaurant_id(resultSet.getLong(indexes.get(0)));
        restaurantVO.setName(resultSet.getString(indexes.get(1)));
        restaurantVO.setAddress(resultSet.getString(indexes.get(2)));
        restaurantVO.setRating(resultSet.getInt(indexes.get(3)));

        return restaurantVO;
    }
}
