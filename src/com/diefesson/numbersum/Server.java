package com.diefesson.numbersum;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket connection = serverSocket.accept();
                RequestProcessor requestProcessor = new RequestProcessor(connection);
                executorService.submit(requestProcessor);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
