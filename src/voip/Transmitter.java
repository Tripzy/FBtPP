package voip;

import CMPC3M06.AudioRecorder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

public class Transmitter implements Runnable{
    
    private AudioRecorder recorder;
    private DatagramSocket sendingSocket;
    private InetAddress clientIP;
    private int PORT;
    boolean running;
    
    public Transmitter(InetAddress clientIP, int PORT){
        recorder = null;
        sendingSocket = null;
        this.clientIP = clientIP;
        this.PORT = PORT;
    }

    public void closeConn(){
        running = false;
        recorder.close();
    }
    
    @Override
    public void run(){
        
        try {
            recorder = new AudioRecorder();
        } catch (LineUnavailableException ex) {
            JOptionPane.showMessageDialog(null, "Could not set recorder",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            clientIP = InetAddress.getByName(clientIP.getHostAddress());
        }catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Could not find client IP",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try{
            sendingSocket = new DatagramSocket();
	}catch (SocketException e){
            JOptionPane.showMessageDialog(null, "Could not open UDP socket to send from",
                    "Error", JOptionPane.ERROR_MESSAGE);
	}
        
        running = true;
        while(running){
            try{
                byte[] block = recorder.getBlock();
                DatagramPacket packet = new DatagramPacket(block, block.length, clientIP, PORT);
                sendingSocket.send(packet);
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(null, "Random IO Error",
                    "IO Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
