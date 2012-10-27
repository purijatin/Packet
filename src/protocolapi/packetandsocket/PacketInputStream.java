
package protocolapi.packetandsocket;

import java.io.ByteArrayInputStream;

/**
 *
 * @author Jatin
 */
public class PacketInputStream extends ByteArrayInputStream
{
    
    public PacketInputStream(byte[] buf)
    {
        super(buf);
    }
    public PacketInputStream(byte[] buf,int offset, int length)
    {
        super(buf,offset,length);
    }
}
