package prototype;

import java.util.HashMap;
import java.util.Map;

public class UMLShapeCollection {
    // hashmap to store enum and UMLSHAPE
    private Map<UMLShapeType, UMLShape> shapes = new HashMap<>();

    public void addShape(UMLShapeType type, UMLShape shape) {
        shapes.put(type, shape);
    }

    public UMLShape getShape(UMLShapeType type) {
        return shapes.get(type);
    }
}
