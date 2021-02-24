DELETE FROM goods;
DELETE FROM inventory;
DELETE FROM bill_of_material;
DELETE FROM erp_user;
DELETE FROM good_type;

INSERT INTO goods (name, description)
    VALUES ('SuperSpeed Bicycle', 'A really fast bike, fun for the whole family');

--insert data for good_type
INSERT INTO good_type (type, description)
    VALUES ('fnmt', 'final product')
    ON CONFLICT (type) DO NOTHING;

INSERT INTO good_type (type, description)
    VALUES ('smsp', 'semi-final self-product')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (type, description)
    VALUES ('smbs', 'semi-final buy or self-product')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (type, description)
    VALUES ('smbp', 'semi-final buy-product')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (type, description)
    VALUES ('rawm', 'raw material')
    ON CONFLICT (type) DO NOTHING;


INSERT INTO good_type (type, description)
    VALUES ('accp', 'accessories')
    ON CONFLICT(type) DO NOTHING;

-- insert test data into the inventory table
-- Final product
INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, bill_of_material)
    VALUES ('SuperSpeed BICYCLE 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 31, 229.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, bill_of_material)
    VALUES ('SuperSpeed BICYCLE 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 10, 299.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, bill_of_material)
    VALUES ('SuperSkill BICYCLE 1',
            (SELECT id FROM good_type WHERE type = 'fnmt' LIMIT 1),
            26, 199.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, sell_price, LOCATION, bill_of_material)
    VALUES ('SuperSkill BICYCLE 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'fnmt'
            LIMIT 1), 82, 249.99, 'MONTREAL CONCORDIA', TRUE);

-- Semi-final self-product
INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('frame speed 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 89, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('frame speed 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 102, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('frame skill 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 94, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('frame skill 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 66, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('Speed Transmission 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 123, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('Speed Transmission 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 245, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('Skill Transmission 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 83, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, LOCATION, bill_of_material)
    VALUES ('Skill Transmission 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smsp'
            LIMIT 1), 96, 'MONTREAL CONCORDIA', TRUE);

--Semi-final buy or self-product
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Control 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 64, 32.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Control 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 82, 39.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Control 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 72, 29.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Control 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 33, 35.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Wheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 200, 19.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 256, 29.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Cheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 188, 24.99, 'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbs'
            LIMIT 1), 300, 22.99, 'MONTREAL CONCORDIA', TRUE);

--Semi-final buy-product = raw material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Brake 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 402, 32.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Brake 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 288, 39.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Brake 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp'
            LIMIT 1), 356, 29.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Brake 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'smbp' LIMIT 1 ), 350, 35.99, 'MONTREAL CONCORDIA', FALSE);

--5 material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed frame base 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed frame base 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill frame base 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill frame base 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed fork 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed fork 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill fork 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill fork 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Steams 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Steams 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Steams 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Steams 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Head 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Head 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Head 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Head 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Crank 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Crank 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Crank 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Crank 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Chain Wheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Chain Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Chain Wheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Chain Wheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Pedal 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Pedal 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Pedal 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Pedal 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Chain 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Chain 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Chain 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Chain 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Freewheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Freewheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Freewheel 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Freewheel 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Derailleur 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Derailleur 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Derailleur 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Derailleur 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Handlebar 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Handlebar 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Handlebar 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Handlebar 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Shifter 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Shifter 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Shifter 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Shifter 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Rim 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Rim 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Rim 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Skill Rim 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, LOCATION, bill_of_material)
    VALUES ('Speed Tire 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 251, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Speed Tire 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 192, 6.99, 'MONTREAL CONCORDIA', FALSE), ('Skill Tire 1', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 285, 4.99, 'MONTREAL CONCORDIA', FALSE), ('Skill Tire 2', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'rawm' LIMIT 1 ), 382, 7.99, 'MONTREAL CONCORDIA', FALSE);

--accessories
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, LOCATION, bill_of_material)
    VALUES ('Reflector', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Rearview Mirror', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Head Light', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Bell', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Carrier', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Basket', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE), ('Kickstand', (
            SELECT
                id
            FROM
                good_type
            WHERE
                type = 'accp'), 251, 5.99, 5.99, 'MONTREAL CONCORDIA', FALSE);

--BILL_OF_MATERIAL
INSERT INTO bill_of_material (item_name, item_needed, quantity) VALUES
    (1,5,1), (1,9,1), (1,13,1), (1,17,2), (2,6,1),
    (2,10,1), (2,14,1), (2,18,2), (3,7,1), (3,11,1),
    (3,15,1), (3,19,2), (4,8,1), (4,12,1), (4,16,1),
    (4,20,2), (5,25,1), (5,29,2), (5,33,2), (5,37,7),
    (6,26,1), (6,30,2), (6,34,2), (6,38,7), (7,27,1),
    (7,31,2), (7,35,2), (7,39,7), (8,28,1), (8,32,2),
    (8,36,2), (8,40,7);
