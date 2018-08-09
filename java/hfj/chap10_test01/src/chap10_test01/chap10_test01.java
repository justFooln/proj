package chap10_test01;
/**
 * Demonstrates need to initialize when using Integer type rather than int.
 * Fix would be to assign reference i to an Integer object. For example, replace the declaration
 * 	Integer i;
 * with
 * 	Integer i = new Integer(5);
 */
public class chap10_test01 {

	Integer i;						// declares a reference to an object of type integer. Value is null
	int j;							// declares a primitive of type int, default value is 0.
	
	public static void main(String[] args) {
		chap10_test01 t = new chap10_test01();
		t.go();
	}
	
	public void go() {
		j = i;						// CRASH! At this point assignment is trying to assign a primative value to a null reference.
		System.out.println(j);
		System.out.println(i);
	}

	
}
