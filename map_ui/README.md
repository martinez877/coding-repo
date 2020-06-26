# Map project in Java. Includs UI using Java Graphics
Project implements a mapping program in Java. The program reads formatted data on the intersections and roads that make up a map. It then calculates the shortest path between two intersections using Dijkstra’s algorithm. Ultimately, the program outputs a graph with a red line highlighting the shortest route utilizing Java Graphic’s GUI. 

The output graph employs a HashMap of Strings to LinkedLists. The Strings represent IntersectionIDs. The LinkedLists store the Intersection object by keeping a reference to a node Head and a pointer to an Edge. Each Edge stores the Road that the Intersection is a part of as well as a pointer to the next Edge, thus forming the LinkedList. When Intersections are first inserted into the graph they are added to a priority queue. The queue finds the shortest path in optimal time. 

Finally, to display the entire Graph, each Road is added to an ArrayList of Roads and a red 2D line is painted with the same endpoints as the Road. The program will also calculate and print the amount of time the algorithm took to compile. 

There are five classes: Graph, Intersection, Road, SMapGUI, and StreetMap. The class StreetMap contains the main method. The SMapGUI class is responsible for rendering the map. The StreetMap contains all the graphing methods that utilize the input data to construct the map. It also contains the methods that implement the algorithm. The Graph, Intersection, and Road classes support the StreetMap and SMapGUI classes.

## Notable Obstacles: 
The main obstacle was in choosing the optimal data structure. An adjacency matrix would be too costly in terms of memory (stack overflow), for the program would need to store all the 0’s for the nodes that do not have a connecting edge. An adjacency list, although it uses less memory, the run time for a big data set (such as for NY State) was very costly, with a run time of 5 minutes. Thus, the best solution was to utilize a priority queue, which decreased the run time to under a minute for any given data point. 

## Runtime analysis:
Plotting the map:
In order to display the final graph, there are two arrays: an array of objects, and an array of edges. These are iterated through in order to graph every road. Therefore, the runtime for the graph display is O(|V|) where V is the number of roads in the given map.

Finding the shortest path between 2 intersections:
After the last implementation of the priority queue, the runtime for displaying the directions between two intersections is O(|E|log(|V|)), where V is the number of intersections that form the path and E is the number of edges.
The overall runtime of the project will depend on the size of the map to be drawn and the distance between the points selected.

## Command line instructions:
A Makefile is included. Just type 'make' in the terminal to build the files.
Simply display the map:
java StreetMap p4dataset/*name of chosen map*.txt —-show
Display the map with preexisting directions given:
java StreetMap p4dataset/*name of chosen map*.txt —-show —-directions i1 i2
After the map is displayed you can change the directions from the GUI.

## Notes: 
After the graph is displayed, the algorithm still takes several seconds to display the path (line in red), and even after it does it will display as its found, so the full path is not displayed at first.
The GUI can be controlled using the drop-down lists and buttons on it. It also displays the distance between two points. It displays “No path” when no path is found. Note that when you want to use the GUI, you would have to type the command :
	java StreetMap map.txt --show --directions
or 
	java StreetMap p4dataset/*name of chosen map*.txt —-show —-directions i1 i2
