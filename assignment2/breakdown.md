# breakdown of pacman

## app.java

```java
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameEngine model = new GameEngineImpl("src/main/resources/config.json");
        GameWindow window = new GameWindow(model, 448, 576);

        primaryStage.setTitle("Pac-Man");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
```

- makes a new GameEngine object which is GameEngineImpl which is a subclass of GameEngine.
- passes in `"src/main/resources/config.json"` which is a resource file.

### `"src/main/resources/config.json"`

looks like this:

```json
{
  "map": "src/main/resources/map.txt",
  "numLives": 3,
  "levels": [
    {
      "levelNo": 1,
      "pacmanSpeed": 3.0,
      "ghostSpeed": {
        "chase": 1.0,
        "scatter": 1.0
      },
      "modeLengths": {
        "chase": 10,
        "scatter": 20
      }
    },
    {
      "levelNo": 2,
      "pacmanSpeed": 2.0,
      "ghostSpeed": {
        "chase": 1.2,
        "scatter": 1.3
      },
      "modeLengths": {
        "chase": 5,
        "scatter": 5
      }
    }
  ]
}
```

has a map field which just provides the location of the map file.
has a number of lives as well.
has the configs for each level e.g.

```json
{
  "levelNo": 1,
  "pacmanSpeed": 3.0,
  "ghostSpeed": {
    "chase": 1.0,
    "scatter": 1.0
  },
  "modeLengths": {
    "chase": 10,
    "scatter": 20
  }
}
```

describes the level number, speed of pacman, speed of ghosts depending on their mode, and how long the modes last.

## models - `GameEngine`

this is an interface which describes all the methods that our actual game engine will need to have. i assume that things which use the actual game engine will be relying on an instance of the interface.

```java
package pacman.model.engine;

import pacman.model.entity.Renderable;
import java.util.List;


/**
 * The base interface for interacting with the Pac-Man model
 */
public interface GameEngine {

    /**
     * Gets the list of renderables in the game
     *
     * @return The list of renderables
     */
    List<Renderable> getRenderables();

    void startGame();

    void moveUp();

    void moveDown();

    void moveLeft();

    void moveRight();

    /**
     * Instruct the model to progress forward in time by one increment.
     */
    void tick();
}
```

### `GameEngineImpl`

the actual class which implements the interface.

```java
package pacman.model.engine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pacman.model.entity.Renderable;
import pacman.model.level.Level;
import pacman.model.level.LevelImpl;
import pacman.model.maze.Maze;
import pacman.model.maze.MazeCreator;

import java.util.List;

/**
 * Implementation of GameEngine - responsible for coordinating the Pac-Man model
 */
public class GameEngineImpl implements GameEngine {

    private Level currentLevel;
    private int numLevels;
    private final int currentLevelNo;
    private Maze maze;
    private JSONArray levelConfigs;

    public GameEngineImpl(String configPath) {
        this.currentLevelNo = 0;

        init(new GameConfigurationReader(configPath));
    }

    private void init(GameConfigurationReader gameConfigurationReader) {
        // Set up map
        String mapFile = gameConfigurationReader.getMapFile();
        MazeCreator mazeCreator = new MazeCreator(mapFile);
        this.maze = mazeCreator.createMaze();
        this.maze.setNumLives(gameConfigurationReader.getNumLives());

        // Get level configurations
        this.levelConfigs = gameConfigurationReader.getLevelConfigs();
        this.numLevels = levelConfigs.size();
        if (levelConfigs.isEmpty()) {
            System.exit(0);
        }
    }

    @Override
    public List<Renderable> getRenderables() {
        return this.currentLevel.getRenderables();
    }

    @Override
    public void moveUp() {
        currentLevel.moveUp();
    }

    @Override
    public void moveDown() {
        currentLevel.moveDown();
    }

    @Override
    public void moveLeft() {
        currentLevel.moveLeft();
    }

    @Override
    public void moveRight() {
        currentLevel.moveRight();
    }

    @Override
    public void startGame() {
        startLevel();
    }

    private void startLevel() {
        JSONObject levelConfig = (JSONObject) levelConfigs.get(currentLevelNo);
        // reset renderables to starting state
        maze.reset();
        this.currentLevel = new LevelImpl(levelConfig, maze);
    }

    @Override
    public void tick() {
        currentLevel.tick();
    }

}
```

attributes include a currentLevel (a `Level` object), number of levels, current level number, a `Maze` object, and a `JSONArray` which holds all the level configs.

## MazeCreator

```java
package pacman.model.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Responsible for creating renderables and storing it in the Maze
 */
public class MazeCreator {

    private final String fileName;
    public static final int RESIZING_FACTOR = 16;

    public MazeCreator(String fileName){
        this.fileName = fileName;
    }

    public Maze createMaze(){
        File f = new File(this.fileName);
        Maze maze = new Maze();

        try {
            Scanner scanner = new Scanner(f);

            int y = 0;

            while (scanner.hasNextLine()){

                String line = scanner.nextLine();
                char[] row = line.toCharArray();

                for (int x = 0; x < row.length; x++){
                    /**
                     * TO DO: use factory method to make all the renderables. we'll have a renderable creator which
                     * other concrete factories (static entity maker, dynamic entity maker, ghost maker, collectable maker etc
                     */
                }

                y += 1;
            }

            scanner.close();
        }
        catch (FileNotFoundException e){
            System.out.println("No maze file was found.");
            exit(0);
        } catch (Exception e){
            System.out.println("Error");
            exit(0);
        }

        return maze;
    }
}
```

