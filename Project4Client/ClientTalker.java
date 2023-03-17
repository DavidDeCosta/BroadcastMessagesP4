import java.net.*;
import java.io.*;

//represents a client that can send and receive messages to a server
public class ClientTalker   
{
  String id;
  private DataOutputStream outstream;

  ClientTalker(String id, Socket normalSocket) throws IOException
  {
    this.id = id;                                                                    
    outstream = new DataOutputStream(normalSocket.getOutputStream());                     // used to send to server
  }

  public void sendMessage(String message) throws IOException 
  {
      outstream.writeBytes(message + "\n");                 // send message to server  
  }

}
