/* Name: Clara Martinez Rubio and Sakhile Mathunjwa
 * NetID: 29552399, 28824213
 * Assignment number: Project 4
 * Lab section: MW 325-440
 * TA: Yiwen Fan, Olivia Morton
 * “We did not collaborate with anyone on this assignment"
 */
import java.util.*;
import java.io.*;

public class StreetMap{
    
    public static final double EARTH_RADIUS = 6371.00; //in km
    private double scalers [] = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
    private Map<String, Intersection> intersections;
    private ArrayList<String> path;
    private Map<String, Road> roads;
    private String [] intersections_list;
    private Graph graph;
    private double distance;
    private PriorityQueue<Intersection> queue;
    
    public StreetMap(String filename){ //constructor
        intersections = new HashMap<String, Intersection>();
        path = new ArrayList<String>();
        roads = new HashMap<String, Road>();
        distance = 0.0;
        try{
            loadData(filename);
        }catch(IOException e){
            e.printStackTrace();
        }
        graph = constructGraph();
    }
    
    /* Converts the degree measure to the radian measure */
    public double degreesToRadians(Double degrees) {
        return degrees * Math.PI / 180.0;
    }
    
    /* Computes the cordinates between two GPS positions */
    public double distance(Intersection i1, Intersection i2) {
        double lat1 = degreesToRadians(i1.latitude);
        double lat2 = degreesToRadians(i2.latitude);
        double dlat = degreesToRadians(i2.latitude - i1.latitude);
        double dlon = degreesToRadians(i2.longitude - i1.longitude);
        
        double a = Math.sin(dlat/2) * Math.sin(dlat/2) +
        Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon/2) * Math.sin(dlon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return Math.abs(EARTH_RADIUS * c);
    }
    
    
    /* Also used to determin x_min, x_max, y_min, and y_max */
    public void  loadData(String filename) throws FileNotFoundException{
        String line = "";
        String[] input = {};
        int i = 0;
        try{/* Put intersection information in map */
            FileReader file_reader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(file_reader);
            while((line = reader.readLine()) != null){
                input = line.split("\t");
                if(!input[0].equals("i")){
                    break;
                }
                //	System.out.println(input[1]);
                double lat = Double.parseDouble(input[2]);
                double lon = Double.parseDouble(input[3]);
                intersections.put(input[1], new Intersection(input[1], lat, lon, i++));
                scalers[0] = Math.min(scalers[0], lon);
                scalers[1] = Math.max(scalers[1], lon);
                scalers[2] = Math.min(scalers[2], lat);
                scalers[3] = Math.max(scalers[3], lat);
            }
            
            /* Put road data in road map */
            do{
                //System.out.println(input[1]);
                roads.put(input[1], new Road(input[1], input[2], input[3]));
                if((line = reader.readLine()) == null){
                    break;
                }
                input = line.split("\t");
            } while(true);
            reader.close();
        } catch(IOException e){
            System.out.println("Could not open file");
        }
    }
    
    /* Constructs a graph given a map of intersections and a map of roads starting and ending at intersections  */
    public Graph constructGraph(){
        Graph graph = new Graph();
        intersections_list = new String[intersections.size()];
        graph.init(intersections.size());
        intersections.forEach((k, v) -> {
            intersections_list[v.index] = k;
            graph.setValue(v.index, v);
        });
        roads.forEach((k, v) -> {
            int a = intersections.get(v.i1).index;
            int b = intersections.get(v.i2).index;
            double weight =  distance(intersections.get(v.i1), intersections.get(v.i2));
            graph.addEdge(a, b, weight);
        });
        return graph;
    }
    
    public void dijkstraPath(int s, int [] parent){
        Intersection u, v;
        queue = new PriorityQueue<Intersection>(graph.nodeCount(), new Comparator<Intersection>(){
            public int compare(Intersection a, Intersection b){ //inner comparator method
                if(a.distance < b.distance){
                    return -1;
                }
                else if( a.distance > b.distance){
                    return 1;
                }
                return 0; // if distances are equal
            }
        });
        for(int i = 0; i < graph.nodeCount(); i++){
            ((Intersection)graph.getValue(i)).distance = Double.POSITIVE_INFINITY;
            parent[i] = -1;
        }
        u = (Intersection)graph.getValue(s);
        u.distance = 0.0; //source node to distance 0
        queue.add(u); //add source node to the PriorityQueue
        int i = 0;
        while(!queue.isEmpty()){
            u = queue.poll();
            u.visited = true;
            int nList [] = graph.neighbors(u.index);
            for(int j = 0; j < nList.length; j++){
                v = (Intersection)graph.getValue(nList[j]);
                if(v.distance > u.distance + graph.weight(v.index, u.index)){
                    v.distance = u.distance + graph.weight(v.index, u.index);
                    parent[v.index] = u.index; //find the parent of the node
                    queue.add(v);
                }
            }
        }
    }
    //find all available paths
    public void pathFinder(int parent[], int end){
        LinkedList<String> list = new LinkedList<String>();
        while(parent[end] != -1){
            list.addFirst(((Intersection)graph.getValue(end)).name);
            end = parent[end];
        }
        int N = list.size();
        for(int i = 0; i < N; i++){
            path.add(list.pollFirst());
        }
    }

