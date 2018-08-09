import com.kineticsnw.helloworldpkgdemo.apl.HelloWorldPkgDemo;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class HelloWorldPkgDemoTest {

  HelloWorldPkgDemo testMe;
  String[] args = {"A", "B", "C"};

  @BeforeEach
  void setUp() {
    this.testMe = new HelloWorldPkgDemo();
    assertNotNull( testMe, "HelloWorldPkgDemo instance is null." );
    assertSame(HelloWorldPkgDemo.class, testMe.getClass(), "HelloWorldPkgDemo instance is not HelloWorldPkgDemo.class.");
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void main() {
    HelloWorldPkgDemo.main( args );
  }
}