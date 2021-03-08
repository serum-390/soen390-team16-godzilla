DELETE FROM goods;
DELETE FROM orders;
DELETE FROM contact;
DELETE FROM inventory;
DELETE FROM erp_user;
DELETE FROM good_type;

-- insert test data into the inventory table
-- Final product
INSERT INTO inventory (id, item_name, good_type, quantity, sell_price, LOCATION, BILL_OF_MATERIAL)
    VALUES (1,'SuperSpeed BICYCLE', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 10, 229.99, 'MONTREAL CONCORDIA', '{"3":1, "4":1, "5":2}');

INSERT INTO inventory (id,item_name, good_type, quantity, sell_price, LOCATION)
    VALUES (2,'SuperSpeed BICYCLE', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 10, 299.99, 'MONTREAL CONCORDIA','{"3":1, "4":1, "5":2}');

-- Semi-final self-product
INSERT INTO inventory (id,item_name, good_type, quantity, LOCATION)
    VALUES (3,'frame speed', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 10, 'MONTREAL CONCORDIA');

--Semi-final buy or self-product
INSERT INTO inventory (id, item_name, good_type, quantity, buy_price, LOCATION)
    VALUES (4,'Speed Control', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 32.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (id, item_name, good_type, quantity, buy_price, LOCATION)
    VALUES (5,'Speed Wheel', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 10, 29.99, 'MONTREAL CONCORDIA');

--Semi-final buy-product = raw material

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Brake', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 356, 29.99, 'MONTREAL CONCORDIA');



--SalesOrder 
INSERT INTO orders(CREATED_DATE, DUE_DATE, DELIVERY_LOCATION, ORDER_TYPE,STATUS, ITEMS) VALUES ('2021-02-15','2021-02-16','montreal','sales', 'new', '{ "300" : 10, "198" : 33}');

--SalesContact
INSERT INTO contact(COMPANY_NAME, CONTACT_NAME, ADDRESS, CONTACT, CONTACT_TYPE) VALUES ('walmart', 'bob', '72 avenue','contact', 'customer');

--VendorContact
INSERT INTO contact(COMPANY_NAME, CONTACT_NAME, ADDRESS, CONTACT, CONTACT_TYPE) VALUES ('walmart', 'bob', '72 avenue','contact', 'vendor');