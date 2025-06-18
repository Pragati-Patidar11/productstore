
INSERT INTO categories (name) VALUES
('Electronics'),
('Books'),
('Fashion');


INSERT INTO products (name, price, description, product_code, release_date, created_at, updated_at, category_id) VALUES
('Smartphone', 499.99, 'Android phone', 'ELEC001', '2023-05-15', CURRENT_DATE, CURRENT_DATE, 1),
('Novel', 15.99, 'Best-selling fiction', 'BOOK001', '2023-01-10', CURRENT_DATE, CURRENT_DATE, 2);


INSERT INTO user (email, password) VALUES
('devuser@example.com', 'password123');


INSERT INTO user_roles (user_id, role) VALUES
(1, 'ROLE_USER');
