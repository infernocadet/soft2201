# java notebook

> a little tracker of the things i learn by developing in java

## 8th aug 2024

working on an exercise, i have to create classes for the following scaffold code:

```java
public class Main {

    public static void main(String[] args) {
        Station centralStation = new Station("Central");
        Station yassStation = new Station("Yass");
        Station waggaWaggaStation = new Station("Wagga Wagga");
        Station southernCrossStation = new Station("Southern Cross");

        ArrayList<Station> stations = new ArrayList<>(Arrays.asList(centralStation, yassStation, waggaWaggaStation,southernCrossStation));
        Route route = new Route(stations);

        Train train = new Train("Sydney-Melbourne", route);
        train.addPassenger(new Passenger("Alice", centralStation, yassStation));
        train.addPassenger(new Passenger("Bob", centralStation, waggaWaggaStation));
        train.addPassenger(new Passenger("Carol", centralStation, southernCrossStation));
        train.addPassenger(new Passenger("David", yassStation, waggaWaggaStation));
        train.addPassenger(new Passenger("Eve", yassStation, southernCrossStation));
        train.addPassenger(new Passenger("Frank", waggaWaggaStation, southernCrossStation));
        // Please add your code here to print the required information
    }
}
```

### `ConcurrentModificationException`

in my `Train.java`, i had a function which would check the `boardingPassengers` to see if any passengers currently on the train would have to depart. `boardingPassengers` was an arraylist of type passenger, and i would add passengers who got on at the current station to this arraylist. the first iteration of this function, `newStation()` would be fine - no passengers would get off. but then on the second iteration, the compiler would run into an issue. this issue isn't checked at compile time, and I used breakpoints to see that the first iteration would work but the second wouldn't. this is because i would be **modifying a collection while iterating over it**. this led to a `ConcurrentModifcationException` which is where the normal iterator is being changed by another part of the code.

```java
ArrayList<Passenger> temp = new ArrayList<>();
        for (Passenger p : boardingPassengers) {
            if (p.destination == this.currentStation) {
                boardingPassengers.remove(p); // this caused the issue
                temp.add(p);
            }
        }
```

to avoid this error, i had to implement an explicit iterator to remove elements safely.

```java
ArrayList<Passenger> temp = new ArrayList<>();
Iterator<Passenger> iterator = boardingPassengers.iterator();
while (iterator.hasNext()) {
    Passenger p = iterator.next();
    if (p.destination == this.currentStation) {
        iterator.remove();
        temp.add(p);
    }
}
```

an `iterator` is an object which allows us to traverse through a collection, and allows us to safely remove objects during the traversal. there are two types of iterators:

- explicit: using an `Iterator` object.
  - has methods like `hasNext()`, `next()` and `remove()`.
- implicit: enhanced for-loops (for each loops).
  - java creates an iterator BTS to iterate.

we can create an explicit iterator by calling the `iterator()` method on the collection.
the `iterator()` method returns an `Iterator` object for the collection. here, we are selecting the next object using `iterator.next()`. it ios safer because the iterator keeps track of the current position in the collection. the `remove()` method removes the last element returned by the iterator's `next()` method.

### `printf()`

i feel like `printf()` is how i should be formatting strings in general. the other option is `String.format()`, but anyway, you can use variables in the string using the `%` symbol:

- `%d` - decimal
- `%n` - new line
- `%s` - string
- `%f` - float

### access modifiers

there are three types of access modifiers when it comes to object/class fields/attributes:

- `public`
  - attributes can be accessed from any file and any package.
- `private`
  - attributes can only be accessed from object instances of the same class and same package.
- `protected`
  - attributes can only be accessed from object instances of either the same class or subclass or any object/file in the same package.

### non-access modifiers

for classes, we can use `final` or `abstract`:

- `final`
  - class cannot be inherited by other classes
- `abstract`
  - class cannot be used to create objects, but can be inherited from another class using the keyword `extends`

for attributes and methods, we can use:

- `final`
  - attributes or methods cannot be overridden/modified
- `static`
  - attributes and methods belongs to the class rather than an object
  - the static member is shared among all instances of the class. its like a global variable. so lets say there was a change to a static attribute belonging to a class. this means that all instances of that class have that attribute updated.
  - because a static method belongs to the class instead of any actual instance, it can be called without creating an instance of that class. static methods can't access any instance variables or methods directly - only static fields and methods.
- `abstract`
  - can only be used in an abstract class. method will not have a body. body is provided by the subclass.

## 23 aug 2024

### `super` keyword

we can use the `super` keyword to call a parent class of a subclass.

```java
class Animal {
    public void makeSound(){
        System.out.println("Making a sound: ");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound(){
        super.makeSound();
        System.out.println("Woof!");
    }
}

public class Main{
    public static void main(String[] args){
        Animal myDog = new Dog();
        Animal animal = new Animal();
        animal.makeSound();
        myDog.makeSound();

    }
}
// output:
// Sound:
// Sound:
// Woof
```

here, the `Animal` class implements a method `makeSound()` that the Dog class will inherit. we can see here that Dog actually overrides the `makeSound()` method implementation, but using the super keyword, we can access the parent class' implementation of `makeSound()`. the `super` keyword can be used to access methods and constructors from the parent class that may have been overridden or hidden in the child class.

### public class vs. class

in java, the difference between `public class ClassName` and `class ClassName` is the access level and visibility of the class.

in `public class ClassName`, a class declared as `public` is accessible from any other class in any package. if a class is declared as `public` the file name must match the class name exactly.

- e.g., if we declare `public class MyClass`, the file must be named `MyClass.java`

in `class Classname`, this is defaulted to `package-private` access. means the class is accessible only in the same package. this is used for helper/utility classes that are meant to be used only in the package.

### packages

i was wondering why in the third exercise, we had to specify package `application.classifiers` in the `Colour.java` file:

```java
package application.classifiers;

public enum Colour {
    RED,
    YELLOW,
    GREEN;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
```

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/str.png" width="auto" height="auto">
</p>

this package line specifies the package to which the `Colour` enum belongs:

- logical grouping: packages group related classes such as enums, interfaces etc
- packages help avoid naming conflicts between classes, e.g. if we had a Colour enum in a different package such as `application.ui`. both can coexist like this using the full name including package.
- classes in a package with default access (package-private) mean that they can only be accessed by other classes within the same package.

### nested classifier

a class or interface in UML
