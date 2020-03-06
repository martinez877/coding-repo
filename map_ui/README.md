# Map project in Java. Includs UI using Java Graphics

Project implements a mapping program in Java. The program reads formatted data on the intersections and roads that make up a map, creates a graph using that data, displays the graph using a GUI with Java Graphics, and calculates the shortest path between two intersections using Dijkstra’s algorithm. The final graph representation used is a HashMap of Strings to LinkedLists, where the Strings are IntersectionIDs and the LinkedList have a node Head, that stores the Intersection object, and a pointer to an Edge, which stores a Road that the intersection is a part of. All Edges have a pointer to the next Edge, thus forming the LinkedList. A priority queue is also implemented for run time purposes. When inserting the intersections into the graph, they are first inserted into a priority queue in order to find the shortest path. Finally, to display the entire Graph, every road is added to an ArrayList of Roads and a red 2D line is painted with the same endpoints as the road. At the end, the program will also print the amount of time the algorithm took to compile. 
	There are five classes named accordingly, Graph, Intersection, Road, SMapGUI, and StreetMap. The class StreetMap contains the main method. The SMapGUI class is responsible for rendering the map. StreetMap contains all the methods that construct the graph from input data and the ones that implement the algorithm. The rest of the classes support these two.

## Notable Obstacles: 
	The most notable obstacle to overcome while doing this project was conceptualizing how to go about the project. Stages were to first to think about what was the best implementation. First dilemma was to either use an adjacency matrix or a list. If a matrix was used the program would run out of memory since it would have to store all the 0’s for the nodes that do not have a connecting edge. Although, the adjacency list did not run out of memory space, computing it for a big data sets, like NY state, took around 5 minutes. The best solution then is to implement a priority queue, making this small adjustment decreased the run time to under a minute for any point.

## Runtime analysis:
Plotting the map:
	In order to display the final graph, the are two arrays, an array of objects, and an array of edges, which are iterated through to graph every road. Therefore, the runtime for the graph display is O(|V|) where V is the number of roads in the given map.
Finding the shortest path between 2 intersections:
	After the last implementation of the priority queue, the runtime for displaying the directions between two intersections is O(|E|log(|V|)) where V is the number of intersections that form the path and E the number of edges.
	The overall runtime of the project will depend on the size of the map to be drawn and the distance between the points that you want to map.

## Command line instructions:
A Makefile is included. Just type 'make' in the terminal to build the files
Simply display the map:
java StreetMap p4dataset/*name of chosen map*.txt —-show
Display the map with preexisting directions given:
java StreetMap p4dataset/*name of chosen map*.txt —-show —-directions i1 i2
After the map is displayed you can change the directions from the GUI.

## Notes: 
	After the graph is displayed, the algorithm still takes several seconds to display the path (line in red), and even after it does it will display as its found, so the full path is not displayed at first.
	The GUI can be controlled using the drop-down lists and buttons on it. It also displays the distance between two points. It displays “No path” when no path is found. Note that when you want to use the GUI, you would have to type the command 
	java StreetMap map.txt --show --directions
or 
	java StreetMap p4dataset/*name of chosen map*.txt —-show —-directions i1 i2
