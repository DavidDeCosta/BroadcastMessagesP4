import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;



public class ConnectionToClient implements Runnable
{
    ClientTalker clientTalker;
    String id;
    Socket normalSocket;

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
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(normalSocket.getInputStream()));
            while (true) 
            {
                String message = in.readLine();               // read message from server
                if(message !=null)
                {
                    System.out.println("ijbhdnawjhkdbhjkawdbn");
                    System.out.println(message);             // print message to console
                }
                else
                {
                    System.out.println("Client has disconnected");
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
}
 