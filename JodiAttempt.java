import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;


public class JodiAttempt {

    private int count = 0;

    // Class that builds the game board
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




    // Game flow logic occurs here in main. Player turns, checking for winners, switching turns, looping back, etc.
    // Everything that happens during a game is called from within main
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Initialization
            int position = 0;
            int turn = 1; // Player 1 or 2
            String playerString = "";
            String prompt = "";
            boolean skipBoardDisplay = false; // Explained in main game loop logic below

        // Create the game board
        JodiAttempt connectFourBoard = new JodiAttempt(6, 7);


        // Loops until a winner or tie is declared
        while(true) {

            // Print out game board as it currently exists, so long as skipBoardDisplay is set to false.
            // skipBoardDisplay only gets temporarily set to true if an invalid piece was selected for movement by the
            // user, so that we don't unnecessarily redisplay the board after no change was made (due to invalid input)
            if(!skipBoardDisplay) {
                connectFourBoard.printBoard();
            }


            if(turn == 1)   { playerString = "Player 1"; }
            else            { playerString = "Player 2"; }

            // Prompt either player for desired piece movement, save that to position variable
            prompt = playerString + ", please enter your move (1-7): ";
            position = promptNumberReadLine(scanner, prompt);


            // Using the move method, if the move is not allowed, print out error message and try again
            if(!connectFourBoard.move(turn, position)) {
                System.out.println("Invalid selection! Please try again.");
                skipBoardDisplay = true; // Skips displaying board at top of loop
                continue; // Jump back to start of loop
            }
            else {
                // This only triggers when a valid movement has been requested by user and then executed by move method
                skipBoardDisplay = false; // Reset skipping board display in case someone previously screwed up
            }


            // Check to see if there is now a winner. This happens right after a player makes their movement and
            // before we switch turns and start the loop over
            if(turn == 1 && connectFourBoard.winnerCheck()) {

                // P1 won, print winner & terminate game
                System.out.println("Player 1 wins!");
                connectFourBoard.printBoard();
                System.exit(0);
            }
            else if(turn == 2 && connectFourBoard.winnerCheck()) {

                // P2 won, print winner & terminate game
                System.out.println("Player 2 wins!");
                connectFourBoard.printBoard();
                System.exit(0);
            }
            else if (connectFourBoard.tieCheck()) {
                // If the tieCheck returns true, we have a tie, end game with no winner
                System.out.println("Somehow, neither player managed to win. Tie game!");
                connectFourBoard.printBoard();
                System.exit(0);
            }

            // Switch turns, no winner or tie was detected, loop continues
            if(turn == 1)   { turn = 2; }
            else            { turn = 1; }

        }
    }

    // print out the game board
    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            System.out.println(board.get(i));
        }
    }


    // Checks to see if there is a winner detected. Occurs at the end of each player's turn, before switching turns
    // Each check loops through the entire game board to check for a winner. If a winner is detected by any method,
    // return true. Whoever turn it is (whoever just played a piece) will be declared winner.
    public boolean winnerCheck() {
        return ( horizontalWin() || verticalWin() || diagonalWin() );
    }

    private boolean verticalWin() {

        // Outer loop moves through each row. Each row is its own ArrayList<Integer>. The 3rd row (where i=2) is the last
        // row we loop through. Looping to the next row would have us checking below the game board (out of bounds)
        for (int i = 0; i < rows - 3; i++) {

            // Inner loop that moves through each column, which is just the same index value in the different ArrayLists
            for (int j = 0; j < columns; j++) {

                // board.get(i) return's the i-th row, which is an ArrayList<Integer>, and get(j) returns column
                // values in that row
                if (    board.get(i).get(j) != 0 &&
                        board.get(i).get(j) == board.get(i + 1).get(j) &&
                        board.get(i).get(j) == board.get(i + 2).get(j) &&
                        board.get(i).get(j) == board.get(i + 3).get(j)) {
                    return true;
                }
            }
        }

        // If we didn't find a vertical win, return false
        return false;
    }

    private boolean horizontalWin() {

        // Outer loop moves through each row. Each row is its own ArrayList<Integer>.
        for (int i = 0; i < rows; i++) {

            // Inner loop that moves through each column, checking for consecutive game pieces in each row
            // The 4th column (where j=3) is the last column we allow the loop to check. Looping past that column on any
            // given row would mean checking past the right-hand side of the game board (out of bounds)
            for (int j = 0; j < columns - 3; j++) {

                // board.get(i) return's the i-th row, which is an ArrayList<Integer>, and get(j) returns column
                // values in that row
                if (    board.get(i).get(j) != 0 &&
                        board.get(i).get(j) == board.get(i).get(j + 1) &&
                        board.get(i).get(j) == board.get(i).get(j + 2) &&
                        board.get(i).get(j) == board.get(i).get(j + 3)) {
                    return true;
                }
            }
        }

        // If we didn't find a horizontal win, return false
        return false;
    }

    private boolean diagonalWin() {

        // We start by checking for a downward diagonal
            // Outer loop moves through each row, with each row its own unique ArrayList
            // The 3rd row (where i=2) is the last row we allow the loop to check. Looping past that row would
            // mean checking past the bottom of the game board (out of bounds)
            for (int i = 0; i < rows - 3; i++) {

                // Inner loop that moves through each column, checking for consecutive game pieces diagonally
                // The 4th column (where j=3) is the last column we allow the loop to check. Looping past that column on
                // any given row would mean checking past the right-hand side of the game board (out of bounds)
                for (int j = 0; j < columns - 3; j++) {

                    // board.get(i) return's the i-th row, which is an ArrayList<Integer>, and get(j) returns column
                    // values in that row
                    if (    board.get(i).get(j) != 0 &&
                            board.get(i).get(j) == board.get(i + 1).get(j + 1) &&
                            board.get(i).get(j) == board.get(i + 2).get(j + 2) &&
                            board.get(i).get(j) == board.get(i + 3).get(j + 3)) {
                        return true;
                    }
                }
            }

        // Then we check for an upward diagonal
            // Outer loop moves through each row. We start the loop on row 4 (where i=3), because if we started on an
            // earlier row we would end up checking past the top of the game board (out of bounds)
            for (int i = 3; i < rows; i++) {

                // Inner loop that moves through each column, checking for consecutive game pieces diagonally
                // The 4th column (where j=3) is the last column we allow the loop to check. Looping past that column on
                // any given row would mean checking past the right-hand side of the game board (out of bounds)
                for (int j = 0; j < columns - 3; j++) {

                    // board.get(i) return's the i-th row, which is an ArrayList<Integer>, and get(j) returns column
                    // values in that row
                    if (    board.get(i).get(j) != 0 &&
                            board.get(i).get(j) == board.get(i - 1).get(j + 1) &&
                            board.get(i).get(j) == board.get(i - 2).get(j + 2) &&
                            board.get(i).get(j) == board.get(i - 3).get(j + 3)) {
                        return true;
                    }
                }
            }

        // If we didn't find a diagonal win, return false
        return false;
    }

    private boolean tieCheck() {


        if(count == 42) {
            return true;
        }
        else {
            return false;
        }

    }


    // Controls game piece movement. If the move is allowed, modifies the game board and returns true. Otherwise,
    // returns false and doesn't modify the game board
    private boolean move(int player, int position) {


        int n = 5;
        int p = position-1;

        if(board.get(0).get(p) !=0)
        {
            System.out.println("That column is full!");
            return false;
        }
        if(player ==1)
        {

            while(n>=0)
            {
                if(board.get(n).get(p) !=0)
                {
                    n--;
                }
                else
                {
                    board.get(n).set(p,1);
                    count ++;
                    return true;
                }

            }
        }
        if(player ==2)
        {
            while(n>=0)
            {
                if(board.get(n).get(p) !=0)
                {
                    n--;
                }
                else
                {
                    board.get(n).set(p,2);
                    count ++;
                    return true;
                }

            }
        }


        return false;

    }



    // Scans user input, sanitizes the input because users are typically dumb, prints errors/info to console
    // Receives incoming prompt for desired user movement
    private static int promptNumberReadLine(Scanner s, String prompt) {

        int input = 0; // Initialize

        // Loops forever until valid input is received
        while(true) {
            System.out.print(prompt); // Display whatever prompt was passed through to this method

            // Checks that the user has entered an integer
            if (s.hasNextInt()) {
                input = s.nextInt(); // Scans the integer

                // Discards newline character, or even erroneous "pizza slices" that may have been typed after the int
                s.nextLine();

                // Finally, checks that the integer was a valid column [1-7]. If valid, breaks the loop
                if(input >= 1 && input <= 7) {
                    break;
                }
            }

            // This part of the loop only gets ran when invalid input is scanned. Discards the invalid input and
            // prints an error message
            s.nextLine();
            System.out.println("That was not a valid number! Please try again.");

        }

        return input; // Returns our sanitized, valid, sexy user input back to method that called this
    }


}
