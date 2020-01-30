CREATE TABLE IF NOT EXISTS Skills (
    Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    Name Varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Accounts (
    Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    Name Varchar(255) NOT NULL,
    Status Varchar(255)
);

CREATE TABLE IF NOT EXISTS Developers (
    Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    First_Name Varchar(255),
    Last_Name Varchar(255),
    Account_Id INT,
    FOREIGN KEY (Account_Id) REFERENCES Accounts(Id)
);

CREATE TABLE IF NOT EXISTS Developer_Skill (
    Developer_Id INT NOT NULL,
    Skill_Id INT NOT NULL,
    UNIQUE(Developer_Id, Skill_Id),
    FOREIGN KEY (Developer_Id) REFERENCES Developers(Id),
    FOREIGN KEY (Skill_ID) REFERENCES Skills(Id)
);