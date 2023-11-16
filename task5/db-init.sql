CREATE DATABASE gm_game;
CREATE DATABASE gm_file;
CREATE DATABASE gm_payment;

\connect gm_game
CREATE SCHEMA migration;
\connect gm_file
CREATE SCHEMA migration;
\connect gm_payment
CREATE SCHEMA migration;