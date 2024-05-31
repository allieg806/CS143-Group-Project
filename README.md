import java.util.ArrayList;
import java.util.Scanner;

public class JodiAttempt {
	
	//Build the game board
	
    private ArrayList<ArrayList<Integer>> board;
    private int rows;
    private int columns;

    public JodiAttempt(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        board = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(0); // Fill with empty slots
            }
            board.add(row);
        }
    }
    
    // print out the game board
    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            System.out.println(board.get(i));
        }
    }
    
    //This is where I struggle, how do I keep the pieces where they need to be? For example, for the first move,
    // 	they player has to choose a number between 1 and 7. 
    
    public void playFirstMoves() {
        for (int i = 0; i < columns; i++) {
            board.get(0).set(i, 1); // Player 1's token in the first row
            i++;
            if(i < columns) {
                board.get(0).set(i, 2); // Player 2's token in the first row
            }
        }
    }
    // Prompt the player to make their selection
    
    public static class ConnectFourGame {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Player 1, enter your move (column number):");
            int playerMove = scanner.nextInt();

            System.out.println("Player 1 chose column: " + playerMove);

            // You can now use the player's move in your game logic
        }
    }
    
    //Check for a winner
    
    
    
    
    

    public static void main(String[] args) {
        JodiAttempt connectFourBoard = new JodiAttempt(6, 7);
        connectFourBoard.printBoard();
    }
}
