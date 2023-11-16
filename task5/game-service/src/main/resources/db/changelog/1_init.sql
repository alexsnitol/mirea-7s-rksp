CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE game
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    title         varchar,
    price_wei     bigint,
    cover_file_id uuid
);

CREATE TABLE "user"
(
    id                        uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    external_id               varchar,
    login                     varchar,
    password                  varchar,
    provider                  varchar,
    role                      varchar,
    crypto_wallet_private_key varchar
);

CREATE UNIQUE INDEX uk_user_external_id_provider ON "user" ("external_id", "provider");