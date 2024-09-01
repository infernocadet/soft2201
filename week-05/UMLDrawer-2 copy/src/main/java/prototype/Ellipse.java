package prototype;

public class Ellipse implements UMLShape {

    private static long idCount = 1;

    /***
     * An ellipse has width 2a
     */
    private double a;

    /***
     * An ellipse has height 2b
     */
    private double b;

    private String text;

    private final long id;

    public Ellipse(double a, double b, String text){
        this.a = a;
        this.b = b;
        this.text = text;
        this.id = idCount++;
    }

    public Ellipse(double a, double b){
        this.a = a;
        this.b = b;
        this.id = idCount++;
    }

    @Override
    public void draw(){
        if (this.text != null){
            if (a == b){
                System.out.printf("%d: A circle with radius = %.2f with text: %s\n", this.id, a, text);
            } else {
                System.out.printf("%d: An ellipse with a = %.2f and b = %.2f with text: %s\n", this.id, a, b, text);
            }
        } else {
            if (a == b){
                System.out.printf("%d: A circle with radius = %.2f\n", this.id, a);
            } else {
                System.out.printf("%d: An ellipse with a = %.2f and b = %.2f\n", this.id, a, b);
            }
        }
    }

    @Override
    public UMLShape clone(){
        if (this.text != null) {
            return new Ellipse(this.a, this.b, this.text);
        } else {
            return new Ellipse(this.a, this.b);
        }
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
