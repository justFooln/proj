package org.gradle.tests0;

import org.junit.Test;

import static junit.framework.TestCase.fail;

public class Test0_0 {
    @Test
    public void myTest() throws Exception {
        Thread.sleep(150);
        // fail on purpose to demonstrate results of failure.
        fail("Ooops");
    }
}