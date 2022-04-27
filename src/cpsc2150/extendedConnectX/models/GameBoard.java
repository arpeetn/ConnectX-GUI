package cpsc2150.extendedConnectX.models;

/**
 * @author Arpeet Naik
 * @version 1.0
 */

/**
 * This class is used to store information about the Board itself
 *
 * @author Arpeet Naik
 * @version 1.0
 * @correspondence Board[o..MAX_ROW][0..MAX_COLUMN]
 * self = Board
 * @invariant Player1 == 'X' AND Player 2 == 'O' AND [no gaps between tokens on GameBoard]
 */

public class GameBoard extends AbsGameBoard implements IGameBoard{

    private static char [][] Board;
    private final int numRow;
    private final int numColumn;
    private final int numToWin;


    /**
     *This constructor creates the 6 x 9 Board used for the game
     *
     * @pre none
     *
     * @post creates a string of characters to form a 6 x 9 Board AND <row><column> == ' '
     */

    public GameBoard(int r, int c, int nw) {
        numRow = r;
        numColumn = c;
        numToWin = nw;

        Board = new char [numRow][numColumn];
        for(int i = 0;i < numColumn; i++){
            for(int j = 0; j < numRow; j++) {
                Board[j][i] = ' ';
            }
        }
    }

    /**
     * This method places the token in the lowest available row in each column
     *
     * @param p - the character that takes up the spot
     * @param c - the column we would like to place the token in
     * @return none
     * @pre check checkIfFree(c) AND LOWEST_COLUMN <= c < COLUMN
     * @post ([i][j] Board = = ' X ' OR ' O ') AND token is placed in the lowest row num that == ' '
     */

    public void placeToken(char p, int c) {
        int i;
        for(i = 0; i < MAX_ROW; i++)
            if(Board[i][c] == (' '))
                break;
        Board[i][c] = p;

    }

    /**
     * This method checks if position on board has a marker or blank space char
     *
     * @param pos - position of the token on the board
     * @return what is at position AND iff no marker than blank space char
     * @pre [pos is within the valid bounds]
     * @post [iff pos == 'X' OR 'O' space is occupied] AND
     * [iff pos == ' ' space is open for token placement]
     */

    public char whatsAtPos(BoardPosition pos) {
        return Board[pos.getRow()][pos.getColumn()];
    }


    /**
     * @return number of rows
     * @description returns the number of rows on the GameBoard
     * @pre none
     * @post gets number of rows
     * row = #row AND getNumRows = ROW
     */

    public int getNumRows(){

        return numRow;
    }

    /**
     * @return number of columns
     * @description returns the number of columns on the GameBoard
     * @pre none
     * @post gets number of columns
     * column = #column AND getNumColumns = COLUMN
     */
    public int getNumColumns(){

        return numColumn;
    }

    /**
     * @return number of tokens in a row needed to win the game
     * @description returns the number of tokens needed to win the game
     * @pre none
     * @post gets number of tokens to win the game
     *  getNumToWin = NUM_TO_WIN
     */
    public int getNumToWin(){

        return numToWin;
    }
}