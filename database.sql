CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE dishes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    recipe TEXT NOT NULL,
    category_id INT REFERENCES categories(id)
);

CREATE TABLE ingredients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity VARCHAR(100), 
    dish_id INT REFERENCES dishes(id)
);

INSERT INTO users (username, password) VALUES ('admin', '123');

INSERT INTO categories (name) VALUES ('Супы'), ('Основные блюда'), ('Десерты');

INSERT INTO dishes (name, recipe, category_id) VALUES 
('Борщ', '1. Сварить бульон. 2. Нашинковать овощи. 3. Сделать зажарку. 4. Все смешать и варить до готовности.', 1);

INSERT INTO ingredients (name, quantity, dish_id) VALUES 
('Свекла', '2 шт.', (SELECT id FROM dishes WHERE name='Борщ')),
('Картофель', '4 шт.', (SELECT id FROM dishes WHERE name='Борщ')),
('Капуста', '300 г', (SELECT id FROM dishes WHERE name='Борщ')),
('Говядина', '500 г', (SELECT id FROM dishes WHERE name='Борщ'));

INSERT INTO dishes (name, recipe, category_id) VALUES 
('Картофельное пюре', '1. Очистить и сварить картофель. 2. Слить воду. 3. Добавить молоко и масло, растолочь.', 2);

INSERT INTO ingredients (name, quantity, dish_id) VALUES 
('Картофель', '1 кг', (SELECT id FROM dishes WHERE name='Картофельное пюре')),
('Молоко', '200 мл', (SELECT id FROM dishes WHERE name='Картофельное пюре')),
('Сливочное масло', '50 г', (SELECT id FROM dishes WHERE name='Картофельное пюре'));

INSERT INTO dishes (name, recipe, category_id) VALUES 
('Тирамису', '1. Взбить желтки с сахаром. 2. Добавить маскарпоне. 3. Окунуть печенье в кофе и выложить слоями с кремом.', 3);

INSERT INTO ingredients (name, quantity, dish_id) VALUES 
('Сыр Маскарпоне', '500 г', (SELECT id FROM dishes WHERE name='Тирамису')),
('Печенье Савоярди', '200 г', (SELECT id FROM dishes WHERE name='Тирамису')),
('Кофе', '300 мл', (SELECT id FROM dishes WHERE name='Тирамису')),
('Яйца', '4 шт.', (SELECT id FROM dishes WHERE name='Тирамису'));