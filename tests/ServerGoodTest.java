import comutils.ComUtils;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by aaron on 30/03/2015.
 */
public class ServerGoodTest extends BaseTest {
    @Test
    public void testGame1() throws Exception {
        Socket socket = new Socket("127.0.0.1", PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("PASS");
        assertEquals("BKSC ", reader.read_string(5));
        assertEquals(7, reader.read_int32());
        assertEquals("ccrcresb1erb4b", reader.read_string(2*7));
        assertEquals(" ", reader.read_string(1));
        assertEquals("07.5", reader.read_string(4));
        assertEquals("GAIN ", reader.read_string(5));
        assertEquals(-100, reader.read_int32());
    }

    @Test
    public void testFullGameAsPlayer() throws Exception {
        Socket socket = new Socket("127.0.0.1", PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        Thread.sleep(1000);
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        Thread.sleep(1000);
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("DRAW");
        Thread.sleep(1000);
        assertEquals("CARD cc", reader.read_string(7));
    }

    @Test
    public void testBet() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
    }

    @Test
    public void testDraw() throws Exception {
        Socket socket = new Socket("127.0.0.1",PORT);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("DRAW");
        assertEquals("CARD cc", reader.read_string(7));
    }

    @Test
    public void testAnte() throws Exception {
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
        writer.write_string("DRAW");
        assertEquals("CARD cc", reader.read_string(7));
    }
}
