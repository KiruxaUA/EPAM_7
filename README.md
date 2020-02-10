# CRUD Application

[![Build Status](https://api.travis-ci.com/KiruxaUA/EPAM_7.svg?branch=master)](https://travis-ci.com/KiruxaUA/EPAM_7)

### Entities
  - Developer
  - Skill
  - Account

### Model
  - Developer (Long id, String name, Set skills, Account account)
  - Skill (Long id, String name)
  - Account (Long id, String name, AccountStatus accountStatus)

### Storage (Jaws MySQL)
  - accounts (Id, Name, Status)
  - skills (Id, Name)
  - developers (Id, First_Name, Last_Name, Account_Id)
  - developer_skill (Id, Developer_Id, Skill_Id)

### Available actions
  - Create
  - Read
  - Update
  - Delete

### Layers
  - Model - POJO classes
  - Rest - servlets that handles user’s POST requests from network via HTTP/HTTPS protocol
  - Service - classes with business logic
  - Repository - classes that provide access to database

### Interfaces
  - AccountRepository extend GenericRepository<T, ID>
  - DeveloperRepository extend GenericRepository<T, ID>
  - SkillRepository extend GenericRepository<T, ID>
  - GenericRepository<T, ID>
  - Mapper<T, S, ID>

### Implementations of appropriate interfaces
  - JdbcAccountRepositoryImpl
  - JdbcDeveloperRepositoryImpl
  - JdbcSkillRepositoryImpl

All basic functionality is covered with unit tests, using JUnit and Mockito. Also pocket H2 DB is used to implement tests with connection to database.

Liquibase is used to initialize tables in DB and fill them by some information automatically during test (for H2 DB) and deploy (for remote DB) phases.

There are a few endpoints in this API, each of them associate with certain servlets:
>/api/v1/skills

>/api/v1/accounts

>/api/v1/developers

There is one index.jsp page, which contains description of project in two languages: 
English and German. And there is a documentation page on endpoint /documentation, 
which has been built with Swagger UI.

To start up application you should compile code (version of Java is 8) and start this with 
entry point in WebApplication class. Then the tomcat server will start locally.

This api is deployed to heroku cloud service. Link to project: https://rest-crud-application.herokuapp.com/