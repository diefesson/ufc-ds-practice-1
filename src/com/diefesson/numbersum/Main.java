package com.diefesson.numbersum;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static void createServer() {
        try {
            new Thread(new Server(5000)).start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void createClients(int count, int simuntaneous) {
        ExecutorService executorService = Executors.newFixedThreadPool(simuntaneous);
        for (int i = 0; i < count; i++) {
            int id = i;
            executorService.submit(() -> {
                try {
                    new Client(id, "localhost", 5000).run();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }

    }

    public static void main(String[] args) {
        createServer();
        createClients(100, 3);
    }

}