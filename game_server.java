import java.io.*;
import java.net.*;
import java.util.*;

public class Game_Server {
  private static ServerSocket welcomeSocket;
  private static final int PORT = 1234;


  public static void main(String[] args) throws IOException
  {
    public int playerCount = 0;
    public ArrayList playerList = new ArrayList();
    public boolean nameCheck =  false;
    try
    {
      welcomeSocket = new ServerSocket(PORT);
    catch (IOException ioEx)
    {
      System.out.println("\nUnable to set up port!");
      System.exit(1);
    }

    do
    {
      Socket client = welcomeSocket.accept();
      playerCount += 1;
      System.out.println("New Player Accepted");

      Client handler = new ClientHandler(client);
      handler.start();

      }
    }while(true);
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

    public ClientHandler(Socket socket)
    {
        client = socket;

        try
        {
            /* get input/output streams from the client socket
             * These represent the i/o for the persistent command connection */
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);

            System.out.println("Getting name for new user, player " + playerCount+1);
            while(nameCheck == false){
              name = input.nextLine();

              for (int i = 0; i < playerList.length(); i++){
                if(name.equalsIgnoreCase(playerList.get(i)))
                {
                  // we found this name on the list, so it's taken
                  // nameCheck is still false
                  // send a message to the user and break
                  // out of the loop to get a new string from the client
                  output.println("TAKEN");
                  break;
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
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }

    public void run()
    {
      /*while(nameCheck == false){
        name = input.nextLine();

        for (int i = 0; i < playerList.length(); i++){
          if(name.equalsIgnoreCase(playerList.get(i)))
          {
            // we found this name on the list, so it's taken
            // nameCheck is still false
            // send a message to the user and break
            // out of the loop to get a new string from the client
            output.println("TAKEN");
            break;
          }
        }
        // in this case we didn't find the name, so it must not be taken.
        // add it to the list of names and
        // set nameCheck to true to exit the loop and
        // send a message to the client
        playerList.add(name);
        nameCheck = true;
        output.println("ACCEPTED");
      } */
      do {



      } while (playerList.length() > 2);
    }



}
