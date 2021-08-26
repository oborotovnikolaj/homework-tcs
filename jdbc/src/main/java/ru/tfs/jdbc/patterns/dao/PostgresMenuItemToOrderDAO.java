package ru.tfs.jdbc.patterns.dao;

import com.sun.istack.NotNull;
import ru.tfs.jdbc.connection_pool.ApplicationConnectionPool;
import ru.tfs.jdbc.connection_pool.ConnectionPool;
import ru.tfs.jdbc.patterns.OptimisticLockException;
import ru.tfs.jdbc.patterns.model.*;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostgresMenuItemToOrderDAO implements SoftDeletableDao<MenuItemToOrderVO> {

    private static final ConnectionPool connectionPool = ApplicationConnectionPool.getInstance();

    private static final String SELECT_QUERY_BY_ORDER_ID = "select   " +
            "       OM.id, OM.quantity, OM.is_delete,   " +
            "       M.menuitem_id, M.name, M.cost,   " +
            "       R.restaurant_id, R.name, R.address, R.rating,   " +
            "       O.order_id, O.status, O.address, O.creation_time, O.end_time, O.is_delete,   " +
            "       C.client_id, C.name, C.address, C.email, C.password   " +
            "from ORDERS_MENUITEM OM   " +
            "         left join MENUITEM M on M.menuitem_id = OM.menuitem_id   " +
            "         left join orders O on OM.order_id = O.order_id   " +
            "         left join RESTAURANT R on R.restaurant_id = M.restaurant_id   " +
            "         left join CLIENT C on C.client_id = O.client_id   " +
            "where OM.is_delete is null and O.order_id = ?";

    private static final String SELECT_QUERY_BY_MENU_ID = "select   " +
            "       OM.id, OM.quantity, OM.is_delete,   " +
            "       M.menuitem_id, M.name, M.cost,   " +
            "       R.restaurant_id, R.name, R.address, R.rating,   " +
            "       O.order_id, O.status, O.address, O.creation_time, O.end_time, O.is_delete,   " +
            "       C.client_id, C.name, C.address, C.email, C.password   " +
            "from ORDERS_MENUITEM OM   " +
            "         left join MENUITEM M on M.menuitem_id = OM.menuitem_id   " +
            "         left join orders O on OM.order_id = O.order_id   " +
            "         left join RESTAURANT R on R.restaurant_id = M.restaurant_id   " +
            "         left join CLIENT C on C.client_id = O.client_id   " +
            "where OM.is_delete is null and M.menuitem_id = ?";

    private static final String SELECT_QUERY = "select  " +
            "       OM.id, OM.quantity, OM.is_delete,  " +
            "       M.menuitem_id, M.name, M.cost,  " +
            "       R.restaurant_id, R.name, R.address, R.rating,  " +
            "       O.order_id,  O.status, O.address, O.creation_time, O.end_time, O.is_delete," +
            "       C.client_id, C.name, C.address, C.email, C.password  " +
            "from ORDERS_MENUITEM OM  " +
            "    left join MENUITEM M on M.menuitem_id = OM.menuitem_id  " +
            "    left join orders O on OM.order_id = O.order_id  " +
            "    left join RESTAURANT R on R.restaurant_id = M.restaurant_id  " +
            "    left join CLIENT C on C.client_id = O.client_id  " +
            "where OM.id = ? and OM.is_delete is null;";

    private static final String UPDATE_QUERY = "update ORDERS_MENUITEM set order_id = ?, menuitem_id = ?, quantity = ?, is_delete = ? where id = ?;";

    private static final String INSERT_QUERY = "insert into ORDERS_MENUITEM(order_id, menuitem_id, quantity, is_delete) values (?, ?, ?, ?)";

    private static final String SOFT_DELETE_QUERY = "update ORDERS_MENUITEM set is_delete = current_timestamp where id = ?";

    private static final String DELETE_QUERY = "delete FROM ORDERS_MENUITEM where id = ?";

    //Можно сделать с ленивой инициализацие, но не хочется, все равно потом все на Spring переделывать
    public static final PostgresMenuItemToOrderDAO dao = new PostgresMenuItemToOrderDAO();

    private PostgresMenuItemToOrderDAO() {
    }

    @Override
    public MenuItemToOrderVO getById(Long id) {
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
                return createVO(resultSet, IntStream.range(1, 22).boxed().collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MenuItemToOrderVO save(MenuItemToOrderVO menuItemToOrderVO) {
        return save(menuItemToOrderVO, null);
    }

    MenuItemToOrderVO save(MenuItemToOrderVO menuItemToOrderVO, Connection connection) {
        try {
            if (connection != null && connection.isValid(2)) {
                innerSave(menuItemToOrderVO, connection);
            } else {
                Connection newConnection = connectionPool.getConnection();
                newConnection.setAutoCommit(false);
                innerSave(menuItemToOrderVO, newConnection);
                newConnection.commit();
                connectionPool.releaseConnection(newConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItemToOrderVO;
    }

    private void innerSave(MenuItemToOrderVO menuItemToOrderVO, @NotNull Connection connection) throws SQLException {
        try (
                PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY);
                PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY);
        ) {

            PostgresOrdersDAO.dao.save(menuItemToOrderVO.getOrder(), connection);
            PostgresMenuItemDAO.dao.save(menuItemToOrderVO.getMenuItem(), connection);

            if (menuItemToOrderVO.getId() != null) {
                updateStatement.setLong(1, menuItemToOrderVO.getOrder().getOrderId());
                updateStatement.setLong(2, menuItemToOrderVO.getMenuItem().getMenuitemId());
                updateStatement.setInt(3, menuItemToOrderVO.getQuantity());
                updateStatement.setTimestamp(4, menuItemToOrderVO.getIsDelete() == null
                        ? null : new Timestamp(menuItemToOrderVO.getIsDelete().getTime()));
                updateStatement.setLong(5, menuItemToOrderVO.getId());
                int count = updateStatement.executeUpdate();
                if (count == 0) {
                    connection.rollback();
                    connectionPool.releaseConnection(connection);
                    throw new OptimisticLockException("Ошибка при обновлении записи с id ", menuItemToOrderVO.getId());
                }
            } else {
                insertStatement.setLong(1, menuItemToOrderVO.getOrder().getOrderId());
                insertStatement.setLong(2, menuItemToOrderVO.getMenuItem().getMenuitemId());
                insertStatement.setInt(3, menuItemToOrderVO.getQuantity());
                insertStatement.setTimestamp(4, menuItemToOrderVO.getIsDelete() == null
                        ? null : new Timestamp(menuItemToOrderVO.getIsDelete().getTime()));
                insertStatement.executeUpdate();

                PreparedStatement statement = connection.prepareStatement("select currval('orders_menuitem_id_seq');");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                Long id = resultSet.getLong(1);
                menuItemToOrderVO.setId(id);
            }
        }
    }

    @Override
    public void delete(MenuItemToOrderVO menuItemToOrderVO) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY);
            ) {

                deleteStatement.setLong(1, menuItemToOrderVO.getId());
                deleteStatement.executeUpdate();
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void softDelete(MenuItemToOrderVO menuItemToOrderVO) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement deleteStatement = connection.prepareStatement(SOFT_DELETE_QUERY);
            ) {
                deleteStatement.setLong(1, menuItemToOrderVO.getId());
                deleteStatement.executeUpdate();
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MenuItemToOrderVO createVO(ResultSet resultSet, List<Integer> indexes) throws SQLException {
        MenuItemToOrderVO menuItemToOrder = new MenuItemToOrderVO();

        menuItemToOrder.setId(resultSet.getLong(indexes.get(0)));
        menuItemToOrder.setQuantity(resultSet.getInt(indexes.get(1)));
        menuItemToOrder.setIsDelete(resultSet.getTimestamp(indexes.get(2)));

        MenuItemVO menuItemVO = PostgresMenuItemDAO.createVO(resultSet, indexes.subList(3, 10));
        menuItemToOrder.setMenuItem(menuItemVO);

        OrdersVO ordersVO = PostgresOrdersDAO.createVO(resultSet, indexes.subList(10, 21));
        menuItemToOrder.setOrder(ordersVO);

        return menuItemToOrder;
    }

    public Set<MenuItemToOrderVO> getByOrderId(Long id) {
        try {
            Connection connection = connectionPool.getConnection();
            try (
                    PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_BY_ORDER_ID);
            ) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                connectionPool.releaseConnection(connection);

                Set<MenuItemToOrderVO> result = new HashSet<>();
                while (resultSet.next()) {
                    result.add(createVO(resultSet, IntStream.range(1, 22).boxed().collect(Collectors.toList())));
                }
                connectionPool.releaseConnection(connection);
                return result;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
