package cpsc2150.extendedConnectX.models;

/**
 * @auhor Arpeet Naik
 * @version 1.0
 */

/**
 * This abstract class implements IGameBoard and has a toString function that is overridden
 *
 * @correspondences none
 *
 * @invariant none
 *
 */
public abstract class AbsGameBoard implements IGameBoard {

    /**
     *This method creates a string to show row and column position
     *
     * @return one string that shows the entire game board
     *
     * @pre none
     * @post string that shows the entire game board = {"
    |0|1|2|3|4|5|6|7|8|
    | | | | | | | | | |
    | | | | | | | | | |
    | | | | | | | | | |
    | | | | | | | | | |
    | | | | | | | | | |
    | | | | | | | | | |
    "}
     * [all of the empty spaces in the game board above will be filled with either an empty space, X, or O
     *
     * if gameboard[i][j] == ' ', the empty space will stay empty
     * if gameboard[i][j] == 'X', the empty space will have an X inside
     * if gameboard[i][j] == 'O', the empty space will have a O inside]
     *
     */

    @Override
    public String toString() {
        String sep = "|";

        for(int i = 0; i < getNumColumns(); i++) {
            if(i < 10) {
                sep = sep.concat(" ");
            }
            sep = sep.concat(Integer.toString(i));
            sep = sep.concat("|");
        }
        sep =sep.concat("\n");

        for(int i = (getNumRows() - 1); i >= 0; i--) {
            for(int k = 0; k < getNumColumns(); k++) {
                BoardPosition pos = new BoardPosition(i, k);
                char c = whatsAtPos(pos);
                sep= sep.concat("|");
                sep = sep.concat(Character.toString(c));
                sep = sep.concat(" ");
            }
            sep = sep.concat("|");
            sep = sep.concat("\n");
        }
        return sep;
    }
}