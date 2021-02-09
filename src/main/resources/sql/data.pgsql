DELETE FROM goods;
INSERT INTO  goods (name, description) VALUES ('SuperSpeed Bicycle', 'A really fast bike, fun for the whole family');

--insert data for good_type
INSERT INTO good_type (type,DESCIRPTION) VALUES ('fnmt','final product');
INSERT INTO good_type (type,DESCIRPTION) VALUES ('smsp','semi-final self-product');
INSERT INTO good_type (type,DESCIRPTION) VALUES ('smbs','semi-final buy or self-product');
INSERT INTO good_type (type,DESCIRPTION) VALUES ('smbp','semi-final buy-product');
INSERT INTO good_type (type,DESCIRPTION) VALUES ('rawm','raw material');
INSERT INTO good_type (type,DESCIRPTION) VALUES ('accp','accessories');

-- insert test data into the inventory table
-- Final product
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSpeed BICYCLE 1',(SELECT id from good_type WHERE type = 'fnmt'),31,229.99,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSpeed BICYCLE 2',(SELECT id from good_type WHERE type = 'fnmt'),10,299.99,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSkill BICYCLE 1',(SELECT id from good_type WHERE type = 'fnmt'),26,199.99,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSkill BICYCLE 2',(SELECT id from good_type WHERE type = 'fnmt'),82,249.99,'MONTREAL CONCORDIA',TRUE);

-- Semi-final self-product
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame speed 1', (SELECT id from good_type WHERE type = 'smsp'),89,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame speed 2', (SELECT id from good_type WHERE type = 'smsp'),102,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame skill 1', (SELECT id from good_type WHERE type = 'smsp'),94,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame skill 2', (SELECT id from good_type WHERE type = 'smsp'),66,'MONTREAL CONCORDIA',TRUE);

INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Speed Transmission 1', (SELECT id from good_type WHERE type = 'smsp'),123,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Speed Transmission 2', (SELECT id from good_type WHERE type = 'smsp'),245,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Skill Transmission 1', (SELECT id from good_type WHERE type = 'smsp'),83,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Skill Transmission 2', (SELECT id from good_type WHERE type = 'smsp'),96,'MONTREAL CONCORDIA',TRUE);

--Semi-final buy or self-product
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Control 1',(SELECT id from good_type WHERE type = 'smbs'),64,32.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Control 2',(SELECT id from good_type WHERE type = 'smbs'),82,39.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Control 1',(SELECT id from good_type WHERE type = 'smbs'),72,29.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Control 2',(SELECT id from good_type WHERE type = 'smbs'),33,35.99,'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Wheel 1',(SELECT id from good_type WHERE type = 'smbs'),200,19.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Wheel 2',(SELECT id from good_type WHERE type = 'smbs'),256,29.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Cheel 1',(SELECT id from good_type WHERE type = 'smbs'),188,24.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Wheel 2',(SELECT id from good_type WHERE type = 'smbs'),300,22.99,'MONTREAL CONCORDIA', TRUE);

--Semi-final buy-product = raw material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Brake 1',(SELECT id from good_type WHERE type = 'smbp'),402,32.99,'MONTREAL CONCORDIA', FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Brake 2',(SELECT id from good_type WHERE type = 'smbp'),288,39.99,'MONTREAL CONCORDIA', FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Brake 1',(SELECT id from good_type WHERE type = 'smbp'),356,29.99,'MONTREAL CONCORDIA', FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Brake 2',(SELECT id from good_type WHERE type = 'smbp'),350,35.99,'MONTREAL CONCORDIA', FALSE);

--5 material 
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed frame base 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed frame base 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill frame base 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill frame base 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed fork 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed fork 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill fork 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill fork 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Steams 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Steams 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Steams 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Steams 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Head 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Head 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Head 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Head 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Crank 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Crank 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Crank 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Crank 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain Wheel 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain Wheel 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain Wheel 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain Wheel 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Pedal 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Pedal 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Pedal 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Pedal 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Freewheel 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Freewheel 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Freewheel 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Freewheel 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Derailleur 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Derailleur 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Derailleur 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Derailleur 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Handlebar 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Handlebar 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Handlebar 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Handlebar 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Shifter 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Shifter 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Shifter 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Shifter 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Rim 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Rim 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Rim 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Rim 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES 
('Speed Tire 1', (SELECT id from good_type WHERE type = 'rawm'),251,5.99,'MONTREALL CONCORDIA',FALSE),
('Speed Tire 2', (SELECT id from good_type WHERE type = 'rawm'),192,6.99,'MONTREALL CONCORDIA',FALSE),
('Skill Tire 1', (SELECT id from good_type WHERE type = 'rawm'),285,4.99,'MONTREALL CONCORDIA',FALSE),
('Skill Tire 2', (SELECT id from good_type WHERE type = 'rawm'),382,7.99,'MONTREALL CONCORDIA',FALSE);

--accessories

INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES 
('Reflector', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE),
('Rearview Mirror', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE),
('Head Light', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE),
('Bell', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE),
('Carrier', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE),
('Basket', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE),
('Kickstand', (SELECT id from good_type WHERE type = 'accp'),251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);