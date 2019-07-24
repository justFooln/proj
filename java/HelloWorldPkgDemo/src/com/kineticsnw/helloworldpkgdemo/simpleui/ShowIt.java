package com.kineticsnw.helloworldpkgdemo.simpleui;


import java.util.Date;
public class ShowIt {

  public ShowIt() {

  }

  String str1 = "I am a string";
  String str2 = "String number two";
  Date date1 = new Date();
  Date date2 = new Date();

  // Now display the text
  public void showNow() {

    if (str1==str2) {


      if ( date1 != date2 ) {
        boolean foo = true;
        String name = foo ? "JOHN" : "john";
        System.out.println( "Printing from ShowIt.showNow" ) ;
      }
    }
  }



}
