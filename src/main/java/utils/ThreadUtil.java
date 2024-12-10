package utils;

import org.testng.Assert;

public class ThreadUtil {

    public static void checkIfRunningInParallel() {
        // Check the current thread
        String currentThread = Thread.currentThread().getName();
        System.out.println("Current thread: " + currentThread);

        // Validate that the tests are running on multiple threads
        Assert.assertTrue(currentThread.startsWith("TestNG-test"),
                "Test is not running in parallel threads. Current thread: " + currentThread);
    }
}
