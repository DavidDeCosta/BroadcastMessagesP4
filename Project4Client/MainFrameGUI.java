import javax.swing.*;
import java.awt.*;                                 // for Toolkit and Dimension
import java.awt.event.*;                            // for ActionListener
import java.io.IOException;
import java.net.Socket;

class MainFrameGUI extends JFrame
                            implements ActionListener
{

    Toolkit toolkit;
    Dimension screenSize;
    JDialog dialog;
    JTextField textField;
    JTextField fieldForDialog;
    JButton send;
    JButton exit;
    JButton connect;
    JLabel label;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    String userID;
    Socket socket;
    ConnectionToClient connectionToClient;
    Talker talker;

    MainFrameGUI()
    {
        dialogSetup();
        setupComponents();
        buildMainFrame();
    }

    void buildMainFrame()
    {
        toolkit = Toolkit.getDefaultToolkit();                                    // used to help get the users screen size
        screenSize = toolkit.getScreenSize();                                     //get the users screen size
        setSize((screenSize.width/2 + 70), (screenSize.height/2 + 70));           
        setLocationRelativeTo(null);                                           // window is placed in the center of screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                          //when close frame the program stops
        setTitle("Project 4");
        setVisible(true);
    }

    void dialogSetup()
    {
        dialog = new JDialog();
        fieldForDialog = new JTextField(20);
        connect = new JButton("Connect");
        connect.addActionListener(this);

        panel3 = new JPanel();
        panel3.add(fieldForDialog);
        panel3.add(connect);
        dialog.add(panel3);

        dialog.setLocationRelativeTo(null);                                  // makes the dialog box position be in the center of screen
        dialog.setSize(400,400);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);         //makes the dialog box show up on top of the mainframe
        dialog.setVisible(true);

    }

    void setupComponents()
    {
        panel1 = new JPanel();
        add(panel1, BorderLayout.CENTER);

        textField = new JTextField(40);
        panel1.add(textField);
        send = new JButton("Send");
        send.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        panel1.add(send);
        panel1.add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("Connect"))
        {
            userID = fieldForDialog.getText();
            try 
        {
            socket = new Socket("127.0.0.1", 12345);
            talker = new Talker(socket);                                      // used to send and receive messages from the server
            connectionToClient = new ConnectionToClient(socket, userID);      // used to create a new thread for each client
        } 
        catch (IOException ex) 
        {
            System.out.println("Failed to connect to server");
            JOptionPane.showMessageDialog(null, "Failed to connect", "Could Not Connect", JOptionPane.ERROR_MESSAGE);
           System.exit(0);
        }

            dialog.dispose();
        }
        else if(e.getActionCommand().equals("Send"))
        {
            String message = textField.getText().trim();
            if(message.isEmpty())                // if the message is empty then do nothing
            {
                return;
            }
            try 
            {
                talker.sendMessage(message);
                textField.setText("");                             //clears the text after sending

            } 
            catch (IOException ex) 
            {
                System.out.println("Failed to send message");
            }
        }
        else if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
        
    }
    
}