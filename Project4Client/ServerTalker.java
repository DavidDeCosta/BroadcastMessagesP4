import java.io.*;
import java.net.*;


//represents a server that can send and receive messages to a client
public class ServerTalker 
{
    String id;
    Socket socket;
    private BufferedReader instream;

    public ServerTalker(Socket socket) throws IOException 
    {
        this.id = id;
        this.socket = socket;                                                           
        instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));        // used to read from server                                           
    }

    public String receiveMessage() throws IOException
    {
        String message;
        System.out.println("Ready to receive message.......");
  
        message = instream.readLine();                                    // read message from server
        String[] nameThenMesssage = message.split(":");                              // split message into Name then Message
        String id = nameThenMesssage[0].trim();                                                // get the name
        String msg = nameThenMesssage[1].trim();                                               // get the message
        System.out.println("Message received from " + id + ": " + msg + "\n");             // print message to console
        return message;                                                   
    }
}