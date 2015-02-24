package statemachine.core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public class ComUtilsWriter extends ComUtilsBase{

    private final int STRSIZE = 40;
    private DataOutputStream dos;

    public ComUtilsWriter(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
    }

    /* Escriure un enter de 32 bits */
    public void write_int32(int number) throws IOException
    {
        byte bytes[]=new byte[4];

        int32ToBytes(number,bytes,"be");
        dos.write(bytes, 0, 4);
    }

    /* Escriure un string */
    public void write_string(String str) throws IOException
    {
        int numBytes, lenStr;
        byte bStr[] = new byte[STRSIZE];

        lenStr = str.length();

        if (lenStr > STRSIZE)
            numBytes = STRSIZE;
        else
            numBytes = lenStr;

        for(int i = 0; i < numBytes; i++)
            bStr[i] = (byte) str.charAt(i);

        for(int i = numBytes; i < STRSIZE; i++)
            bStr[i] = (byte) ' ';

        dos.write(bStr, 0,STRSIZE);
    }

    /* Escriure un string mida variable, size = nombre de bytes especifica la longitud  */
	/* String str = string a escriure.*/
    public  void write_string_variable(int size,String str) throws IOException
    {

        // Creem una seqÃ¼Ã¨ncia amb la mida
        byte bHeader[]=new byte[size];
        String strHeader;
        int numBytes=0;

        // Creem la capÃ§alera amb el nombre de bytes que codifiquen la mida
        numBytes=str.length();

        strHeader=String.valueOf(numBytes);
        int len;
        if ((len=strHeader.length()) < size)
            for (int i =len; i< size;i++){
                strHeader= "0"+strHeader;}
        for(int i=0;i<size;i++)
            bHeader[i]=(byte)strHeader.charAt(i);
        // Enviem la capÃ§alera
        dos.write(bHeader, 0, size);
        // Enviem l'string writeBytes de DataOutputStrem no envia el byte mÃ©s alt dels chars.
        dos.writeBytes(str);
    }
}
