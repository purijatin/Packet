package protocolapi.packetandsocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import protocolapi.Exceptions.JpacketException;


class Jpacket
{
    private DatagramPacket packet;
    private int length;
    private int seqnum;
    private int datagramsize=8205;
    private int headerl;
    private byte[] netdata;
    ArrayList<String> list;
    private JpacketType ptype;
    private int acknum;
    private int rcvwin;
    private boolean option=false;
    private boolean typeSet=false;
    private boolean exSeq=true;
    private boolean exAck=true;
    private boolean fin=false;
    private boolean syn=false;
    private boolean rst=false;
    private boolean psh=false;
    private boolean ack=false;
    private boolean urg=false;
    /*Jpacket(JpacketType ptype,int buffersize) throws JpacketException
    {
        this.ptype=ptype;
        list = ptype.getPacketType();
        datagramsize=buffersize;
        netdata = new byte[datagramsize];
        installHeaders();

    }*/
    Jpacket(JpacketType ptype)
    {
        this.ptype=ptype;
        list = ptype.getPacketType();
        netdata = new byte[datagramsize];
        headerl=ptype.hlength;
        installHeaders();
        System.out.println(headerl+" head");

    }

   /**
    * Call this Constructor only to receive data at the receivers end. This is called by readData() of the SocketInputStream
    * @param data
    */
    Jpacket(byte[] data)
    {
        netdata = data;
        byte ch =netdata[0];
        ptype = new JpacketType(ch);
        // complete the code ;
    }

     /*public void setDatagramSize(int datagramsize)
    {
        this.datagramsize=datagramsize;
    }*/

    protected JpacketType getPacketType()
     {
         return ptype;
     }
     protected void setPacketType(JpacketType ptype)
     {
        this.ptype=ptype;
        installHeaders();
     }
     
     public DatagramPacket generateDatagramPacket(InetAddress address, int port)
     {
         packet = new DatagramPacket( netdata,netdata.length, address,port);
         return packet;
     }
     

     protected void setData(byte[] buf,int offset)
     {
        length = headerl*8;
        int a=0;
        a=buf.length-offset;
        if(a>8192)
        {
            a=8192;
        }

        //System.out.println("\n"+buf.length);
        for(int i=0;i<a;i++)
        {
            netdata[length+i]=buf[offset+i];
        }
        
     }
     


     private void installHeaders()
     {
        if(list.contains("UDP"))
        {
            System.out.println("Installed Headers for UDP");
        }
        else
        {
            installType();
            installSeqNum();
            installAckNum();
            installHeaderLength();
            installUnaryHeaders();
            installRcvWin();
            installOptions();
            System.out.print("Installed Headers for ");
            for(int i=0;i<list.size();i++)
            {
                System.out.print(list.get(i));
            }

        }


     }
     private void installType()
     {
       int j=0;
        int i=0;
        byte sum=0;
        if(list.contains("UDP")==false){ j=0;}else{j=1;} sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(list.contains("TCP")==false){j=0;}else{j=1;} sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(list.contains("FC")==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(list.contains("CC")==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(list.contains("RDT")==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        netdata[0]=sum;
     }
     private void installUnaryHeaders()
     {
        int j=0;
        int i=0;
        byte sum=0;
        if(fin==false){ j=0;}else{j=1;} sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(syn==false){j=0;}else{j=1;} sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(rst==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(psh==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(ack==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        if(urg==false){j=0;}else{j=1;}sum=(byte)(sum+Math.pow(2,i)*j);i++;
        netdata[1]=sum;
     }
     private void installOptions()
     {
        netdata[2]=0;
        netdata[3]=0;
     }
     private void installRcvWin()
     {
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(getRcvWin());
            byte[] bbb = bb.array();
            netdata[14]=bbb[2];
            netdata[15]=bbb[3];
            
     }
     private void installSeqNum()
     {
         //installs SeqNum
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(getSeqNum());
            byte[] bbb = bb.array();
            netdata[4]=bbb[0];
            netdata[5]=bbb[1];
            netdata[6]=bbb[2];
            netdata[7]=bbb[3];
           

     }
     private void installAckNum()
     {
            //installs AckNum
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(getAckNum());
            byte[] bbb = bb.array();
            netdata[8]=bbb[0];
            netdata[9]=bbb[1];
            netdata[10]=bbb[2];
            netdata[11]=bbb[3];
            

     }
     private void installHeaderLength()
     {
            //puts header length of 4 bits
            netdata[12]=new Integer(headerl).byteValue();           
            netdata[13]=0;
            
     }

    protected void setSeqNum(int seqnum)
    {
        this.seqnum= seqnum;// It is compulsory to set this method or else exception should be thrown. i didnt write code for exception, shall do it later
        installSeqNum();
    }
    protected void setAckNum(int acknum)
    {
        this.acknum=acknum;// It is compulsory to set this method or else exception should be thrown. i didnt write code for exception, shall do it later
        installAckNum();

    }
    protected void setRcvWin(int rcvwin)
    {
        this.rcvwin = rcvwin;
        installRcvWin();
    }
    protected int getSeqNum()
    {
        return seqnum;
    }
    protected int getAckNum()
    {
        return acknum;
    }
    protected int getRcvWin()
    {
        return rcvwin;
    }
    
    protected void setOption()
    {
        option=true;
        headerl= (byte)(headerl+4);
    }
    protected boolean isOptionSet()
    {
        return option;
    }
     protected void setFin()
    {
        fin = true;
        installUnaryHeaders();
    }
    protected void setSyn()
    {
        syn = true;
        installUnaryHeaders();
    }
    protected void setRst()
    {
        rst = true;
        installUnaryHeaders();
    }
    protected void setPsh()
    {
        psh = true;
        installUnaryHeaders();
    }
    protected void setAck()
    {
        ack = true;
        installUnaryHeaders();
    }
    protected void setUrg()
    {
        urg = true;
        installUnaryHeaders();
    }

    
}
