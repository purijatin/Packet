package protocolapi.packetandsocket;

import java.nio.ByteBuffer;
import java.util.*;


/**
 *
 * @author Jatin
 */
public  class JpacketType
{
    private final String RDT = "RDT";
    private final String FC = "FC";
    private final String CC = "CC";
    private final String TCP = "TCP";
    private final String UDP = "UDP";
    protected byte hlength;//this is the mod 8 of the length
    
    private HashSet<String> types = new HashSet<String>();
    private ArrayList<String> types2 = new ArrayList<String>();
    

    /**
     *
     * @param type The Type which user wants to have, Input only among the following:"TCP","UDP","CC","FC","RDT"
     * <li>Where FC is Flow Control
     * <li>CC is Congestion Control
     * <li>RDT is Reliable Data Transfer
     *
     */
    public JpacketType(String... type)
    {
        cheackAppropriateAndRemoveRedundancy(type);
        types2.addAll(types);
    }
     public JpacketType(byte buf)
    {
         
    }


    private void cheackAppropriateAndRemoveRedundancy(String[] type)
    {
        // throws NoAppropriateJPacketType Exception if no appropriate type.Removes all the redundancies by default
        // add everything to the type and then later remove redundancies and check fror appropriate type

        for(int i=0;i<type.length;i++)
        {
            if((type[i]==RDT)||type[i]==FC||type[i]==CC||type[i]==TCP||type[i]==UDP)
            {
                types.add(type[i]);
            }
            else
            {
                System.err.println("Not Appropriate type from packet.JpacketType, Unappropriate type has been removed");
            }
        }
        if(types.contains(UDP)&&types.size()==1)
        {
            hlength =4;
        }
        else
        {
            hlength = 16;
        }
       

    }
    public void setPacketType(String... type)
    {
        cheackAppropriateAndRemoveRedundancy(type);
        
        types2.addAll(types);
    }
    protected ArrayList<String> getPacketType()
    {
        return types2;
    }
    
    protected int bytesToInt(byte[] intBytes)
    {
	ByteBuffer bb = ByteBuffer.wrap(intBytes);
	return bb.getInt();
    }

    
    

}
