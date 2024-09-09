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
