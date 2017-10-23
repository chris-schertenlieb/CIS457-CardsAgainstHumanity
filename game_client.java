import java.awt.Checkbox;
import java.io.*;
import java.net.*;
import java.util.*;

public class Game_Client{
  private static InetAddress host;
  private static final int PORT = 1236;

  public static void main(String[] args)
  {
    Socket server = null;
    String message, command, response, serverName = "";
    int serverPort;

    Scanner serverInput = null;
    PrintWriter serverOutput = null;

    Scanner userEntry = new Scanner(System.in);


    try {
      do {
        {
          System.out.println("Welcome to Cards Against Humanity Online");

          message = userEntry.nextLine();
          String Tokenizer tokens = new StringTokenizer(message);
          command = tokens.nextToken();

          // user will send "JOIN" with the IP and port info
          if(command.equals("JOIN"))
          {
            if (server != null)
            {
              System.out.println("A connection with the game has already been establisheed");
              continue;
            }
            serverName = tokens.nextToken();
            serverPort = Integer.parseInt(tokens.nextToken());
            server = new Socket(serverName, serverPort);

            serverInput = new Scanner(server.getInputStream());
            serverOutput = new PrintWriter(server.getOutputStream(), true);

            System.out.println("Connection with " + serverName + " has been established");
            System.out.println("Please enter a username");
            // stuff


            // since we don't have "commands" like in the last project
            // we will want to do a big while loop that the game stuff runs in instead
            do {


            }
          }

        }
      } while ();
    }
  }
}
