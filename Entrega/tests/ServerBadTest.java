import comutils.ComUtils;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by aaron on 13/03/2015.
 */
public class ServerBadTest extends BaseTest{
    @Test
    public void testAnteState() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("ANTE");
        writer.write_char(' ');
        writer.write_int32(31);
        writer.write_string("STRT");
        assertEquals("ERRO", reader.read_string(4));
    }

    @Test
    public void testAnteNegative() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("ANTE");
        writer.write_char(' ');
        writer.write_int32(-31);
        assertEquals("ERRO", reader.read_string(4));
    }

    @Test
    public void testAnteOverflow() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("ANTE");
        writer.write_char(' ');
        writer.write_int32(Integer.MAX_VALUE);
        assertEquals("ERRO", reader.read_string(4));
    }

    @Test
    public void testTwoStarts() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT ", reader.read_string(5));
        assertEquals(100,reader.read_int32());
        writer.write_string("STRT");
        assertEquals("ERRO ", reader.read_string(5));
    }

    @Test
    public void testSSTART() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("SSTRT");
        assertEquals("STBT ", reader.read_string(5));
        assertEquals(100, reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD", reader.read_string(4));
    }

    @Test
    public void testSendOneByteMore() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_char('S');
        writer.write_string("STRT");
        assertEquals("STBT ", reader.read_string(5));
        assertEquals(100, reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD", reader.read_string(4));
    }

    @Test
    public void testTwoInstructions() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_char('S');
        writer.write_string("STRTDRAW");
        assertEquals("STBT ", reader.read_string(5));
        assertEquals(100,reader.read_int32());
        assertEquals("CARD", reader.read_string(4));

    }

    //@Test
    public void testSendByteByByte() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_char('S');
        Thread.sleep(1000);
        writer.write_char('T');
        writer.write_char('R');
        writer.write_char('T');
        assertEquals("STBT ", reader.read_string(5));
        assertEquals(100,reader.read_int32());
    }

    @Test
    public void testAnteWait() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT ", reader.read_string(5));
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD ", reader.read_string(5));
        reader.read_string(2);

        writer.write_string("ANTE ");
        Thread.sleep(30000);
        writer.write_int32(20);

        writer.write_string("DRAW");
        writer.write_string("PASS");

        //System.out.println(reader.read_string(40));
        String s;
        //card
        s = reader.read_string(7);
        System.out.printf(s);
        //bksc
        s = reader.read_string(5);
        System.out.println(s);
        int n = reader.read_int32();
        s = reader.read_string(n*2);
        System.out.println(s);
        reader.read_char();
        reader.read_string(4);
        assertEquals("GAIN ", reader.read_string(5));
        assertEquals(-120, reader.read_int32());
    }
}
