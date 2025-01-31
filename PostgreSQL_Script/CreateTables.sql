-- Crear la tabla de fragmentos
CREATE TABLE fragment (
    fragment_id SERIAL PRIMARY KEY,
    order_number INT NOT NULL,
    message TEXT NOT NULL
);

-- Crear la tabla de colecciones
CREATE TABLE collection (
    collection_id SERIAL PRIMARY KEY,
    fragment_list INT[]
);

-- Crear la tabla de usuarios
CREATE TABLE player (
    player_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    record_time TEXT,
    collection_id INT,
    FOREIGN KEY (collection_id) REFERENCES collection(collection_id)
);
