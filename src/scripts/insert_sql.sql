-- Use the H2 database schema
-- Sample Data

-- Users
INSERT INTO users (username, first_name, last_name, email, password) VALUES
('admin', 'System', 'Administrator', 'admin@example.com', 'admin123'),
('john_doe', 'John', 'Doe', 'john.doe@example.com', 'password123');



-- Restaurants

INSERT INTO Restaurants (name, cuisineType, logoPath) VALUES

('La Bella Italia', 'Italian', 'resources/italian_logo.png'),

('Sakura Sushi Bar', 'Japanese', 'resources/japanese_logo.png'),

('Spice Route', 'Indian', 'resources/indian_logo.png');



-- Menu Items

INSERT INTO MenuItems (restaurantId, name, type, price, quantity, imagePath) VALUES

(1, 'Margherita Pizza', 'Food', 7.99, 9, 'resources/margherita.png'),

(1, 'Chianti Wine', 'Drink', 9.99, 0, 'resources/chianti.png'),

(2, 'Salmon Nigiri', 'Food', 12,0,'resources/sushi.png');

