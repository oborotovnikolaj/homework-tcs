--liquibase formatted sql
--changeset author: oborotov-ns-20210321-1 endDelimiter:;
INSERT INTO public.client (id, created_date, last_modified_date, phone, region)
VALUES (1, '2021-05-15 21:06:26.599000', '2021-05-15 21:06:26.599000', '+7 999 999 99 99', 'deep deep');

INSERT INTO public.currency (id, created_date, last_modified_date, multiplier, name)
VALUES (2, '2021-05-16 15:25:23.150000', '2021-05-16 15:25:23.150000', 100.00, 'euro');
INSERT INTO public.currency (id, created_date, last_modified_date, multiplier, name)
VALUES (1, '2021-05-16 15:25:18.737000', '2021-05-16 15:25:37.631000', 10.00, 'rubles');

INSERT INTO public.language (id, created_date, last_modified_date, name)
VALUES (2, '2021-05-16 15:25:14.481000', '2021-05-16 15:25:14.481000', 'English');
INSERT INTO public.language (id, created_date, last_modified_date, name)
VALUES (1, '2021-05-16 15:25:06.697000', '2021-05-16 15:25:32.156000', 'Geramn');

INSERT INTO public.parameter_type (id, created_date, last_modified_date, name)
VALUES (1, '2021-05-16 15:25:49.469000', '2021-05-16 15:25:54.126000', 'hight');

INSERT INTO public.product (id, created_date, last_modified_date, price)
VALUES (1, '2021-05-16 15:25:59.933000', '2021-05-16 15:26:05.865000', 22.00);

INSERT INTO public.parameter (id, created_date, last_modified_date, value, parametertype_id, product_id)
VALUES (1, '2021-05-16 15:25:59.993000', '2021-05-16 15:26:05.865000', '1000 kg', 1, 1);

INSERT INTO public.info (id, created_date, last_modified_date, description, tittle, language_id, product_id)
VALUES (2, '2021-05-16 15:26:05.859000', '2021-05-16 15:26:05.859000', 'очень плохо', 'стол', 2, 1);
INSERT INTO public.info (id, created_date, last_modified_date, description, tittle, language_id, product_id)
VALUES (1, '2021-05-16 15:25:59.961000', '2021-05-16 15:26:05.865000', 'very very boring', 'table', 1, 1);

INSERT INTO public.orders (id, created_date, last_modified_date, version, client_id)
VALUES (1, '2021-05-16 15:26:29.150000', '2021-05-16 15:26:29.150000', 0, 1);

INSERT INTO public.revinfo (rev, revtstmp)
VALUES (1, 1621178789361);

INSERT INTO public.orders_aud (id, rev, revtype, client_id)
VALUES (1, 1, 0, 1);

INSERT INTO public.order_item (id, created_date, last_modified_date, quantity, version, orders_id, product_id)
VALUES (1, '2021-05-16 15:26:29.178000', '2021-05-16 15:26:29.178000', 10, 0, 1, 1);

INSERT INTO public.order_item_aud (id, rev, revtype, quantity, orders_id, product_id)
VALUES (1, 1, 0, 10, 1, 1);

INSERT INTO public.payment (id, created_date, last_modified_date, cardpan, value, version, orders_id)
VALUES (1, '2021-05-16 15:26:29.227000', '2021-05-16 15:26:29.227000', 'cardPan', 100.00, 0, 1);

INSERT INTO public.payment_aud (id, rev, revtype, cardpan, value, orders_id)
VALUES (1, 1, 0, 'cardPan', 100.00, 1);

INSERT INTO public.shipment (id, created_date, last_modified_date, address, version, orders_id)
VALUES (1, '2021-05-16 15:26:29.201000', '2021-05-16 15:26:29.201000', 'Moscow, Kremlin', 0, 1);

INSERT INTO public.shipment_aud (id, rev, revtype, address, orders_id)
VALUES (1, 1, 0, 'Moscow, Kremlin', 1);

commit;