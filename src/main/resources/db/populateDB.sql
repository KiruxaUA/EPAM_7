INSERT INTO Skills (Name) VALUES ('Java');
INSERT INTO Skills (Name) VALUES ('C++');
INSERT INTO Skills (Name) VALUES ('C#');
INSERT INTO Skills (Name) VALUES ('Python');

INSERT INTO Accounts (Name, Status) VALUES ('Joe', 'ACTIVE');
INSERT INTO Accounts (Name, Status) VALUES ('William', 'DELETED');
INSERT INTO Accounts (Name, Status) VALUES ('John', 'BANNED');

INSERT INTO Developers (First_Name, Last_Name, Account_Id) VALUES ('Joe', 'Williams', 1);
INSERT INTO Developers (First_Name, Last_Name, Account_Id) VALUES ('William', 'Shakespear', 2);
INSERT INTO Developers (First_Name, Last_Name, Account_Id) VALUES ('John', 'Higgins', 3);

INSERT INTO developer_skill (Developer_Id, Skill_Id) VALUES ('1', '2');
INSERT INTO developer_skill (Developer_Id, Skill_Id) VALUES ('2', '1');
INSERT INTO developer_skill (Developer_Id, Skill_Id) VALUES ('2', '3');
INSERT INTO developer_skill (Developer_Id, Skill_Id) VALUES ('3', '4');