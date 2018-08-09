/**
 * Created by jhake on 4/21/17.
 */

import java.io.*;
import java.net.*;


public class DailyAdviceClient {

  final private String serverIP = "127.0.0.1";
  final private int serverPort = 4242;

  public void go() {

    try {

      Socket adviceSoc = new Socket( serverIP, serverPort );

      InputStreamReader streamReader = new InputStreamReader( adviceSoc.getInputStream() );
      BufferedReader bufReader = new BufferedReader( streamReader );

      String advice = bufReader.readLine();
      System.out.println( "Daily Advice Client: Today you should " + advice );

      bufReader.close();

    } catch ( IOException ex ) {
      ex.printStackTrace();
    }

  } // End go

  public static void main(String[] args) {

    DailyAdviceClient clientApp = new DailyAdviceClient();

    clientApp.go();

  }

} // End class Daily Advice Client
