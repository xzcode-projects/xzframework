package org.xzframework.security.core.test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    public static Long r = System.currentTimeMillis();

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println(System.currentTimeMillis() - r);
                    r = System.currentTimeMillis();
                    Thread.sleep(6000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 5000);

    }
}
