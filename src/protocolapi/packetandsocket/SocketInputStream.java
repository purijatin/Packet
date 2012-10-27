/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package protocolapi.packetandsocket;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import protocolapi.packetandsocket.JpacketType;

/**
 *
 * @author Jatin
 */
public class SocketInputStream extends InputStream
{
   Jpacket jpacket;
   JpacketType ptype;
   JSocket mothersocket;
   DatagramSocket socket;
   DatagramPacket packet=new DatagramPacket(new byte[8205],8205);
    void setJsocket(JSocket aThis)
    {
        mothersocket = aThis;
        socket = mothersocket.getActualSocket();

    }

    @Override
    /**
     * @return The length of the data
     */
    public int read() throws IOException
    {
        byte[] buf=readData();
        return buf.length;
    }
    /**
     * Method to get the data from Stream
     * @return The byte array of data
     * @throws IOException
     */
    public byte[] readData() throws IOException
    {
        socket.receive(packet);
        byte[] buf = packet.getData();
        jpacket = new Jpacket(buf);
        return packet.getData();
    }
    public void activate()
    {
        

    }




}




