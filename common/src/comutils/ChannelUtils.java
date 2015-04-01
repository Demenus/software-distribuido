package comutils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class ChannelUtils {

    private static int int32ToBytes(int number,byte bytes[], String endianess)
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
    private static int bytesToInt32(byte bytes[], String endianess)
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

    public static class Reader {
        private ByteChannel mChannel;

        public Reader(ByteChannel channel) {
            mChannel = channel;
        }

        /* Llegir un enter de 32 bits */
        public int read_int32() throws IOException
        {
            byte[] bytes = read_bytes(4);

            return bytesToInt32(bytes,"be");
        }

        public String read_string(int strLen) throws IOException {
            String str;
            byte[] bStr;
            char[] cStr = new char[strLen];

            bStr = read_bytes(strLen);

            for (int i = 0; i < strLen; i++) {
                cStr[i] = (char) bStr[i];
            }

            str = String.valueOf(cStr);

            return str;
        }

        //llegir bytes.
        private byte[] read_bytes(int numBytes) throws IOException{
            byte[] bStr = new byte[numBytes];
            ByteBuffer buffer = ByteBuffer.wrap(bStr);
            int b = mChannel.read(buffer);
            if (b < numBytes) {
                throw new IOException("Broken Pipe");
            }
            return bStr;
        }

        public char read_char() throws IOException {
            return (char)read_bytes(1)[0];
        }

        /* Llegir un string  mida variable size = nombre de bytes especifica la longitud*/
        public  String read_string_variable(int size) throws IOException
        {
            byte bHeader[]=new byte[size];
            char cHeader[]=new char[size];
            int numBytes=0;

            // Llegim els bytes que indiquen la mida de l'string
            bHeader = read_bytes(size);
            // La mida de l'string ve en format text, per tant creem un string i el parsejem
            for(int i=0;i<size;i++){
                cHeader[i]=(char)bHeader[i]; }
            numBytes=Integer.parseInt(new String(cHeader));

            // Llegim l'string
            byte bStr[]=new byte[numBytes];
            char cStr[]=new char[numBytes];
            bStr = read_bytes(numBytes);
            for(int i=0;i<numBytes;i++)
                cStr[i]=(char)bStr[i];
            return String.valueOf(cStr);
        }
    }

    public static class Writer {
        private ByteChannel mChannel;

        public Writer(ByteChannel channel) {
            mChannel = channel;
        }

        /* Escriure un enter de 32 bits */
        public void write_int32(int number) throws IOException
        {
            byte bytes[]=new byte[4];

            int32ToBytes(number,bytes,"be");
            mChannel.write(ByteBuffer.wrap(bytes));
        }

        /* Escriure un string */
        public void write_string(String str) throws IOException
        {
            int numBytes, lenStr;
            lenStr = str.length();
            byte bStr[] = new byte[lenStr];

            for(int i = 0; i < lenStr; i++)
                bStr[i] = (byte) str.charAt(i);

            ByteBuffer buf = ByteBuffer.wrap(bStr);
            mChannel.write(buf);
        }

        public void write_char(char c) throws IOException {
            byte[] bytes = {(byte) c};
            mChannel.write(ByteBuffer.wrap(bytes));
        }
    }
}
