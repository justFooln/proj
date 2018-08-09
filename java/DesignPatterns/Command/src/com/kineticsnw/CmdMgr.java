package com.kineticsnw;

// Was called DeviceButton in the original example code
// This is known as the invoker
// It has a method doCmd() that when executed
// causes the execute method to be called

// The execute method for the Command interface then calls
// the method assigned in the class that implements the
// Command interface


import java.util.LinkedList;
import java.util.NoSuchElementException;


public class CmdMgr {

  // Store a history of commands in a stack
  private LinkedList<Command> cmdStack = new LinkedList<Command>();
  private int cmdStackLoc;

  public CmdMgr() {

    // Clear all previous commands in the stack - belt and suspenders
    this.cmdStack.clear();

    this.cmdStackLoc = 0;

  }

  // Execute a command
  public void doCmd( Command theNextCommand ) {

    theNextCommand.execute();

    resetCmdStack( theNextCommand );

  }

  // Now the remote can undo past commands

  public void undoCmd() {

    Command theCommand = null;
    try {
      theCommand = popCmd();
      theCommand.undo();
      cmdStackLoc++;
    } catch (NoSuchElementException err) {
      System.out.println( "Cannot undo any more: " + err.getMessage() );
    }

  }

  public void redoCmd() {
    Command theCommand = null;
    try {
      cmdStackLoc--;
      theCommand = popCmd();
      theCommand.execute();
    } catch (NoSuchElementException err) {
      System.out.println( "Cannot redo any more: " + err.getMessage() );
    }
  }

  private void pushCmd( Command cmd ) {

    cmdStack.addFirst( cmd );

  }

  private Command popCmd() {

    Command lastExecuted = null;

    if (cmdStack.size() == 0) {

      throw new NoSuchElementException( "Command Stack is empty" );

    } else {

      lastExecuted = cmdStack.get(cmdStackLoc);

    }
    return lastExecuted;
  }


  private void resetCmdStack( Command cmdJustExecuted ) {

    if (cmdStackLoc !=0){
      // We're in the midst of the cmd stack and have received a new command
      // Flush the command stack
      cmdStack.clear();
      cmdStackLoc = 0;
    }
    // Push the new command on the head of the command stack.
    pushCmd( cmdJustExecuted );

  }

}