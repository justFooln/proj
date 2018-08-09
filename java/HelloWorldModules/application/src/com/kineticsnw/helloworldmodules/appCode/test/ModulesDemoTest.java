package com.kineticsnw.helloworldmodules.appCode.test; /**
 * Copyright 2018 Kinetics Northwest LLC.
 * See the LICENSE.TXT file for the specific language governing permissions and limitations.
 */

import com.kineticsnw.helloworldmodules.appCode.java.ModulesDemo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ModulesDemoTest {

  ModulesDemo testMe;
  String[] args = {"A"};

  @BeforeEach
  void setUp() {
    this.testMe = new ModulesDemo();
    assertNotNull( testMe, "ModulesDemo instance is null." );
    assertSame(ModulesDemo.class, testMe.getClass(), "ModulesDemo instance is not ModulesDemo.class.");
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void main() {
    ModulesDemo.main( args );
  }
}