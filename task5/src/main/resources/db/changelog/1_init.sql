CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE file
(
    id   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    data bytea
);

CREATE TABLE game
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    title         varchar,
    cover_file_id uuid,

    CONSTRAINT fk_game_file_id FOREIGN KEY (cover_file_id) REFERENCES file (id)
);