-- Eliminar tablas
DROP TABLE IF EXISTS player_fragment;
DROP TABLE IF EXISTS fragment;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS user_account;

-- Crear tabla de usuarios
CREATE TABLE user_account (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Crear la tabla de jugadores
CREATE TABLE player (
    player_id SERIAL PRIMARY KEY,
    record_time VARCHAR(255),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE
);

-- Crear la tabla de fragmentos
CREATE TABLE fragment (
    fragment_id SERIAL PRIMARY KEY,
    order_number INT NOT NULL,
    message TEXT NOT NULL
);

-- Crear tabla intermediario entre fragmentos y jugadores
CREATE TABLE player_fragment (
    player_id INT NOT NULL,
    fragment_id INT NOT NULL,
    PRIMARY KEY (player_id, fragment_id),
    FOREIGN KEY (player_id) REFERENCES player(player_id) ON DELETE CASCADE,
    FOREIGN KEY (fragment_id) REFERENCES fragment(fragment_id) ON DELETE CASCADE
);