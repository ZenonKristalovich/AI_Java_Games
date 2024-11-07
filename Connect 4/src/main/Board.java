/*Class: Board
 * Purpose: handles all calculations required for the ai to calculate its next move
 */

package main;

public class Board {
    private int[][] board; // 2D array to represent the game board
    private final int ROWS = 6;
    private final int COLS = 7;
    GamePanel gp;
    
    // Constructor to initialize the board
    public Board(GamePanel gp) {
    	this.gp = gp;
    	
        board = new int[COLS][ROWS];
        // Initialize the board with empty spaces
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                board[i][j] = 9;
            }
        }
    }
    
    
    public boolean checkWin(int token, int x, int y) { //checks if a win occurred

    	x = x -2;
    	y = Math.abs(y - 8);
    	
    	boolean outcome = false;
    	
        // Check horizontally
        for (int col = 0; col <= COLS - 4; col++) {
            if (board[col][y] == token && board[col + 1][y] == token && 
                board[col + 2][y] == token && board[col + 3][y] == token) {
                outcome = true; // Horizontal win
            }
        }

        // Check vertically
        for (int row = 0; row <= ROWS - 4; row++) {
            if (board[x][row] == token && board[x][row + 1] == token && 
                board[x][row + 2] == token && board[x][row + 3] == token) {
            	outcome = true; // Vertical win
            }
        }

        // Check diagonally (down-right)
        for (int col = 0; col <= COLS - 4; col++) {
            for (int row = 0; row <= ROWS - 4; row++) {
                if (board[col][row] == token && board[col + 1][row + 1] == token &&
                    board[col + 2][row + 2] == token && board[col + 3][row + 3] == token) {
                	outcome = true;// Diagonal win (down-right)
                }
            }
        }

        // Check diagonally (down-left)
        for (int col = 3; col < COLS; col++) {
            for (int row = 0; row <= ROWS - 4; row++) {
                if (board[col][row] == token && board[col - 1][row + 1] == token &&
                    board[col - 2][row + 2] == token && board[col - 3][row + 3] == token) {
                	outcome = true; // Diagonal win (down-left)
                }
            }
        }

        
        if (outcome == true) {
        	gp.checkWin(token);
        }
        
        return false; // No win
    }
    
    public void cleanBoard() { //resets the board
    	for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                board[i][j] = 9;
            }
        }
    }

    
    
    public void updateBoard(int token, int x, int y) {//updates the board with data
    	
    	board[x - 2][ Math.abs(y - 8)] = token;
    }
    
    
    // Method to make a move for the AI player
    public int makeAIMove() {
        int[] move = minimax(4, Integer.MIN_VALUE, Integer.MAX_VALUE, true); // Depth limit set to 4
        return move[1]; // Return the column index of the best move
    }
    
    // mini-max algorithm with alpha-beta pruning
    private int[] minimax(int depth, int alpha, int beta, boolean maximizingBot) {//Recursive go through all possible options
        if (depth == 0) {
            int score = evaluateBoard();
            return new int[]{score, -1}; // Return the score and no move
        }
        
        if (maximizingBot) {
            int bestScore = Integer.MIN_VALUE;
            int bestMove = -1;
            for (int column = 0; column < 7; column++) { // Iterate over all columns
                if (isValidMove(column)) {
                    makeMove(column, maximizingBot); // Temporarily make the move
                    int score = evaluateBoard();
                    score = minimax(depth - 1, alpha, beta, false)[0]; // Recurse
                    undoMove(column); // Undo the move

                    if (score > bestScore) { // Update best score and move
                        bestScore = score;
                        bestMove = column;
                    }
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
                        break; // Alpha-beta pruning
                    }
                }
            }
            return new int[]{bestScore, bestMove};
        } else {
            int bestScore = Integer.MAX_VALUE;
            int bestMove = -1;
            for (int column = 0; column < 7; column++) { // Iterate over all columns
                if (isValidMove(column)) {
                    makeMove(column, maximizingBot); // Temporarily make the move
                    int score = evaluateBoard();
                    score = minimax(depth - 1, alpha, beta, true)[0]; // Recurse
                    undoMove(column); // Undo the move

                    if (score < bestScore) { // Update best score and move
                        bestScore = score;
                        bestMove = column;
                    }
                    beta = Math.min(beta, score);
                    if (beta <= alpha) {
                        break; // Alpha-beta pruning
                    }
                }
            }
            return new int[]{bestScore, bestMove};
        }
    }

    
    
    private boolean isValidMove(int column) { //Check is possible to place a token there
    	if ( board[column][5] != 9 ) {
    		return false;
    	}
    	return true;
    }
    
    private void makeMove(int column, boolean maximizingBot) { //Puts a token in given column
        for (int row = 0; row < ROWS; row++) {
            if (board[column][row] == 9) {
                board[column][row] = maximizingBot ? 1 : 0;
                break; // Exit after making the move
            }
        }
    }

    
    private void undoMove(int column) { //Takes out the top token in a column
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[column][row] != 9) {
                board[column][row] = 9;
                break; // Exit after undoing the move
            }
        }
    }
    
    private int evaluateBoard() { //Calculates the score of the current board
        int score = 0;

        // Score Horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                score += evaluatePosition(row, col, 0, 1);
            }
        }

        // Score Vertical
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                score += evaluatePosition(row, col, 1, 0);
            }
        }

        // Score Diagonal (Down-Right)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                score += evaluatePosition(row, col, 1, 1);
            }
        }

        // Score Diagonal (Up-Right)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                score += evaluatePosition(row, col, -1, 1);
            }
        }

        return score;
    }

    private int evaluatePosition(int row, int col, int deltaRow, int deltaCol) { //Calculates rewards based on token lines
        int humanTokens = 0;
        int aiTokens = 0;
        for (int i = 0; i < 4; i++) {
            // Check if the next position is within the bounds of the board
            if ((row >= 0 && row < ROWS) && (col >= 0 && col < COLS)) {
                if (board[col][row] == 1) { // AI token
                    aiTokens++;
                } else if (board[col][row] == 0) { // Human token
                    humanTokens++;
                }
            }
            row += deltaRow;
            col += deltaCol;
        }

        // Scoring based on the tokens counted in the sequence
        if (aiTokens == 4) {
            return 1000; // AI wins
        } else if (humanTokens == 4) {
            return -10000; // Opponent wins
        } else if (aiTokens == 3 && humanTokens == 0) {
            return 5; // AI has 3 tokens in a row
        } else if (humanTokens == 3 && aiTokens == 0) {
            return -500; // Opponent has 3 tokens in a row
        } else {
            return 0; // Neutral or no significant advantage
        }
    }




    

}
