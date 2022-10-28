// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;
import java.util.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient implements Observer
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  ObservableClient obsclient;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    obsclient = new ObservableClient(host, port); //Call the superclass constructor
    obsclient.addObserver(this);
    this.clientUI = clientUI;
    obsclient.openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    /* 
    if (message.startsWith("#")) {
      switch(message) {
        case "#quit":
          quit();
          break;
        case "#gethost":
          clientUI.display("The Host is : " + getHost());
          break;
        case "#getport":
          clientUI.display("The port is : " + getPort());
          break;
      }
    }
    else {*/
      try
      {
        obsclient.sendToServer(message);
      }
      catch(IOException e)
      {
        clientUI.display
          ("Could not send message to server.  Terminating client.");
        quit();
      }
    }
  //}
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      obsclient.closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }

  public void connectionEstablished() {
    clientUI.display("Connection has been established");
  }

  public void connectionException(Exception exception) {
    clientUI.display("Connection with the server died brutally");
    
  }

  public void connectionClosed() {
    clientUI.display("Connection closed");
    System.exit(0);
  }

  public void update(Observable o, Object arg) {
    
  }
}
//End of ChatClient class
