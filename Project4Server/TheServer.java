//Name: David DeCosta

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class TheServer 
{
    ServerSocket serverSocket;
    Socket normalSocket;
    BufferedReader instream;
    DataOutputStream outStream;
    String message;
    String clientID;
    ConnectionToClient connection;
    Socket clientSocket;

    HashMap<String, ConnectionToClient> clientConnections;                //HashMap of all the clients connected to the server

    public TheServer()
    {
        System.out.println("Server is running...");
        clientConnections = new HashMap<>();                                //initialize the HashMap
        setupServer();
    }

    void setupServer()
    {
        try
        {
            serverSocket = new ServerSocket(12345);
            while(true)
            {
            System.out.println("Waiting for client to connect...");
            clientSocket = serverSocket.accept();                                                     //clientSocket is the socket that connects to the client
            instream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientID = instream.readLine();     
            System.out.println("Client: " + clientID + " connected! ");                                     //read the id of the client
            connection = new ConnectionToClient(clientSocket,clientID,clientConnections);                   //create that clients personal connection
            clientConnections.put(clientID, connection);                                              //add the connection and id to the HashMap so that it can be used to send messages
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: \n" + e);
        }
    }

}