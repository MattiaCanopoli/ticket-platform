INSERT INTO user (email, name, password) VALUES ('mattia.canopoli@ticket.com','mattia.canopoli','{noop}password'),('mario.rossi@ticket.com', 'mario.rossi','{noop}password'), ('generic.user@ticket.com','generic.user','{noop}password'),('generic.admin@ticket.com','generic.admin','{noop}password');
INSERT INTO role (role_name) VALUES ('ADMIN'),('USER');
INSERT INTO user_roles (user_id, roles_id) VALUES (1,1),(2,2),(3,2),(4,1);
INSERT INTO category (name) VALUES ('not urgent'),('urgent'),('blocking')