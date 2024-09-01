package prototype;

public class Box implements UMLShape {
    private static long idCount = 1;
    private double width;
    private double height;
    private String text;
    private final long id;

    public Box(double width, double height, String text) {
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = idCount++;
    }

    public Box(double width, double height) {
        this.width = width;
        this.height = height;
        this.id = idCount++;
    }

    @Override
    public void draw() {
        if (text != null) {
            System.out.printf("%d: A Rectangle with width = %.2f and height = %.2f and text: %s\n", this.id, width, height, text);
        } else {
            System.out.printf("%d: A Rectangle with width = %.2f and height = %.2f\n", this.id, width, height);
        }
    }

    @Override
    public UMLShape clone() {
        if (this.text != null) {
            return new Box(this.width, this.height, this.text);
        } else {
            return new Box(this.width, this.height);
        }
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
