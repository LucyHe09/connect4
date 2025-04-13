// Connect Four is a two-player game in which the players take turns dropping tokens 
// into a six-row, seven-column vertically suspended grid
// Four of the same player’s tokens in a row either horizontally, vertically, or diagonally 
// triggers the end condition

import java.util.Scanner;

// A class to represent a game of Connect Four that extends the 
// AbstractStrategyGame class
public class ConnectFour extends AbstractStrategyGame {
    public static final char player1Emoji = '#';
    public static final char player2Emoji = '&';

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int TIE = 0;
    public static final int GAME_IS_OVER = -1;
    public static final int GAME_NOT_OVER = -1;
    public static final int columnNum = 7;

    private char[][] board;
    private int whichTurn;


    // Behavior: 
    //   - Constructor to create a new, blank Connect Four game
    public ConnectFour(){
        this.board = new char[][]{
            {'1', '2', '3', '4', '5', '6', '7'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '}
        };
        this.whichTurn = 2;
    }

    // Behavior: 
    //   - Constructs a String describing how to play the game
    // Returns:
        // - instructions returns a String describing how to play the game
    public String instructions(){ //required
        return "Welcome to Connect Four!\n\n" +
            "Instructions: \n" +
            "You and your partner will select who goes first. \n" +
            "Use the column numbers to guide which column to drop your disc in, \n" +
            "taking turns to use any open slot in the top of the grid. \n" +
            "Whoever gets four discs in a row horizontally, vertically, or diagonally wins! \n" +
            "The game will also end if the board is full " +
            "and no disks can be placed, in which the players will tie.";
    }

    // Behavior: 
    //   - toString represents the game and the current game state
    // Returns:
        // - toString returns a String that is the current game board
    public String toString() { //required
        String result = "";
        for (int i = 0; i < board.length; i++) {
            result += "|";
            for (int j = 0; j < columnNum; j++) {                
                result += " " + board[i][j] + "|";
            }
                result += "\n";
                result += "----------------------";
                result += "\n";
        }
        return result;
    }

    // Behavior: 
    //   - getNextPlayer alternates the player that is up, 
        // or identifies no player if the game is over
    // Returns:
        // getNextPlayer returns the index of which player's turn it is.
            // 1 if player 1, 2 if player 2, -1 if the game is over
    public int getNextPlayer() { //required
        if (isGameOver()) {
            return GAME_IS_OVER;
        }
        if(this.whichTurn == 1){
            this.whichTurn = 2;
            return 2;
        } else {
            this.whichTurn = 1;
            return 1;
        }
    }

    // Behavior: 
    //   - makeMove allows the player to place their token and changes the board accordingly
    // Parameters:
    //   - Scanner input that reads in user input
    // Exceptions:
        // - throws an IllegalArgumentException when given input is null
        // - throws an IllegalArgumentException when cell is out of bounds of board
    public void makeMove(Scanner input){ //required
        if(input == null){
            throw new IllegalArgumentException("input is null");
        }        
        System.out.print("Column? ");
        int column = input.nextInt();
        if(!(column < columnNum + 1  && column > 0)){
            throw new IllegalArgumentException("cell out of bounds");
        } 
        changeBoard(column, whichTurn);
    }

    // Behavior: 
    //   - changeBoard changes the board for the cell specified by the player,
        // changing the correct row and column so that the token lands at the bottom of the board
    // Parameters:
    //   - int col for the column chosen by the player
    //   - int whichTurn for which player made the move
    private void changeBoard(int col, int whichTurn){
        int row = board.length - 1;
        int column = (col - 1) % columnNum;
        for(int i = board.length - 1; i > 0; i--){
            if(board[i][column] == player1Emoji || board[i][column] == player2Emoji){
                row--;
            }
        }
        
        if(whichTurn == 1 && isEmpty(row, column)){
            board[row][column] = player1Emoji;
        } else if (whichTurn == 2 && isEmpty(row, column)) {
            board[row][column] = player2Emoji;
        } else{
            throw new IllegalArgumentException("cell not available");
        }
    }

    // Behavior: 
    //   - getWinner gets the player that won the game, whether horizontal, vertical or diagonal
    // Returns:
        // getWinner returns the index of the winner of the game
        // 1 if player 1 (#), 2 if player 2 (&), 0 if a tie occurred,
        // and -1 if the game is not over.
    public int getWinner() { //required
        for (int i = 0; i < board.length; i++) {
            // check row i
            int rowWinner = getRowWinner(i);
            if (rowWinner != GAME_NOT_OVER) {
                return rowWinner;
            }

            int colWinner = getColWinner(i);
            if (colWinner != GAME_NOT_OVER) {
                return colWinner;
            }
        }

        //check diagonals
        int diagWinner = getAscendingDiagWinner();
        if (diagWinner != GAME_NOT_OVER) {
            return diagWinner;
        }

        int diagWinner2 = getDescendingDiagWinner();
        if (diagWinner2 != GAME_NOT_OVER) {
            return diagWinner2;
        }

        return checkTie();
    }

    // Behavior: 
    //   - getRowWinner checks the board for any wins that come from four tokens in a row
    // Parameters:
    //   - int row that is the row to be checked
    // Returns:
        // - getRowWinner returns a 1 or a 2 for the player that won the row,
            // or -1 if no winner is identified
    private int getRowWinner(int row) {
        for(int i = 0; i < columnNum; i++){ //checks across the row
            if (i + 3 < columnNum){ // i should be 0, 1, 2, 3
                if (board[row][i] == board[row][i+1] && board[row][i] == board[row][i+2] &&  board[row][i] == board[row][i+3]) {
                    return getPlayer(board[row][i]);
                }
            }
        }
        return GAME_NOT_OVER;

    }

    // Behavior: 
        // - getColWinner checks the board for any wins that come 
        // from four tokens in a column
    // Parameters:
    //   - int col that is the column to be checked
    // Returns:
        // - getColWinner returns a 1 or a 2 for the player that won the column,
            // or -1 if no winner is identified
    private int getColWinner(int col) {
        for(int i = board.length - 1; i > 2; i--){ //checks up each row
            if (board[i][col] == board[i-1][col] && board[i][col] == board[i-2][col] &&  board[i][col] == board[i-3][col]) {
                return getPlayer(board[i][col]);
            }
        }

        return GAME_NOT_OVER;
    }

    // Behavior: 
    //   - getAscendingDiagWinner checks the board for any wins that come from four tokens 
        // in a ascending diagonal
    // Returns:
        // - getAscendingDiagWinner returns a 1 or a 2 for the player that won the diagonal,
            // or -1 if no winner is identified
    private int getAscendingDiagWinner() {
        for (int i = 0; i < board.length; i++) { //row
            for (int j = 0; j < columnNum; j++) {            
                //System.out.println(i + " " + j);    
                if(i > 2 && j < 4){
                    if(!isEmpty(i,j)){
                        if(board[i][j] == board[i-1][j+1] && board[i][j] == board[i-2][j+2] && board[i][j] == board[i-3][j+3]){
                            return getPlayer(board[i][j]);
                        }
                    }
                } 
            }
        }
        return GAME_NOT_OVER;
    }

    // Behavior: 
        // - getDescendingDiagWinner checks the board for any wins that come from four tokens 
        // in a descending diagonal
    // Returns:
        // - getDescendingDiagWinner returns a 1 or a 2 for the player that won the diagonal,
        // or -1 if no winner is identified
    private int getDescendingDiagWinner() {
        //for(int i = 0; i < 3; i++){ // row by row
        for(int i = 2; i >= 0; i--){ 
            for(int j = 0; j < 4; j++){ //columns
                if(board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3]){
                    return getPlayer(board[i][j]); // descending diagonal \
                    // i can be 0, 1, 2, 
                    // j can be 0, 1, 2, 3
                }
            }
        }
        return GAME_NOT_OVER;
    }

