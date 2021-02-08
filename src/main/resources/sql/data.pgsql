DELETE FROM goods;
INSERT INTO  goods (name, description) VALUES ('SuperSpeed Bicycle', 'A really fast bike, fun for the whole family');

-- insert test data into the inventory table
INSERT INTO inventory (item_name, good_type, quantity, sell_price, location, bill_of_material) VALUES ('SuperSpeed BICYCLE 1','FINISH GOOD',3,250,'MONTREAL CONCORDIA',TRUE);