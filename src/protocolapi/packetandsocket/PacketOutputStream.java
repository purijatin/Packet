/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package protocolapi.packetandsocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import protocolapi.Algorithms.*;
import protocolapi.Exceptions.IOPacketException;

/**
 *
 * @author Jatin
 */
 class PacketOutputStream extends OutputStream
{
    private ArrayList<ByteArrayOutputStream> netlist = new ArrayList<ByteArrayOutputStream>();
    private JSocket mothersocket;
    public PacketOutputStream()
    {
        super();
    }
     /*public PacketOutputStream(int size)
    {
        super(size);
    }*/
   
   int p=0;
   int k=0;
   float f=0;
    @Override
   public void write(byte[] buf,int off,int len)
    {

       f =(float) (buf.length-off)/8192;
       if((f > 1.0)&&(p<=(int)f+k)&&((f-(int)f)!=0))
        {
            System.out.println("asd0 ");
            mothersocket.setWrittenData(buf,8192*p);
            p++;
            k++;
            write(buf,8192*p,8192);
        }
        else
        {
        System.out.println("asd ");
         mothersocket.setWrittenData(buf,8192*p);
        }
    }
   
    public void write(int b)
    {
        //super.write(b);
    }
   
    @Override
    public void write(byte[] buf) throws IOPacketException, IOException
    {
        write(buf,0,buf.length);
    }

    void setJSocket(JSocket aThis)
    {
        mothersocket = aThis;
    }
}
