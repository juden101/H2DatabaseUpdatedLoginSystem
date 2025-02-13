

-- Insert models.Users
INSERT INTO users (username, first_name, last_name, email, password, role,restaurant_id) VALUES
('jd123','John', 'Doe', 'john@example.com', 'hashed_password', 'admin',-1),
('as123','Alice', 'Smith', 'alice@example.com', 'hashed_password', 'customer',-1),
('staffPP','Owner', 'Pizza Place', 'owner@pizza.com', 'hashed_password', 'staff',1)
,('staffBH','Owner', 'Burger House', 'owner@bhouse.com', 'hashed_password', 'staff',2)
;

-- Insert Restaurants
INSERT INTO restaurants (name, location) VALUES 
('Pizza Palace', '123 Main St'),
('Burger House', '456 Elm St');

-- Insert Menus
INSERT INTO menus (restaurant_id, name, description) VALUES 
(1, 'Pizza models.Menu', 'A variety of classic and gourmet pizzas'),
(2, 'Burger models.Menu', 'Delicious burgers with fresh ingredients');

-- Insert models.Menu Items (Food with serving_size)
INSERT INTO menu_items (menu_id, name, price, type, description, image_url, serving_size, is_alcoholic) VALUES 
(1, 'Margherita Pizza', 10.99, 'Food', 'Tomato, mozzarella, and basil', 'images/margherita.jpg', 'Large', NULL),
(2, 'Cheeseburger', 8.99, 'Food', 'Juicy burger with melted cheese', 'images/cheeseburger.jpg', 'Medium', NULL);

-- Insert models.Menu Items (Drinks with is_alcoholic)
INSERT INTO menu_items (menu_id, name, price, type, description, image_url, serving_size, is_alcoholic) VALUES 
(1, 'Coca Cola', 2.50, 'Drink', 'Chilled soft drink', 'images/coke.jpg', NULL, FALSE),
(2, 'Red Wine', 12.99, 'Drink', 'Rich and full-bodied red wine', 'images/wine.jpg', NULL, TRUE);

