package com.kineticsnw;

/*
*   This code illustrates the Command Pattern.
*   I'm pretty happy with the results - it supports do, undo, and redo
*   in the command manager (invoker). To support these functions I put
*   the "memory" of past commands in the command manager.
*
*   There is one thing I don't like: commands hold their target (receiver)
*   but the client must remember to use the command manager associated
*   with their target. This seems like a great opportunity for fails and
*   bugs.
*
*   I would like to see a failsafe approach - closer binding between the target and
*   the target's command manager. If the Command interface supported setTarget(), then
*   the client could create a concrete command, and submit it to the target's command
*   manager. The target would be set in the concrete command by the command manager.
*/


import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.List;



public class Main {

  public static void main( String[] args ) {

    //------------ TV ----------------------------------------------

    // Create the ElectronicDevice to use
    ElectronicDevice myTV = new Television();

    // Create a command to turn on myTV
    Command turnOn = new TurnDeviceOn(myTV);

    // Get the "invoker" or cmd manager for the device
    CmdMgr myTVcmdMgr = myTV.getCmdMgr();

    // Instruct the cmd manager ("invoker") to process the command
    // When doCmd() is called theCommand.execute(); is called
    myTVcmdMgr.doCmd(turnOn);

    // Create a command to turn off myTV
    Command turnOff = new TurnDeviceOff(myTV);

    // Cause the cmd manager ("invoker") to process the command
    // Note there is only one cmdMgr for each device, so we reuse the
    // one defined above
    myTVcmdMgr.doCmd(turnOff);

    // Now create a volume up cmd with myTV as the target
    TurnDeviceUp volUp = new TurnDeviceUp( myTV );

    // Note that volUp is targeting myTV, so the cmdMgr for myTV must be used
    myTVcmdMgr.doCmd(volUp);
    myTVcmdMgr.doCmd(volUp);
    myTVcmdMgr.doCmd(volUp);

    // Now create a volume down command targeting myTV
    TurnDeviceDown volDwn = new TurnDeviceDown( myTV );

    // Turn the volumen up and down
    myTVcmdMgr.doCmd(volDwn);
    myTVcmdMgr.doCmd(volUp);
    myTVcmdMgr.doCmd(volDwn);
    myTVcmdMgr.doCmd(volDwn);

    // Now try to undo/redo the last commands
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.redoCmd();
    myTVcmdMgr.redoCmd();
    myTVcmdMgr.redoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.undoCmd();
    myTVcmdMgr.doCmd(turnOn);

    //----------- Radio -----------------------------------------------

    // Create the ElectronicDevice to use
    ElectronicDevice myRadio  = new Radio();

    // Create a command to turn on myRadio
    Command turnOnRadio = new TurnDeviceOn(myRadio);

    // Get the "invoker" or cmd manager for the device
    CmdMgr myRadioCmdMgr = myRadio.getCmdMgr();

    // Instruct the cmd manager ("invoker") to process the command
    // When doCmd() is called theCommand.execute(); is called
    myRadioCmdMgr.doCmd(turnOnRadio);

    // Create a command to turn off myRadio
    Command turnOffRadio = new TurnDeviceOff(myRadio);

    // Cause the cmd manager ("invoker") to process the command
    // Note there is only one cmdMgr for each device, so we reuse the
    // one defined above
    myRadioCmdMgr.doCmd(turnOffRadio);

    // Now create a volume up cmd with myRadio as the target
    TurnDeviceUp volUpRadio = new TurnDeviceUp( myRadio );

    // Note that volUpRadio is targeting myRAdio, so the cmdMgr for myRadio must be used
    myRadioCmdMgr.doCmd(volUpRadio);
    myRadioCmdMgr.doCmd(volUpRadio);
    myRadioCmdMgr.doCmd(volUpRadio);

    // Now create a volume down command targeting myRadio
    TurnDeviceDown volDwnRadio = new TurnDeviceDown( myRadio );

    // Turn the volumen up and down
    myRadioCmdMgr.doCmd(volDwnRadio);
    myRadioCmdMgr.doCmd(volUpRadio);
    myRadioCmdMgr.doCmd(volDwnRadio);
    myRadioCmdMgr.doCmd(volDwnRadio);

/*
    //-------------------- All On Off -----------------------
    // Add the Electronic Devices to a List

    List<ElectronicDevice> allDevices = new ArrayList<ElectronicDevice>();

    allDevices.add( theTV );
    allDevices.add( theRadio );

    // Send the List of Electronic Devices to TurnItAllOff
    // where a call to run execute() on this function will
    // call off() for each device in the list

    TurnItAllOff turnOffDevices = new TurnItAllOff( allDevices );

    // This calls for execute() to run which calls for off() to
    // run for every ElectronicDevice

    CmdMgr turnThemOff = new CmdMgr( turnOffDevices );

    turnThemOff.doCmd();

    //----------------------------------------------------------
*/

  }

}

