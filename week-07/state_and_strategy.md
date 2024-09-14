# state and strategy

## behavioural patterns review

behavioural patterns are concerned with algorithms and how responsibilities are assigned between objects

focuses on how these classes communicate with each other:

- one class can define a set of methods representing certain behaviours - who calls these methods:
  - observer and command
- how should we design certain behaviours of a class to follow good OO design principles
  - memento, state and strategy

### command

who is responsible for invoking various methods defined in a set of classes?

- invocation can be delayed
- invocation can be triggered by a different external event (on or off)
- we might also want to undo/redo behaviours

command turns a request into an actual object which contains all the information about the request.

it decouples the sender and receiver, the object which invokes the operation (sender) is decoupled from the object that knows how to perform it (receiver).

the command object contains an action and its parameters - can execute, undo or redo the action.

invoker: this class calls the command to execute it. doesn't know the concrete class of the command, only interacts with the command interface.

receiver: this class performs the work needed for the command - responsible for execution of the actual command.

delayed execution: commands can be queued and executed at a later time.

undo/redo: storing commands allows undoing or redoing

### memento

we can save snapshots represening useful internal states of objects - creating a Memento class.

used for undo/redo and object state restoration.

encapsulation: memento pattern preserves the encapsulation of the object whose state is saved - the internal state is note exposed to any other objects.

the memento object stores the internal state of the originator object.

originator: the class whos state needs to be saved - snapshot of the originator - has the ability to restore its state from the memento.

caretaker: this class is responsible for keeping the memento - never operates or examines the contents but simply stores and passes the memento.

## state pattern

the state pattern is a behavioural design pattern which allows an object to change its behaviour when its internal state changes. useful when an object has multiple states - with different behaviours and it can transition from one state to another.

the object's field value or value combination could determine its behaviour. each meaningful value or value combination can be referred to as a state

each state is represented by a separate class - state transition is handled by the context class.

think of a gumball machine as an example: it has different states depending on the internal conditions.

- NoQuarterState: No quarter has been inserted
- HasQuarterState: A quarter has been inserted
- SoldOutState: the machine is out of gumballs
- SoldState: a gumball has been dispensed

### in the gumball

there are three important states that we can see with this

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/gst.png" width="auto" height="auto">
</p>

quarter == true (has quarters)
quarter == false && count > 0 (no quarters)
count == 0 (out of gumballs)

it is easy to see these transitions as they are mapped to methods in the `gumballmachine` class.

### state pattern structure

the state pattern provides an easy way to map the state transition diagram to code
purpose:

- allow an object to change its behaviour when internal state changes
- the object will appear to change its class when state changes
- can use subtypes of classes with different functionality to represent different states

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/stp.png" width="auto" height="auto">
</p>

at any time, a context will be in a particular state - which will be a concrete state implementing a state interface.

encapsulate each state in its own class - which implements a common interface - e.g. `State`.

participants:

- **context** (GumballMachine): The class that maintains an instance of the ConcreteState subclass and delegates state-specific requests to it.
- state: defines an interface for encapsulating the behaviour associated with a particular state of the context.
- concretestate: each subclass implements a behaviour associated with a state of the Context

### collaboration

- context delegates state-specific requests to the current ConcreteState object.
- the state objects then handle the logic for state transitions and behaviours. gumballmachine can change its current state by setting a new concretestate as its field.
- a context (gumballmachine) can pass itself as an argument to the state object handling the request. this lets the state object access the context if it is necessary.
- context is the primary interface for clients. clients can configure a context with State objects - one a context is configured, clients don't ever have to directly deal with the state objects.

## designing the gumball state interface

we have to identify methods which will change the state, and then split them into concrete states.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/co.png" width="auto" height="auto">
</p>

then we have to connect it to the context, which is the gumball machine. this is a field/attribute of the object, e.g. when a client tries to insert a quarter, this is delegated to the state object's insertQuarter method.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/cor.png" width="auto" height="auto">
</p>

### implementing a state

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/plo.png" width="auto" height="auto">
</p>

very simple implementing a state. will set the state of the machine to another state.

### implementing the context

the context will need an object to represent each state. only one copy needed for each state. a variable keeps the current state.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/uug.png" width="auto" height="auto">
</p>

for state its just like

```java
public void insertQuarter(){
  state.insertQuarter();
}
```

common requests such as refill() cna be implemented inside the context.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/bia.png" width="auto" height="auto">
</p>

### considerations for the state pattern

- who defines state transition?
- one transition is implemented in the context, but most state transitions are inside the individual state subclass

### creating and destroying state objects

can create State object only when they are needed and destroy after, or create them ahead of time and keep them

only one copy of each state subclass object is needed - using singleton is a possible approach.

- however state object needs to hold a copy of the context object. parameterised singleton constructor is a way to do it but less common

we can include passing the context as a method argument

```java
public interface State {
  public void insertQuarter(GumballMachine gm);
  public void ejectQuarter(GumballMachine gm);
  ...
}
```

for further reference please check the slides. they used an enum and stuff. should check this

## strategy pattern

pull out common behaviours, such as fly or quack behaviour into FlyBehaviour and QuackBehaviour interface.think of each set of behaviours as a family of algorithms.

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/stra.png" width="auto" height="auto">
</p>

### structure

<p align="center">
    <img src="https://github.com/infernocadet/soft2201/blob/main/mdgraphics/000.png" width="auto" height="auto">
</p>

intent: define a pattern of algorithms, encapsulate each and make them interchangeable. strategy lets the algorithm vary independently from clients that use it.

## tutorial notes

behavioural patterns focus on how objects interact and manage responsibilities, which allow for dynamic behaviour changes through abstraction and encapsulation.

patterns like State and Strategy enable objects to adjust behaviour based on internal state or algorithmic choices.

### strategy pattern

a key aspect of the behavioural pattern is that it encapsulates the information needed to vary behaviour. in the strategy pattern, the choice of algorithm (or method, essentially), is determined at runtime, where the decision of algorithm is managed by the invoking method.
