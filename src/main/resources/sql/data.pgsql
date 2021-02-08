DELETE FROM goods;
INSERT INTO  goods (name, description) VALUES ('SuperSpeed Bicycle', 'A really fast bike, fun for the whole family');

-- insert test data into the inventory table
-- Final product
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSpeed BICYCLE 1','FINISH GOOD',31,229.99,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSpeed BICYCLE 2','FINISH GOOD',10,299.99,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSkill BICYCLE 1','FINISH GOOD',26,199.99,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSkill BICYCLE 2','FINISH GOOD',82,249.99,'MONTREAL CONCORDIA',TRUE);

-- Semi-final self-product
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame speed 1', 'Semi-finished-selfproduct',89,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame speed 2', 'Semi-finished-selfproduct',102,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame skill 1', 'Semi-finished-selfproduct',94,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('frame skill 2', 'Semi-finished-selfproduct',66,'MONTREAL CONCORDIA',TRUE);

INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Speed Transmission 1', 'Semi-finished-selfproduct',123,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Speed Transmission 2', 'Semi-finished-selfproduct',245,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Skill Transmission 1', 'Semi-finished-selfproduct',83,'MONTREAL CONCORDIA',TRUE);
INSERT INTO inventory (item_name, good_type, quantity, location, bill_of_material) VALUES ('Skill Transmission 2', 'Semi-finished-selfproduct',96,'MONTREAL CONCORDIA',TRUE);

--Semi-final buy or self-product
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Control 1','semi-final buy or self-product',64,32.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Control 2','semi-final buy or self-product',82,39.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Control 1','semi-final buy or self-product',72,29.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Control 2','semi-final buy or self-product',33,35.99,'MONTREAL CONCORDIA', TRUE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Wheel 1','semi-final buy or self-product',200,19.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Wheel 2','semi-final buy or self-product',256,29.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Cheel 1','semi-final buy or self-product',188,24.99,'MONTREAL CONCORDIA', TRUE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Wheel 2','semi-final buy or self-product',300,22.99,'MONTREAL CONCORDIA', TRUE);

--Semi-final buy-product = raw material
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Brake 1','semi-final buy-product',402,32.99,'MONTREAL CONCORDIA', FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Brake 2','semi-final buy-product',288,39.99,'MONTREAL CONCORDIA', FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Brake 1','semi-final buy-product',356,29.99,'MONTREAL CONCORDIA', FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Brake 2','semi-final buy-product',350,35.99,'MONTREAL CONCORDIA', FALSE);

--Raw material 
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed frame base 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed frame base 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill frame base 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill frame base 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed fork 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed fork 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill fork 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill fork 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Steams 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Steams 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Steams 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Steams 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Head 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Head 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Head 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Head 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Crank 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Crank 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Crank 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Crank 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain Wheel 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain Wheel 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain Wheel 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain Wheel 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Pedal 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Pedal 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Pedal 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Pedal 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Chain 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Chain 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Freewheel 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Freewheel 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Freewheel 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Freewheel 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Derailleur 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Derailleur 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Derailleur 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Derailleur 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Handlebar 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Handlebar 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Handlebar 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Handlebar 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Shifter 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Shifter 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Shifter 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Shifter 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Rim 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Rim 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Rim 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Rim 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Tire 1', 'raw',251,5.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Speed Tire 2', 'raw',192,6.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Tire 1', 'raw',285,4.99,'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, location, bill_of_material) VALUES ('Skill Tire 2', 'raw',382,7.99,'MONTREALL CONCORDIA',FALSE);

--Raw accessories

INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Reflector', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Rearview Mirror', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Head Light', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Bell', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Carrier', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Basket', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);
INSERT INTO inventory (item_name, good_type, quantity, buy_price, sell_price, location, bill_of_material) VALUES ('Kickstand', 'accessories',251,5.99, 5.99, 'MONTREALL CONCORDIA',FALSE);