package server;

import java.util.HashMap;

/**
 * Created by aaron on 19/03/2015.
 */
public class Server {


    public static void main(String[] args) {
        if (args.length%2 != 0) {
            System.exit(-1);
        }
        HashMap<String, String> arguments = new HashMap<String, String>();
        arguments.put("-p","1212");
        arguments.put("-b","100");
        arguments.put("-f","deck.txt");
        for (int i = 0; i < args.length; i=i+2) {
            arguments.put(args[i], args[i+1]);
        }
        int port = Integer.valueOf(arguments.get("-p"));
        int bet = Integer.valueOf(arguments.get("-b"));
        String deck = arguments.get("-f");
        ServerContextManager manager = new ServerContextManager(port, bet, deck);
        manager.runServer();
    }
}
