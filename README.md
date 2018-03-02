# Maze Generation

Maze generation code, inspired by working through [Mazes for
Programmers](https://pragprog.com/book/jbmaze/mazes-for-programmers).
Because the book is coded in Ruby I thought it would be a fun learning exercise to code it in Java.

Eventually I would like to use this library to generate mazes inside of Minecraft worlds via a Java plugin.

Except where otherwise noted, all algorithms produce "perfect" mazes. Perfect
mazes have exactly one path between any two cells in the maze. This also means
that you designate any two cells as the start and end and guarantee that there
is a solution.

