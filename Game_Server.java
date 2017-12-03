import java.io.*;
import java.net.*;
import java.util.*;

public class Game_Server {
    private static ServerSocket welcomeSocket;
    private static final int PORT = 1234;
    ArrayList playerList = new ArrayList();
    ArrayList whiteDeck = new ArrayList();
    ArrayList blackDeck = new ArrayList();
    ArrayList whiteUsed = new ArrayList();
    ArrayList blackUsed = new ArrayList();
    private static final int MAX_NUMBER_OF_PLAYERS = 4;
    private static final int SIZE_OF_HAND = 10;
    private String[][] playerHands;
    private Scanner[] clientInputs;
    private PrintWriter[] clientOutputs;
    private Boolean[] waiting;
    private Boolean[] done;
    private int cardTzar;
    private String winningWhiteCard;
    private Object pointsAndStats;  //TODO: this object will contain the most recent winner and the current point totals
    private Boolean gameOver;
    private String currentBlackCard;
    private Random r;

    //shared objects
    private String[] playerNames;
    private String[] whiteCardsSelected;

    private int threadCount = -1;

	public static void main(String[] args)
    {
        try {
            Game_Server game_Server = new Game_Server();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Game_Server() throws IOException
    {
        //initialize shared objects
        playerNames = new String[MAX_NUMBER_OF_PLAYERS];
        whiteCardsSelected = new String[MAX_NUMBER_OF_PLAYERS];
        playerHands = new String[MAX_NUMBER_OF_PLAYERS][SIZE_OF_HAND];
        clientInputs = new Scanner[MAX_NUMBER_OF_PLAYERS];
        clientOutputs = new PrintWriter[MAX_NUMBER_OF_PLAYERS];
        waiting = new Boolean[MAX_NUMBER_OF_PLAYERS];
        done = new Boolean[MAX_NUMBER_OF_PLAYERS];
        gameOver = false;


        currentBlackCard = "";
        r = new Random();
        boolean finished;


        try
        {
            welcomeSocket = new ServerSocket(PORT);
        }
        catch (IOException ioEx)
        {
            System.out.println("Unable to set up port!");
            System.exit(1);
        }

        int playerCount = 0;

        // primary loop
        while(true){
            // either we have enough players or we don't
            while(playerCount<MAX_NUMBER_OF_PLAYERS)
            {
                System.out.println("Waiting for new players...");
                Socket client = welcomeSocket.accept();
                System.out.println("New Player Accepted");
                ClientHandler handler = new ClientHandler(client);
                playerCount++;
                handler.start();
            }

            // yay we have enough people let's get started
            System.out.println("Starting game...");
            while(playerCount == MAX_NUMBER_OF_PLAYERS)
            {
                //this block loops until every user has successfully negotiated username
                finished = false;
				int count = 0;
                do {
					finished = true;
					for(int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
						if(playerNames[i] == null) {
							finished = false;
						}
					}
                } while (!finished);

                //initialize the first round
                //TODO: set pointsAndStats object
                
                // grab a black card
                selectNewBlackCard();

                // generate each player's white cards for the first hand
                int player = 0;
                do {
                    for (int card = 0; card < SIZE_OF_HAND; card++)
                    {
                        String cardForPlayer = getWhiteCard();
                        playerHands[player][card] = cardForPlayer;
                    }                    
                    player++;
                } while (player < MAX_NUMBER_OF_PLAYERS);
                
                // tzar is initialized to -1 for first round increment
                cardTzar = -1;
                
                do {  //this part can loop for all normal rounds
                    
                    // threads may send each player their hand, their tzar status, and the white card up
                    unleashThreads();
                    waitForThreads();  //wait for threads to send their player their white cards
                    
                    // white cards have been sent
                    // time to determine the first tzar
                    cardTzar++;
                    if (cardTzar == MAX_NUMBER_OF_PLAYERS)
                        cardTzar = 0;
                    System.out.println(playerNames[cardTzar] + " is the Card Tzar this turn");
                    
                    unleashThreads();
                    waitForThreads();  //wait for threads to notify players if they are the tzar
    
                    // players have been notified if they're the tzar
                    // time to have threads send the black card
                    unleashThreads();
                    waitForThreads();  //wait for threads to send the black card to their players
    
                    // players have the black card
                    // main doesn't need to do anything now; just wait for players to pick their white cards
                    unleashThreads();
                    waitForThreads();  //wait for threads to get selected white cards from their players
    
                    // all white cards have been received
                    // now threads need to send the selected white cards to their users
                    unleashThreads();
                    waitForThreads();  //wait for threads to send the selected white cards out
    
                    // the list of selected white cards has been sent
                    // now wait for the tzar to make a selection
                    unleashThreads();
                    waitForThreads();  //wait for tzar to make a selection
                    
                    // tzar has selected a winner
                    //TODO: respond to tzar selection; update pointsAndStats
                    // TODO: if it's the end of the game; gameOver = true; 
                    
                    // it's the end of the round; have the threads send the winner and pointsAndStats to each user
                    unleashThreads();
                    waitForThreads();  //wait for all users to get the result
                    
                    // all users have gotten the result
                    if (!gameOver)
                    {
                        /* intitialize a new round */
                        // get a new black card
                        selectNewBlackCard();
                        
                        // replace the used white card in everyone's hand
                        for (player = 0; player < MAX_NUMBER_OF_PLAYERS; player++)
                        {
                            String lastSelectedWhiteCard = whiteCardsSelected[player];
                            int cardIndex;
                            for (cardIndex = 0; cardIndex < SIZE_OF_HAND; cardIndex++)
                            {
                                if (playerHands[player][cardIndex].equals(lastSelectedWhiteCard))
                                    break;
                            }
                            playerHands[player][cardIndex] = getWhiteCard();
                        }
                        
                        
                    }
                } while(!gameOver);
                
                //TODO: Handle game over situation
            }
            System.out.println("loop d loop");

        } // </big while loop>
//        System.exit(1);
    } // </main>



    /*****
     * Pulls a random white card out of the deck
     *   adds it to the used white cards deck
     *   and returns the card selected
     * @return
     */
    private String getWhiteCard() {
        int random = r.nextInt(whiteDeck.size() - 1);
        String cardForPlayer = (String) whiteDeck.remove(random);
        whiteUsed.add(cardForPlayer);
        return cardForPlayer;
    }


    /*****
     * selects a new Black card at random
     *   removes it from the black card deck
     *   sets it as the current black card
     *   and adds it to the black card used deck
     */
    private void selectNewBlackCard() {
        int random = r.nextInt(blackDeck.size() - 1);
        currentBlackCard = (blackDeck.get(random)).toString();
        blackDeck.remove(currentBlackCard);
        blackUsed.add(currentBlackCard);
    }



    /*****
     * The server calls this method when it wants to wait for all threads to finish their tasks
     */
    private void waitForThreads() {
        Boolean somebodyIsntDone;
        do {
            somebodyIsntDone = false;
            for (int thread = 0; thread < MAX_NUMBER_OF_PLAYERS; thread++)
            {
                if (done[thread] == false)
                    somebodyIsntDone = true;
            }
        } while (somebodyIsntDone);
    }



    /*****
     * A small method which tells all the threads they may progress
     */
    private void unleashThreads() {
        for (int thread = 0; thread < MAX_NUMBER_OF_PLAYERS; thread++)
        {
            done[thread] = false;
            waiting[thread] = false;
        }
    }



    class ClientHandler extends Thread
    {
        private Socket client;
        private Scanner input;
        private PrintWriter output;
        private int dataConnPort;
        private String command;
        private String received;
        private int dataInputPort = 1236;
        private String name;
        private int myThreadNumber;


        public ClientHandler(Socket socket)
        {
            myThreadNumber = getMyThreadNumber();
            waiting[myThreadNumber] = true;
            done[myThreadNumber] = false;

            client = socket;

            try {
                /* get input/output streams from the client socket
                 * These represent the i/o for the persistent command connection */
                input = new Scanner(client.getInputStream());
                output = new PrintWriter(client.getOutputStream(), true);

                // give inputs and outputs to the respective shared objects
                //    so master server can access them
                clientInputs[myThreadNumber] = input;
                clientOutputs[myThreadNumber] = output;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            //negotiate username
            System.out.println("Getting name for new user...");

            name = input.nextLine();

            while (!usernameAccepted(name, myThreadNumber))
            {
                output.println("TAKEN");
                name = input.nextLine();
            }

            output.println("ACCEPTED");
            System.out.println("New Player " + name + " has joined");

            /* I think this whole thing loops for every hand..... */

            while (!gameOver) {

                // wait for main to finish initializing the round
                waitForMain();

                // send out the list of cards
                sendObject(playerHands[myThreadNumber]);
                done[myThreadNumber] = true;

                // wait for server to determine card tzar
                waitForMain();

                // send boolean to client to tell them if they're the card tzar
                sendObject(myThreadNumber == cardTzar);
                done[myThreadNumber] = true;

                // wait for main to check if everyone is ready
                waitForMain();

                // send black card to user
                sendObject(currentBlackCard);
                done[myThreadNumber] = true;

                // wait for main to check if everyone is ready
                waitForMain();

                // send selected white cards to the user
                sendObject(whiteCardsSelected);
                done[myThreadNumber] = true;

                // wait for main to check that everyone is done
                waitForMain();

                // wait for user input to pick white card they want to play
                if (myThreadNumber != cardTzar) {
                    whiteCardsSelected[myThreadNumber] = input.nextLine();
                }
                else
                {
                    whiteCardsSelected[myThreadNumber] = "";
                }
                done[myThreadNumber] = true;

                // wait for main to read everyone's selection
                waitForMain();

                // send selected cards to everyone
                sendObject(whiteCardsSelected);
                done[myThreadNumber] = true;

                // wait for main to confirm that everyone has the card list
                waitForMain();

                // if tzar, wait for user to select winner; else do nothing
                if (myThreadNumber == cardTzar)
                    winningWhiteCard = input.nextLine();
                done[myThreadNumber] = true;

                // wait for main to confirm winning card
                waitForMain();

                // send winner to everyone and updated point totals
                sendObject(pointsAndStats);
                done[myThreadNumber] = true;
            }

            //TODO: cleanup for game over situation


        }

        /****
         * Send object o to the client associated with this thread.
         * The client will have to appropriately cast the object sent to it.
         * @param o
         */

        private void sendObject(Object o) {
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.writeObject(o);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /****
         * small helper that waits for main to send signal
         *   then resets signal for next wait
         */
        private void waitForMain()
        {
            while(waiting[myThreadNumber]){};

            // prep for next wait
            waiting[myThreadNumber] = true;
        }

    }

    /******
     * Threadsafe method returns a unique int which is used by each
     *   process to index shared object arrays to change values
     * @return the thread's unique number
     */
    private synchronized int getMyThreadNumber()
    {
        threadCount++;
        return threadCount;
    }

    /*****
     * If a submitted username is not taken, it needs to be immediately
     *   written to memory in a threadsafe function so that another
     *   user cannot take it
     * @param userName
     * @return
     */
    private synchronized boolean usernameAccepted(String userName, int threadNumber)
    {
        for (int i = 0; i < playerNames.length; i++)
        {
            if (playerNames[i] != null)
            {
                if (playerNames[i].equalsIgnoreCase(userName))
                    //name has been taken; return false
                    return false;
            }
        }

        //name has not been taken; write in name; return true
        playerNames[threadNumber] = userName;
        return true;
    }

} // </game_server>
