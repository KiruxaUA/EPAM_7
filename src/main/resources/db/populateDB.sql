INSERT INTO skills (id, name) VALUES ('1', 'Java');
INSERT INTO skills (id, name) VALUES ('2', 'C++');
INSERT INTO skills (id, name) VALUES ('3', 'C#');
INSERT INTO skills (id, name) VALUES ('4', 'Python');

INSERT INTO accounts (id, name, status) VALUES ('1', 'Joe', 'ACTIVE');
INSERT INTO accounts (id, name, status) VALUES ('2', 'William', 'DELETED');
INSERT INTO accounts (id, name, status) VALUES ('3', 'John', 'BANNED');

INSERT INTO developers (id, first_name, last_name, account_id) VALUES ('1', 'Joe', 'Williams', '1');
INSERT INTO developers (id, First_Name, last_name, account_id) VALUES ('2', 'William', 'Shakespear', '2');
INSERT INTO developers (id, First_Name, last_name, account_id) VALUES ('3', 'John', 'Higgins', '3');

INSERT INTO developer_skill(developer_id, skill_id) VALUES ('1', '1');
INSERT INTO developer_skill(developer_id, skill_id) VALUES ('1', '3');
INSERT INTO developer_skill(developer_id, skill_id) VALUES ('2', '2');
INSERT INTO developer_skill(developer_id, skill_id) VALUES ('3', '1');
INSERT INTO developer_skill(developer_id, skill_id) VALUES ('3', '2');