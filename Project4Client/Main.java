//name: David DeCosta

import java.io.IOException;
import java.net.Socket;

class Main
{

public static void main(String[] args)
{
        String ipAdress = "127.0.0.1";               //ip address of server
        int portNumber = 12345;                       //port number of server

        try
        {
        new MainFrameGUI();
        Socket socket = new Socket(ipAdress, portNumber);
        ServerTalker serverTalker = new ServerTalker(socket);     
            while(true)
            {
            serverTalker.receiveMessage();
            }
        }
        catch (IOException e)
        {
            System.out.println("there was a problem \n");
        }
}

}