/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Sam
 */
public class EchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            Socket sock = new Socket("localhost", 6013); //Create socket with server
            String echoRequest = null;
            
            System.out.println("This application will send your data to the server, which will echo it back. Enter the character '~' to exit.");
            do {
                System.out.print("Phrase to server: "); //Get echo from user
                echoRequest = scan.next();

                sendMessage(echoRequest, sock);
                String echoedString = recieveEcho(sock);

                System.out.println("Server reply> " + echoedString);
            } while (!echoRequest.equals("~"));
            sock.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public static void sendMessage(String sendEcho, Socket sock) {
        try {
            PrintWriter rout = new PrintWriter(sock.getOutputStream(), true); //Create output stream to server
            rout.println(sendEcho); //Send echo string to server
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public static String recieveEcho(Socket sock) {
        String line = null;
        try {
            InputStream in = sock.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            line = bin.readLine();

        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        return line;
    }

}
