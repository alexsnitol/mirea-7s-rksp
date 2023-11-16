CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE payment
(
    id      uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    address varchar,
    value_wei   decimal
);