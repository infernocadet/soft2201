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
