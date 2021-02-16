CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS goods (
    id UUID DEFAULT uuid_generate_v4(),
    name VARCHAR,
    description VARCHAR,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS good_type(
    ID SERIAL PRIMARY KEY NOT NULL,
    type VARCHAR UNIQUE,
    description VARCHAR
);

CREATE TABLE IF NOT EXISTS inventory (
    ID UUID SERIAL PRIMARY KEY NOT NULL uuid_generate_v4(),
    ITEM_NAME VARCHAR NOT NULL,
    GOOD_TYPE INT references good_type(ID) NOT NULL,
    QUANTITY INT NOT NULL,
    BUY_PRICE FLOAT,
    SELL_PRICE FLOAT,
    LOCATION VARCHAR NOT NULL,
    BILL_OF_MATERIAL BOOLEAN NOT NULL
);


CREATE TABLE IF NOT EXISTS contact (
    ID SERIAL PRIMARY KEY NOT NULL,
    COMPANY_NAME VARCHAR NOT NULL,
    CONTACT_NAME VARCHAR,
    ADDRESS VARCHAR NOT NULL,
    CONTACT VARCHAR NOT NULL,
    CONTACT_TYPE VARCHAR
);

CREATE TABLE IF NOT EXISTS orders(
    ID SERIAL PRIMARY KEY NOT NULL,
    CREATED_DATE DATE NOT NULL,
    DUE_DATE DATE NOT NULL,
    DELIVERY_LOCATION VARCHAR NOT NULL
);
