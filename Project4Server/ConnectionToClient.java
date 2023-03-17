import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

class ConnectionToClient implements Runnable 
{
    Socket clientSocket;
    BufferedReader instream;
    DataOutputStream outStream;
    Thread thread;
    String id;
    HashMap<String, ConnectionToClient> clientConnections;

    ConnectionToClient(Socket clientSocket, String id,HashMap<String, ConnectionToClient> clientConnections) throws IOException 
    {
        this.clientSocket = clientSocket;                       
        this.id = id;                                           
        this.clientConnections = clientConnections;                                        //  get the HashMap of all the clients connected to the server
        instream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outStream = new DataOutputStream(clientSocket.getOutputStream());          
        this.thread = new Thread(this);                                                    //  create a new thread for the client
        thread.start();        
    }


    void broadcastMessage(String id, String message) 
    {
        for (ConnectionToClient connection : clientConnections.values())         // loop through all the clients connected to the server
        {
            try 
            {
                connection.outStream.writeBytes(id + ": " + message + "\n");      // send the id and message to the clients connected
            } 
            catch (IOException e) 
            {
                System.out.println("Error writing to client " + connection.id);
            }
        }
        
    }

    @Override
    public void run() 
    {
        try 
        {
            outStream.writeBytes("Hello Client, I am the Server \n");                  
            String message;
            while ((message = instream.readLine()) != null) 
            {
                System.out.println("Message received from client " + id + ": " + message);
                broadcastMessage(id, message);                                  // broadcast the id and message to all clients
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading or writing to client (" + id + "): " + e.getMessage());
            clientConnections.remove(id);                                       // remove the client from the HashMap if they disconnect
        }
    }
}
