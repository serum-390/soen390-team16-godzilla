CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS goods (
    id UUID DEFAULT uuid_generate_v4(),
    name VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (id)
);
