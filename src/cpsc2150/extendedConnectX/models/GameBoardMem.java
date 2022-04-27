package cpsc2150.extendedConnectX.models;
/**
 * @author Arpeet Naik
 * @version 1.0
 */

import java.util. *;

/**
 * @description This class extends AbsGameBoard and implements IGameBoard
 * This implementation is slower but more memory efficient
 *
 * @correspondence none
 *
 * @invariant numRow >= MIN_ROW AND numRow <= MAX_ROW AND numColumn >= MIN_COLUMN AND numColumn <= MAX_COLUMN
 *            AND numToWin >= MIN_NUM_TO_WIN AND numToWin <= MAX_NUM_O_WIN
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard{


    private static int numRow;
    private static int numColumn;
    private static int numToWin;

    Map<Character, List<BoardPosition>> gameBoard = new HashMap<Character, List<BoardPosition>>();

    /**
     * @description this constructor sets the private int variable equal to its corresponding values
     *
     * @param r number of rows
     * @param c number of columns
     * @param nw number of wins
     *
     * @pre none
     * @post sets the private int variables to the values passed into the constructor
     */
    public GameBoardMem(int r, int c, int nw){
        numRow = r;
        numColumn = c;
        numToWin = nw;
    }

    /**
     * @description this function places the token on a specific spot on the board
     *
     * @param p the token to be placed
     * @param c the column where the token will be placed
     *
     * @pre player selects 'm' OR player selects 'M'
     * @post each player has their own list of placed tokens that are remembered
     *       throughout the game
     */
    @Override
    public void placeToken(char p, int c){
        if(!gameBoard.containsKey(p)){
            gameBoard.put(p, new ArrayList<>());
        }
        for(int i = 0; i < getNumRows(); i++){
            if(whatsAtPos(new BoardPosition(i, c)) == ' '){
                gameBoard.get(p).add(new BoardPosition(i,c));
                break;
            }
        }
    }

    /**
     * @description this function finds what is at the position you have selected
     *
     * @param pos position of the token on the Board
     * @return ' ' a blank char
     *
     * @pre [pos is within the valid bounds]
     * @post [iff pos == 'X' OR 'O' space is occupied] AND
     *       [iff pos == ' ' space is open for token placement]
     *       [what is at the position is memorized using a HashMap]
     */
    @Override
    public char whatsAtPos(BoardPosition pos){
        for(HashMap.Entry<Character, List<BoardPosition>> map : gameBoard.entrySet()){
            if(isPlayerAtPos(pos, map.getKey())){
                return map.getKey();
            }
        }
        return ' ';
    }

    /**
     * @description This method checks to see if player is at the position selected
     *
     * @param pos    - position of the token on the board
     * @param player - variable for player to move on board
     * @return true or false
     *
     * @pre [pos is within valid bounds]
     * @post [function returns true if player is in the gameBoard at the specified position]
     *       [program memorizes if token is placed at a position]
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player){
        if(!gameBoard.containsKey(player)){
            return false;
        }
        for(BoardPosition var : gameBoard.get(player)){
            if(var.equals(pos)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNumRows() {
        return numRow;
    }

    @Override
    public int getNumColumns() {
        return numColumn;
    }

    @Override
    public int getNumToWin(){
        return numToWin;
    }
}
