import comutils.ComUtils;
import org.junit.Test;

import java.net.Socket;

/**
 * Created by aaron on 12/03/2015.
 */
public class StressTest extends BaseTest {

    @Test
    public void test20() throws Exception {
        for (int i = 0; i < 20; i++)
        {
            Socket socket = new Socket("127.0.0.1",PORT);
            ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
            writer.write_string("STRT");
        }
    }

    @Test
    public void test20Errors() throws Exception {
        for (int i = 0; i < 20; i++)
        {
            Socket socket = new Socket("127.0.0.1",PORT);
            ComUtils.Writer writer = new ComUtils.Writer(socket.getOutputStream());
            writer.write_string("START");
            Thread.sleep(100);
        }
    }
}
