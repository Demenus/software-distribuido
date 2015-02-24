import game.GameStateMachine;
import game.States;
import game.Commands;
import statemachine.StateNode;
import statemachine.comutils.ComUtils;
import statemachine.protocolexceptions.CommandException;
import statemachine.applicationexceptions.ApplicationException;
import statemachine.protocolexceptions.ParseException;
import statemachine.protocolexceptions.StateException;

import java.io.*;

/**
 * Created by aaron on 24/02/2015.
 */
public class Server {
    public static void main(String[] args) {
        GameStateMachine s = new GameStateMachine(States.START_STATE);
        File f = new File("./prueba.dat");
        File f2 = new File("./res.dat");
        try {
            ComUtils.Writer writer = new ComUtils.Writer(new FileOutputStream(f));
            writer.write_string(Commands.START);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = new FileInputStream(f);
            FileOutputStream outputStream = new FileOutputStream(f2);
            StateNode node = s.parseNextState(inputStream);
            s.processNextState(inputStream, node);

        } catch (FileNotFoundException e) {
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
        }
    }
}
