package com.diefesson.numbersum;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestProcessor implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public RequestProcessor(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try (socket; input; output) {
            long sum = 0;
            long[] numbers = new long[1_000_000];
            long start = System.currentTimeMillis();
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = input.readLong();
            }
            long ioEnd = System.currentTimeMillis();
            for (int i = 0; i < numbers.length; i++) {
                sum += numbers[i];
            }
            output.writeLong(sum);
            long end = System.currentTimeMillis();
            long ioTime = ioEnd - start;
            long computationTime = end - ioEnd;
            System.out.printf("Server: numbers received in %d ms, calculated in %d ms%n", ioTime, computationTime);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
