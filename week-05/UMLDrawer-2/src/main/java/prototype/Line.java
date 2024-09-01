package prototype;

class Line implements UMLShape {
    private static long idCount = 1;
    private String style;
    private String startArrow;
    private String endArrow;
    private final long id;

    public Line(String style, String startArrow, String endArrow) {
        this.style = style;
        this.startArrow = startArrow;
        this.endArrow = endArrow;
        this.id = idCount++;
    }

    @Override
    public UMLShape clone(){
        return new Line(this.style, this.startArrow, this.endArrow);
    }

    @Override
    public void draw() {
        System.out.printf("%d: A line with style: %s, start arrow: %s, end arrow: %s\n", this.id, this.style, this.startArrow, this.endArrow);
    }
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStartArrow() {
        return startArrow;
    }

    public void setStartArrow(String startArrow) {
        this.startArrow = startArrow;
    }

    public String getEndArrow() {
        return endArrow;
    }

    public void setEndArrow(String endArrow) {
        this.endArrow = endArrow;
    }
}