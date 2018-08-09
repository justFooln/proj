import java.io.*;
import java.net.*;

import java.io.IOException;

public class DailyAdviceServer {

    final private String[] adviceList = {"foo", "bar", "red", "orange", "yellow", "green", "blue", "indigo", "violet"};

    final private int serverPort = 4242;

    public void go() {
        try {

            ServerSocket advSerSoc = new ServerSocket( serverPort );

            while ( true ) {
                Socket sock = advSerSoc.accept();

                PrintWriter advSerPW = new PrintWriter( sock.getOutputStream() );
                String randomAdv = getAdvice();
                advSerPW.println( randomAdv );
                advSerPW.close();
                System.out.println( "Daily Advice Server: " + randomAdv );
            }
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    } // End go

    private String getAdvice() {
        int random = (int) ( Math.random() * adviceList.length );
        return adviceList[random];
    }  // End getAdvice

    public static void main( String[] args ) {

        // Instantiate an advice server
        DailyAdviceServer serverInst = new DailyAdviceServer();

        // Start the instance
        serverInst.go();

    }  // End main


}  // Daily Advice Server



