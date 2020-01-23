# CRUD Application

### Entities
  - Developer
  - Skill
  - Account

### Model
  - Developer (Long id, String name, Set skills, Account account)
  - Skill (Long id, String name)
  - Account (Long id, String name, AccountStatus accountStatus)

### Storage (MySQL)
  - accounts (Id, Name, Status)
  - skills (Id, Name)
  - developers (Id, FirstName, LastName, AccountId)
  - developerskill (Id, DeveloperId, SkillId)

### Available actions
  - Create
  - Read
  - Update
  - Delete

### Layers
  - Model - POJO classes
  - View - all data that are required for user/console interaction
  - Service - classes with business logic
  - Controller - user’s requests handling
  - Repository - classes that provide access to text files

### Interfaces
  - AccountRepository extend GenericRepository<T, ID>
  - DeveloperRepository extend GenericRepository<T, ID>
  - SkillRepository extend GenericRepository<T, ID>
  - GenericRepository<T, ID>
  - Mapper<T, S, ID>

### Implementations of appropriate interfaces
  - JavaIODeveloperRepositoryImpl
  - JavaIOSkillRepositoryImpl
  - JavaIOAccountRepositoryImpl
  - JdbcAccountRepositoryImpl
  - JdbcDeveloperRepositoryImpl
  - JdbcSkillRepositoryImpl

### Requirements
  - Java 8