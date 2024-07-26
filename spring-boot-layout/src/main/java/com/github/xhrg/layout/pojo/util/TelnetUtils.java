package com.github.xhrg.layout.pojo.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TelnetUtils {

    private static final int TIMEOUT_MS = 1 * 1000;

    public static boolean telnet(String hostname, int port) {
        Socket socket = new Socket();
        boolean isConnection = false;
        try {
            socket.connect(new InetSocketAddress(hostname, port), TIMEOUT_MS);
            isConnection = socket.isConnected();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isConnection;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        boolean ok = telnet("10.39.84.172", 9092);
        System.out.println(ok);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
