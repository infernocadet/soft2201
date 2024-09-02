# command & memento

## revision

**creational patterns:**

- factory method
  - object creation happens in the body of a method - representing an implementation of an _abstract factory method_ defined in some super class
  - both super and subclass contain other methods for manipulating the objects
- singleton
  - object creation happens in the class itself - static, private method
- prototype
  - object creation happens in the class itself - in an instance method called `clone()`
  - clone method creates a copy of the object where the clone method is called
  - key challenge of protoype is to implement the object copying method correctly - shallow or deep clone.

**behavioural patterns:**

concerns with algorithms and assignment of responsibilites between objects - how objects or classes communicate.

- e.g. observer pattern
- one class defines a set of methods representing certain behaviours
- **who calls these methods?**

### observer pattern

in this diagram:

- the states of the **_Subject_** are expected to change.
- the **_Observers_** are expected to react to such changes.
  - all observers implement a common method, such as `update()` to represent their reaction
- the **_Subject_** subsequently call all of its **_Observers_** `update()` method, it knows this because it has a registry of observers
- **_Subject_** must maintain a list of **_Observers_**, and provide methods to add or remove individual **_Observers_**.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/aa.png" width="auto" height="auto">
</p>

in the observer pattern, the observer itself initiates the call to add an observer o to the observer list maintained by subject s.

so the concrete subject will have public methods such as:

```java
public void addObserver(Observer o){
  observers.add(o)
}
```

this maintains the decoupled nature of the observer pattern.

## the command pattern

the situation is we have a remote which can control heaps of different devices.

we need to decouple the **control** and **concrete device** classes.

there is no clear hierarchy of the different classes, and different classes have different numbers of methods.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/con.png" width="auto" height="auto">
</p>

### interaction among objects

we can encapsulate the invocation through a `Command`, which implements an interface called `Execute`.

we create a hierarchy to represent individual invocations - each concrete class represents an invocation of a particular method in a particular class.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/int.png" width="auto" height="auto">
</p>

### single command implementation

we just make a Command interface. we also make Concrete Commands, which represent or call a particular method of a particular class.

its constructor will pass the specific light that the command is going to control as an instance variable. when the execute is called, then that light object will receive the execute request.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/om.png" width="auto" height="auto">
</p>

### simple control class

we have as simple Control class which takes one slot/device. there is a method that sets the command the slot will control. this can be called multiple times if the client of the code wanted to change the behaviour of the remote button. when the button is pressed, take the current command slot object and execute.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/asim.png" width="auto" height="auto">
</p>

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cas.png" width="auto" height="auto">
</p>
