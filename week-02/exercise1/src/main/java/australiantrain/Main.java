package australiantrain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public class Station {
        protected String stationName;

        Station(String name) {
            this.stationName = name;
        }
    }

    public class Route {
        protected ArrayList<Station> stations;

        Route(ArrayList<Station> givenStations) {
            this.stations = givenStations;
        }
    }

    public class Train {
        
    }

    public Main() {
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
    }

    public static void main(String[] args) {
        new Main();
        // Please add your code here to print the required information
    }
}