package prototype;

public class UMLDiagramTool {
    private final UMLShapeCollection shapeCollection;

    public UMLDiagramTool(UMLShapeCollection shapeCollection) {
        this.shapeCollection = shapeCollection;
    }

    public void createShape(UMLShapeType type) {
        UMLShape shape = shapeCollection.getShape(type);
        if (shape != null) {
            UMLShape clonedShape = shape.clone();
            clonedShape.draw();
        } else {
            System.out.println("Shape not found: " + type);
        }
    }
}