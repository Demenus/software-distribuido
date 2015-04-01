import org.junit.Before;
import server.ServerContextManager;


/**
 * Created by aaron on 30/03/2015.
 */
public abstract class BaseTest {

    public static int PORT = 1212;
    public static int BET = 100;
    public static String DECK = "deck.txt";
    public static ServerContextManager manager = null;
    public static boolean start = false;

    @Before
    public void setUp() throws Exception {
        /*if (!start) {
            manager = new ServerContextManager(PORT, BET, DECK);
            manager.runServer();
            start = true;
        }*/
    }

}
