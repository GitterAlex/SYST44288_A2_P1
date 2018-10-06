/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo.server;

import java.net.*;
import java.io.*;

/**
 *
 * @author Sam
 */
public class EchoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket client = null;
            boolean connected = false;
            ServerSocket sock = new ServerSocket(6013);
            InputStream in = null;
            BufferedReader bin = null;
            PrintWriter pout = null;

            System.out.println("Waiting for a connection");
            while (connected == false) { //listen for connections
                client = sock.accept();
                if (client != null) {
                    System.out.println("Connection established");
                    connected = true;
                    in = client.getInputStream();
                    bin = new BufferedReader(new InputStreamReader(in));
                    pout = new PrintWriter(client.getOutputStream(), true);
                }
            }
            while (connected == true) {
                /* read the echo request from the client socket */
                String line;

                while ((line = bin.readLine()) != null) {
                    pout.println(line);
                }
            }
            client.close();
            System.out.println("Connection closed");
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

}
