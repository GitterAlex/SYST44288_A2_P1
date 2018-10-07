/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo.server;

import java.net.*;
import java.io.*;
import java.util.Arrays;

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
            OutputStream out = null;

            byte[] clientInData = new byte[8000]; //A large size is used and will be truncated into another array for output

            System.out.println("Waiting for a connection");
            
            while (connected == false) { //Listen for connections
                client = sock.accept();
                if (client != null) {
                    System.out.println("Connection established");
                    connected = true;
                }
            }
            while (connected == true) { //Read and send the echo request from the client
                in = client.getInputStream(); //Get input stream from client
                out = client.getOutputStream(); //Create byte stream to client
                byte[] clientOutData;
                
                int nBytesRead = in.read(clientInData); //Track the number of bytes read while reading them
                
                if (nBytesRead != -1) { //Ensure the socket connection is still established
                    clientOutData = Arrays.copyOf(clientInData, nBytesRead); //Resize the original input byte array
                } else {
                    connected = false;
                    break;
                }

                out.write(clientOutData); //Write bytes to client
                out.flush();
            }
            client.close(); //Close client connection if -1 is returned from the input stream
            System.out.println("Connection closed. Terminating.");
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

}
