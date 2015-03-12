import comutils.ComUtils;
import org.junit.Test;
import server.ServerContextManager;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by aaron on 12/03/2015.
 */

public class ServerGoodTest {

    private Thread serverThread;
    private ServerContextManager mServerContextManager;

    public ServerGoodTest() {
        /*serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerContextManager manager = new ServerContextManager();
                manager.runServer();
            }
        });
        serverThread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //mServerContextManager = new ServerContextManager();
        //mServerContextManager.runServer();
    }


    @Test
    public void testDraw() throws Exception {
        Socket socket = new Socket("127.0.0.1",8000);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        Thread.sleep(5000);
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
        writer.write_string("DRAW");
        Thread.sleep(5000);
        assertEquals("CARD 3o", reader.read_string(7));
        writer.write_string("DRAW");
        Thread.sleep(5000);
        assertEquals("CARD cc", reader.read_string(7));
    }

    //@Test
    public void testBet() throws Exception {
        Socket socket = new Socket("127.0.0.1",8000);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
    }

    //@Test
    public void testBet2() throws Exception {
        Socket socket = new Socket("127.0.0.1",8000);
        ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
        ComUtils.Reader reader = new ComUtils.Reader(socket.getInputStream());
        writer.write_string("STRT");
        assertEquals("STBT", reader.read_string(4));
        assertEquals(' ',reader.read_char());
        assertEquals(100,reader.read_int32());
    }
}
