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

    //shared objects
    private String[] playerNames;
    private String[] blackCardSelected;

    private int threadCount = -1;



    public void main(String[] args) throws IOException
    {
        //initialize shared objects
        playerNames = new String[MAX_NUMBER_OF_PLAYERS];
        blackCardSelected = new String[MAX_NUMBER_OF_PLAYERS];

        String currentBlackCard = "";
        ArrayList submittedWhiteCards = new ArrayList();
        Random r = new Random();
        int random = -1;
        int cardTzar = 1;
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
            while(playerCount == MAX_NUMBER_OF_PLAYERS)
            {
                //this block loops until every user has successfully negotiated username
                finished = true;
                do {
                    for (int i = 0; i < playerNames.length; i++)
                    {
                        if (playerNames[i] == null || playerNames[i].isEmpty())
                        {
                            finished = false;
                            break;
                        }
                    }
                } while (finished);

                // TODO deal out first hand

                // declare the card tzar
                System.out.println(playerList.indexOf(cardTzar-1) + " is the Card Tzar this turn");

                // grab a black card
                random = r.nextInt(blackDeck.size() -1);
                currentBlackCard = (blackDeck.get(random)).toString();

                // TODO send black card to each Thread
                // TODO receive submissions from each thread

                // chceck who the card tzar is
                if (cardTzar+1 > 4){
                    cardTzar = 1;
                }
                else cardTzar++;

                // TODO send submissions out for judging

                // TODO receive judgement

                // TODO send results out

            }
            System.out.println("loop d loop");

        } // </big while loop>
//        System.exit(1);
    } // </main>



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

            client = socket;

            try {
                /* get input/output streams from the client socket
                 * These represent the i/o for the persistent command connection */
                input = new Scanner(client.getInputStream());
                output = new PrintWriter(client.getOutputStream(), true);
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

            // TODO print this message out to all other users
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

