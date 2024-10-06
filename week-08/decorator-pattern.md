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