    // Behavior: 
    //   - isEmpty checks if a player has placed a token in the token
    // Parameters:
    //   - int row for the row of the cell
    //   - int col for the column of the cell
    // Returns:
        // - isEmpty returns true if the cell is empty 
        // (does not contain either a disc of player 1 or player 2)
        // - isEmpty returns false if the cell is not empty
        // (contains either a disc of player 1 or player 2)
    private boolean isEmpty(int row, int col) {
        return board[row][col] != player1Emoji && board[row][col] != player2Emoji;
    }

    // Behavior: 
    //   - checkTie checks if a the board is full and 
        // no player has four discs in a row, identifying a tie
    // Returns:
        // checkTie returns 0 if there's a tie and -1 otherwise
    private int checkTie() {
        // check for tie
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isEmpty(i, j)) {
                    return GAME_NOT_OVER;
                }
            }
        }
        return TIE;
    }  
    
    // Behavior: 
    //   - getPlayer identifies which player is assigned to a token
    // Returns:
        // getPlayer returns the player associated with the player token.
        // Returns 1 if player 1 (#), 2 if player 2 (&), -1 if the space is empty
    private int getPlayer(char token) {
        if (token == player1Emoji) {
            return PLAYER_1;
        } else if (token == player2Emoji) {
            return PLAYER_2;
        }
        return GAME_NOT_OVER;
    }
}