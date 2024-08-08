package australiantrain;


import java.util.ArrayList;

public class Route {
    protected ArrayList<Station> stations;

    public Route(ArrayList<Station> stationList) {
        this.stations = stationList;
    }

    public Station getLastStation() {
        return stations.get(stations.size() - 1);
    }

    public Station getNextStation(Station currentStation) {
        return stations.get(stations.indexOf(currentStation) + 1);
    }
}
