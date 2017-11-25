# CIS457-CardsAgainstHumanity
A cards against humanity game playable on the command line with friends using hybrid server connections.

GAME LOGIC:

We start with the game_server starting and initializing all the Card Lists
(and some other stuff). It then waits for players to connect.

The client then starts and gets a line from the client. Join, the port, and then the port number. Once the connection is established we start doing
username registration.

We get a line in from the user with their username, we send it to the server,
and the server checks the playerList/database thing and then sends blackDeck
either "ACCEPTED" or "TAKEN", and the user either continues on or retries
to enter a username.

Assuming the username is good, they then set up a data connection

On the server side, this continues until we have 4 players, then the game begins.

HOW I ENVISION THE REST WORKING:
Server grabs 7 cards from deck and sends them to player 1, repeats this process with each player.

The server then selects a black card and sends it to each player, and awaits receiving a white card from each player. Once the server has 4 white cards, it will check which player is the card tzar this turn and then **send that username to each player**. The clients will then perform a check if(receivedUsername.equals(myUsername)) then they will receive the submissions and be given time to choose a winner. Doing it this way means that we don't have to *target* the correct thread, which could be tricky. Each other player just sees "card tzar is deciding" or something.

Then once the card tzar sends their selection, the server will send back the final result so each player can see the winner, and iterate the scoreboard, and print out the scoreboard to each player. Then the server sends a new card to each player individually and at that point, both loops (server and client) loop around and we begin again.  
