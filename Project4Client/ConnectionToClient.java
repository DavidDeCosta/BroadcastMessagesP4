import java.io.IOException;
import java.net.*;



public class ConnectionToClient implements Runnable  // this class is used to create a new thread for each client
{
    String id;                  // this is the id of the client
    Socket normalSocket;        

    Talker talker;
    ConnectionToClient(Socket normalSocket, String id) throws IOException
    {
        this.normalSocket = normalSocket;
        talker = new Talker(normalSocket);
        talker.sendMessage(id);           
        new Thread(this).start();                                        // start a new thread
    }

    @Override
    public void run() 
    {
        try 
        {
            while (true) 
            {
                talker.receiveMessage();
            } 
        }
        catch (IOException e) 
        {
            System.out.println("Connection to server lost");
        }
}
}
 