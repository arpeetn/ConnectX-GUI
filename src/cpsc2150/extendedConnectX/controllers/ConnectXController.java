package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;
    
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */

    private final Character[] players = {'X', 'O', 'A', 'M', 'K', 'T', 'N', 'Q', 'E', 'F'};
    private int numPlayers;
    private int currPlayer;
    private boolean isOver;

    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     * 
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * 
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
        currPlayer = 0;
        isOver = false;
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     * 
     * @param col 
     *      The column of the activated button
     * 
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col) {
        if (isOver) {
            newGame();
            return;
        }

        Character player = players[currPlayer];

        screen.setMessage("It is " + player + "'s turn");

        if (!curGame.checkIfFree(col)) {
            screen.setMessage("spot not available!");
            return;
        }
        int temp = 0;
        for (int i = 0; i < curGame.getNumRows(); i++) {
            BoardPosition val = new BoardPosition(i, col);
            if (curGame.whatsAtPos(val) == ' ') {
                temp = i;
                break;
            }
        }
        curGame.placeToken(players[currPlayer], col);
        screen.setMarker(temp, col, player);


        if (curGame.checkForWin(col)) {
            screen.setMessage("Player " + player + " won! \nPress any column to start a new game");
            isOver = true;
        }
        if (!isOver) {
            currPlayer = (currPlayer + 1) % numPlayers;
            screen.setMessage("It is " + players[currPlayer] + "'s turn");
        }
        if (curGame.checkTie()) {
            screen.setMessage("It is a draw! \nPress any column to start a new game");
        }
    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     * 
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        
        //start back at the set-up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}
