package com.example;
 
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

 
public class App {
    public static void main(String[] args) throws Exception {
        runn();
    }

    public static void runn() throws Exception {
        
        PrintStream out = new PrintStream(new FileOutputStream("first_example.txt"));
        System.setOut(out);


        Queue<String> q = new ConcurrentLinkedQueue<String>();
        long t = System.currentTimeMillis();
        long end = t + 10000;
        int count = 0;
        System.out.println();
        System.out.println("==================== " + "Current time: " 
            + 0 + "s" + " ====================");
        System.out.println();

        for (int i = 3; i >= 0; i--) {
            ThreadRunner r = new ThreadRunner(i, q);
            r.start();
        }
        
        // Producer send message.
        String basePayload = "Important Task";
        for (int i = 0; i < 20; i++) {
            // Create message.
            String msg = basePayload + i;

            // Producer send message.
            System.out.println("Producer sending text '" + msg + "'" + " to MessageQueue");
            q.add(msg); 
        }

        while(System.currentTimeMillis() < end) {
            if ((int)((System.currentTimeMillis() - t) / 999) > count) {
                System.out.println();
                System.out.println("==================== " + "Current time: " 
                    + (count + 1) + "s" + " ====================");
                System.out.println();
                count += 1;
            }
        }
    }
}

