/*Class: GamePanel
 * Purpose: Controls the overall flow of the game. Handles calls between classes to make game functional
 */


package moving;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import qlearning.Action;
import qlearning.QTable;
import qlearning.State;

public class AIMind {
	
	private static final Random random = new Random();
	
	//QTable
	QTable qTable = new QTable(); 
	private double learningRate; // Alpha
	private double discountFactor; // Gamma
	public double explorationRate; // Epsilon (exploration rate)
	
	//Constructor
	public AIMind(double learningRate, double discountFactor, double explorationRate) {
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.explorationRate = explorationRate;
    }
		
	//METHODS
	
	public Action calculateMove(State currentState) {//Decides if a random action is made or a best action is made
        Random random = new Random();
        if (random.nextDouble() < explorationRate) {
            // Choose a random action
            Action randomAction = getRandomAction();
            // Perform the random action
            return randomAction;
        } else {
            // Choose the action with the highest Q-value for the current state
            Action bestAction = getBestAction(currentState);
            // Perform the best action
            return bestAction;
        }
    }
	
	private Action getBestAction(State state) {//Finds the best actions that can be made

	    List<Action> bestActions = new ArrayList<>();
	    double maxQValue = Double.NEGATIVE_INFINITY; // Start with negative infinity

	    // Iterate over all possible actions to find the ones with the highest Q-value for the next state
	    for (Action action : Action.values()) {
	        // Get the next state resulting from taking the current action
	        State nextState = getNextState(state, action);
	        
	        // Calculate the Q-value for the next state-action pair
	        double qValue = qTable.getQValue(nextState, action);
	        // If the Q-value is higher than the current maximum, update the maximum and clear the list of best actions
	        if (qValue > maxQValue) {
	            maxQValue = qValue;
	            bestActions.clear();
	            bestActions.add(action);
	        } else if (qValue == maxQValue) { // If the Q-value is equal to the current maximum, add the action to the list
	            bestActions.add(action);
	        }
	    }
	    
	    // Randomly select one of the best actions
	    Random random = new Random();
	    Action selectedAction = bestActions.get(random.nextInt(bestActions.size()));

	    return selectedAction;
	}
	
	private State getNextState(State state, Action action) {//Get the next state depending upon the action
		
		if (action == Action.MOVE_UP) {
			return new State(state.up);
		}else if(action == Action.MOVE_DOWN) {
			return new State(state.down);
		}else if(action == Action.MOVE_LEFT) {
			return new State(state.left);
		}else  {
			return new State(state.right);
		}
	}

	
	private Action getRandomAction() { //Selects a random action
		//System.out.println("Random");
	    Action[] actions = Action.values();
	    int randomIndex = random.nextInt(actions.length); // Generate a random index
	    return actions[randomIndex]; // Return the randomly selected action
	}

	
	public void updateQValue(State state, Action action, double reward) {//Updates the Q table
       
        qTable.update(state, action, reward);
	 }

}
