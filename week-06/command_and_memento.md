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

we would also need a LightOffCommand, as for every controllable method in a class, we would need a concrete command class.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/gd.png" width="auto" height="auto">
</p>

### the command pattern, in summary

- there is a client, responsible for creating a ConcreteCommand and setting its Receiver (device which is to be controlled)
- there is a Receiver, who knows how to perform the work needed to carry out the request.
- there is a Command interface, which will declare an `execute()` and maybe an `undo()` method.
- there is an Invoker, which holds a command and at some point asks the command to carry out the request by calling its `execute()` method.
- the ConcreteCommand defines the actual binding between the action and receiver. Invoker will make a request by calling `execute()` on the command interface, and the ConcreteCommand will carry it out by calling actions on the Receiver.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cc.png" width="auto" height="auto">
</p>

### collaborations

- client creates ConcreteCommand object and specifies its receiver.
- invoker object stores the ConcreteCommand object
- invoker will issue the request, calling Execute.
- concreteobject will tell the reciever to do it.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/act.png" width="auto" height="auto">
</p>

a bit of a big slide but essentially the remote control has two lists, one of the onCommands and the offCommands, initially we give them a null command for each space in each list, and then we have methods to set commands, and to execute different things based on the buttons that were pressed.

`the remote is the invoker btw.`

### the client

loads all devices and methods

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/rem.png" width="auto" height="auto">
</p>

## implementing undo

we can make it so that concrete commands provide implenmentation for undo() through declaring it in the Command interface.

in our invoker (remote) we add in a new command, called `undoCommand` and then we can stash the most recent command called into the undoCommand field.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/un.png" width="auto" height="auto">
</p>

now when the undo button is pressed, we invoke the `.undo()` method of the command stored in the undoCommand.

```java
public void undoButtonWasPushed() {
  undoCommand.undo();
}
```

### devices with multiple states

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/sta.png" width="auto" height="auto">
</p>

in order to make a command which has an undo, we create a concreteclass for one of the speed commands which holds another field, `int prevSpeed`.

in the execute function, get the current speed of the ceilingFan and store it in that variable

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/sun.png" width="auto" height="auto">
</p>

this is a bit silly, cos we need to do this for all commands. as you can see, `undo()` is shared across the different command classes.

## the memento pattern

intent: without violating encapsulation, capture and externalise an object's internal state (such as fanSpeed) so that the object can be restored to this state later.

solution: use an object (**memento**) to store a snapshot of the internal state of another object (**originator**)

### memento structure

creates a memento containing a snapshot of its current internal state. uses the memento to restore its internal state.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/mem.png" width="auto" height="auto">
</p>

the memento's sole responsibility is to store the internal state of the Originator object. its not a copy, it can just contain specific information that the Originator states. it protects against access by objects other than the originator.

the caretaker is responsible for the memento's safekeeping - it never actually operates or examines its contents. caretaker is an aggregate of Memento objects, and Memento objects do not know about the caretaker.

### collaborations

a caretaker requests a memento from an originator - holds it for a bit, then passes it back to originator.

mementos are passive - only the originator that created a memento can assign or retrieve its state.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/que.png" width="auto" height="auto">
</p>

so here, the caretaker tells the originator to create a memento, then the originator will create the memento, and set its state. the caretaker will then ask "setMemento" and the originator will get the state of the memento.

### using memento for undo

```java
public class Memento {
  private int savedSpeed;

  public Memento(int savedSpeed) {
    this.savedSpeed = savedSpeed;
  }

  public int getSavedSpeed {
    return this.savedSpeed;
  }

}
```

the originator is the only class that can create this, so they need to know how to do so:

```java
public Memento createMemento() {
  return new Memento(speed);
}

public void setMemento(Memento memento) {
  int prevSpeed = memento.getSavedSpeed();

  // if statements to check if the saved speed is high low or wtv
}
```

the CareTakers are the concretecommands which hold reference to the originator, but now also have a field which is a Memento object.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/ccm.png" width="auto" height="auto">
</p>
