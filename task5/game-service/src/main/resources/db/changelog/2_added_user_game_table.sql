CREATE TABLE user_game
(
    id      uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id uuid,
    game_id uuid,

    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (game_id) REFERENCES game (id)
)