/* Name: Clara Martinez Rubio and Sakhile Mathunjwa
 * NetID: 29552399, 28824213
 * Assignment number: Project 4
 * Lab section: MW 325-440
 * TA: Yiwen Fan, Olivia Morton
 * “We did not collaborate with anyone on this assignment"
 */
public class Intersection {
    public String name;
    public double longitude;
    public double latitude;
    public int index;      //use when constructing graphs. Useless otherwise
    public Boolean visited;
    public double distance;
    
    public Intersection(String name, double latitude, double longitude, int index){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.index = index;
        visited = false; //all nodes are still unknown
        distance = Double.POSITIVE_INFINITY; //all distances to infinity
        
    }
}
