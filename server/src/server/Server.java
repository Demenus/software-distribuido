package server;

import comutils.ComUtils;
import connectionlayer.GameContext;
import connectionlayer.GameStateMachine;
import connectionlayer.States;

import java.io.*;

/**
 * Created by aaron on 24/02/2015.
 */
public class Server {
    public static void main(String[] args) {

        GameStateMachine s = new GameStateMachine(States.VOID_STATE);
        File f = new File("./prueba.dat");
        File f2 = new File("./res.dat");
        GameContext context = new GameContext(null, s);
        try {
            ComUtils.Writer writer = new ComUtils.Writer(new FileOutputStream(f));
            writer.write_string("sTrt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = new FileInputStream(f);
            FileOutputStream outputStream = new FileOutputStream(f2);
            //StateNode node = s.parseNextState(inputStream);
            //s.processNextState(inputStream, node);
            context.innerProcessInputData(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       /* } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StateException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }*/
    }
}
