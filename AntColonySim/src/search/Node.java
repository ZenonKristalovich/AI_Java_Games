/*Class: Node
 * Purpose: Nodes for our path list
 */

package search;

public class Node implements Comparable<Node> {
	
	//Variables 
	
    public int x, y;
    public int cost;
    public Node parent;
    public double f, g, h; // f = g + h
    
    
    //Constructor
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    //Compare Methods for Comparing Nodes
    
    // Compare nodes based on f value
    @Override
    public int compareTo(Node other) {
        return Double.compare(this.f, other.f);
    }
    
 // Override equals to compare nodes based on their positions
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    // Override hashCode to ensure nodes with the same position have the same hash code
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}