    --liquibase formatted sql

--changeset author: oborotov-ns-20210321-1 endDelimiter:;
create sequence if not exists restaurant_id_seq;

--changeset author: oborotov-ns-20210321-2
create TABLE RESTAURANT
(
    restaurant_id int primary key DEFAULT nextval('restaurant_id_seq'),
    name          varchar(512),
    address       varchar(512),
    rating        int
);

--changeset author: oborotov-ns-20210321-3
ALTER sequence restaurant_id_seq owned by RESTAURANT.restaurant_id;

--changeset author: oborotov-ns-20210321-4
create TABLE MENUITEM
(
    menuitem_id   SERIAL primary key,
    restaurant_id int,
    foreign key (restaurant_id) REFERENCES RESTAURANT (restaurant_id),
    name          varchar(512),
    cost          int
);

--changeset author: oborotov-ns-20210321-5
create TABLE CLIENT
(
    client_id      SERIAL primary key,
    name           varchar(512),
    address        varchar(512),
    email          varchar(512),
    password       varchar(512)
);

--changeset author: oborotov-ns-20210321-6
create TABLE ORDERS
(
    order_id      SERIAL primary key,
    client_id     int,
    foreign key (client_id) REFERENCES CLIENT (client_id),
    status        varchar(512),
    address       varchar(512),
    creation_time timestamp,
    end_time      timestamp,
    is_delete     timestamp
);

--changeset author: oborotov-ns-20210321-7
create TABLE ORDERS_MENUITEM
(
    id            SERIAL primary key,
    order_id      int,
    foreign key (order_id) REFERENCES ORDERS (order_id),
    menuitem_id int,
    foreign key (menuitem_id) REFERENCES MENUITEM (menuitem_id),
    quantity      int,
    is_delete     timestamp
);
