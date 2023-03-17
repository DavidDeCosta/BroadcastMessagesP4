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

    public ConnectionToClient(Socket clientSocket, String id,HashMap<String, ConnectionToClient> clientConnections) throws IOException 
    {
        this.clientSocket = clientSocket;
        this.id = id;                                           //  store the ID passed in by the client
        this.clientConnections = clientConnections;             //  store the HashMap of all the clients
        instream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outStream = new DataOutputStream(clientSocket.getOutputStream());          
        this.thread = new Thread(this);                                  //  create a new thread for the client
        thread.start();        
    }


    public void broadcastMessage(String id, String message) 
    {
        for (ConnectionToClient connection : clientConnections.values()) 
        {
            try 
            {
                connection.outStream.writeBytes(id + ": " + message + "\n");  // send the id and message to the client
                connection.outStream.flush();                                 // flush the stream
            } catch (IOException e) 
            {
                System.out.println("Error writing to client " + connection.id + ": " + e.getMessage());
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
            while ((message = instream.readLine()) != null) {
                System.out.println("Message received from client " + id + ": " + message);
                broadcastMessage(id, message);  // broadcast the id and message to all clients
            }
        } catch (IOException e) 
        {
            System.out.println("Error reading or writing to client: " + e.getMessage());
        }
    }
}
