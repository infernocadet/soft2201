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