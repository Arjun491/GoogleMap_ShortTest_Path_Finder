# Overview

This application is a re-creation of Google Maps' path finding capabilities.  Its GUI component interfaces with Google Maps.

This application will show and tell you the shortest path from a start intersection to up to seven other intersections traveled in order.  Times are displayed in minutes, and distances are displayed in miles.

This application will also show and tell you a path that is very close to the shortest path from a start intersection that visits up to seven other intersections exactly once and ends back at the starting location (the Traveling Salesperson Problem (TSP)).

# Purpose of this Application

The purpose of this application is to 1) give a user the shortest path, in terms of trip duration, from a start location to up to seven other locations (and optionally back to the start location) and 2) demonstrate the differences between various graph search algorithms.

The user can select a pre-loaded map, load their own map, select a start location, select up to seven stop locations, and select one of five pathing algorithms.  The GUI will then visually display the shortest path calculated with the selected algorithm with numbered stops and show some stats about the path.

# Our Work on This Project

The initial commit for this repository is the starter code as described in the section below.  All subsequent commits are my (Ryan Connor’s) work.  I:

1. Implemented the Main datastructure behind the the shortest path finding : BFS and Dijksta algorithm in MapEdge.java class 
2. Creating the custom map using the fecth data for  data testing 
3. Gui moified to have 3 stop points instead of two 
4. Modified the GUI to accept seven stops instead of just one.
5. Wrote tests to Cases to ensure the data is accurate 

BFS Alfo demo below:

![BFS Viz](https://user-images.githubusercontent.com/28630022/105339294-5d61ad00-5b91-11eb-928f-3baf400e1c90.png)

DFS Algo demo below:


A-Star Algo demo below: 

![aStar Viz](https://user-images.githubusercontent.com/28630022/105339065-2095b600-5b91-11eb-857e-93aba4b73bee.png)
