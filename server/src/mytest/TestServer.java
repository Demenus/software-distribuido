package mytest;

import comutils.ComUtils;
import server.GameStateMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by aaron on 24/02/2015.
 */
public class TestServer {
    public static void main(String[] args) {

        GameStateMachine s = new GameStateMachine();
        File f = new File("./prueba.dat");
        File f2 = new File("./res.dat");

        try {
            ComUtils.Writer writer = new ComUtils.Writer(new FileOutputStream(f));
            writer.write_string("sTrt");
            writer.write_string("draw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TestGameContext context = new TestGameContext(null, "deck.txt", 100, f, f2);
        context.processInputData();
        context.processInputData();

        /*try {
            FileInputStream inputStream = new FileInputStream(f);
            FileOutputStream outputStream = new FileOutputStream(f2);
            context.innerProcessInputData(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
