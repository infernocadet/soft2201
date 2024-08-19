# factory method & singleton

## design patterns

- pattern: description of a problem and its solution
- tried and test ideas for a recurring problem
- design or implementation structure which achieves a particular purpose

### components of a pattern

- the **pattern name**
  - e.g. Factory Method
- the **problem**
  - the pattern is designed to solve this
- the **solution**
  - the components of the design and how they are related
- consequence
  - the results and trade-offs of applying the pattern
  - advantages and disadvantages of using the pattern

## classification based on purpose

- creational patterns
  - abstract instantiation
  - make a system independent of how objects are created, composed and represented
- structural patterns
  - how classes and objects are composed to form larger structures
- behavioural patterns
  - concerned with algorithms and assignment of responsibilities

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/dp.png" width="auto" height="auto">
</p>

## simple factory implementation

### revisting object instantiation and coupling

we create objects using the `new` operator in a concrete class, not an abstract one. this may introduce some unwanted dependency/coupling:

```java
Duck duck = new MallardDuck();
```

in the code above, we use the abstract type to keep code flexible - but we have to create an instance of a concrete class.

in another example:

```java
Duck duck;

if (picnic) {
  duck = new MallardDuck();
} else if (hunting) {
  duck = new DecoyDuck();
}
```

here, we have a bunch of different duck classes, and we don't know until runtime which one to instantiate. this creates dependencies.

the problem is that when one object directly instantiates a new object, a dependency is created on its concrete class. we can decide **where to put** the `new` statement, to avoid undesirable dependencies.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/depe.png" width="auto" height="auto">
</p>

assuming we have a DuckApp class, we declare a Duck object and use the new keyword to make instances - this makes a dependency between the DuckApp and the Duck classes. any changes in the duck classes makes changes to the DuckApp.

depending on abstraction, instead of concrete class makes things safer in terms of future changes.

### pizza shop

```java
Pizza orderPizza() {
  Pizza pizza = new Pizza();

  pizza.prepare();
  pizza.bake();
  pizza.cut();
  pizza.box();
  return pizza;
}
```

for flexibility, it would be ideal if the `Pizza` class is an abstract class or interface, but unfortunately we can't directly instantiate either of these.

what we can do to accommodate for different pizzas:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/pizza.png" width="auto" height="auto">
</p>

we pass in the type of pizza, and based on that, we instantiate the correct concrete class.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/uh.png" width="auto" height="auto">
</p>

however this is not closed for modification. we will need to delete pizza cases, add some, and we need to keep changing this over and over.

so what we do is that take the object creation code out of the `orderPizza()` method, and then place it in an object that only worries about creating pizzas.

so we make a SimplePizzaFactory class - this focuses on creating pizzas.

```java
public class SimplePizzaFactory {

  public Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
      pizza = new CheesePizza(); // etc etc
    }
  }
}
```

the `createPizza()` method is the method all clients will use to instantiate new objects.

so for our Pizza store to operate, we need to give it reference to a `SimplePizzaFactory` object - it will be used in the constructor of the `PizzaStore` object - and the `orderPizza()` method uses the factory to create pizzas by passing on the type of pizza.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/piz.png" width="auto" height="auto">
</p>

this decouples the pizza store from the concrete pizza classes. the og problem is pushed to a new class SimplePizzaFactory - but it is localised in a smaller place.

SRP is achieved - each class has a single responsibility now. the factory can be used by other classes such as PizzaShopMenu which may use the factory to get pizzas for their current description and price.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/za.png" width="auto" height="auto">
</p>

## subclass and method based solution

lets assume the store is super successful. different franchises make different pizza styles though so they have their own factories.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ny.png" width="auto" height="auto">
</p>

we want to have different factories for different stores because they may differ slightly in behaviour.

### a little more control

at the moment, each store is an object of the `PizzaStore` class - they could only differ in which factory they use to make pizzas. but if they want to have their own behaviours - running different promotions, providing different ordering methods - we can create a subclass to represent a different store.

however, each subclass having its own factory object seems like overkill.

since the simple factory is a class with a single method, we can look at making it a factory method instead.

### abstract `PizzaStore`

now we've made PizzaStore abstract.

our factory method is also now abstract in PizzaStore.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/suc.png" width="auto" height="auto">
</p>

in this case, we allow the subclass to decide - each subclass provides an implementation of the `createPizza()` method.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/chi.png" width="auto" height="auto">
</p>

### a test class

we would create two different stores, then use one store to make Ethan's order and then Joels.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ptest.png" width="auto" height="auto">
</p>

## factory method defined

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/fms.png" width="auto" height="auto">
</p>

creator is a class which contains the implementations for all of the methods to manipulate products, except the factory method. all Creator subclasses must implement their own factory method.

the ConcreteCreator class implements the factory method - this method produces products. ConcreteCreator is responsible for creating one or more concrete products - the only class that has the knowledge of how to create these products. the concrete products made are subclasses of an abstract Product class/interface so that the classes that use the products refer to the abstract class, not the concrete one.

### the factory method itself

`abstract Product factoryMethod(String type)` the factory method is abstract - subclasses are counted on to handle object creation. it returns a Product that is used in methods defined in the superclass. the factory method isolates the client (code in superclass, like orderPizza()) from knowing what kind of concrete Product is actually made.

### participants

- product
  - defines the interface of objects the factory method create
- concreteProduct
  - implements Product interface
- Creator
  - declares factory method, returning an object of type Product
- ConcreteCreator
  - overrides the factory method to return an instance of ConcreteProduct

### intent

we want to define an interface for creating an object, but let subclasses decide which class to instantiate. let a class defer instantiation to subclasses.

applicability:

- a class can't anticipate the class of objects it must create
- a class wants it subclasses to specify the objects it creates
- classes delegate responsibility to one of several helper subclasses, and you want to localise the knowledge of which helper subclass is delegate

### creator classes

our abstract creator class defines an abstract factory method (in our context, `createPizza()`) that the subclasses will have to implement to produce products. the creator will also contain code that depends on the abstract Product (produced by a subclass). the creator should never really know which concrete product was actually produced.

classes that produce products are called concrete creators.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/chic.png" width="auto" height="auto">
</p>

### product classes

factories create products, but the actual concrete classes which are instantiated are subclasses of the abstract class.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/typ.png" width="auto" height="auto">
</p>
