# intro to design patterns

## a simple design principle

> identify the aspects of your application that vary and separate them from what remains static.

take parts that vary, and encapsulate them, to allow for future alteration or extension without affecting those that don't.

if we had a duck with flying behaviours, we can separate the `fly()` behaviour out of the `Duck` superclass, and make it an interface, _FlyBehaviour_. each unique flying behaviour is implemented in a separate class which implements the interface.

with inheritance, the structure of an object is fixed - can't change it at runtime. however, with using interfaces, we can keep things flexible, and behaviours can be assigned to an instance through initialisation or setter methods (something we can cahnge at runtime).

instead of duck subclasses having to implement the flying and quacking interfaces, we make a set of classes, whose reason to exist is to represent a behaviour or a variant of a behaviour, such as squeaking or quacking. it is the behaviour class rather than the Duck class which will implement the behaviour interface.

> this is the **dependency inversion principle**.

### program to an interface, not an implementation

- **interface**: defines a contract that specifies what functionalities a class or object must provide. it can a Java interface, an abstract class or another construct representing a supertype
- **implementation**: the actual code which carries out functionalities.

this ensures a particular class is not locked in a specific implementation. when behaviour is implemented in a separate class that conforms to a predefined interface, it can be attached to the object at runtime.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/quack.png" width="350" height="auto">
</p>

## integration with the Duck class

we now need to add two instance variables of the type `FlyBehaviour` and `QuackBehaviour`, and replace the original `fly()` and `quack()` method with two new methods: `performQuack()` and `performFly()`.

```java
public abstract class Duck {
  private FlyBehaviour flyBehaviour;
  private QuackBehaviour quackBehaviour;

  public void performQuack() {
    quackBehaviour.quack();
  }
}
```

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/inte.png" width="350" height="auto">
</p>

here, each Duck has a reference to something that implements the QuackBehaviour interface. rather than handling the quack behaviour itself, the Duck object delegates that behaviour to the objectr referenced by `quackBehaviour`.

looking at the `MallardDuck` class:

```java
public class MallardDuck extends Duck {

  public MallardDuck() {
    // remember, mallardduck inherits the quackBehaviour and flyBehaviour instance variables from the Duck superclass
    quackBehaviour = new Quack();
    flyBehaviour = new FlyWithWings();
  }
}
```

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/absd.png" width="350" height="auto">
</p>

## flying behaviour implementation

assume these are different classes:

```java
public interface FlyBehaviour {
  public void fly();
}

public class FlyWithWings implements FlyBehaviour {
  public void fly() {
    // do something
  }
}

public class FlyNoWay...
```

## simple test class

```java
public class MiniDuckSimulator {
  public static void main(String[] args) {
    Duck mallard = new MallardDuck();
    // calls MallardDucks inherited performQuack() method, which then delegates to the object's QuackBehaviour (calls quack() on the duck's inherited quackBehaviour reference)
    mallard.performQuack();
    mallard.performFly();
  }
}
```

## strategy pattern

the redesign uses a design pattern called the **Strategy** pattern.

> the strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable.

key takeaways

- brush up on:
  - abstraction
  - encapsulation
  - polymorphism
  - inheritance
- principles
  - encapsulate what varies
  - favor composition over inheritance
  - program to interfaces not implemenations
- patterns
  - strategy - a family of algorithms, encapsulates each one and makes them interchangeable.
