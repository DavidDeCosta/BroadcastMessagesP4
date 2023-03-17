import java.io.IOException;
import java.net.*;



public class ConnectionToClient implements Runnable  // this class is used to create a new thread for each client
{
    ClientTalker clientTalker;  // this is the clientTalker that will be used to send messages to the server
    String id;                  // this is the id of the client
    Socket normalSocket;          
    ServerTalker serverTalker;  // this is the serverTalker that will be used to receive messages from the server

    ConnectionToClient(Socket normalSocket, String id) throws IOException
    {
        this.normalSocket = normalSocket;
        clientTalker = new ClientTalker( id,normalSocket);
        clientTalker.sendMessage(id);              
        new Thread(this).start();                                        // start a new thread
    }

    @Override
    public void run() 
    {
        try 
        {
            serverTalker = new ServerTalker(normalSocket);
            while (true) 
            {
                serverTalker.receiveMessage();
            } 
        }
        catch (IOException e) 
        {
            System.out.println("Connection to server lost");
        }
}
}
 