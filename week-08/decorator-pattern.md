# decorator pattern

## code reusing mechanisms

such mechanisms include:

- inheritance (is-a)
  - subclasses reuse super class code
  - static binding, compile time binding
- composition (has-a)
  - allows one class to delegate part of behaviours to other classes
  - no type relationship between these classes
  - dynamic binding, run time binding

it seems we favour composition over inheritance - e.g. strategy, state, command patterns.

composition is considered to be more flexible as the binding can happen at run time, and is not restricted by type requirements.

we use inheritance when there is a clear type hierarchy between classes, and common behaviours which are shared. this is when we can see that there is a clear benefit of polymorphism achieved through type hierarchy

we use composition when a complex object can be built by combining simpler ones, separated by responsibility and patterns. also when we need to support the flexibility of extending certain aspects of the objects - easier to split them up

### implementation relationship

interface implementation focuses on the behavioural aspect of an object, allowing classes to support different sets of behaviours.

we can see classes which implement an interface to refer to the is-a relationship by inheritance and type relationship.

## the coffee application

lets say we have a coffee application

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cof.png" width="auto" height="auto">
</p>

we mainly look at the description and the cost of the different beverages, and clients can maybe ask for other condiments.

beverage is abstract - cost method is abstract, meaning that subclasses have to provide implementation.

now, clearly we don't want to make each combination a subclass:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/coff.png" width="auto" height="auto">
</p>

why dont we use prototype method? not sure

what about using the strategy method? this way we can delegate different cost calculations to different condiments:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cond.png" width="auto" height="auto">
</p>

### the issue

it is clear that each condiment needs its own class. but how do we build a structure to connect the coffee and condiments to allow:

- dynamic combination of condiments
- correct applying the behaviour defined in each condiment, e.g. total cost

## the decorator pattern

it is astructure pattern - concerned with how classes and objects are composed to form larger structures.

the intent of the decorator pattern is to attach additional responsibilities to an object dynamically. they provide a flexible alternative to subclasses for extending functionality.

participants:

- the Component is the abstract object. each Component can be used on its own, or wrapped by a decorator.
- the ConcreteComponent is the object we are going to dynamically add new behaviour to. it extends Component
- the Decorator wraps a components (HAS-A), which means the decorator has an instance variable which holds reference to the object. Decorators implement the same interface or extend the same abstract class as the component they want to decorate.
- the ConcreteDecorator inherits an instance variable for the thing it decorates (the component that the decorator wraps). it inherits this thru the super Decorator class. decorators can even be used to extend the state of the component.

formally:
component: defines the interface for objects that can have responsibilities added to them dynamically
concretecomponent: defines an object to which additional responsibilities can be attached
decorator: maintains a reference to a component object and defines an interface that conforms to Components interface
concretedecorator: adds responsibilities to the component

collaborations:
decorator forwards requests to its component object. it may also optionally perform additional operations before and after forwarding the request.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/dec.png" width="auto" height="auto">
</p>

## decorating the beverage

so we still have our abstract Beverage class, which some base beverages will extend from, e.g. DarkRoast, Espresso etc.

but we will also have a CondimentDecorator, which is an abstract class which extends the Component object (Beverage). it'll store a reference to its component object that it will artifically "extend".

then the concrete decorator objects e.g. Milk, Mocha, Soy etc will extend the CondimentDecorator, and it'll have its own `cost()` method, which calls the original beverage cost method as well. this way we will be able to look at multiple cost functions.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/dbev.png" width="auto" height="auto">
</p>

### constructing a drink order with decorators

the DarkRoast inherits from Beverage and has a `cost()` method. the Mocha Object is a decorator - its type mirrors the object it is decorating (Beverage). therefore, Mocha has a `cost()` method too and we can treat any Beverage wrapped in Mocha as a Beverage, because Mocha is a subtype of Beverage.

the power of the Decorator is that we can add multiple decorators.

Whip is a decorator, and it also mirrors DarkRoast's type, and includes a `cost()` method.

note that when we call cost, on the outermost Beverage layer (Whip), it'll be a recursive call calculating cost.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/moc.png" width="auto" height="auto">
</p>

### inheritance and composition

the decorator pattern uses inheritance/implementation to achieve type matching between the decorator and component - using composition to add behaviour to the component.

here, the component can be an interface or an abstract class - the decorator has to be an abstract class (so that it can be referred to as its parent class, the component)

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ic.png" width="auto" height="auto">
</p>

## java i/o

the java io package uses the decorator pattenr to organise classes which represent different i/o functionalities

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/io.png" width="auto" height="auto">
</p>

### the input hierarchy

here, the InputStream is the abstract component, and FilterInputStream is the abstract decorator.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/jio.png" width="auto" height="auto">
</p>

### potential downsides

there are a lot of small objects and hard to understand at first. there are multiple ways to read a file and at least one decorator is needed to implement some read function.

there can be multiple different hierarchies:

```java
new BufferedReader(new FileReader("test.txt"));
new BufferedInputStream(new FileInputStream("test.txt"));
```

## implementation consideration

conformance:

a decorator object's interface must conform to the interface of the component it decorates (i.e., its concrete component must implement component)

omission:

it is okay to leave out an abstract Decorator class when you only need to add one responsibility. you can "promote" the concrete decorator classes.

lightweight:

to ensure a conforming interface, components and decorators must descend from a common Component class. this common class should be lightweight - it shouldn't store any data. the definition of data representation should be deferred to subclasses. getters are okay
