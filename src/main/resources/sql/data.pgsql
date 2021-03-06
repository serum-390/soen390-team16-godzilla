DELETE FROM packaged_products;
DELETE FROM planned_products;
DELETE FROM shippings;
DELETE FROM orders;
DELETE FROM contact;
DELETE FROM inventory;
DELETE FROM erp_user;
DELETE FROM good_type;

SELECT setval(pg_get_serial_sequence('inventory', 'id'), 1, true);

--insert data for good_type
INSERT INTO good_type (id,type, description)
VALUES (1,'fnmt', 'final product')
ON CONFLICT (type) DO NOTHING;

INSERT INTO good_type (id, type, description)
VALUES (2,'smsp', 'semi-final self-product')
ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id, type, description)
VALUES (3,'smbs', 'semi-final buy or self-product')
ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id, type, description)
VALUES (4,'smbp', 'semi-final buy-product')
ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id, type, description)
VALUES (5,'rawm', 'raw material')
ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id,type, description)
    VALUES (6,'accp', 'accessories')
    ON CONFLICT(type) DO NOTHING;

INSERT INTO inventory ( item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES ('SuperSpeed BICYCLE 1 A', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 1, 299.99, 'MONTREAL CONCORDIA', '{"8":1, "11":1, "14":2, "17":1}');

INSERT INTO inventory ( item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES ('SuperSpeed BICYCLE 1 B', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 1, 249.99, 'MONTREAL CONCORDIA', '{"9":1, "12":1, "15":2, "18":1}');

INSERT INTO inventory ( item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES ('SuperSpeed BICYCLE 1 C', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 1, 229.99, 'MONTREAL CONCORDIA', '{"10":1, "13":1, "16":2, "19":1}');

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES ('SuperSpeed BICYCLE 2 A ', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 1, 299.99, 'MONTREAL CONCORDIA','{"8":1, "11":1, "14":2, "17":1}');

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES ('SuperSpeed BICYCLE 2 B', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 1, 249.99, 'MONTREAL CONCORDIA','{"9":1, "12":1, "15":2, "18":1}');

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES ('SuperSpeed BICYCLE 2 C', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 1, 229.99, 'MONTREAL CONCORDIA','{"10":1, "13":1, "16":2, "19":1}');

-- Semi-final self-product
INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame speed A', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 10, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame speed B', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 10, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame speed C', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 10, 'MONTREAL CONCORDIA');


--Semi-final buy or self-product
INSERT INTO inventory ( item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Control A', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 34.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory ( item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Control B', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 32.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory ( item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Control C', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 28.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory ( item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Wheel A', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 29.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory ( item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Wheel B', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 24.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory ( item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Wheel C', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 19.99, 'MONTREAL CONCORDIA');

--Semi-final buy-product = raw material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Brake A', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 10, 29.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Brake B', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 10, 29.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Brake C', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 10, 29.99, 'MONTREAL CONCORDIA');


--SalesOrder
INSERT INTO orders(CREATED_DATE, DUE_DATE, DELIVERY_LOCATION, ORDER_TYPE,STATUS, ITEMS) VALUES ('2021-02-15','2021-10-16','montreal','sale', 'packaged', '{ "2" : 7}');
INSERT INTO orders(CREATED_DATE, DUE_DATE, DELIVERY_LOCATION, ORDER_TYPE,STATUS, ITEMS) VALUES ('2021-02-15','2021-10-16','montreal','sale', 'new', '{ "2" : 15, "3" : 15}');
INSERT INTO orders(CREATED_DATE, DUE_DATE, DELIVERY_LOCATION, ORDER_TYPE,STATUS, ITEMS) VALUES ('2021-02-15','2021-10-16','montreal','sale', 'new', '{ "2" : 4, "3" : 2}');
--SalesContact
INSERT INTO contact(COMPANY_NAME, CONTACT_NAME, ADDRESS, CONTACT, CONTACT_TYPE)
VALUES ('walmart', 'bob', '72 avenue','contact', 'customer');

--VendorContact
INSERT INTO contact(COMPANY_NAME, CONTACT_NAME, ADDRESS, CONTACT, CONTACT_TYPE) VALUES ('walmart', 'bob', '72 avenue','contact', 'vendor');

--Packaging
INSERT INTO packaged_products(LENGTH, WIDTH, HEIGHT, WEIGHT, PACKAGE_TYPE, PACKAGE_DATE) VALUES (10, 11, 12, 14.2, 'small box','2021-02-15');