    //pick the shortest path
    public ArrayList<String> getMinPath(int start, int end){
        int [] parent = new int[graph.nodeCount()];
        dijkstraPath(start, parent);
        distance = ((Intersection)graph.getValue(end)).distance; //distance to destination
        if(distance >= Double.POSITIVE_INFINITY){
            return new ArrayList<String>();
        }
        path.clear();
        path.add(((Intersection)graph.getValue(start)).name);
        path.add(((Intersection)graph.getValue(start)).name);
        pathFinder(parent, end);
        return path;
    }
    //print 2D line of the final path
    public void printPath(String start, String end ){
        if(!(intersections.containsKey(start) && intersections.containsKey(end))){
            System.out.println("At least one of the input is not recognized!");
            return;
        }
        int i = intersections.get(start).index; //start point
        int j = intersections.get(end).index; //end point
        int [] parent = new int[graph.nodeCount()];
        dijkstraPath(i, parent);
        distance = ((Intersection)graph.getValue(j)).distance;
        if(distance >= Double.POSITIVE_INFINITY){
            return;
        }
        path.clear();
        path.add(start);
        pathFinder(parent, j);
        for(int k = 0; k < path.size(); k++){
            System.out.print(path.get(k) + "\t");
        }
        System.out.println("");
    }
    
    public double getMinDistance(){
        return distance;
    }
    
    public double[] getScalers(){
        return scalers;
    }
    
    public Map<String, Intersection> getIntersections(){
        return intersections;
    }
    
    public Map<String, Road> getRoads(){
        return roads;
    }
    
    public String[]  getIntersectionsList(){
        return intersections_list;
    }
    
    public Graph getGraph(){
        return graph;
    }
    
    /**************************** MAIN *********************************************/
    public static void main(String [] args){
        
        long startTime = System.currentTimeMillis();
        
        //command line controls
        if((args.length < 1) || (args.length > 5)){
            System.out.println("Usage: java StreetMap map.txt [--show] [--directions startIntersection endIntersection]");
            System.exit(0);
        }
        
        StreetMap smap = new StreetMap(args[0]);
        if((args.length == 2) && (args[1].equals("--show"))){
            SMapGUI map = new SMapGUI(smap, false, "", "");
        }
        else if((args.length == 3) && args[1].equals("--show") && args[2].equals("--directions")){
            SMapGUI map = new SMapGUI(smap, true, "", "");
            
        }
        else if((args.length == 4) && args[1].equals("--directions")){
            smap.printPath(args[2], args[3]);
            double distance = smap.getMinDistance();
            if(distance >= Double.POSITIVE_INFINITY){
                System.out.println("Destination unreachable from starting point");
                System.exit(0);
            }
            else{
                System.out.printf("Distance: %.3f miles\n",  distance/1.6);
            }
            
        }
        else if((args.length == 5) && (args[1].equals("--show")) && (args[2].equals("--directions"))){
            SMapGUI map = new SMapGUI(smap, true, args[3], args[4]);
            smap.printPath(args[3], args[4]);
            double distance = smap.getMinDistance();
            if(distance >= Double.POSITIVE_INFINITY){
                System.out.println("Destination unreachable from starting point");
            }
            else{
                System.out.printf("Distance: %.3f miles\n",  distance/1.6);
            }
        }
        else if(args.length != 1){
            System.out.println("Command not understood!");
            System.out.println("Usage: java StreetMap map.txt [--show] [--directions startIntersection endIntersection]");
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime-startTime;
        System.out.println("\n Program Time: " + Math.round( 1000.0 * elapsedTime)/1000.0 + " ms.");
    }
}
