# CRUD Application

### Entities
  - Developer
  - Skill
  - Account

### Model
  - Developer (Long id, String name, Set skills, Account account)
  - Skill (Long id, String name)
  - Account (Long id, String name, AccountStatus accountStatus)

### Storage
  - developers.txt
  - skills.txt
  - accounts.txt

### Available actions
  - Create
  - Read
  - Update
  - Delete

### Layers
  - Model - POJO classes
  - View - all data that are required for user/console interaction
  - Controller - user’s requests handling
  - Repository - classes that provide access to text files
  - Resources

### Interfaces
  - AccountRepository extend GenericRepository<T, ID>
  - DeveloperRepository extend GenericRepository<T, ID>
  - SkillRepository extend GenericRepository<T, ID>
  - GenericRepository<T, ID>

### Implementations of appropriate interfaces
  - JavaIODeveloperRepositoryImpl
  - JavaIOSkillRepositoryImpl
  - JavaIOAccountRepositoryImpl

### Requirements
  - Java 8
