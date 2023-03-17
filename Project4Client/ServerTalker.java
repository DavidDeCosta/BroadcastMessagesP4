import java.io.*;
import java.net.*;


//represents a server that receive messages to a client
public class ServerTalker 
{
    String id;
    Socket socket;
    private BufferedReader instream;

    public ServerTalker(Socket socket) throws IOException 
    {
        this.socket = socket;                                                           
        instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));        // used to read from server                                           
    }


    void receiveMessage() throws IOException
    {
        String message;
        System.out.println("Ready to receive message.......");
        message = instream.readLine();                                    // read message from server
        System.out.println(message);                                         
    }
    
}