package statemachine.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public class ComUtilsReader extends ComUtilsBase {

    private final int STRSIZE = 40;
    private DataInputStream dis;

    public ComUtilsReader(InputStream inputStream) {
        dis = new DataInputStream(inputStream);
    }

    /* Llegir un enter de 32 bits */
    public int read_int32() throws IOException
    {
        byte bytes[] = new byte[4];
        bytes  = read_bytes(4);

        return bytesToInt32(bytes,"be");
    }

    /* Llegir un string de mida STRSIZE */
    public String read_string() throws IOException
    {
        String str;
        byte bStr[] = new byte[STRSIZE];
        char cStr[] = new char[STRSIZE];

        bStr = read_bytes(STRSIZE);

        for(int i = 0; i < STRSIZE;i++)
            cStr[i]= (char) bStr[i];

        str = String.valueOf(cStr);

        return str.trim();
    }

    //llegir bytes.
    private byte[] read_bytes(int numBytes) throws IOException{
        int len=0 ;
        byte bStr[] = new byte[numBytes];
        do {
            len += dis.read(bStr, len, numBytes-len);
        } while (len < numBytes);
        return bStr;
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