the maze itself holds a list of the renderables which is what we want to do here. we just want to use the function `addRenderable()`

```java
package pacman.model.maze;

import pacman.model.entity.dynamic.DynamicEntity;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.Direction;

import java.util.*;


/**
 * Stores and manages the renderables for the Pac-Man game
 */
public class Maze {

    private static final int MAX_CENTER_DISTANCE = 4;
    private final List<Renderable> renderables;
    private Renderable pacman;
    private final List<Renderable> ghosts;
    private final List<Renderable> pellets;
    private final Map<String, Boolean> isWall;
    private int numLives;

    public Maze() {
        this.renderables = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.pellets = new ArrayList<>();
        this.isWall = new HashMap<>();
    }

    /**
     * Adds the renderable to maze
     * @param renderable renderable to be added
     * @param renderableType the renderable type
     * @param x grid X position
     * @param y grid Y position
     */
    public void addRenderable(Renderable renderable, char renderableType, int x, int y) {
        if (renderable != null){
            if (renderableType == RenderableType.PACMAN){
                this.pacman = renderable;
            } else if (renderableType == RenderableType.GHOST){
                this.ghosts.add(renderable);
            } else if (renderableType == RenderableType.PELLET){
                this.pellets.add(renderable);
            } else {
                this.isWall.put(formatCoordinates(x, y), true);
            }

            this.renderables.add(renderable);
        }
    }

    private static String formatCoordinates(int x, int y){
        return String.format("(%d, %d)", x, y);
    }

    public List<Renderable> getRenderables() {
        return renderables;
    }

    public Renderable getControllable() {
        return pacman;
    }

    public List<Renderable> getGhosts() {
        return ghosts;
    }

    public List<Renderable> getPellets() {
        return pellets;
    }

    private int getCenterOfTile(int index){
        return index * MazeCreator.RESIZING_FACTOR + MazeCreator.RESIZING_FACTOR/2;
    }

    /**
     * Updates the possible directions of the dynamic entity based on the maze configuration
     */
    public void updatePossibleDirections(DynamicEntity dynamicEntity){
        int xTile = (int) Math.floor(dynamicEntity.getCenter().getX()/MazeCreator.RESIZING_FACTOR);
        int yTile = (int) Math.floor(dynamicEntity.getCenter().getY()/MazeCreator.RESIZING_FACTOR);

        Set<Direction> possibleDirections = new HashSet<>();

        // calculates whether entity is in a position where it is able to turn
        if (Math.abs(getCenterOfTile(xTile) - dynamicEntity.getCenter().getX()) < MAX_CENTER_DISTANCE &&
                Math.abs(getCenterOfTile(yTile) - dynamicEntity.getCenter().getY()) < MAX_CENTER_DISTANCE){

            String aboveCoordinates = formatCoordinates(xTile, yTile - 1);
            if (isWall.get(aboveCoordinates) == null){
                possibleDirections.add(Direction.UP);
            }

            String belowCoordinates = formatCoordinates(xTile, yTile + 1);
            if (isWall.get(belowCoordinates) == null){
                possibleDirections.add(Direction.DOWN);
            }

            String leftCoordinates = formatCoordinates(xTile - 1, yTile);
            if (isWall.get(leftCoordinates) == null){
                possibleDirections.add(Direction.LEFT);
            }

            String rightCoordinates = formatCoordinates(xTile + 1, yTile);
            if (isWall.get(rightCoordinates) == null){
                possibleDirections.add(Direction.RIGHT);
            }
        } else {
            possibleDirections.add(dynamicEntity.getDirection());
            possibleDirections.add(dynamicEntity.getDirection().opposite());
        }

        dynamicEntity.setPossibleDirections(possibleDirections);
    }


    /**
     * Returns true if possible directions indicates entity is at an intersection (i.e. can turn in at least 2 adjacent directions)
     * @param possibleDirections possible directions of entity
     * @return true, if entity is at intersection
     */
    public static boolean isAtIntersection(Set<Direction> possibleDirections) {
        // can turn
        if (possibleDirections.contains(Direction.LEFT) || possibleDirections.contains(Direction.RIGHT)) {
            return possibleDirections.contains(Direction.UP) ||
                    possibleDirections.contains(Direction.DOWN);
        }

        return false;
    }

    public void setNumLives(int numLives){
       this.numLives = numLives;
    }

    public int getNumLives() {
        return numLives;
    }

    /**
     * Resets all renderables to starting state
     */
    public void reset(){
        for (Renderable renderable : renderables){
            renderable.reset();
        }
    }
}

```

in the same package, we have an enum of mapping of characters to renderable type

```java
package pacman.model.maze;

/**
 * Mapping of characters used in map text files to renderable type
 */
public interface RenderableType {
    char HORIZONTAL_WALL = '1';
    char VERTICAL_WALL = '2';
    char UP_LEFT_WALL = '3';
    char UP_RIGHT_WALL = '4';
    char DOWN_LEFT_WALL = '5';
    char DOWN_RIGHT_WALL = '6';
    char PELLET = '7';
    char PACMAN = 'p';
    char GHOST = 'g';
}
```

now we have an `entity` package, which will hold all our `renderables`.

we have a renderable interface, which i won't dive into for now.

but we have a collection of different things (entities) which extend the `renderable` interface. we have a `StaticEntity` interface (which actual implementations of static entities implement), we have a `Collectable` interface, then we have `DynamicEntity` which is another interface that extends `Renderable`, a `Controllable` interface which extends `DynamicEntity`, a `Ghost` interface which also extends the dynamic entity interface.
