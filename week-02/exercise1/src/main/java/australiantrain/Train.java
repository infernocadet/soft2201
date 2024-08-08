package australiantrain;

import java.util.ArrayList;
import java.util.Iterator;

public class Train {
    protected String pathName;
    protected Route route;
    protected Station currentStation;
    protected ArrayList<Passenger> passengers;
    protected ArrayList<Passenger> boardingPassengers;
    protected ArrayList<Passenger> newPassengers;
    protected ArrayList<Passenger> leavingPassengers;
    protected Station lastStation;

    public Train(String name, Route routePath) {
        this.pathName = name;
        this.route = routePath;
        this.currentStation = this.route.stations.get(0);
        this.passengers = new ArrayList<>();
        this.boardingPassengers = new ArrayList<>();
        this.newPassengers = new ArrayList<>();
        this.leavingPassengers = new ArrayList<>();
        this.lastStation = this.route.getLastStation();
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Processes passengers boarding and departing the train at the current station, and moves to the next station.
     * @return if there is a next station, or if the train has reached the end of the line.
     */
    public boolean newStation() {
        System.out.println(this);

        ArrayList<Passenger> temp = new ArrayList<>();
        Iterator<Passenger> iterator = boardingPassengers.iterator();
        while (iterator.hasNext()) {
            Passenger p = iterator.next();
            if (p.destination == this.currentStation) {
                iterator.remove();
                temp.add(p);
            }
        }

        if (!temp.isEmpty()) {
            if (temp.size() == 1) {
                System.out.printf("%d passenger departs the train at this station. They are as follows:%n", 1);
            } else {
                System.out.printf("%d passengers departs the train at this station. They are as follows:%n", temp.size());
            }
            for (Passenger p : temp) {
                System.out.println(p.passengerName);
            }
            temp.clear();
        }

        for (Passenger p : passengers) {
            if (p.origin == this.currentStation) {
                boardingPassengers.add(p);
                temp.add(p);
            }
        }

        if (!temp.isEmpty()) {
            if (temp.size() == 1) {
                System.out.printf("%d passenger boards the train at this station. They are as follows:%n", temp.size());
            } else {
                System.out.printf("%d passengers board the train at this station. They are as follows:%n", temp.size());
            }
            for (Passenger p : temp) {
                System.out.println(p.passengerName);
            }
            temp.clear();
        }

        System.out.printf("There are now %d passengers aboard this train.%n%n", boardingPassengers.size());

        return this.nextStation();
    }

    /**
     * Checks to see if there is another station the train must travel to.
     * @return a boolean - true if there is a station ahead, or false if the train has reached the end.
     */
    public boolean nextStation() {
        if (this.currentStation != this.lastStation){
            this.currentStation = this.route.getNextStation(this.currentStation);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return String.format("The %s train is currently at %s station.", this.pathName, this.currentStation);
    }
}
