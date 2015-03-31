package server;

import java.util.HashMap;

/**
 * Created by aaron on 31/03/2015.
 */
public class Server {
    public static final String DEFAULT_PORT = "1212";
    public static final String DEFAULT_BET = "100";
    public static final String DEFAULT_DECK = "deck.txt";

    public static void main(String[] args) {
        if (args.length%2 != 0) {
            System.exit(-1);
        }
        HashMap<String, String> arguments = new HashMap<String, String>();
        arguments.put("-p",DEFAULT_PORT);
        arguments.put("-b",DEFAULT_BET);
        arguments.put("-f",DEFAULT_DECK);
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
