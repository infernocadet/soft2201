# prototype and observer

## creational patterns - revision

creational patterns deal with object creation

there are two mechanisms:

- a class deines one or more constructors to set out the steps of creating an object of the class
- the language specifies a way to call the constructor. this is the `new` operatior in java.

creational patterns deeal with these two mechanisms:

- who is responsible for creating an object
  - how do we define the constructors
  - where should we put the constructor call

### creational patterns - factory method

no special requirements for constructor methods

- can be used in any object creation

this method specifies who is in charge of the `new` keyword. an abstract super type declares the interface for creating the object, containing the abstract `factoryMethod`

the concrete subtype implements the creation process. this is the concrete `factoryMethod`. the `new` statement appears in the body of the concrete `factoryMethod`.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/fms.png" width="auto" height="auto">
</p>

when do we use the factory method?

- when we have different products based on a super product type
  - the set of concrete products needs to be flexible and allowing for change
- there are common ways of manipulating all objects belonging to the super type
  - this is when the abstract Creator class can have maybe like a static implementation of a method which manipulates the product
- some subtype mayu need special manipulatio (or just have its own controls)

### simple factory

not a Gang of Four pattern - it creates a factory class JUST to deal with the creation process.

in the factory method - the creation process is implemented in a method.

simple factory can only handle simple creation logic with a few product types, and assumes standard manipulation for all products.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/za.png" width="auto" height="auto">
</p>

### creational pattern - singleton

this relates to a special creation requirement, where only one object should be created - the class keeps its sole instance.

the class constructor is private - and there are two types of constructor calls:

- lazy initialisation: put inside a static method of the class itself
  - singleton instance is created when a method (typically `getInstance()` is called)
  - resource efficient and avoids unnecessary initialisation
  - however there may be thread-safety issues.
- eager initialiation: call when the variable is declared?? tf
  - when class is loaded, it is is created, using a static initialiser
  - instance is created when class is loaded so no race conditions

eager initialisation:

```java
public class Singleton {
  private static final Singleton instance = new Singleton();

  private Singleton(){
    // constructor which is private to prevent instantiation
  }

  public static Singleton getInstance(){
    return instance;
  }
}
```

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/le.png" width="auto" height="auto">
</p>

## prototype pattern

in this scenario, we want to create an object by making a copy of an existing object. we may want to change the copy accordingly.

similar shapes are likely to be the objects of the same class with different attribute values. making copies of a "template" object is much easier than creating an object from scratch, and then setting attribute values accordingly.

### object copying/cloning

java has a `clone()` method which performs a shallow copy. shallow copy means that the fields of the cloned object are copied directly from the og object - if a field is of a primitive type like int or char, then the actual value is copied. but, if the field is of non-primitive type, then only the reference is copied. these non-primitive fields are not independent, they point to the same instance of the referenced object.

### prototype pattern structure

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/pps.png" width="auto" height="auto">
</p>

so it looks like here we have a supertype `Prototype` abstract class which declares an abstract `clone()` method. any class which wants to make itself "clonable" can extend from the `Prototype` class and provide implementation.

so yeah i was kind of right, Prototype is an abstract class/interface which declares a clone method. classes which want to be cloned will implement this method themselves and returns a copy of their instance. the client class interacts with prototypes - instead of craeting new objects, it asks the prototype to clone itself. the client does not need to know the specifc class of the object being cloned.

### protoype participants

- Prototype
  - declares an interface for cloning itself
- ConcretePrototype
  - implements an operation for cloning itself
- Client
  - creates a new object by asking a prototype to clone itself
- Collaborations
  - a client asks a prototype to clone itself

### prototype registry

when the number of prototypes in a system isn't fixed - meaning prototypes can be created or destroyed dynamically, we keep a registry of available prototypes.

### prototype example

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/pty.png" width="auto" height="auto">
</p>

here, the registry keeps a collection of different shapes which is predefined. we have a Shape interface which is clonable and can draw. our only concrete class is Line and has its own attributes, but it implements the interface methods.

