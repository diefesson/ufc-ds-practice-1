package com.diefesson.numbersum;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {

    private final int id;
    private final Socket connection;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final Random random;

    public Client(int id, String host, int port) throws IOException {
        this.id = id;
        this.connection = new Socket(host, port);
        this.input = new DataInputStream(connection.getInputStream());
        this.output = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
        this.random = new Random();
    }

    @Override
    public void run() {
        try (connection) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1_000_000; i++) {
                output.writeLong(random.nextInt(1_000));
            }
            output.flush();
            long result = input.readLong();
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.printf("Client %d: result %d obtained in a total time of %d miliseconds%n",
                    id,
                    result,
                    time);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
