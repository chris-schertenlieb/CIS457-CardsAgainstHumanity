import java.io.*;
import java.net.*;
import java.util.*;

public class Game_Server {
  private static ServerSocket welcomeSocket;
  private static final int PORT = 1234;


  public static void main(String[] args) throws IOException
  {
    public int playerCount = 0;
    try
    {
      welcomeSocket = new ServerSocket(PORT);
    }
    catch (IOException ioEx)
    {
      System.out.println("\nUnable to set up port!");
      System.exit(1);
    }

    do
    {
      Socket client = welcomeSocket.accept();
      playerCount += 1;
      System.out.println("\nNew Player Accepted: ");

      Client handler = new ClientHandler(client);

      handler.start();
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
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }

    public void run()
    {


      do {

        received = input.nextLine();
        StringTokenizer tokens = new StringTokenizer(received);
        command = tokens.nextToken();

      } while (true);
    }



}
