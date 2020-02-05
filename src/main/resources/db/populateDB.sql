USE scjb6rzjixjf7taw;
INSERT INTO skills (name) VALUES ('Java');
INSERT INTO skills (name) VALUES ('C++');
INSERT INTO skills (name) VALUES ('C#');
INSERT INTO skills (name) VALUES ('Python');

INSERT INTO accounts (name, status) VALUES ('Joe', 'ACTIVE');
INSERT INTO accounts (name, status) VALUES ('William', 'DELETED');
INSERT INTO accounts (name, status) VALUES ('John', 'BANNED');
INSERT INTO accounts (name, status) VALUES ('James', 'ACTIVE');

INSERT INTO developers (first_name, last_name, account_id) VALUES ('Joe', 'Williams', '1');
INSERT INTO developers (first_name, last_name, account_id) VALUES ('William', 'Shakespear', '2');
INSERT INTO developers (first_name, last_name, account_id) VALUES ('John', 'Higgins', '3');
INSERT INTO developers (first_name, last_name, account_id) VALUES ('James', 'Holden', '4');

INSERT INTO developer_skill (developer_id, skill_id) VALUES ('1', '3');
INSERT INTO developer_skill (developer_id, skill_id) VALUES ('2', '2');
INSERT INTO developer_skill (developer_id, skill_id) VALUES ('3', '1');
INSERT INTO developer_skill (developer_id, skill_id) VALUES ('3', '2');
INSERT INTO developer_skill (developer_ID, skill_id) VALUES ('4', '4');