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
//            BufferedReader bin = null;
//            PrintWriter pout = null;
            DataOutputStream out = null;
            ByteArrayOutputStream outBuffer = null;

            byte[] clientInData = new byte[8000]; //WIP! I will try to allocate byte size dynamically

            System.out.println("Waiting for a connection");
            while (connected == false) { //listen for connections
                client = sock.accept();
                if (client != null) {
                    System.out.println("Connection established");
                    connected = true;

                    in = client.getInputStream();
                    out = new DataOutputStream(client.getOutputStream());
                    outBuffer = new ByteArrayOutputStream();
//                    bin = new BufferedReader(new InputStreamReader(in));
//                    pout = new PrintWriter(client.getOutputStream(), true);
                }
            }
            while (connected == true) { //Read and send the echo request from the client
                if ((in.read(clientInData, 0, clientInData.length)) != -1) {
                    outBuffer.write(clientInData);
                } else {
                    connected = false;
                    break;
                }
                
                outBuffer.flush();
                byte[] clientOutData = outBuffer.toByteArray(); //Flush buffer to array of bytes
                
                out.write(clientOutData);
                out.flush();
                outBuffer.close();
            }
            client.close();           
            System.out.println("Connection closed. Terminating.");
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

}
