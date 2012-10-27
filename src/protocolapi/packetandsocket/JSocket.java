
package protocolapi.packetandsocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolapi.Exceptions.IOPacketException;
import protocolapi.Exceptions.JsocketException;

/**
 *
 * 
 * <li>JSocket class acts as an gateway to send the data. The required methods to be called to send the data:
 * <li>For example :
 * <li>JSocket s = new JSocket();
 * <li>s.setAttributesToSendData(InetAddress.getByName("127.0.0.1"),9000,new JpacketType("UDP"));
 * <li>DataOutputStream a =new DataOutputStream(s.getOutputStream());
 * <li>a.write(data to be sent);
 * 
 * <li>Only JSocket can make Jpacket
 * @author Jatin and Anurag
  *@version 1.0
 * @since Oct 2010
 *
 *
 */

public class JSocket
{
     
    private int lport;
    private InetAddress address;
    private int dport;
    private byte[] netdata;
    private SocketInputStream bin;
    private DatagramSocket socket;
    private PacketOutputStream bout;
    private JpacketType ptype;
    private Jpacket packet;
    private boolean rdtDone=false;

    /**
     *
     */
    public JSocket() throws SocketException
    {
        socket = new DatagramSocket();
    }
    /**
     * 
     * @param lport THe port number of the local host
     */
    public JSocket(int lport) throws SocketException
    {
        socket = new DatagramSocket(lport);
        this.lport = lport;
    }

    /**
     * 
     * @param address Address to send the data
     * @param dport Port of the destination machine
     * @param lport port of the local host machine
     * @param ptype JpacketType
     * @throws JsocketException
     * @throws SocketException
     */
    public JSocket(InetAddress address,int dport,int lport,JpacketType ptype) throws JsocketException,SocketException
    {
        this.lport=lport;
        this.address=address;
        this.dport=dport;
        this.ptype = ptype;
    }
  
    /**
     * 
     * @param address Address to which data is to be sent
     * @param dport The port number on which the program is running on the destination host
     * @param ptype The packet type of the data to be sent
     * @throws JsocketException
     * @throws SocketException
     */
    public void setAttributesToSendData(InetAddress address,int dport,JpacketType ptype) throws JsocketException,SocketException
    {
        this.address=address;
        this.dport=dport;
        this.ptype =ptype;
    }
    /**
    *
    * @return port number of local host
    */
    
    public int getPort()
    {
       return lport;
    }
    /**
     * 
     * @return The Actual DatagramSocket through which the data is sent ot obtained
     */
    protected DatagramSocket getActualSocket()
    {
        return socket;
    }
    public SocketInputStream getInputStream()
    {
        bin = new SocketInputStream();
        bin.setJsocket(this);
        return bin;
    }
    /**
     * The User needs to call this method to get the output stream to pump data
     * /
     * @return OutputStream
     * @throws IOPacketException
     * @throws IOException
     */
    public OutputStream getOutputStream() throws IOPacketException, IOException
    {
        bout = new PacketOutputStream();
        bout.setJSocket(this);
        return bout;
    }

    /**
     *
     * @param packet The Jpacket is sent to this method to send it to RDT application
     * @throws IOException
     */
    private void sendPacket() throws IOException
    {
        //this.packet=packet;
        DatagramPacket r =packet.generateDatagramPacket(address, dport);
        socket.send(r);
        
    }
    protected void rdtDone() throws IOException
    {
        rdtDone = true;
        sendPacket();
    }
   /** This class is only mean to be used by Packet Output Stream It gives output to the Anurag class where he wants it 
    * 
    */
    protected void setWrittenData(byte[] buf,int offset)
    {
        
         Jpacket jpacket = new Jpacket(ptype);
        jpacket.setData(buf,offset);
        packet =jpacket;
        try {
            //send the jpacket to anurag's class send PacketAnurag(packet,this);
            rdtDone();
            
            /*DatagramPacket packet2=jpacket.generateDatagramPacket(address, dport);// This is how anurag should call to generate datagram packet out of Jpacket
            // Now send the packets to Anurag's class
            try {
            //testing
            DatagramSocket s = new DatagramSocket();
            try {
            System.out.println("Data packet sent at time:   "+System.currentTimeMillis());
            s.send(packet2);
            System.out.println("Data sent");
            } catch (IOException ex) {
            Logger.getLogger(JSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (SocketException ex) {
            Logger.getLogger(JSocket.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } catch (Exception ex) {
            Logger.getLogger(JSocket.class.getName()).log(Level.SEVERE, null, ex);
        }



    }



}
