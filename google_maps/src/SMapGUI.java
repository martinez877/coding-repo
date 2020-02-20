/* Name: Clara Martinez Rubio and Sakhile Mathunjwa
 * NetID: 29552399, 28824213
 * Assignment number: Project 4
 * Lab section: MW 325-440
 * TA: Yiwen Fan, Olivia Morton
 * “We did not collaborate with anyone on this assignment"
 */
import javax.swing.Timer;
import javax.swing.JComboBox;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class SMapGUI extends JFrame{
    protected static final String FRAME_TITLE = "Street Map";
    protected static final int CANVAS_WIDTH = 800;//640;
    protected static final int CANVAS_HEIGHT = 800; //480;
    protected static final int CLOCK_PERIOD = 1000/30; // ms
    protected static final int DISPLAY_TAB_HEIGHT = 30;
    protected static final String PLAYING_MESSAGE = "  PLAYING";
    
    private StreetMap smap;
    private Graph graph;
    private int graph_size;
    private int canvas_width, canvas_height;
    private double x_min, x_max, y_min, y_max;
    private Boolean showPath;
    private String []iList;
    private Map<String, Intersection> imap;
    private ArrayList<String> path;
    private String startPosition, endPosition;
    private JPanel display_tab;
    private JLabel distanceLabel;
    private JButton fromButton, toButton, pathButton, distanceButton;
    private JComboBox<String> start;
    private JComboBox<String> end;
    
    private GroupLayout layout;
    private Timer timer;
    
    public SMapGUI(StreetMap smap, Boolean showPath, String begin, String finish) {
        super();
        //Initialize class members
        this.smap = smap;
        graph = smap.getGraph();
        iList = smap.getIntersectionsList();
        graph_size = graph.nodeCount();
        this.showPath = showPath;
        startPosition = begin;
        endPosition = finish;
        x_min = smap.getScalers()[0];
        x_max = smap.getScalers()[1];
        y_min = smap.getScalers()[2];
        y_max = smap.getScalers()[3];
        imap = smap.getIntersections();
        canvas_width = CANVAS_WIDTH;
        canvas_height = CANVAS_HEIGHT;
        path = new ArrayList<String>();
        display_tab = new JPanel();
        fromButton = new JButton("From");
        toButton = new JButton("To");
        pathButton = new JButton("Find Path");
        distanceLabel = new JLabel("Distance: 0 miles");
        layout = new GroupLayout(display_tab);
        display_tab.setLayout(layout);
        //layout.setAutoCreateGaps(true);
        //layout.setAutoCreateContainerGaps(true);
        //Add components to frame
        setResizable(true);
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true); ///
        Canvas canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBackground(Color.LIGHT_GRAY);
        add(display_tab, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(canvas, BorderLayout.SOUTH);
        display_tab.setPreferredSize(new Dimension(CANVAS_WIDTH, DISPLAY_TAB_HEIGHT));
        display_tab.setBackground(Color.LIGHT_GRAY);
        //Create ComboBox
        start = new JComboBox<String>(iList);
        end = new JComboBox<String>(iList);
        start.addActionListener(new ClickDetector());
        end.addActionListener(new ClickDetector());
        pathButton.addActionListener(new ClickDetector());
        layout.setHorizontalGroup(
                                  layout.createSequentialGroup()
                                  .addComponent(fromButton)
                                  .addComponent(start)
                                  .addGap(10, 20, 50)
                                  .addComponent(toButton)
                                  .addComponent(end)
                                  .addGap(10, 20, 50)
                                  .addComponent(pathButton)
                                  .addGap(10, 20, 50)
                                  .addComponent(distanceLabel)
                                  
                                  );
        layout.setVerticalGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(fromButton)
                                .addComponent(start)
                                .addComponent(toButton)
                                .addComponent(end)
                                .addComponent(pathButton)
                                .addComponent(distanceLabel)
                                
                                );
        addComponentListener(new FrameListener());
        timer = new Timer(CLOCK_PERIOD, new TimerHandler());
        timer.start();
        pack();
        setVisible(true);
        if(showPath && (!startPosition.equals("") && !endPosition.equals(""))){
            if(imap.containsKey(startPosition) && imap.containsKey(endPosition)){
                int s = imap.get(startPosition).index;
                int f = imap.get(endPosition).index;
                path = smap.getMinPath(s, f);
                double distance_3dp = Math.round(1000.0 * (smap.getMinDistance()/1.6))/1000.0;    //Rounds to 3 decimal places
                distanceLabel.setText("Distance: " + Double.toString(distance_3dp) + " miles");
            }
            else{
                System.out.println("Atleast one of the intersections not recognised");
            }
        }
    }
    
    public void drawCircle(Graphics g, int x, int y, int r){
        x = x - r/2;
        y = y - r/2;
        g.fillOval(x, y, r, r);
    }
    
    public void updateMap(Graphics g){
        int j = 0;
        Intersection vertex, temp;
        LinkedList<Intersection> queue = new LinkedList<Intersection>();
        boolean visited[] = new boolean[graph_size];
        queue.add((Intersection)graph.getValue(0)); //add first value of the map
        visited[0] = true; //initialize first node to visited
        while(queue.size() > 0){ //still elements left
            j++;
            vertex = queue.poll();
            int neighborsList[] = graph.neighbors(vertex.index);
            for(int i = 0; i < neighborsList.length; i++){
                temp = (Intersection) graph.getValue(neighborsList[i]);
                g.setColor(Color.BLACK);
                if(!path.isEmpty() && showPath){
                    if(path.contains(vertex.name) && path.contains(temp.name)){
                        g.setColor(Color.RED);
                        drawCircle(g, scaleX(vertex.longitude), canvas_height - scaleY(vertex.latitude), 10);
                        drawCircle(g, scaleX(temp.longitude), canvas_height - scaleY(temp.latitude), 10);
                    }
                }
                g.drawLine(scaleX(vertex.longitude), canvas_height - scaleY(vertex.latitude), scaleX(temp.longitude), canvas_height - scaleY(temp.latitude));
                if(!visited[neighborsList[i]]){
                    visited[neighborsList[i]] = true;
                    queue.add(temp);
                }
            }
            
            /* Needed for unconnected graphs */
            if((queue.size() == 0 ) && (j != graph_size)){
                for(int k = 0; k < graph_size; k++){
                    if(!visited[k]){
                        visited[k] = true;
                        queue.add((Intersection)graph.getValue(k));
                        break;
                    }
                }
            }
            
        }
        //System.out.println(j);
        
    }
    
    /* Used to linearly scale road map
     * Notes: Scaling from [min, max] to [a, b]
     * 	  f(x) = [b - a] * [x - min] / [max - min] + a
     * Returns int because that is what the draw functions takes
     */
    
    /* For x axis, a = 0, b = canvas_width */
    public int scaleX(double x){
        return (int)((double)canvas_width * (x - x_min)/(x_max - x_min) );
    }
    
    /* For y - axis, a = 0, b = canvas_height */
    public int scaleY(double y){
        return (int) ((double)canvas_height * (y - y_min)/(y_max - y_min));	
    }
    
    
    protected class Canvas extends JPanel{
        Canvas(){
            setFocusable(true);
        }
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            updateMap(g);
        }						
    }
    
    protected class FrameListener implements ComponentListener {
        public void componentHidden(ComponentEvent e){}
        public void componentMoved(ComponentEvent e){}
        public void componentShown(ComponentEvent e){}
        
        public void componentResized(ComponentEvent e){
            Rectangle rect = getBounds();
            canvas_width = rect.width;
            canvas_height = rect.height - DISPLAY_TAB_HEIGHT;
        }
    }		
    
    protected class ClickDetector implements ActionListener{	
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == start){
                JComboBox<String> b = (JComboBox<String>)e.getSource();
                startPosition = (String)b.getSelectedItem();
            }
            else if(e.getSource() == end){
                JComboBox<String> b = (JComboBox<String>)e.getSource();
                endPosition = (String)b.getSelectedItem();
            }
            else if(e.getSource() == pathButton){
                if(imap.containsKey(startPosition) && imap.containsKey(endPosition)){
                    int s = imap.get(startPosition).index;
                    int f = imap.get(endPosition).index;
                    path = smap.getMinPath(s, f);	
                    double distance_3dp = Math.round(1000.0 * (smap.getMinDistance()/1.6))/1000.0;    //Rounds to 3 decimal places	
                    distanceLabel.setText("Distance: " + Double.toString(distance_3dp) + "miles");
                }
            }
        }
    }	
    
    protected class TimerHandler implements ActionListener{
        //@Override
        public void actionPerformed(ActionEvent e) {
            repaint();			
        }
    }
}
