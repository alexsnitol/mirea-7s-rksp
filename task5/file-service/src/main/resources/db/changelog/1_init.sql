CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE file
(
    id   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    data bytea
);