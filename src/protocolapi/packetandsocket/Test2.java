/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package protocolapi.packetandsocket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**^
 *
 * @author Jatin
 */
public class Test2 {
public static void main(String args[]) throws IOException
{
    new Test2().go();
}


void go() throws IOException
{
        try
        {
        JSocket s = new JSocket(9000);
        SocketInputStream a =(SocketInputStream) s.getInputStream();
        a.activate();
        while(true)
        {
        byte[] b= null;
        b=a.readData();
        
        System.out.println(new String(b));
        }

           /*
            working code to receive
            JSocket s = new JSocket(9000);
            BufferedInputStream i = new BufferedInputStream(new DataInputStream(s.getInputStream()));
            System.out.println(i.read(new byte[8192],0,8192));*/


            /*byte[] buf = new byte[8205];
            DatagramSocket a = new DatagramSocket(9000);
            DatagramPacket b = new DatagramPacket(buf,8205);
            int i=0;
            while(true)
            {
            a.receive(b);
            System.out.println("hello"+i+" time : "+System.currentTimeMillis());i++;*/

            //System.out.println(new String(b.getData())+" with the length"+b.getLength());
             /*i = (buf.length-off)/8192;
       f =(float) (buf.length-off)/8192;
       j=(buf.length-off)%8192;
        if((i > 0)&&(p<=i+k)&&(j!=0))// There is a serious bug here, it will omit data for any multiples of 8192
        {
            System.out.println("asd0 ");
            mothersocket.setWrittenData(buf,8192*p);
            p++;
            k++;
            write(buf,8192*p,8192);
        }*/
            

        } catch (SocketException ex) {
            Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
        }


}
}
