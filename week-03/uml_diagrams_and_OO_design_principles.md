# uml diagrams and OO design principles

> contents
>
> uml modelling
> use case modelling
> class diagram
> sequence diagram
> solid principles
> grasp

## uml diagrams

two views:
structural (static) view:

- static structure of system (objects, attributes, operations and relationships)
  - class diagram

behavioural (dynamic) view

- dynamic behaviour of the system (collaboration among objects, and changes to the internal states of objects)
  - use case diagram
- interaction (subset of dynamic view) - emphasises flow of control and data
  - sequence diagram

## use case modelling

use case - textual capture of the requirements of the system in achieving a goal. use case diagram is a graphical representation. usually created during system analysis stage to capture the behavioural part of system requirements.

**use case diagram** provides a high-level view of the system's functionality by illustrating the various ways users can interact with it.

### use case

also called user stories - a way an actor interacts with the system, and what goal is achieved. so how does an actor use the system, what goals do they have - how should they interact with the system.

definitions:
actor: somethign with a behaviour, person, system, organisation
scenario: specific sequence of actions and interactions between actor(s) and system
use case: collection of related success and failure scenarios describing the actors attempts to support a specific goal

### common use case formats

brief: one-paragraph summary, main success scenario
casual: multiple, informal paragraphs covering many scenarios
fully-dressed: all steps and variations written in detail with supporting sections

#### casual format

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cf.png" width="350" height="auto">
</p>

#### fully dressed format

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/fd.png" width="350" height="auto">
</p>

## identifying use cases

- identify the system boundary
- identify the primary actors
  - goals fulfilled by uising the system
- identify goals for each actor
  - user uses ATM to withdraw
  - user uses ATM to purchase voucher
- define use cases that satisfy goals
  - starts with a verb: verb phrase such as "purchase good" "withdraw money"

<p align="center">
  <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/stor.png" width="350" height="auto">
</p>

system actors have the arrows next to their name: `<<system>>`

### example use case: retrieve transcript

```
Retrieve Transcript
  Primary actor: university staff
  Preconditions: the staff member has a unikey and password and is assigned the permission to retrieve transcripts
  Main Success Scenario:
  1. A staff logins to Sydney Student
  2. The system presents a home screen with menus
  3. The staff chooses the option for student transcript
  4. The system prompts the user to enter a student id or a unikey
  5. The staff enters the student id or unikey
  6. The system displays the transcript of the student

Extensions:
Incorrect student id or unikey in step 5:
  The system returns a message that no student can be found, it also presents an option to reenter the information. repeat step 5 and 6.
```

## class diagram

a class diagram is a static structure diagram that visually represents the structure of a system by showing its classes, their attributes, operations (or methods), and the relationships among objects/classes.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/uml.png" width="350" height="auto">
</p>

### visibilities

both attributes and operations can include visibility signs before their names:
\+ Public
\- Private
\# Protected
~ Package

the default access level is user defined - default is usually private and the default visibility of operations is public

in java, good practice is all fields are private.

## relationships

- association
  - association is a relationship between objects that indicates meaningful and interesting connect
  - associates do not indiciate a certain implementation construct
  - a typical use of associate is to iondicate that one object is the other's attribute
- aggregation
  - a type of association that indicates a "has-a" relationship between two objects
- composition
  - a type of aggregation that indicates a strong "has-a" relationship between two objects
- inheritance
- implementation
- dependency

### associations

just a line between two classes, has a relationship name which is meaningful - has multiplicity as well indicated through a number on the end of the line

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/as.png" width="350" height="auto">
</p>

a typical use of association is to show attribute of non-primitive type, e.g. class type

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ast.png" width="350" height="auto">
</p>

using the association notation which indicates that the Register class / instances has a field of type Sale. now it is also possible that a class could have many instances of another class, such as a collection of those classes, in which we would still use a line, but indicate that it could be one or many:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/astd.png" width="350" height="auto">
</p>

multiplicity: number of instances involved in the relationship. it also communicates domain constraints that will be implemented. it focuses on the relationship at a particular moment, rather than over a span of time.

| multiplicity | meaning            |
| ------------ | ------------------ |
| \*           | zero or more; many |
| 0..1         | zero or one        |
| 1..\*        | one or more        |
| 1.n          | one to n           |
| n            | exactly n          |
| n, m, k      | exactly those      |

#### association class

modeling an association as a class (with attributes, operations & other features)

lets say a company employs many persons. employs can be an employment class with attributes salary and startDate

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ac.png" width="350" height="auto">
</p>

when there is many to many relationships, it would be helpful to have an association class to represent their relationships.

### aggregation and composition

special case of assocation - aggregation represents a whole-part or "has-a" relationship between objects. in a basic aggregation relationship, the life cycle of the part is independent to the life cycle of the whole. in a composition relationship, the life cycle of the part depends on the life cycle of the whole, when the whole is deleted - the parts are deleted as well.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ca.png" width="350" height="auto">
</p>

### inheritance/generalisation

typical relationship between classes (is-a) relationship. _BankAccount_ is abstract as indicated by italicised name. the class contains an abstract method `withdraw`, indicated by the italicised name. use arrows to point to superclass.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ig.png" width="350" height="auto">
</p>

### implementation/realisation

in java context, this shows the relationship between an interface and its implementing class. an interface is represented using the class notation with `<<interface>>` keyword at the top. for a hand drawn UML, keyword `<<abstract>>` can be used to indicate an abstract class.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ir.png" width="350" height="auto">
</p>

# textbook readings

> from UML Distilled, 3rd Edition

### use cases

use cases are a technique for capturing functional requirements of a system.

- describes typical interactions between users of a system and system itself.
- a **scenario** is a sequence of steps describing this interaction.
- a use case is a set of scenarios tied together by a common user goal.
- users are **actors** - a role a user has in the system.

#### contents of a use case

- starts with a main success scenario. take other scenarios and write them as extensions:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/uc.png" width="350" height="auto">
</p>

each scenario has a primary actor, with the goal the use case is trying to satisfy and is the initiator of the user case. each step in a use case is an element of the interaction between actor and system. each step is a simple statement and shows who is carrying out the step.

an extension names a condition that results in different interactions from those described in the main success scenario.

### user stories

user stories help to describe requirements.