implementation looks like

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/pcp.png" width="auto" height="auto">
</p>

clone just returns a `new` object which carries over the values of the current object's attributes.

if the class contains fields of non-primitive and mutable type, a copy of the non-primitive value needs to be made.

### the registry

the registry looks like this:

```java
class ShapeCollection {
  private Map<String, Shape> shapes = new HashMap<>();

  public void addShape(String name, Shape shape) {
    shapes.put(name, shape); // we use the new constructor here e.g. shapes.put("triangle" new Polygon("triangle"))
  }

  public Shape getShape(String name){
    return shapes.get(name);
  }
}
```

### the client

```java
class DiagramTool {
  private ShapeCollection shapeCollection;

  public DiagramTool(ShapeCollection shapeCollection) {
    this.shapeCollection = shapeCollection;
  }

  public void createShape(String name){
    Shape shape = shapeCollection.getShape(name).clone();
    shape.draw();
  }
}
```

the tool keeps a registry and its value is passed when the DiagramTool is created

### consequences

- hides concrete product classes from client - less dependencies. these patterns let a client work with application specific classes without modifcation. e.g., the diagram tool doesn't need to know which classes implement a line or a box.
- adding and removing products in run time
  - we can add a new prototype belonging to a different class by registering a new instance of that class.
- specifying new objects by varying values or structures
  - if we need a new notation to represent composition, we can create an object of that Line class and change its attributes. any user can modify the feature of an object on the diagram and make a copy of it.
- configure an application with classes dynamically
  - e.g. most diagram tool allows users to plugin third party libraries - those classes are unknown to the diagram tool anyway.
- reduced subclassing
  - class is the blueprint of objects. an alt sol would be to have different subclasses like GeneralisationLine, ImplementationLine, etc each with a different constructor. this pattern uses an existing object like a blueprint.

### implementing deep copy in clone()

we want to create a notion to represent the object lifeline in a sequence diagram. this notation contains two parts, a box and line - represented by Box object and Line object. this means our LifeLine class has fields referring to non-primitive types. we can create a new class to represent this, which implements the Shape interface.

e.g.

```java
class Lifeline implements Shape {
  private Box objectBox;
  private Line line;

  public Lifeline(Box objectBox, Line line){
    this.objectBox = objectBox;
    this.line = line;
  }

  public Shape clone(){
    Box box = (Box) this.objectBox.clone();
    Line line = (Line) this.line.clone();
    return new Lifeline(box, line);
  }

  public void draw(){
    //...
  }
}

class Box implements Shape {
  private String lineStyle;
  private String text;

  public Box(String lineStyle, String text) {
    this.lineStyle = lineStyle;
    this.text = text;
  }

  public Shape clone() {
    return new Box(this.lineStyle, this.text)
  }

  public void draw(){
    ...
  }
}
```

both Lifeline and Box implement the Shape interface and provide their `clone()` methods. the LifeLine class uses a deep copy by cloning its obtained Box and Line objects. this ensures that changes to the Lifeline object's Box or Line do not affect the original LifeLine.

## the weather monitoring application

assume a application called **Weather-O-Rama**. the weather station is a physical device that acquires actual data. the **WeatherData** object (which tracks the data coming from the Weather Station and updates displays) there is also a display which shows users the **current weather condition**, **weather statistics** and a **simple forecast**.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/wra.png" width="auto" height="auto">
</p>

our WeatherData class is like this:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/wd.png" width="auto" height="auto">
</p>

our first implementation is like this

```java
public class WeatherData {
  // instance variable declarations

  public void measurementsChanged() {

    // first, grab recent measurements by calling the getter methods. assign each value to an appropriately named variable.
    float temp = getTemperature();
    float humidity = getHumidity();
    float pressure = getPressure();

    // update each display by calling its method and passing it the measurements
    currentConditionsDisplay.update(temp, humidity, pressure);
    statisticsDisplay.update(temp, humidity, pressure);
    forecastDisplay.update(temp, humidity, pressure);
  }

  // other weatherdata methods
}
```

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/g.png" width="auto" height="auto">
</p>

