-- Eliminar tablas
DROP TABLE IF EXISTS fragment;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS "user";

-- Crear tabla de usuarios
CREATE TABLE "user" (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Crear la tabla de jugadores
CREATE TABLE player (
    player_id SERIAL PRIMARY KEY,
    record_time VARCHAR(255),
    fragment_list VARCHAR(5)[],
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE
);

-- Crear la tabla de fragmentos
CREATE TABLE fragment (
    fragment_id SERIAL PRIMARY KEY,
    order_number INT NOT NULL,
    message TEXT NOT NULL
);

-- Crear la tabla de fragmentos
CREATE TABLE player_fragment_list (
    player_player_id INT NOT NULL,
    fragment VARCHAR(255),
    FOREIGN KEY (player_player_id) REFERENCES player(player_id) ON DELETE CASCADE
);