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
