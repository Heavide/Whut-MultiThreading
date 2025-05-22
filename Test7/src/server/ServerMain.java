package server;

import server.console.DataProcessing;

public class ServerMain {
    public static void main(String[] args) {
        Server x = new Server();
        DataProcessing.connect();
        x.runServer();
    }
}
