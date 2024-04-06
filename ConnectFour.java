
/**
 * Connect Four is a two-player board game in which the players alternately drop colored
disks into a seven-column, six-row vertically suspended grid
 *
 * @author Ruvarashe Sadya
 * @version 1.0
*/
import java.util.Scanner;
import java.util.Random;

public class ConnectFour {
    private static Scanner scanner; // So it's accessible by all methods

    /**
     * Main method to run the Connect Four game.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String args[]) {

        char[][] board = createBoard(); // Creates the connect 4 board

        displayBoard(board); // Displays the board without anything yet

        // Select random color between red and yellow as the start color
        String promptColor = setPromptColor();

        int columnNum = getColumnNum(promptColor); // Get the column selection from the user
        columnNum = convertColumn(columnNum); // Change user column to usable index considering dashes

        // Set start character in accordance with the prompt color
        char playChar = setPlayChar(promptColor);

        // Conditions for checking and playing the game
        boolean winMade = false;

        // Has to run while the winning play has not been made
        while (!winMade) {

            // get the row number for the blank spaces ALSO plays the game
            int rowNum = getRowNum(board, columnNum, promptColor);

            // If the rowNum is negative, tell the user to pick a new column
            if (rowNum < 0) {
                System.out.println("Column is full. Please choose another column.");
                columnNum = getColumnNum(promptColor);
                columnNum = convertColumn(columnNum);
                continue;
            }

            // put playChar in the board array
            board[rowNum][columnNum] = playChar;

            // Show the new board after each change
            displayBoard(board);

            // Check for win either horizontally, vertically or diagonally
            String winner = getWinner(board);

            if (winner == "") {
                // Check if there was a draw i.e that the board is filled and noone won
                if (isBoardFilled(board)) {
                    System.out.println("Boo-hoo! You Drew!");
                    break;
                }
            } else if (winner != "") {
                // Check if we do have a winner and the game is over
                System.out.println("The " + winner + " player won");
                break;
            }

            // Change values so as to give the other player a turn
            promptColor = changePromptColor(promptColor); // Change the promptColor after a play
            playChar = changePlayChar(playChar); // Change the playChar after a play
            columnNum = getColumnNum(promptColor); // Need user to be reprompted for column
            columnNum = convertColumn(columnNum); // Change user column to usable index considering dashes
        }

        scanner.close();
    }

    /**
     * Creates a new Connect Four game board.
     *
     * @return A 2D char array representing the game board
     */
    public static char[][] createBoard() {

        // Initialize a 2D array to represent the game board
        char[][] board = new char[6][16]; // made columns x2 because of the blanks I made

        // Define characters for vertical bar and blank space
        char dashUp = '|';
        char blank = ' ';

        // Populate the board array with vertical bars and blanks
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (column == 0 || column % 2 == 0) {
                    board[row][column] = dashUp;
                } else {
                    board[row][column] = blank;
                }
            }
        }

        return board;

    }

    /**
     * Displays the current Connect Four game board.
     *
     * @param board The game board to display
     */
    public static void displayBoard(char[][] board) {
        // Print the game board
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                System.out.print(board[row][column]);
            }
            System.out.println();
        }

        // Draw horizontal base line
        String baseLine = "---------------";
        System.out.print(baseLine);
        System.out.println();
    }

    /**
     * Asks the user to input a column number between 0 and 6.
     *
     * @param startColor The color of the current player
     * @return The column number chosen by the user
     */
    public static int getColumnNum(String startColor) {
        // Asks user for entry of integers between 1-6
        scanner = new Scanner(System.in);
        int columnNum; // User column selection
        do {
            System.out.print(String.format("Drop a %s disk at column (0-6): ", startColor));
            columnNum = scanner.nextInt();
            if (columnNum >= 0 && columnNum <= 6) {
                break;
            } else {
                System.out.println("Please enter a number between 0 and 6, inclusive!");
            }
        } while (true);

        return columnNum;
    }

    /**
     * Converts the column number to the appropriate index in the game board array.
     *
     * @param columnNum The column number chosen by the user
     * @return The corresponding index in the game board array
     */
    public static int convertColumn(int columnNum) {
        return 2 * columnNum + 1;
    }

    /**
     * Sets the player's character based on the color (either 'R' or 'Y').
     *
     * @param promptColor The color of the current player
     * @return The character representing the current player
     */
    public static char setPlayChar(String promptColor) {
        char playChar = ' ';
        if (promptColor.equals("red")) {
            playChar = 'R';
        } else if (promptColor.equals("yellow")) {
            playChar = 'Y';
        }
        return playChar;
    }

    /**
     * Changes the player's character from 'R' to 'Y' or vice versa.
     *
     * @param playChar The current player's character
     * @return The character representing the other player
     */
    public static char changePlayChar(char playChar) {
        if (playChar == 'R') {
            playChar = 'Y';
        } else if (playChar == 'Y') {
            playChar = 'R';
        }
        return playChar;
    }

    /**
     * Randomly selects a color ('red' or 'yellow') to start the game.
     *
     * @return The color of the player who starts the game
     */
    public static String setPromptColor() {
        String[] colors = { "red", "yellow" };
        Random random = new Random();
        int colorIndex = random.nextInt(colors.length);
        String promptColor = colors[colorIndex];
        return promptColor;
    }

    /**
     * Changes the color prompt from 'red' to 'yellow' or vice versa.
     *
     * @param promptColor The current color prompt
     * @return The new color prompt for the next turn
     */
    public static String changePromptColor(String promptColor) {
        if (promptColor.equals("red")) {
            promptColor = "yellow";
        } else if (promptColor.equals("yellow")) {
            promptColor = "red";
        }
        return promptColor;
    }

    /**
     * Determines the row number for the player's move in the game board.
     *
     * @param board       The game board
     * @param columnNum   The column number chosen by the player
     * @param promptColor The color of the current player
     * @return The row number where the player's character will be placed
     */
    public static int getRowNum(char[][] board, int columnNum, String promptColor) {
        int rowNum = 5; // Because we start counting rows from the top at therefor bottom is [5]
        while (rowNum >= 0 && board[rowNum][columnNum] != ' ') {
            rowNum -= 1; // decrease rowNum by 1 to move up
        }
        return rowNum;
    }

    /**
     * Checks if the game board is completely filled.
     *
     * @param board The game board
     * @return True if the board is filled, false otherwise
     */
    public static boolean isBoardFilled(char[][] board) {

        // Starting with the bottom most row and first column
        for (int row = 5; row >= 0; row--) {
            // Have to subtract 1 from the length because the way I printed my board, the
            // last column is space chars
            for (int col = 0; col < 14; col++) {
                char chars = board[row][col];
                if (chars == ' ') {
                    // means the index is empty
                    return false;
                }
            }
        }
        // Otherwise the board is full and there is a draw / noone wins
        return true;
    }

    /**
     * Determines if there is a winner based on the current game board.
     *
     * @param board The game board
     * @return The color of the winning player ('red' or 'yellow'), or an empty
     *         string if there is no winner yet
     */
    public static String getWinner(char[][] board) {
        // Check horizontal wins
        String winner = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 1; j < board[0].length; j = j + 2) {
                if (board[i][j] == 'R' && board[i][j + 2] == 'R' && board[i][j + 4] == 'R'
                        && board[i][j + 6] == 'R') {
                    winner = "red";
                } else if (board[i][j] == 'Y' && board[i][j + 2] == 'Y' && board[i][j + 4] == 'Y'
                        && board[i][j + 6] == 'Y') {
                    winner = "yellow";
                }

            }
        }

        // Check vertical wins
        for (int j = 1; j < board[0].length; j = j + 2) {
            for (int i = board.length - 1; i >= 0; i--) {
                if (i - 3 >= 0) {
                    if (board[i][j] == 'R' && board[i - 1][j] == 'R' && board[i - 2][j] == 'R'
                            && board[i - 3][j] == 'R') {
                        winner = "red";
                    } else if (board[i][j] == 'Y' && board[i - 1][j] == 'Y' && board[i - 2][j] == 'Y'
                            && board[i - 3][j] == 'Y') {
                        winner = "yellow";
                    }
                }
            }
        }

        // Check diagonal wins (/)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j = j + 2) {
                if (board[i][j + 1] != ' ') { // the space next right up shouldn't be empty
                    if (board[i][j + 1] == 'R' && board[i - 1][j + 3] == 'R' && board[i - 2][j + 5] == 'R'
                            && board[i - 3][j + 7] == 'R') {
                        winner = "red";
                    } else if (board[i][j + 1] == 'Y' && board[i - 1][j + 3] == 'Y' && board[i - 2][j + 5] == 'Y'
                            && board[i - 3][j + 7] == 'Y') {
                        winner = "yellow";
                    }
                }
            }
        }

        // Check diagonal wins (\)
        for (int i = 0; i < 3; i++) { // 3 because don't want it giving an index out of bounds when calc
            for (int j = 0; j < 9; j = j + 2) { // 9 as reason as above
                if (board[i][j + 1] != ' ') { // the space next left up shouldn't be empty
                    if (board[i][j + 1] == 'R' && board[i + 1][j + 3] == 'R' && board[i + 2][j + 5] == 'R'
                            && board[i + 3][j + 7] == 'R') {
                        winner = "red";
                    } else if (board[i][j + 1] == 'Y' && board[i + 1][j + 3] == 'Y' && board[i + 2][j + 5] == 'Y'
                            && board[i + 3][j + 7] == 'Y') {
                        winner = "yellow";
                    }
                }
            }
        }

        return winner;
    }

}
