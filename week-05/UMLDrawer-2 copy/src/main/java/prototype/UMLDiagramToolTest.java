package prototype;

import java.util.ArrayList;
import java.util.List;

public class UMLDiagramToolTest {

    private static UMLShape getUseCaseDiagramTemplate(){
        List<UMLShape> insideShapes = new ArrayList<>();
        insideShapes.add(new Box(1000.00, 1500.00));
        for (int i = 0; i < 2; i++) {
            insideShapes.add(new Ellipse(30.00, 15.00, "use case"));
        }
        UMLShape insideContainer = new Container(insideShapes, "System Boundary");

        List<UMLShape> ucdShapes = new ArrayList<>();
        UMLShape actor = getActorTemplate();
        ucdShapes.add(actor.clone());
        ucdShapes.add(insideContainer);

        return new Container(ucdShapes, "Use Case Diagram");
    }

    private static UMLShape getActorTemplate(){
        List<UMLShape> actorShapes = new ArrayList<>();

        actorShapes.add(new Ellipse(30.00, 30.00));

        for (int i = 0; i < 5; i++) {
            actorShapes.add(new Line("solid", "none", "none"));
        }

        return new Container(actorShapes, "Actor");

    }

    private static UMLShape getUMLClassTemplate(){
        List<UMLShape> uctShapes = new ArrayList<>();
        UMLShape aRect = new Box(50.00, 20.00, "ClassName");
        UMLShape bRect = new Box(50.00, 50.00, "- variableExample: int");
        UMLShape cRect = new Box(50.00, 50.00, "+ methodExample(arg: String): void");
        uctShapes.add(aRect);
        uctShapes.add(bRect);
        uctShapes.add(cRect);

        return new Container(uctShapes, "UML Class");
    }

    public static UMLShapeCollection getUMLShapeCollection(){
        UMLShapeCollection shapeCollection = new UMLShapeCollection();

        // TO DO: Fill in with the corresponding UMLShape template object
        shapeCollection.addShape(UMLShapeType.BASIC_LINE, new Line("solid", "none", "none"));
        shapeCollection.addShape(UMLShapeType.GENERALISATION, new Line("solid", "triangle", "none"));
        shapeCollection.addShape(UMLShapeType.IMPLEMENTATION, new Line("dashed", "triangle", "none"));
        shapeCollection.addShape(UMLShapeType.USE_CASE, new Ellipse(30.00, 15.00, "use case"));
        shapeCollection.addShape(UMLShapeType.ACTOR, getActorTemplate());
        shapeCollection.addShape(UMLShapeType.USE_CASE_DIAGRAM, getUseCaseDiagramTemplate());
        shapeCollection.addShape(UMLShapeType.UML_CLASS, getUMLClassTemplate());

        return shapeCollection;
    }


    public static void main(String[] args) {

        // prepare a prototype register
        UMLShapeCollection shapeCollection = getUMLShapeCollection();

        // pass the register to the diagramTool
        UMLDiagramTool umlDiagramTool = new UMLDiagramTool(shapeCollection);
        umlDiagramTool.createShape(UMLShapeType.USE_CASE_DIAGRAM);
        System.out.println("------------------------------");
        umlDiagramTool.createShape(UMLShapeType.UML_CLASS);
    }
}