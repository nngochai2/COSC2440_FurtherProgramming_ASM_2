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
