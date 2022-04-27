package cpsc2150.extendedConnectX.models;

public interface IGameBoard {
    int MAX_ROW = 100;
    int MAX_COLUMN = 100;
    int MIN_ROW = 3;
    int MIN_COLUMN = 3;
    int MAX_NUM_TO_WIN = 25;
    int MIN_NUM_TO_WIN = 3;
    int MAX_PLAYERS = 10;
    int MIN_PLAYERS = 2;

    /**
     * @description This method checks to see if the column can accept a token
     *
     * @param c - the column we would like to place the token in
     * @return iff (c == ' ') then true AND iff else then false
     * @pre 0 <= c < MAX_COLUMN_NUM
     * @post iff (c == ' ') token is placed inside empty space ('X' OR 'O') AND
     * [i][j] Board include (' ' OR 'X' OR 'O') AND gameBoard = #gameBoard
     */

    default boolean checkIfFree(int c) {
        BoardPosition val = null;

        for(int i = 0; i < this.getNumRows(); ++i) {
            val = new BoardPosition(i, c);
            if (this.whatsAtPos(val) == ' ') {
                return true;
            }
        }

        return false;
    }

    void placeToken(char var1, int var2);

    /**
     * @description This method checks to see if the last token placed in the column won the game
     *
     * @param c - the column we would like to place the token in
     * @return iff c placed wins game then true AND iff else than false
     * @pre pos == [latest play/move]
     * @post iff checkHorizWin == true than win OR iff checkDiagWin == true than win
     * OR iff checkVertWin == true than win
     */

    default boolean checkForWin(int c) {
        BoardPosition temp = null;
        BoardPosition val = null;
        char character = ' ';

        for(int i = 0; i < this.getNumRows(); ++i) {
            val = new BoardPosition(i, c);
            if (this.whatsAtPos(val) == ' ') {
                temp = new BoardPosition(i - 1, c);
                character = this.whatsAtPos(temp);
                break;
            }

            if (i == this.getNumRows() - 1) {
                temp = new BoardPosition(i, c);
                character = this.whatsAtPos(temp);
                break;
            }
        }

        if (this.checkHorizWin(temp, character)) {
            return true;
        } else if (this.checkVertWin(temp, character)) {
            return true;
        } else {
            return this.checkDiagWin(temp, character);
        }
    }

    /**
     * @description This method checks the board for open spots to see if there is a tie
     *
     * @return true iff no open spots left and false iff otherwise
     * @pre [iff game is not won]
     * @post iff all pos == 'X' OR 'O' AND check win functions != win then checkTie == true
     * AND theBoard = #theBoard
     */

    default boolean checkTie() {
        BoardPosition val = null;

        for(int i = 0; i < this.getNumColumns(); ++i) {
            for(int j = 0; j < this.getNumRows(); ++j) {
                val = new BoardPosition(j, i);
                if (this.whatsAtPos(val) == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @description This method checks to see if the last token placed resulted in a horizontal win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row horizontally AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkHorizWin == true iff NUM_TO_WIN in a row horizontally and player wins] AND
     * [checkHorizWin == false iff not NUM_TO_WIN in a row horizontally and next players turn] AND
     * theBoard = #theBoard
     */

    default boolean checkHorizWin(BoardPosition pos, char p) {
        int count = 0;
        int row = pos.getRow();
        int column = pos.getColumn();
        BoardPosition temp = null;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                column = pos.getColumn();
                break;
            }

            ++count;
            if (column + 1 >= this.getNumRows()) {
                break;
            }

            ++column;
        }

        --count;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                break;
            }

            ++count;
            if (column - 1 < 0) {
                break;
            }

            --column;
        }

        return count >= this.getNumToWin();
    }

    /**
     * @description This method checks to see if the last token placed resulted in a vertical win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row vertically AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkVertWin == true iff NUM_TO_WIN in a row vertically AND player wins] AND
     * [checkVertWin == false iff not NUM_TO_WIN in a row vertically and next players turn] AND
     * theBoard = #theBoard
     */

    default boolean checkVertWin(BoardPosition pos, char p) {
        int count = 0;
        int row = pos.getRow();
        int column = pos.getColumn();
        BoardPosition temp = null;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                break;
            }

            ++count;
            if (row - 1 < 0) {
                break;
            }

            --row;
        }

        return count >= this.getNumToWin();
    }

    /**
     * @description This method checks to see if the last token placed resulted in a diagonal win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row diagonally AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkDiagWin == true iff NUM_TO_WIN in a row diagonally AND player wins] AND
     * [checkDiagWin == false iff not NUM_TO_WIN in a row vertically and next players turn] AND
     * theBoard = #theBoard
     */

    default boolean checkDiagWin(BoardPosition pos, char p) {
        int count1 = 1;
        int count2 = 1;
        int row = pos.getRow();
        int column = pos.getColumn();
        BoardPosition temp = null;
        count1 = count1 - 1;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                break;
            }

            ++count1;
            if (row + 1 >= this.getNumRows()) {
                break;
            }

            ++row;
            if (column + 1 >= this.getNumColumns()) {
                break;
            }

            ++column;
        }

        row = pos.getRow();
        column = pos.getColumn();
        count2 = count2 - 1;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                break;
            }

            ++count2;
            if (row + 1 >= this.getNumRows()) {
                break;
            }

            ++row;
            if (column - 1 < 0) {
                break;
            }

            --column;
        }

        row = pos.getRow();
        column = pos.getColumn();
        --count1;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                break;
            }

            ++count1;
            if (row - 1 < 0) {
                break;
            }

            --row;
            if (column - 1 < 0) {
                break;
            }

            --column;
        }

        row = pos.getRow();
        column = pos.getColumn();
        --count2;

        while(true) {
            temp = new BoardPosition(row, column);
            if (this.whatsAtPos(temp) != p) {
                break;
            }

            ++count2;
            if (row - 1 < 0) {
                break;
            }

            --row;
            if (column + 1 >= this.getNumColumns()) {
                break;
            }

            ++column;
        }

        row = pos.getRow();
        column = pos.getColumn();
        return count1 >= this.getNumToWin() || count2 >= this.getNumToWin();
    }

    char whatsAtPos(BoardPosition var1);

    /**
     * @description This method checks to see if player is at the position selected
     *
     * @param pos    - position of the token on the board
     * @param player - variable for player to move on board
     * @return true iff (player == pos) AND iff otherwise then false
     * @pre [pos is within valid bounds]
     * @post [function returns true if player is in the gameBoard at the specified position]
     */

    default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return this.whatsAtPos(pos) == player;
    }

    int getNumRows();

    int getNumColumns();

    int getNumToWin();
}