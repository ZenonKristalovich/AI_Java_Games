/*Class: AStarSearch
 * Purpose: Used for calculating the path to a goal point
 */

package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import tile.TileManager;

public class AStarSearch {
	
	//Variables
	
	private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0} };
	public List<Node> viewed = new ArrayList<>();

    public List<Node> findPath(TileManager tileM, Node start, Node goal) {//Finds the path to the Location
    	
    	double timeStart = (System.nanoTime() / 1000000000.0);
    	int [][] grid = tileM.mapTileNum;
    	start.g = 0;
        start.h = distance(start, goal);
        start.f = start.g + start.h;
    	
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        openSet.add(start);
        
        while (!openSet.isEmpty()) {
        	
        	Node current = openSet.poll();
        	viewed.add(current);
        	//Check if We Found the goal
        	if (current.x == goal.x && current.y == goal.y) {//Location Found
                return constructPath(current);
            }
        	
        	closedSet.add(current);//We viewed this node, so add it to ones we used
        	
        	for (int[] direction : DIRECTIONS) {//Look at all the options available
        		int newX = current.x + direction[0];
        		int newY = current.y + direction[1];
        		
        		Node neighbor = new Node(newX, newY);
      
        		if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid[0].length || tileM.tile[grid[newX][newY]].collision || closedSet.contains(neighbor)) {
        			continue; // Check for valid position and obstacles
                }
        		
        		//Verify Diagnol Path is possible
        		if (direction[0] != 0 && direction[1] != 0) {
        			if (tileM.tile[grid[current.x][current.y + direction[1]]].collision || tileM.tile[grid[current.x + direction[0]][current.y]].collision) {
        				continue;
        			}
        		}
        		
        		double tentativeG = current.g + tileM.tile[grid[newX][newY]].cost; // Adjust for cost of Tile

        		if (openSet.contains(neighbor) && tentativeG >= neighbor.g) {//Check if there is a current node we have at this location that has a better g score
                    continue;
                }
        		//Fill in Node info
        		neighbor.parent = current;
                neighbor.g = tentativeG;
                neighbor.h = distance(neighbor, goal);
                neighbor.f = neighbor.g + neighbor.h;
        		
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
        	}	
        	
        }
    	System.out.println("No Exit was Found");
        return Collections.emptyList(); // Return empty path if goal is not reachable
    }
    
    private static List<Node> constructPath(Node goal) {//After Finding the goal, reverse back to form the path
        List<Node> path = new ArrayList<>();
        for (Node at = goal; at != null; at = at.parent) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    
    public double distance(Node a, Node b) {//Calculate the distance between nodes
    	return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
