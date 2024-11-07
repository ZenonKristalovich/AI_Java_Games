/*Class: QTable
 * Purpose: Where the learning Q-table is stored
 */


package qlearning;
import java.util.HashMap;
import java.util.Map;

public class QTable {
    private Map<State, Map<Action, Double>> table;

    public QTable() {
        this.table = new HashMap<>();
    }

    public void update(State state, Action action, double value) { // Method to update the Q-value for a state-action pair
    	//System.out.println(" Action: " + action + ", Tile: " + state.current + ", Reward set: " + value);
        if (!table.containsKey(state)) {
            table.put(state, new HashMap<>());
        }
        table.get(state).put(action, value);
    }

    public double getQValue(State state, Action action) {// Method to get the Q-value for a state-action pair
        if (!table.containsKey(state) || !table.get(state).containsKey(action)) {
            return 0; // Default Q-value
        }
        return table.get(state).get(action);
    }
}
