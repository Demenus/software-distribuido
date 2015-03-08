import comutils.ComUtils;
import junit.framework.TestCase;

import java.net.Socket;

/**
 * Created by aaron on 08/03/2015.
 */
public class FullPlayTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
        //ServerContextManager manager = new ServerContextManager();
        //manager.runServer();
    }

    public void testGame() throws Exception {
        Socket socket = new Socket("127.0.0.1",8000);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(101,reader.read_int32());
    }
}
