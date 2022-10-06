package com.example;

import java.util.Queue;

class ThreadRunner implements Runnable {
   private Thread t;
   private String threadName;
   private int id;
   private Queue<String> queue;
   
   ThreadRunner( int id, Queue<String> queue) {
      threadName = "Thread-" + id;
      this.id = id;
      this.queue = queue;
      // System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      // System.out.println("Running " +  threadName );
      int count = 0;
      try {
         long t = System.currentTimeMillis();
         long end = t + 10000;
         while(System.currentTimeMillis() < end) {
            if ((int)((System.currentTimeMillis() - t) / 1000) >= count) {
               String msg = (String)queue.poll(); 
               if (msg != null) {
                  count += 1;
                  System.out.println("Consumer " + id + " received: "
                     + "'" + msg + "'" + " and finish after " + (1) + " seconds");
               }
            }
            Thread.sleep(100);
         }  
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   
   public void start () {
      // System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}