//Name: David DeCosta

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class TheServer 
{
    ServerSocket serverSocket;
    Socket clientSocket;

    BufferedReader instream;
    DataOutputStream outStream;

    String message;
    String clientID;
    ConnectionToClient connection;

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
            System.out.println("Waiting for client to connect...");

            while(true)
            {
//            System.out.println("Before accept");
            clientSocket = serverSocket.accept();                                                     //clientSocket is the socket that connects to the client
//            System.out.println("After accept");
            instream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientID = instream.readLine();                                                           //read the id of the client
            System.out.println("Client: " + clientID + " connected! ");                                     
            connection = new ConnectionToClient(clientSocket,clientID,clientConnections);             //create that clients personal connection
            clientConnections.put(clientID, connection);                                              //add the connection and id to the HashMap so that it can be used to send messages
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: \n" + e);
        }
    }

}