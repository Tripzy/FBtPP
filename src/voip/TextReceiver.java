package voip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextReceiver implements Runnable{
    
    static DatagramSocket receiving_socket;
    
    public void start(){
        Thread thread = new Thread(this);
	thread.start();
    }
    
    public void run (){
     
        //***************************************************
        //Port to open socket on
        int PORT = 55555;
        //***************************************************
        
        //***************************************************
        //Open a socket to receive from on port PORT
        
        //DatagramSocket receiving_socket;
        try{
		receiving_socket = new DatagramSocket(PORT);
	} catch (SocketException e){
                System.out.println("ERROR: TextReceiver: Could not open UDP socket to receive from.");
		e.printStackTrace();
                System.exit(0);
	}
        //***************************************************
        
        //***************************************************
        //Main loop.
        
        boolean running = true;
        
        while (running){
         
            try{
                //Receive a DatagramPacket (note that the string cant be more than 80 chars)
                byte[] buffer = new byte[80];
                DatagramPacket packet = new DatagramPacket(buffer, 0, 80);

                receiving_socket.receive(packet);

                //Get a string from the byte buffer
                String str = new String(buffer);
                //Display it
                Voip.addMessage(str, packet.getAddress());

               
            } catch (IOException e){
                System.out.println("ERROR: TextReceiver: Some random IO error occured!");
                e.printStackTrace();
            } catch (InterruptedException ex) {
                Logger.getLogger(TextReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Close the socket
        receiving_socket.close();
        //***************************************************
    }
}