if we have more displays, then boom we are cooked. we'd have to add another instance variable, adn then add a new display object in the method. it is not the best way of doing this. we are asking the weatherdata object to depend on a number of concrete classes. not good. needs to depend on abstractions instead. also we might want to leave it open to extension, clsoed for modificaiton.

## the observer pattern method

a little analogy:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/a.png" width="auto" height="auto">
</p>

in our weather monitoring app, the WeatherData class is the publisher, and all current and potential displays are subscribers

in the pattern langugae:

- the publisher is called the **subject**
- the subscriber is called the **Observer**

whenever the subject changes, the observer does something to update itself

### one to many relationship

the observer pattern defines a one to many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically.

observers can be of different classes or types.

### class diagram

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cd.png" width="auto" height="auto">
</p>

we start with a `Subject` interface. objects use this interface to register as observers and also to remove themselves from being observers.

a concrete subject always implements the `Subject` interface. in addition to the register and remove methods (which this class provides implementation for), the concrete subject implements a `notifyObservers()` method which is used to update all the current observers whenever state changes.

the concrete subject may also have methods for setting and getting its state.

each subject can have many observers. all potential observers need to implement the `Observer` interface. the interface has one method, `update()`, that is called when the Subject's state changes.

concrete observers can be any class that implements the Observer interface. each observer registers with a concrete subject to receive updates.

### notification sequence

in the context of a single `ConcreteObject` - lets say `notifyObservers()` was called, it has a lot of observers, it should literally just loop through a list of observers watching it and update. and the list would be `List<Observer>` so we'll depend on the abstract class.

### loose coupling

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/seq.png" width="auto" height="auto">
</p>

when two objects are loosely coupled, they can interact, but they typically have very little knowledge of each other.

observer pattern achieves loose coupling:

- the only thing the subject knows about an observer is that it implements a certain interface (Observer interface)
- can add any new observers at any time
- never need to modify the subject to add new types of observersz
- can reuse subjects or observers independently of each other
- changes to either the subject or an observer will not affect the other

## redesigning the weather app

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ops.png" width="auto" height="auto">
</p>

here we can see that we have an `Observer` interface which declares an `update()` method. all of our displays implement this interface, seen through a dashed line with a hollow arrow. they implement that `update()` method.

there is a `Subject` interface as well, which defines methods like `registerObserver()`, `removeObserver()` and `notifyObserve()`. this interface provides the necessary structure to be able to update observers about changes. there is a solid line with a filled arrow from Subject to Observer, which indicates a unidirectional association - the subject class has a dependency on the observer interface - meaning it references to observers. observers do not necessarily know about the subject?

our WeatherData class implements subject, with its own methods as well. each of the concrete displays have an association with WeatherData,

### defining the interfaces

```java
public interface Subject {
  public void registerObserver(Observer o);
  public void removeObserver(Observer o);
  // these methods take an observer as an argument
  public void notifyObservers();
}

public interface Observer {
  public void update(float temp, float humidity, float pressure);
}

public interface DisplayElement {
  public void display();
}
```

here is our actual subject concrete class:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/sj.png" width="auto" height="auto">
</p>

you might be thinking how do the classes know about the WeatherData object? when these classes are created or instantiated, they will have that WeatherData object passed into their constructor:

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/oo.png" width="auto" height="auto">
</p>

connecting them:

```java
public class WeatherStation {

  public static void main(String[] args) {
    // create weatherdata obj
    WeatherData weatherData = new WeatherData();

    // create our displays and pass the WD obj
    CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
    StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);

    weatherData.setMeasurements(80, 65, 30.4f); // sim weather measurements

  }
}
```

right now our actual interface is a bit inflexible - espeically with the `update()` method. right now we assume it will always take the same three parameters, but what if that changes? instead, our observers will take the information straight from the `WeatherData` object that they have reference to since their instantiation.

```java
public void update() {
  this.temperature = weatherData.getTemperature();
  this.humidity = weatherData.getHumidity();
  display();
}
```
