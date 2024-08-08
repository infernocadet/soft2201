package australiantrain;

public class Passenger {
    protected String passengerName;
    protected Station origin;
    protected Station destination;

    public Passenger(String name, Station start, Station end) {
        this.passengerName = name;
        this.origin = start;
        this.destination = end;
    }
}
