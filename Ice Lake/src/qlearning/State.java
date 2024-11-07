/*Class: State
 * Purpose: Stores the current state of the player
 */

package qlearning;

import java.util.Objects;

public class State {
    public TileStatus up;
    public TileStatus down;
    public TileStatus left;
    public TileStatus right;
    public TileStatus current;

    public State( TileStatus current) {
        this.current = current;
    }

    // Getters and setters for each field
    
    public void options(TileStatus up, TileStatus down, TileStatus left, TileStatus right) {
    	this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
    
    public void print() { //Prints out the State
    	System.out.println("Current Tile" + current);
    }

    @Override
    public boolean equals(Object o) { 
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(current, state.current);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current);
    }
}

