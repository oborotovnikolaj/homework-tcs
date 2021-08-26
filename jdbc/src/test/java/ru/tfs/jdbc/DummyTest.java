package ru.tfs.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.tfs.jdbc.patterns.OptimisticLockException;
import ru.tfs.jdbc.patterns.dao.*;
import ru.tfs.jdbc.patterns.model.*;

import java.util.Date;

@SpringJUnitConfig(locations = "/test_application_context.xml")
class DummyTest {

    @Test
    public void test() {

        ClientVO plankton = new ClientVO();
        plankton.setName("Plankton");
        plankton.setAddress("Bikini Bottom, Ocean 6");
        plankton.setEmail("plankton@bikinibottom.com");
        plankton.setPassword("sexy_kissa_1996");

        ClientVO spongeBob = new ClientVO();
        spongeBob.setName("SpongeBob SquarePants");
        spongeBob.setAddress("Bikini Bottom, Conch Street 6, Pineapple");
        spongeBob.setEmail("spongebob@bikinibottom.com");
        spongeBob.setPassword("admin1234");

        plankton = PostgresClientDAO.dao.save(plankton);
        spongeBob = PostgresClientDAO.dao.save(spongeBob);

        Assertions.assertEquals(plankton, PostgresClientDAO.dao.getById(plankton.getClientId()));
        Assertions.assertEquals(spongeBob, PostgresClientDAO.dao.getById(spongeBob.getClientId()));

        RestaurantVO krustyKrab = new RestaurantVO();
        krustyKrab.setName("Krusty Krab");
        krustyKrab.setAddress("Bikini Bottom, Leninskaya street, 7");
        krustyKrab.setRating(Integer.MAX_VALUE);

        RestaurantVO bar100Rengen = new RestaurantVO();
        bar100Rengen.setName("100 ренген");
        bar100Rengen.setAddress("Чернобыль, база Долга");
        bar100Rengen.setRating(10);

        krustyKrab = PostgresRestaurantDAO.dao.save(krustyKrab);
        bar100Rengen = PostgresRestaurantDAO.dao.save(bar100Rengen);

        Assertions.assertEquals(krustyKrab, PostgresRestaurantDAO.dao.getById(krustyKrab.getRestaurantId()));
        Assertions.assertEquals(bar100Rengen, PostgresRestaurantDAO.dao.getById(bar100Rengen.getRestaurantId()));

        MenuItemVO krabsburger = new MenuItemVO();
        krabsburger.setName("Krabsburger");
        krabsburger.setRestaurant(krustyKrab);
        krabsburger.setCost(1000);

        MenuItemVO krabsShawarma = new MenuItemVO();
        krabsShawarma.setName("Krabs Shawarma");
        krabsShawarma.setRestaurant(krustyKrab);
        krabsShawarma.setCost(350);

        MenuItemVO stew = new MenuItemVO();
        stew.setName("Тушенка");
        stew.setRestaurant(bar100Rengen);
        stew.setCost(2000);

        krabsburger = PostgresMenuItemDAO.dao.save(krabsburger);
        krabsShawarma = PostgresMenuItemDAO.dao.save(krabsShawarma);
        stew = PostgresMenuItemDAO.dao.save(stew);

        Assertions.assertEquals(krabsburger, PostgresMenuItemDAO.dao.getById(krabsburger.getMenuitemId()));
        Assertions.assertEquals(krabsShawarma, PostgresMenuItemDAO.dao.getById(krabsShawarma.getMenuitemId()));
        Assertions.assertEquals(stew, PostgresMenuItemDAO.dao.getById(stew.getMenuitemId()));

        OrdersVO planktonOrder = new OrdersVO();
        planktonOrder.setClient(plankton);
        planktonOrder.setStatus("REGISTERED");
        planktonOrder.setCreationTime(new Date(100));
        planktonOrder.setAddress("Bikini Bottom, NOT PLANKTON ADDRESS STREET, 7");

        OrdersVO spongeBobOrder = new OrdersVO();
        spongeBobOrder.setClient(spongeBob);
        spongeBobOrder.setStatus("REGISTERED");
        spongeBobOrder.setCreationTime(new Date(100));
        spongeBobOrder.setAddress("Bikini Bottom, SPONGE BOB ADDRESS, 7");

        planktonOrder = PostgresOrdersDAO.dao.save(planktonOrder);
        spongeBobOrder = PostgresOrdersDAO.dao.save(spongeBobOrder);

        Assertions.assertEquals(planktonOrder, PostgresOrdersDAO.dao.getById(planktonOrder.getOrderId()));
        Assertions.assertEquals(spongeBobOrder, PostgresOrdersDAO.dao.getById(spongeBobOrder.getOrderId()));

        MenuItemToOrderVO stewForSpongeBob = new MenuItemToOrderVO();
        stewForSpongeBob.setOrder(spongeBobOrder);
        stewForSpongeBob.setMenuItem(stew);
        stewForSpongeBob.setQuantity(50);

        MenuItemToOrderVO shawarmaForSpongeBob = new MenuItemToOrderVO();
        shawarmaForSpongeBob.setOrder(spongeBobOrder);
        shawarmaForSpongeBob.setMenuItem(krabsShawarma);
        shawarmaForSpongeBob.setQuantity(100);

        MenuItemToOrderVO krabsburgerForPlankton = new MenuItemToOrderVO();
        krabsburgerForPlankton.setOrder(planktonOrder);
        krabsburgerForPlankton.setMenuItem(krabsburger);
        krabsburgerForPlankton.setQuantity(Integer.MAX_VALUE);

        PostgresMenuItemToOrderDAO.dao.save(stewForSpongeBob);
        PostgresMenuItemToOrderDAO.dao.save(shawarmaForSpongeBob);
        PostgresMenuItemToOrderDAO.dao.save(krabsburgerForPlankton);

        Assertions.assertEquals(stewForSpongeBob, PostgresMenuItemToOrderDAO.dao.getById(stewForSpongeBob.getId()));
        Assertions.assertEquals(shawarmaForSpongeBob, PostgresMenuItemToOrderDAO.dao.getById(shawarmaForSpongeBob.getId()));
        Assertions.assertEquals(krabsburgerForPlankton, PostgresMenuItemToOrderDAO.dao.getById(krabsburgerForPlankton.getId()));

        Assertions.assertEquals(2, PostgresMenuItemToOrderDAO.dao.getByOrderId(spongeBobOrder.getOrderId()).size());
        Assertions.assertEquals(1, PostgresMenuItemToOrderDAO.dao.getByOrderId(planktonOrder.getOrderId()).size());

        PostgresMenuItemToOrderDAO.dao.softDelete(shawarmaForSpongeBob);
        PostgresMenuItemToOrderDAO.dao.softDelete(stewForSpongeBob);
        PostgresMenuItemToOrderDAO.dao.softDelete(krabsburgerForPlankton);

        PostgresOrdersDAO.dao.softDelete(spongeBobOrder);
        PostgresOrdersDAO.dao.softDelete(planktonOrder);

        Assertions.assertEquals(0, PostgresMenuItemToOrderDAO.dao.getByOrderId(spongeBobOrder.getOrderId()).size());
        Assertions.assertEquals(0, PostgresMenuItemToOrderDAO.dao.getByOrderId(planktonOrder.getOrderId()).size());

        PostgresMenuItemToOrderDAO.dao.delete(shawarmaForSpongeBob);
        PostgresMenuItemToOrderDAO.dao.delete(stewForSpongeBob);
        PostgresMenuItemToOrderDAO.dao.delete(krabsburgerForPlankton);

        PostgresOrdersDAO.dao.delete(spongeBobOrder);
        PostgresOrdersDAO.dao.delete(planktonOrder);

        PostgresMenuItemDAO.dao.delete(krabsburger);
        PostgresMenuItemDAO.dao.delete(krabsShawarma);
        PostgresMenuItemDAO.dao.delete(stew);

        PostgresClientDAO.dao.delete(plankton);
        PostgresClientDAO.dao.delete(spongeBob);

        PostgresRestaurantDAO.dao.delete(krustyKrab);
        PostgresRestaurantDAO.dao.delete(bar100Rengen);

        Assertions.assertNull(PostgresMenuItemToOrderDAO.dao.getById(shawarmaForSpongeBob.getId()));
        Assertions.assertNull(PostgresMenuItemToOrderDAO.dao.getById(stewForSpongeBob.getId()));
        Assertions.assertNull(PostgresMenuItemToOrderDAO.dao.getById(krabsburgerForPlankton.getId()));
        Assertions.assertNull(PostgresOrdersDAO.dao.getById(spongeBobOrder.getOrderId()));
        Assertions.assertNull(PostgresOrdersDAO.dao.getById(planktonOrder.getOrderId()));
        Assertions.assertNull(PostgresMenuItemDAO.dao.getById(krabsburger.getMenuitemId()));
        Assertions.assertNull(PostgresMenuItemDAO.dao.getById(krabsShawarma.getMenuitemId()));
        Assertions.assertNull(PostgresMenuItemDAO.dao.getById(stew.getMenuitemId()));
        Assertions.assertNull(PostgresClientDAO.dao.getById(plankton.getClientId()));
        Assertions.assertNull(PostgresClientDAO.dao.getById(spongeBob.getClientId()));
        Assertions.assertNull(PostgresRestaurantDAO.dao.getById(krustyKrab.getRestaurantId()));
        Assertions.assertNull(PostgresRestaurantDAO.dao.getById(bar100Rengen.getRestaurantId()));

        //тест на атомарность записи, при записи неправильной сущности не появляются безхозные сущности в бд
        shawarmaForSpongeBob.setId(null);
        shawarmaForSpongeBob.getOrder().setOrder_id(null);
        shawarmaForSpongeBob.getOrder().getClient().setClient_id(null);
        shawarmaForSpongeBob.getMenuItem().setMenuitem_id(null);

        Assertions.assertThrows(
                OptimisticLockException.class,
                () -> PostgresMenuItemToOrderDAO.dao.save(shawarmaForSpongeBob)
        );
        //Проверяем, что ордер не сохранился хотя перед ошибкой был уже в базе, но не закоммичен
        Assertions.assertNull(PostgresOrdersDAO.dao.getById(shawarmaForSpongeBob.getOrder().getOrderId()));

        System.out.println("Тесты прошли успешно");

    }


}