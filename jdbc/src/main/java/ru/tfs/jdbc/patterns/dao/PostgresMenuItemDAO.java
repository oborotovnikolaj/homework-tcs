package ru.tfs.jdbc.patterns.dao;

import ru.tfs.jdbc.connection_pool.ApplicationConnectionPool;
import ru.tfs.jdbc.connection_pool.ConnectionPool;
import ru.tfs.jdbc.patterns.OptimisticLockException;
import ru.tfs.jdbc.patterns.model.MenuItemToOrderVO;
import ru.tfs.jdbc.patterns.model.MenuItemVO;
import ru.tfs.jdbc.patterns.model.RestaurantVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostgresMenuItemDAO implements VoDao<MenuItemVO> {

    private static final ConnectionPool connectionPool = ApplicationConnectionPool.getInstance();

    private static final String SELECT_QUERY = "select M.menuitem_id, m.name, m.cost, R.restaurant_id, R.name, R.address, R.rating " +
            "from MENUITEM M " +
            "left join RESTAURANT R on M.restaurant_id = R.restaurant_id " +
            "where menuitem_id = ?";

    private static final String UPDATE_QUERY = "update MENUITEM SET name = ?, cost = ?, restaurant_id = ? where menuitem_id = ?";

    private static final String INSERT_QUERY = "insert into MENUITEM( name, cost, restaurant_id ) values ( ?, ?, ?)";

    private static final String DELETE_QUERY = "delete from MENUITEM where menuitem_id = ?";

    //Можно сделать с ленивой инициализацие, но не хочется, все равно потом все на Spring переделывать
    public static final PostgresMenuItemDAO dao = new PostgresMenuItemDAO();

    private PostgresMenuItemDAO() {
    }

    ;

    @Override
    public MenuItemVO getById(Long id) {
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

                return createVO(resultSet, IntStream.range(1, 9).boxed().collect(Collectors.toList()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MenuItemVO save(MenuItemVO menuItem) {
        return save(menuItem, null);
    }

    MenuItemVO save(MenuItemVO menuItemVO, Connection connection) {
        try {
            if (connection != null && connection.isValid(2)) {
                innerSave(menuItemVO, connection);
            } else {
                Connection newConnection = connectionPool.getConnection();
                newConnection.setAutoCommit(false);
                innerSave(menuItemVO, newConnection);
                newConnection.commit();
                connectionPool.releaseConnection(newConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItemVO;
    }

    private void innerSave(MenuItemVO menuItem, Connection connection) throws SQLException {

        try (
                PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY);
                PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY);
        ) {

            PostgresRestaurantDAO.dao.save(menuItem.getRestaurant(), connection);
            if (menuItem.getMenuitemId() != null) {
                updateStatement.setString(1, menuItem.getName());
                updateStatement.setInt(2, menuItem.getCost());
                updateStatement.setLong(3, menuItem.getRestaurant().getRestaurantId());
                updateStatement.setLong(4, menuItem.getMenuitemId());
                int count = updateStatement.executeUpdate();
                if (count == 0) {
                    connection.rollback();
                    connectionPool.releaseConnection(connection);
                    throw new OptimisticLockException("Ошибка при обновлении записи с id ", menuItem.getMenuitemId());
                }
            } else {
                insertStatement.setString(1, menuItem.getName());
                insertStatement.setInt(2, menuItem.getCost());
                insertStatement.setLong(3, menuItem.getRestaurant().getRestaurantId());
                insertStatement.executeUpdate();

                PreparedStatement statement = connection.prepareStatement("select currval('menuitem_menuitem_id_seq')");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                Long id = resultSet.getLong(1);
                menuItem.setMenuitem_id(id);
            }
        }
    }

    @Override
    public void delete(MenuItemVO menuItem) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY);
            ) {

                deleteStatement.setLong(1, menuItem.getMenuitemId());
                deleteStatement.executeUpdate();
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MenuItemVO createVO(ResultSet resultSet, List<Integer> indexes) throws SQLException {

        MenuItemVO menuItem = new MenuItemVO();
        menuItem.setMenuitem_id(resultSet.getLong(indexes.get(0)));
        menuItem.setName(resultSet.getString(indexes.get(1)));
        menuItem.setCost(resultSet.getInt(indexes.get(2)));

        RestaurantVO restaurant = PostgresRestaurantDAO.createVO(resultSet, indexes.subList(3, 7));
        menuItem.setRestaurant(restaurant);

        return menuItem;
    }
}
