# template method and code review

## cafe for tea and coffee

how should we implement/structure very similar classes?

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/mcof.png" width="auto" height="auto">
</p>

this is our Coffee class, each of the steps are a separate method. tea looks very very similar:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/mte.png" width="auto" height="auto">
</p>

the obvious hierarchy is to have some sort of Beverage super class. the methods which need to be uniquely implemented can be abstract methods, and shared methods can be declared in the superclass.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/mtb.png" width="auto" height="auto">
</p>

we can break down more common aspects - especially in `prepareRecipe();`. we want to try to represent the overall sequence of preparation in the super/base class.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/sut.png" width="auto" height="auto">
</p>

we can kind of abstract the methods themselves - instead of making them specific to the Beverage, generalise them.

the new super class looks like this:

```java
public abstract class CaffeineBeverage {

  final void prepareRecipe() {
    boilWater();
    brew();
    pourInCup();
    addCondiments();
  }

  abstract void brew();

  abstract void addCondiments();

  void boilWater(){
    System.out.println("Boiling water");
  }

  void pourInCup(){
    System.out.println("Pouring");
  }

}
```

we can see that the `prepareRecipe()` method is declared final because we don't want the subclasses to override the method.

coffee and tea handle `brew()` and `addCondiments()` in different ways so they will be abstract.

## the template method pattern

here, `prepareRecipe()` is our template method. it serves as a template for an algorithm, and in the template, each step of the algorithm is represented by a method. some methods are handled by the abstract class, and some are handled by subclasses. methods that need to be supplied by a subclass are declared abstract.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/tem.png" width="auto" height="auto">
</p>

intent: define skeleton of algorithm, defer some steps to subclasses.

applicability:

- implement the invariant (unchanging) parts of an algortihm once and leave it to subclasses to implement the behaviour in the methods.
- when common behaviour among subclasses should and can be factored and localised in a common class to avoid code duplication
- control subclassess extensions by hook method

### structure

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/utr.png" width="auto" height="auto">
</p>

abstract class contains the template method and declares the abstract methods which the subclasses must provide the logic for. the template method makes use of those primitive operations to implement an algorithm. some concrete classes prpovide the implementation of those abstract methods.

### hook method

a hook is a method that is declared in the abstract class, but only given an empty or default implementation.

we add a new method call, `hook()` at the end of the templateMethod. `hook()` can be a concrete method, but it does nothing. subclasses are free to override these but they don't have to. its not abstract btw

this gives subclasses the ability to "hook into" the algorithm at various points if they wish.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/h.png" width="auto" height="auto">
</p>

take a look at this example:

```java
public abstract class CaffeineBeverageWithHook {

  final void prepareRecipe() {
    boilWater();
    brew();
    pourInCup();
    if (customerWantsCondiments()){
      addCondiments();
    }
  }

  abstract void brew();

  abstract void addCondiments();

  void boilWater(){
    print("boiling water")
  }

  void pourInCup(){
    print("pouring")
  }

  boolean customerWantsCondiments() {
    return true;
  }
}
```

so the normal methods we want the subclasses to implement remain the same, e.g. brew and addCondiments. we add another concrete method called customerWantsCondiments. the purpose of the hook is to allow the subclass to change how it works based on the nature of the subclass. here the concrete implementation in the abstract class doesn't really do anything - if the hook is ignored, the sequence will remain the same - `customerWantsCondiments()` will always return true so we will always add condiments. this is the default behaviour. if we want, we can make a subclass have more logic to deal with this:

```java
public class CoffeeWithHook extends CaffeineBeverageWithHook {

  public void brew() {
    System.out.println("Dripping Coffee through filter");
  }

  public void addCondiments() {
    System.out.println("Adding condiemnts")
  }

  public boolean customerWantsCondiments() {
    String answer = getUserInput();

    if (answer.toLowerCase().startsWith("y")) {
        return true;
    } else {
        return false;
    }
}

private String getUserInput() {
    String answer = null;

    System.out.print("Would you like milk and sugar with your coffee (y/n)? ");

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try {
        answer = in.readLine();
    } catch (IOException ioe) {
        System.err.println("IO error trying to read your answer");
    }
    if (answer == null) {
        return "no";
    }
    return answer;
}

}
```

use hooks when a part of the algorithm is optional. use abstract methods when the subclass must provide implementation.

keep the number of abstract methods small - a concrete subclass must provide implementation to all abstract methods - too many abstract methods would make it hard to implement a subclass.

## the hollywood principle

we allow low-level compoennts to hook themselves into a systme, but the high-level components determine when they are needed and how.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/hol.png" width="auto" height="auto">
</p>

### factory method + hollywood

this is a special case of the template method pattern - defines an abstract method repsonsible for object creation (delegates this to the concrete factories). it implements the hollywood principle using inheritance.

the `factoryMethod()` is an abstract method, that could be called inside a template method. the superclass Creator calls these operations which are defined the subclass.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/tfa.png" width="auto" height="auto">
</p>

the main concern of the template method pattern is the skeleton of the algorithm. this can call abstract methods.

the main concern of the factory method pattern is about having one abstract method deal with object creation.

we can sort of combine the two.

```java
public abstract class PizzaStore {
  public Pizza orderPizza(String type) {
    Pizza pizza;

    pizza = createPizza(type);

    pizza.prepare();
    pizza.bake();
    pizza.cut();

    return pizza;
  }

  abstract Pizza createPizza(String type);
}
```

## template method usages

very common pattern - applicable in things like logging, unit testing

it is straightforward to implement

## reviews and refactoring

code smells:
indicators of potential problems in the code that may hinder maitnainability and scalability

- mysterious name
- duplicated code
- long functions
- long parameter list
