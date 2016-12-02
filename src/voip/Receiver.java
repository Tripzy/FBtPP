package voip;

import CMPC3M06.AudioPlayer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

public class Receiver implements Runnable{

    @Override
    public void run(){
      
        DatagramSocket receivingSocket = null;
        AudioPlayer player = null;
        int PORT = 16454;
        
        try{
            player = new AudioPlayer();
        }catch(LineUnavailableException e){
            JOptionPane.showMessageDialog(null, "Could not set audioplayer",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try{
            receivingSocket = new DatagramSocket(PORT);
	} catch (SocketException e){
            JOptionPane.showMessageDialog(null, "Could not set UDP socket to receive from",
                    "Error", JOptionPane.ERROR_MESSAGE);
	}
    
        boolean running = true, printed = false, allowed = false;
        while(running){
            try{
                byte[] buffer = new byte[512];
                
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                receivingSocket.receive(packet);
                player.playBlock(buffer);
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(null, "Random IO Error",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        player.close();
    }
    
}
