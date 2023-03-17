import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Talker 
{
        Socket socket;
        private BufferedReader instream;
        private DataOutputStream outstream;
      
        public Talker(Socket socket) throws IOException 
        {
          this.socket = socket;
          this.instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // used to read from server
          this.outstream = new DataOutputStream(socket.getOutputStream());                     // used to send to server
        }
      
        public void sendMessage(String message) throws IOException 
        {
          outstream.writeBytes(message + "\n");                           // send message to server
        }
      
        public void receiveMessage() throws IOException 
        {
            String message;
  //          System.out.println("Ready to receive message.......");
            message = instream.readLine();                                    // read message from server
            System.out.println(message);     
        }
      
}
