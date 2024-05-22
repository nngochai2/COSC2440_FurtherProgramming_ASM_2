# COSC2440_FurtherProgramming_ASM_2
This is an approach to implement Hibernate and JPA

**What is Object-Relational Impedance Mismatch (ORIM)?**
While the objects in object-oriented application follow the OOP principles, objects in the backend follow database normalization principles
--> Different reprensentation requirements

**Why using JPA/Hibernate?**
- Hibernate handles ORIM problems by replacing direct and persistent database accesses with high-level object handling functions
- JPA lets you define which and how objects should be _persisted_ in Java applications

**Mapping with Hibernate**
implemented by:
	- the configuration of an XML file 
	- OR using Java Annotations
		- **then use them to maintain the database schema**
- can generate skeleton source code for the persistence classes
- there are provided facilities to arrange **one-to-many** and **many-to-many** relationships between classes
- can also manage reflexive associations wherein an object has a **one-to-many** relationship with other instances of the class type
- Hibernate makes the following possible: 
	- overriding the default SQL type when mapping a column to a property
	- mapping Java Enums to columns 
	- mapping a single property to multiple columns

## Contribution Score

Nguyen Ngoc Hai (s3978281)  - 5

Nguyen Chi Nghia (s3979170) - 5

Duong Viet Hoang (s3962514) - 5

## Account for testing

**Admin**
- username: admin
- password: admin 

**Policy holder**
- username: devans
- password: Pass123word 

**Dependent**
- username: mlopez
- password: MyP@ssword

**Insuruance surveyor**
- username: lharris
- password: Pass4321word

**Insuruance manager**
- username: rgonzalez
- password: SecurePwd741

**Policyowner**
- username: Rmit123
- password: P@ssword963

## NOTE
To run our jar file just run below script at out/artifacts/COSC2440_Further_Programming_jar folder
```bash
java -jar COSC2440_Further_Programming.jar
```
However, because of several limitations and conflicts among different Java and JavaFX versions, various libraries, or even different operating systems, our JAR file may not run perfectly across all platforms. In a few cases, it might encounter errors. In such instances, I kindly ask you to spend a little time to re-add the artifact for launcher.java and rebuild it. I assure you that with the current settings, this new JAR will operate effectively on your machine. 

Alternatively, just simply run our Main.java to active our system.




