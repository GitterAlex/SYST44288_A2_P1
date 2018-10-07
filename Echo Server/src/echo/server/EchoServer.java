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
            DataOutputStream out = null;
            ByteArrayOutputStream outBuffer = null;

            byte[] clientInData = new byte[8000]; //A large size is used and will be truncated by the ByteArrayOutputStream

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
                out = new DataOutputStream(client.getOutputStream()); //Create byte stream to client
                outBuffer = new ByteArrayOutputStream(); //Setup a dynamic byte array for String conversion

                int bufsize = in.read(clientInData, 0, clientInData.length); //Track the number of bytes read while reading them
                if (bufsize != -1) { //Ensure the socket connection is still established
                    outBuffer.write(clientInData, 0, bufsize); //Write the number of bytes read into the dynamic array for clean output
                } else {
                    connected = false;
                    break;
                }

                outBuffer.flush();
                byte[] clientOutData = outBuffer.toByteArray(); //Send resized array to new array of bytes for the client

                out.write(clientOutData); //Write bytes to client
                out.flush();
                outBuffer.close();
            }
            client.close(); //Close client connection if -1 is returned from the input stream
            System.out.println("Connection closed. Terminating.");
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

}
