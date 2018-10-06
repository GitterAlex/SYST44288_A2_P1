/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date.client.socket;

import java.net.*;
import java.io.*;

public class DateClientSocket {

    public static void main(String[] args) {
        try {
            /* make connection to server socket */
            Socket sock = new Socket("localhost", 6013);
            InputStream in = sock.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            /* read the date from the socket */
            String line;
            while ((line = bin.readLine()) != null) {
                System.out.println(line);
            }
            /* close the socket connection*/
            sock.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
