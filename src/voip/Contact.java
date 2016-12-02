/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voip;

import java.net.InetAddress;

/**
 *
 * @author RnD
 */
public class Contact {
    
    private String name;
    private InetAddress addr;
    private String chat;
    
    public Contact(String name, InetAddress addr){
        this.name = name;
        this.addr = addr;
        chat = "";
    }
    
    public String getName(){
        return name;
    }
    
    public void addMessage(String msg){
        chat += msg;
    }
    
    public String getChat(){
        return chat;
    }
    
    public InetAddress getIP(){
        return addr;
    }
    
    @Override
    public String toString(){
        return name + "@" + addr.getHostAddress();
    }
}

