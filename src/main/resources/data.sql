INSERT INTO users (id, username, password, email, role) VALUES (1, 'alice', 'password1', 'alice@example.com', 'USER');
INSERT INTO users (id, username, password, email, role) VALUES (2, 'bob', 'password2', 'bob@example.com', 'ADMIN');
INSERT INTO users (id, username, password, email, role) VALUES (3, 'rishabh', '$2y$10$i.Va5vwiX2iPljb77ot67ehOmdtzzZK1Lo8EWU9IAA5O99wrrwyzy', 'rishabh@xyz.com', 'ADMIN');
-- password for rishabh = abc12345

-- Sample Products
INSERT INTO products (name, description, price, stock) VALUES ('Phone', 'A smartphone', 800.00, 25);
INSERT INTO products (name, description, price, stock) VALUES ('iPhone', 'Apple phone', 999.99, 10);
INSERT INTO products (name, description, price, stock) VALUES ('Laptop', 'A high-end laptop', 1200.00, 10);


-- Sample Orders
INSERT INTO orders (user_id, status, total_amount, created_at) VALUES (3, 'PENDING', 2000.00, CURRENT_TIMESTAMP());
INSERT INTO orders (user_id, status, total_amount, created_at) VALUES (3, 'CONFIRMED', 800.00, CURRENT_TIMESTAMP());

-- Sample Order Items (for order 1)
-- Sample Order Items (for order 1)
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (1, 3, 'Laptop', 1, 1200.00);
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (1, 1, 'Phone', 1, 800.00);
-- Sample Order Items (for order 2)
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (2, 1, 'Phone', 1, 800.00);
