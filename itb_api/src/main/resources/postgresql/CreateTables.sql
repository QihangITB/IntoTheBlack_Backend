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
    fragment_list VARCHAR(2)[],
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE
);

-- Crear la tabla de fragmentos
CREATE TABLE fragment (
    fragment_id SERIAL PRIMARY KEY,
    order_number INT NOT NULL,
    message TEXT NOT NULL
);