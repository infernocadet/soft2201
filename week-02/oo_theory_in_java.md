# oo theory in java

> java data types
> four pillars of OOP
> encapsulation in java
> abstraction in java
> inheritance in java
> polymorphism in java

## java data types

- java is strongly typed
- primitive data types - built in types of the VM
  - cannot be changed by programs and have same meaning across machines
- non-primitive data types
  - predominantly user defined

### primitive data types

- integral types
  - byte, short, int, long
- floating point number types
  - float, double
- character type
  - char
- boolean

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/stor.png" width="350" height="auto">
</p>

### conversion between primitive types

- implicit widening of integral and floating point number types
  - byte to short, int, long or double
  - short to int, long, float or double
  - etc
  - special case:
    - char to byte or short
    - boolean to nothing else. maybe 0 or 1
- everything else requires explicit type casts or is not possible

### implicit widening and casting

```java
byte x = 10;

/* implicit widening */
short y = x;
long z = x;
float f = x;

/* implicit widening */
byte p = (byte) z;
short q = (short) f;
```

types causs some unexpected behaviour if the value held by the data type (e.g. byte or short) is outside the range of values they can express (e.g. -128 - 127 ot -32768 - 32767)

## non-primitive types - class

java classes are types - and we can declare a variable whose type is a class

```java
class Demo {
  int a, b;

  // constructor
  Demo(int a, int b) {
    this.a = a;
    this.b = b;
  }

  // class method to add numbers
  int addition() {
    return a + b;
  }
}
```

take this demo class. we declare its attributes that it requires to have. then we define its constructor and make some methods as well.

we can instantiate this object:

```java
public class Main {
  public static void main(String[] args) {
    Demo d1 = new Demo(10, 4);
    // other stuff
  }
}
```

the variable d1 is of Demo type and holds reference to an object created using the Demo constructor.

## basic classes

a basic class has a name, fields and methods.

fields refer to instance variables or attributes.

there is a special method called a `Constructor`.

inside the constructor, the `this` keyword is used to refer to the current object.

### basic java object

an object is an instance of a class. each object has a unique identity but shares the structure provided by its class.

each object gets a copy of the instance variables, the object referenced by d1 has its own integer variables a and b. this is in contrast to static variables, which belongs to the class and there is only one copy.

### non-primitive type - string

`String s1 = "Hello` or `String s2 = new String("Doin")`

### array

used to store multiple values in a single variable instead of declaring separate variables for each value. it is declared using square brackets.

```java
String[] cars = {"Car 1", "Car 2", "Car 3"};
int[] nums = {10, 20, 30};
```

### interface

interface is a java construct used to group related methods, with empty bodies.

it is like a contract - classes that "sign" this contract are required to provide implementations for all its methods. this is done through `<class_name> implements <interface>`.

```java
interface Animal {
  public void animalSound(); // interface method
}

class Pig implements Animal {
  public void animalSound() {
    // sound
  }
}
```

### primitive vs non primitive

- primitive types are innately defined
- primitive types are designed to hold a single value
- primitive types are allocated memory on the stack
- non primitive types - stack contains a reference while the actual object is in heap

## four pillars of oop

based on the concept of objects. objects can contain data and code. data and code that belong to a class should be declared using a static keyword. the data part refers to instance variables, and code is methods.

- encapsulation
  - bundles data and operations into a single unit. hiding internal state and some functionality from outside.
- inheritance
  - allows one class to inherit data and methods from another. allows code reuse and hierarchical relationships.
- abstraction
  - hide complex implementation details and only show essential features
- polymorphism
  - objects can take different forms or have multiple behaviours

## encapsulation

about data protection and controlled access.
key elements of encapsulation:

- private access modifier:
  - class fields/attributes to be declared as `private`
  - only accessible within that class
- public access modifier:
  - some methods can be declared as `public`
  - methods can be accessed from anywhere in the program
- getter and setter methods
  - provide public methods to access and modify private fields
  - controlled access to data

```java
public class Person {
  // private = restricted access
  private String name;
  // getter
  public String getName() {
    return name;
  }
  public void setName(String newName){
    this.name = newName;
  }
}
```

benefits:

- control: using encapsulation, we can add conditions to control how data is accessed or modified
- flexibility and maintenance: by encapsulating data, any internal changes to a class won't directly affect its interactions with other classes
- increased security - shielding class members

## inheritance

allows programmers to create subclasses by inheriting properties and methods fom existing classes (superclasses). subclasses inherit all accessible fields and methods from its superclass.

java supports inheritance through the `extends` keyword.

### inheritance of data

a subclass inherits all non-private data for its superclass.

- public
- protected (protected data is accessible anywhere, but only by inherited classes)
- data declare as private is not accessible
- a subclass can override a method inherited from the superclass by providing a new implementation but with the same signature (**name, parameter types and return types**)

#### method overloading vs overriding

- overloading lets a class have several methods with the same name but different parameters, allowing varied actions based on parameters provided.

take the following example:

```java
class Calculator {
  int add(int a, int b ) {
    return a + b;
  }
  int add(int a, int b, int c) {
    return a + b + c;
  }
}

class ScientificCalculator extends Calculator {
  @Override
  int add(int a, intb) {
    return a + b + 10;
  }
}
```

scientific calculator inherits both methods, but it overrides the first method. the calculator class in general demonstrates method overloading, which is having the same method but different behaviour for different parameters.

### constructor inheritance

constructors are not inherited. if a superclass has multiple constructors with different parameters, the subclass does not inherit them. subclass needs its own constructors if the default one is not enough.

- implicit call to superclass constructor: when a subclass constructor is invoked, the first statement is a call to the superclass's default constructor.
- explicit call the superclass constructor: we can explicitly call a specific superclass constructor using the super keyword followed by arguments.

## abstraction

the process of hiding certain details and showing only essential information. achieved either through abstract classes or interfaces.

### abstract classes

these are defined using abstract keyword.

abstract classes can contain both abstract methods and regular methods. cannot be instantiated directly.

```java
abstract class Animal {
  public abstract void animalSound();
  public void sleep() {
    // do somethign
  }
}

class Pig extends Animal {
  public void animalSound() {
    // sound
  }
}
```

java does not have multi-inheritance: interface is a way-out.

an interface is completely abstract that is used to group related methods with empty bodies.

interfaces can even inherit from other interfaces.

```java
interface Printable {
  void print() // abstract method with no implementation. anything that implements this must provide implementation
}

interface Constants {
  // declare constant variables (implicitly public, static and final)
  int MAX_VALUE = 100;
  String APP_NAME = "MyApp";
}

// take this next example
interface Drawable {
  void draw();
}

interface Resizeable extends Drawable {
  void resize();
}

// resizable has two methods: draw and resize
```

## polymorphism

it means to have many forms - occurs when we have many classes that are related to each other by inheritance or implementation. a method can behave different depending on the objects it is called on, or depending on the parameters passed to it.

polymorphism in Java is achieved through overloading or overriding:

- compile-time polymorphism (static polymorphism)
- run-time polymorphism (dynamic polymorphism)

### casting in polymorphism

**upcasting**: invokes casting an object to one of its superclass types. being an implicit conversion, it is safe and dont implicitly, kind of like widening in the primitive types.

`Shape Y = new Rectangle();`

**downcasting**: cast an object to one of its subclass types, must be done explicitly due to risks.

```java
Shape Y = new Rectangle();
Rectangle z = new Shape();
```
