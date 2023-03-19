import java.io.IOException;
import java.net.*;

class ConnectionToClient implements Runnable  // this class is used to create a new thread for each client
{
    String id;                  // this is the id of the client
    Socket normalSocket;        // socket used to communicate with client

    Talker talker;
    ConnectionToClient(Socket normalSocket, String id) throws IOException
    {
        this.normalSocket = normalSocket;
        talker = new Talker(normalSocket);                                // create a talker for this client 
        talker.sendMessage(id);                                           //send the id to the server                           
        new Thread(this).start();                                        //client gets its own thread
    }

    @Override
    public void run() 
    {
        try 
        {
            while (true) 
            {
                talker.receiveMessage();                        //always ready to receive message from server
            } 
        }
        catch (IOException e) 
        {
            System.out.println("Connection to server lost");
        }
}
}
 