/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package protocolapi.packetandsocket;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolapi.Exceptions.JpacketException;

/**
 *
 * @author Anurag and Jatin
 */
public class Test
{
    public static void main(String args[])
     {
        try {
            new Test().go();
        } catch (SocketException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    void go() throws SocketException, IOException
    {
        /*JpacketType a = new JpacketType("TCP");
        a.setSeqNum(123456);
        a.setAckNum(2345);
        a.setRcvWin(234);  
        byte[] b= new byte[234];
        try {
            Jpacket asd = new Jpacket(a, b);
            byte[] qwe = asd.getMainData();
            byte[] qw = new byte[4];
            qw[0] =qwe[0];
            qw[1] =qwe[1];
            qw[2] =qwe[2];
            qw[3] =qwe[3];
            int fy =asd.bytesToInt(qw);
            System.out.println(fy);
            System.out.println(asd.getPacketType().getHeaderLength());
        } catch (JpacketException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] buf = new byte[23];
        Jpacket a = new Jpacket(new JpacketType("TCP"));
        a.setData(buf);
        try {
            
            
            DatagramPacket p = a.generateDatagramPacket(InetAddress.getByName("127.0.0.1"), 9000);
            DatagramSocket s = new DatagramSocket();
            s.send(p);
            

        } catch (UnknownHostException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        

        // Working code to check output;
         JSocket s = new JSocket();
        s.setAttributesToSendData(InetAddress.getByName("127.0.0.1"),9000,new JpacketType("TCP"));
        OutputStream a = s.getOutputStream();
        String d = "1";
        System.out.println(System.currentTimeMillis()+" program starts now");
        for(int i=0;i<20;i++)
        {
            d=d+"hello";
        }
      // d=d+2;
        //System.out.println(d.getBytes().length+" from test");
        System.out.println(System.currentTimeMillis()+" Time starts now");
        byte[] f = d.getBytes();
        System.out.println(" THe length of string is "+f.length);
        a.write(f);
        a.close();
       
        
        





    }
}
