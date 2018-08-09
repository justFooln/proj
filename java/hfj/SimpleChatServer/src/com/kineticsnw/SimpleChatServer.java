package com.kineticsnw;

import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {

  ArrayList clientOutputStreams;

  /*
  * Member class for thread to handle messages from clients
  */
  public class ClientHandler implements Runnable {
    BufferedReader readerBuf;
    Socket sock;

    public ClientHandler( Socket clientSocket ) {
      try {
        sock = clientSocket;
        InputStreamReader isReader = new InputStreamReader( sock.getInputStream() );
        readerBuf = new BufferedReader( isReader );
      } catch ( Exception ex ) {
        ex.printStackTrace();
      }
    } // end constructor

    public void run() {
      String message;
      try {
        while ( ( message = readerBuf.readLine() ) != null ) {
          System.out.println( "Server: read " + message );
          tellEveryone( message );
        }  // while
      } catch ( Exception ex ) {
        ex.printStackTrace();
      }  // catch/try
    }  // end run

  }  // end ClientHandler

  public static void main( String[] args ) {
    SimpleChatServer chatServer = new SimpleChatServer();
    chatServer.go();
  }

  public void go() {
    clientOutputStreams = new ArrayList();

    try {
      ServerSocket serverSock = new ServerSocket( 5000 );

      while ( true ) {

        // Waits on this statement until a client attempts to connect to server.
        // When a client connects, serverSock returns a new socket with the IP and port
        // connect to the client. Record the client's writer so the server can broadcast
        // to all writers (clients) in the clientOutputStreams ArrayList.
        Socket clientSocket = serverSock.accept();
        PrintWriter writer = new PrintWriter( clientSocket.getOutputStream() );
        clientOutputStreams.add( writer );

        // Now create a thread specific to the client
        Thread threadClient = new Thread( new ClientHandler( clientSocket ) );
        threadClient.start();
        System.out.println( "Server: Got a client connection" );

      } // end infinite while loop

    } catch ( Exception ex ) {
      ex.printStackTrace();
    } // end try/catch
  } // end go


  public void tellEveryone( String message ) {

    Iterator it = clientOutputStreams.iterator();
    System.out.println( "Server: number of clients: " + clientOutputStreams.size() );

    while ( it.hasNext() ) {
      try {
        PrintWriter writer = (PrintWriter) it.next();
        writer.println( message );
        writer.flush();
      } catch ( Exception ex ) {
        ex.printStackTrace();
      } // end try/catch
    } // end while

  } // end tellEveryone


} // end SimpleChatServer
