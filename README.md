# Maze Generation

Maze generation code, inspired by working through [Mazes for
Programmers](https://pragprog.com/book/jbmaze/mazes-for-programmers).
Because the book is coded in Ruby I thought it would be a fun learning exercise to port its code to Java.

I use this library to generate mazes inside of Minecraft worlds via a [Spigot plugin](https://github.com/gmills82/hedgeMaze). I've also included an image renderer that will create printable images of the mazes.

Except where otherwise noted, all algorithms produce "perfect" mazes. Perfect
mazes have exactly one path between any two cells in the maze. This also means
that you designate any two cells as the start and end and guarantee that there
is a solution.

