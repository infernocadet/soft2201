# assignment 1 overview

we are making an **application model** for a **Pac-man** game across the 3 assignments.

in Pac-man, a player navigates the character, Pac-man through a maze to consume all the dots, while avoiding the four ghosts (Blinky, Pinky, Inky and Clyde).

player uses **arrow keys** to move Pac-man vertically and horizontally. the goal is to **eat all the dots**.

- if a ghost catches Pac-man, **he loses a life** and **both Pac-man and ghosts return to starting positions**.
- game ends when Pac-man loses all his lives, indicated by Pac-man symbols.

there are three Ghost Modes: `SCATTER`, `CHASE`, and `FRIGHTENED`. this determines how they move.

- ghosts alternate between `SCATTER` and `CHASE`.
  - in `SCATTER`, ghosts move towards a pre-defined target corner of the maze.
  - in `CHASE`, the ghosts move based on unique personalities.

in `CHASE`

- Blinky tries to chase Pac-man directly
- Pinky tries to position himself in front of Pac-man
- Inky tries to target a spot relative to both Blinky and Pac-man
- Clyde tries to chase Pac-man if he is far away, but if he is close to him, he will retreat to his target corner

in the maze, there are larger flashing dots called `power pellets`. when Pac-man eats one, the ghosts turn `FRIGHTENED`. they turn blue and move around the maze randomly. Pac-man can eat ghosts to eat more Points. the ghost will return to their starting position and in `SCATTER` mode.

fruit will also appear in the maze at certain milestones and rewards the player with more points. a level is won when all the pellets are eaten. when a player completes all levels, the player wins.

## assignment 1 requirements

- your application takes in a JSON config file which contains info about the number of lives Pac-man has and map name.
  - for each level, JSON file has specific details about pac-man and ghosts.
  - example config [here](https://github.com/infernocadet/soft2201/blob/main/assignment1/example_config.json)
- the application needs to process the text file which stores the map config for all levels and also create necessary entities.
  - map is represented as a multidimensional char array. each char represents what is in that cell
    - empty space: 0
    - wall-type: 1-6
    - pellet: 7
    - ghost: g
    - Pac-man: p
  - example map file [here](https://github.com/infernocadet/soft2201/blob/main/assignment1/map.txt)
- Pac-man
  - application should support the player in navigating Pac-man using arrpw keys
  - when Pac-man eats pellet, application should reflect the change in Pac-mans score on the screen
  - when pac-man is caught by a ghost, application should indicate Pac-man has lost a life, and return Pac-man and the ghosts to starting positions
  - when Pac-man has no lives, terminate application.
  - when pac-man collected all the pellets, continue to next level. game ends when there are no more level specifications.
  - number of lives Pac-man has and speed for each level is configured in JSON file
- Ghosts
  - Application must support ghosts moving through the maze vertically and horizontally
  - in application, ghosts move according to target location. their current target location is determined by what mode they are in.
    - `NOTE: ASSIGNMENT 1 ONLY INCLUDES SCATTER AND CHASE MODE`
    - if Ghost is in `SCATTER`, target location is a pre-assigned corner of map.
    - if ghost is in `CHASE`, target location is Pac-mans position. (notice this is different from problem overview)
    - application will alternate the ghosts between these two modes. the durations of these modes are specified in JSON file.
  - speed of ghosts for each level is configured in JSON file

## task 1 - use case and use case diagram

we have to write a use case which models the game scenario:

> [!IMPORTANT] > **Player directs Pac-Man to eat a ghost.**

in use case:

- identify scope, stakeholders and interests, primary actor, pre- and post conditions, main success scenario and extesnions.
- subsequently, model the Pac-man system described in a use-case diagram.

## task 2 - sequence diagram

consider the following scenario:

> [!IMPORTANT] > **Player loses a game of Pac-Man.**

based on this scenario, design the corresponding sequence diagram which illustrates all important interactions among participating actors and system relevant to the game

```
Game scenario - **Player loses a game of Pac-Man**:
1. Player starts the game.
2. Game System places Player and Ghosts in starting position.
3. Player directs Pac-Man to move left/right/up/down through the maze.
4. Game System moves Ghosts to move through the maze.
5. Game System detects collision between Player and a Ghost.
6. Game System updates Player that they have lost a life.
7. Game System repeats steps 2-6 until all lives are lost.
8. Game System ends the game.
```

## task 3 - class diagram

produce a UML class diagram to visualise the design of the Pac-man application.

- show important classes, interfaces, associations, attributes (with visibilities), operations (with visibilities), and one instance of a composition relationship.
- maximum 400 words - concise rationale of design decisions according to OO theory (abstraction, encapsulation, inheritance and polymorphism) and design princples (SOLID and GRASP)
