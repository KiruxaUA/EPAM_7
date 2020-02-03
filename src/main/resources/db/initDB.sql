DROP TABLE IF EXISTS developer_skill;
DROP TABLE IF EXISTS developers;
DROP TABLE IF EXISTS skills;
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS skills (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name Varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name Varchar(255) NOT NULL,
    status Varchar(255)
);

CREATE TABLE IF NOT EXISTS developers (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    first_name Varchar(255),
    last_name Varchar(255),
    account_id INT,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE IF NOT EXISTS developer_skill(
    developer_id INT NOT NULL,
    skill_id INT NOT NULL,
    UNIQUE (developer_id, skill_id),
    FOREIGN KEY (developer_id) REFERENCES developers(id),
    FOREIGN KEY (skill_id) REFERENCES skills(id)
);