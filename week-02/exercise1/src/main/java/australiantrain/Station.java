package australiantrain;

public class Station {
    protected String stationName;

    public Station(String name) {
        this.stationName = name;
    }

    public String toString(){
        return this.stationName;
    }
}
