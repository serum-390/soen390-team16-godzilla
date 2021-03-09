DELETE FROM goods;
DELETE FROM orders;
DELETE FROM contact;
DELETE FROM inventory;
DELETE FROM erp_user;
DELETE FROM good_type;

INSERT INTO goods (name, description)
    VALUES ('SuperSpeed Bicycle', 'A really fast bike, fun for the whole family');

--insert data for good_type
INSERT INTO good_type (id,type, description)
    VALUES (1,'fnmt', 'final product')
    ON CONFLICT (type) DO NOTHING;

INSERT INTO good_type (id,type, description)
    VALUES (2,'smsp', 'semi-final self-product')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id,type, description)
    VALUES (3,'smbs', 'semi-final buy or self-product')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id,type, description)
    VALUES (4,'smbp', 'semi-final buy-product')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id,type, description)
    VALUES (5,'rawm', 'raw material')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (id,type, description)
    VALUES (6,'accp', 'accessories')
    ON CONFLICT(type) DO NOTHING;

-- insert test data into the inventory table
-- Final product
INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION)
    VALUES ('SuperSpeed BICYCLE 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 31, 229.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION)
    VALUES ('SuperSpeed BICYCLE 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 10, 299.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION)
    VALUES ('SuperSkill BICYCLE 1',
            (SELECT id FROM good_type WHERE type = 'fnmt' LIMIT 1),
            26, 199.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION)
    VALUES ('SuperSkill BICYCLE 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 82, 249.99, 'MONTREAL CONCORDIA');

-- Semi-final self-product
INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame speed 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 89, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame speed 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 102, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame skill 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 94, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('frame skill 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 66, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('Speed Transmission 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 123, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('Speed Transmission 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 245, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('Skill Transmission 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 83, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, LOCATION)
    VALUES ('Skill Transmission 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 96, 'MONTREAL CONCORDIA');

--Semi-final buy or self-product
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Control 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 64, 32.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Control 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 82, 39.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Control 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 72, 29.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Control 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 33, 35.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Wheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 200, 19.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 256, 29.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Cheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 188, 24.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 300, 22.99, 'MONTREAL CONCORDIA');

--Semi-final buy-product = raw material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Brake 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 402, 32.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Brake 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 288, 39.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Brake 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 356, 29.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Brake 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp' LIMIT 1 ), 350, 35.99, 'MONTREAL CONCORDIA');

--5 material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed frame base 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed frame base 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill frame base 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill frame base 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed fork 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed fork 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill fork 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill fork 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Steams 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Steams 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Steams 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Steams 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Head 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Head 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Head 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Head 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Crank 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Crank 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Crank 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Crank 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Chain Wheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Chain Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Chain Wheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Chain Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Pedal 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Pedal 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Pedal 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Pedal 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Chain 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Chain 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Chain 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Chain 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Freewheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Freewheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Freewheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Freewheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Derailleur 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Derailleur 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Derailleur 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Derailleur 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Handlebar 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Handlebar 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Handlebar 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Handlebar 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Shifter 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Shifter 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Shifter 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Shifter 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Rim 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Rim 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Rim 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Skill Rim 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION)
    VALUES ('Speed Tire 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA'), ('Speed Tire 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA'), ('Skill Tire 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA'), ('Skill Tire 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA');

--accessories
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, LOCATION)
    VALUES ('Reflector', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA'), ('Rearview Mirror', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA'), ('Head Light', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA'), ('Bell', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA'), ('Carrier', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA'), ('Basket', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA'), ('Kickstand', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA');

--SalesOrder
INSERT INTO orders(CREATED_DATE, DUE_DATE, DELIVERY_LOCATION, ORDER_TYPE,STATUS, ITEMS) VALUES ('2021-02-15','2021-02-16','montreal','sales', 'new', '{ "300" : 10, "198" : 33}');

--SalesContact
INSERT INTO contact(COMPANY_NAME, CONTACT_NAME, ADDRESS, CONTACT, CONTACT_TYPE) VALUES ('walmart', 'bob', '72 avenue','contact', 'customer');

--VendorContact
INSERT INTO contact(COMPANY_NAME, CONTACT_NAME, ADDRESS, CONTACT, CONTACT_TYPE) VALUES ('walmart', 'bob', '72 avenue','contact', 'vendor');