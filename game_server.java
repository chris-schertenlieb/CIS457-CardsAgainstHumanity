import java.io.*;
import java.net.*;
import java.util.*;

public class game_server {
  private static ServerSocket welcomeSocket;
  private static final int PORT = 1234;
  public int playerCount = 0;
  ArrayList playerList = new ArrayList();
  ArrayList whiteDeck = new ArrayList();
  ArrayList blackDeck = new ArrayList();
  ArrayList whiteUsed = new ArrayList();
  ArrayList blackUsed = new ArrayList();


  public void main(String[] args) throws IOException
  {
    String currentBlackCard = "";
    ArrayList submittedWhiteCards = new ArrayList();
    Random r = new Random();
    int random = -1;
    int cardTzar = 1;

    try
    {
      welcomeSocket = new ServerSocket(PORT);
    }
    catch (IOException ioEx)
    {
      System.out.println("Unable to set up port!");
      System.exit(1);
    }

    // primary loop
    while(true){
      // either we have enough players or we don't
      while(playerCount<4)
      {
        System.out.println("Waiting for new players...");
        Socket client = welcomeSocket.accept();
        System.out.println("New Player Accepted");
        ClientHandler handler = new ClientHandler(client);
        playerCount++;
        handler.start();
      }

      // yay we have enough people let's get started
      while(playerCount==4)
      {

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
      System.exit(1);
    } // </main>
} // </game_server>



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


    public ClientHandler(Socket socket)
    {
        client = socket;
        input = new Scanner(client.getInputStream());
        output = new PrintWriter(client.getOutputStream(), true);
    }

    public void run()
    {
      try
      {

        boolean nameCheck = false;
          /* get input/output streams from the client socket
           * These represent the i/o for the persistent command connection */

          System.out.println("Getting name for new user...");
          while(nameCheck == false)
          {
            name = input.nextLine();

            for (int i = 0; i < playerList.length(); i++){
              if(name.equalsIgnoreCase(playerList.get(i)))
              {
                // we found this name on the list, so it's taken
                // nameCheck is still false
                // send a message to the user and break
                // out of the loop to get a new string from the client
                output.println("TAKEN");

                break; // continue? We want to starrt the while loop at the beginning
              }
            }
            // in this case we didn't find the name, so it must not be taken.
            // add it to the list of names and
            // set nameCheck to true to exit the loop and
            // send a message to the client
            playerList.add(name);
            nameCheck = true;
            output.println("ACCEPTED");
            System.out.println("New Player " + name + " has joined");
            // FIXME print this message out to all other users
          }
      }
      catch(IOException ioEx)
      {
          ioEx.printStackTrace();
      }
    }
}
