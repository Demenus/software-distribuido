import comutils.ComUtils;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by aaron on 13/03/2015.
 */
public class ServerBadTest {
    @Test
    public void testAnteState() throws Exception {
        Socket socket = new Socket("127.0.0.1",8000);
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
        Socket socket = new Socket("127.0.0.1",8000);
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
        Socket socket = new Socket("127.0.0.1",8000);
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
}
