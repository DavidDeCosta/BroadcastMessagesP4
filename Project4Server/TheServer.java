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

    HashMap<String, ConnectionToClient> clientConnections;  //HashMap of all the clients connected to the server

    public TheServer()
    {
        System.out.println("Server is running...");
        clientConnections = new HashMap<>();                      //initialize the HashMap
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
            Socket clientSocket = serverSocket.accept();                                                     //clientSocket is the socket that connects to the client
            instream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientID = instream.readLine();                                                          //read the id of the client
            ConnectionToClient connection = new ConnectionToClient(clientSocket,clientID,clientConnections);                   //connection is the thread that handles the client
            clientConnections.put(clientID, connection);                                                     //add the connection and id to the HashMap
            System.out.println("Client " + connection.id + " connected");                                    //print out the id of the client that connected

            }
        }
        catch(Exception e)
        {
            System.out.println("Error: \n" + e);
        }
    }

}