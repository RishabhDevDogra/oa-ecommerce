INSERT INTO users (id, username, password, email, role) VALUES (1, 'alice', 'password1', 'alice@example.com', 'USER');
INSERT INTO users (id, username, password, email, role) VALUES (2, 'bob', 'password2', 'bob@example.com', 'ADMIN');
INSERT INTO users (id, username, password, email, role) VALUES (3, 'rishabh', '{bcrypt}$2a$10$9BZDRHp6aRgcxdoWtg7bPOsqGb9J.2UYh0GwLmMf4sf2trdp3bbPW', 'rishabh@xyz.com', 'USER');
-- password for rishabh = abc12345

-- Sample Products
INSERT INTO products (id, name, description, price, stock) VALUES (1, 'Phone', 'A smartphone', 800.00, 25);
INSERT INTO products (id, name, description, price, stock) VALUES (2, 'iPhone', 'Apple phone', 999.99, 10);
INSERT INTO products (id, name, description, price, stock) VALUES (3, 'Laptop', 'A high-end laptop', 1200.00, 10);


-- Sample Orders
INSERT INTO orders (id, user_id, status, total_amount, created_at) VALUES (1, 1, 'PENDING', 2000.00, CURRENT_TIMESTAMP());
INSERT INTO orders (id, user_id, status, total_amount, created_at) VALUES (2, 2, 'CONFIRMED', 800.00, CURRENT_TIMESTAMP());

-- Sample Order Items (for order 1)
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (1, 3, 'Laptop', 1, 1200.00);
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (1, 1, 'Phone', 1, 800.00);
-- Sample Order Items (for order 2)
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (2, 1, 'Phone', 1, 800.00);
