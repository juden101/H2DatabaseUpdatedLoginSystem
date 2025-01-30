-- Create the DineDash Database (optional, depending on your DB setup) 

--CREATE DATABASE DineDash;

--USE DineDash;

  

-- Users Table 

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

  

-- Restaurants Table 

CREATE TABLE IF NOT EXISTS Restaurants  (

    id INT AUTO_INCREMENT PRIMARY KEY, 

    name VARCHAR(100) NOT NULL, 

    cuisineType VARCHAR(50), 

    logoPath VARCHAR(255), -- Path to the restaurant logo (optional) 

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 

); 

  

-- Menu Items Table 

CREATE TABLE IF NOT EXISTS MenuItems  (

    id INT AUTO_INCREMENT PRIMARY KEY, 

    restaurantId INT NOT NULL, 

    name VARCHAR(100) NOT NULL, 

    type ENUM('Food', 'Drink') NOT NULL, -- Type can be 'Food' or 'Drink' 

    price DOUBLE NOT NULL, 

    quantity INT NOT NULL, -- Available quantity for the item 

    imagePath VARCHAR(255), -- Path to the menu item image (optional) 

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 

    FOREIGN KEY (restaurantId) REFERENCES Restaurants(id) ON DELETE CASCADE 

); 

  

-- Orders Table 

CREATE TABLE IF NOT EXISTS Orders (

    id INT AUTO_INCREMENT PRIMARY KEY, 

    userId INT NOT NULL, 

    restaurantId INT NOT NULL, 

    totalCost DOUBLE NOT NULL, 

    status ENUM('Pending', 'Accepted', 'En Route', 'Delivered') DEFAULT 'Pending', 

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 

    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE, 

    FOREIGN KEY (restaurantId) REFERENCES Restaurants(id) ON DELETE CASCADE 

); 

  

-- Order Items Table 

CREATE TABLE IF NOT EXISTS OrderItems  (

    id INT AUTO_INCREMENT PRIMARY KEY, 

    orderId INT NOT NULL, 

    menuItemId INT NOT NULL, 

    quantity INT NOT NULL, 

    price DOUBLE NOT NULL, -- Price at the time of order 

    FOREIGN KEY (orderId) REFERENCES Orders(id) ON DELETE CASCADE, 

    FOREIGN KEY (menuItemId) REFERENCES MenuItems(id) ON DELETE CASCADE 

); 

  

