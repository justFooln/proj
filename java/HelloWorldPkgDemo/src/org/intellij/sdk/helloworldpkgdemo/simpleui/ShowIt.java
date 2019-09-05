package org.intellij.sdk.helloworldpkgdemo.simpleui;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ShowIt {
  
  public ShowIt() {
  }
  
  String str1 = "I am a string";
  String str2 = "String number two";
  Date date1 = new Date();
  Date date2 = new Date();
  
  public void showNow() {
//234567890123456789012345678901234567890123456789012345678901234567890
    String str = new String("تعطي يونيكود رقما فريدا لكل حرف".getBytes(), UTF_8);
    System.out.println(str);
    
    if (str1 == str2) {
      if (date1 != date2) {
        boolean foo = true;
        String name = foo ? "JOHN" : "john";
        System.out.println("Printing from ShowIt.showNow");
      }
    }
    
  }
  
}
