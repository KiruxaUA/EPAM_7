INSERT INTO Skills (Name) VALUES ('Java');
INSERT INTO Skills (Name) VALUES ('C++');
INSERT INTO Skills (Name) VALUES ('C#');
INSERT INTO Skills (Name) VALUES ('Python');

INSERT INTO Accounts (Name, Status) VALUES ('Joe', 'ACTIVE');
INSERT INTO Accounts (Name, Status) VALUES ('William', 'DELETED');
INSERT INTO Accounts (Name, Status) VALUES ('John', 'BANNED');

INSERT INTO Developers (FirstName, LastName, AccountId)
    VALUES ('Joe', 'Williams', 1);

INSERT INTO Developers (FirstName, LastName, AccountId)
    VALUES ('William', 'Shakespear', 2);

INSERT INTO Developers (FirstName, LastName, AccountId)
    VALUES ('John', 'Higgins', 3);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (1, 1);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (1, 2);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (2, 1);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (2, 3);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (3, 1);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (3, 2);

INSERT INTO DeveloperSkill (DeveloperId, SkillId)
    VALUES (3, 3);