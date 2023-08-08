package com.jgcs.chp3.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class App3_2 {
	public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("myGroup");
        List<String> list = new ArrayList<>();
        //Vector<String> list = new Vector<>();
        MyRunnable runnable = new MyRunnable(list);
        for (int i = 0; i < 10000; i++) {
            new Thread(group, runnable).start();
        }

        //保证所有线程都执行完毕
        while (group.activeCount() > 0) {
            Thread.sleep(10);
        }

        System.out.println(list.size());
	}
}

class MyRunnable implements Runnable {
    private List<String> list;
    public MyRunnable(List<String> list) {
        this.list = list;
    }

    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 2));
        } catch (InterruptedException e) { }

        Random random = new Random();
        //synchronized(list) {
        	list.add(Thread.currentThread().getName() + "\t" + random.nextInt(100));
        //}
    }
}