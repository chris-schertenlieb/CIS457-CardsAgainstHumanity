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
    boolean nameAccepted = false;
    String username = "";


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



            System.out.println("Please enter a username...");
            while(nameAccepted == false) {
              username = userEntry.nextLine();
              serverOutput.println(username);
              if(serverInput.next().equals("ACCEPTED")){
                System.out.println("Your username has been accepted. You are: " + username);
                nameAccepted = true;
              }

              else if (serverInput.next().equals("TAKEN")) {
                System.out.println("This username has already been taken. Please enter another");
                continue;
              }

              else {
                System.out.println("An error has occurred. Please enter another username");
                continue;
              }
            }
            System.out.println("Entering game...");
            serverOutput.println(PORT);
            ServerSocket welcomeSocket = new ServerSocket(PORT);
            Socket dataSocket = welcomeSocket.accept();
            InputStream inStream = dataSocket.getInputStream;
            OutputStream outStream = dataSocket.getOutputStream;

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
