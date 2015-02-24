package statemachine.core;

/**
 * Created by aaron on 24/02/2015.
 */
public class ComUtilsBase {
    /* Passar d'enters a bytes */
    protected int int32ToBytes(int number,byte bytes[], String endianess)
    {
        if("be".equals(endianess.toLowerCase()))
        {
            bytes[0] = (byte)((number >> 24) & 0xFF);
            bytes[1] = (byte)((number >> 16) & 0xFF);
            bytes[2] = (byte)((number >> 8) & 0xFF);
            bytes[3] = (byte)(number & 0xFF);
        }
        else
        {
            bytes[0] = (byte)(number & 0xFF);
            bytes[1] = (byte)((number >> 8) & 0xFF);
            bytes[2] = (byte)((number >> 16) & 0xFF);
            bytes[3] = (byte)((number >> 24) & 0xFF);
        }
        return 4;
    }

    /* Passar de bytes a enters */
    protected int bytesToInt32(byte bytes[], String endianess)
    {
        int number;

        if("be".equals(endianess.toLowerCase()))
        {
            number=((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) |
                    ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
        }
        else
        {
            number=(bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) |
                    ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
        }
        return number;
    }
}
