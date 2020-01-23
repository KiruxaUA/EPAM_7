CREATE TABLE IF NOT EXISTS Skills (
    Id INT PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
    Name Varchar(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Accounts (
    Id INT PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
    Name Varchar(255) UNIQUE NOT NULL,
    Status Varchar(255)
);

CREATE TABLE IF NOT EXISTS Developers (
    Id INT PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
    FirstName Varchar(255) NOT NULL,
    LastName Varchar(255) NOT NULL,
    AccountId INT,
    FOREIGN KEY (AccountId) REFERENCES Accounts(Id)
);

CREATE TABLE IF NOT EXISTS DeveloperSkill (
    Id INT PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
    DeveloperId INT NOT NULL,
    SkillId INT NOT NULL,
    UNIQUE(DeveloperId, SkillId),
    FOREIGN KEY (DeveloperId) REFERENCES Developers(Id),
    FOREIGN KEY (SkillID) REFERENCES Skills(Id)
);