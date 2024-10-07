INSERT INTO user (email, name,lastname, username, password) VALUES ('mattia.canopoli@ticket.com','mattia','canopoli','mattia.canopoli','{noop}password'),('mario.rossi@ticket.com','mario','rossi','mario.rossi','{noop}password'),('generic.user@ticket.com','generic','user','generic.user','{noop}password'),('generic.admin@ticket.com','generic','admin','generic.admin','{noop}password');
INSERT INTO role (role_name) VALUES ('ADMIN'),('USER');
INSERT INTO user_roles (user_id, roles_id) VALUES (1,1),(2,2),(3,2),(4,1);
INSERT INTO category (id,name) VALUES (1,'bug fix'),(2,'support'),(3,'new implementation'),(4,'meeting request');
INSERT INTO ticket_status (id,name) VALUES (1,'OPEN'),(2,'IN PROGRESS'),(3,'COMPLETED');