package com.jgcs.chp6.p2;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

class Process implements Comparable<Process>{
	private int pid; //进程id
	private String pname; //进程名称
	private String pgroup; //进程所属进程组的名称
	private int ppriority; //进程优先级
	private int pwaitingtime; //进程等待时间
	
	Process(int id, String name, String group, int priority, int waitingtime){
		this.pid = id;
		this.pname = name;
		this.pgroup = group;
		this.ppriority = priority;
		this.pwaitingtime = waitingtime;
	}
	
	public static Process generateProcess() { //Process的工厂方法，自动生成Process对象
		int id = new Random().nextInt() >>> 1; //无符号右移一位，使id永远为正整数
		
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //字母表
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < 6; i++){
	       int index = new Random().nextInt(alphabet.length());
	       sb.append(alphabet.charAt(index));
	    }
		String name = sb.toString();
		
		String[] groups = {"main", "user", "system", "daemon", "mysql", "hadoop", "spark", "office"};
		String group = groups[new Random().nextInt(groups.length)];
		
		int priority = new Random().nextInt(1000);
		
		int waitingtime = new Random().nextInt(10000) >>> 1; //无符号右移一位，使waitingtime永远为正整数
		
		return new Process(id, name, group, priority, waitingtime);
	}
	
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	public String getPgroup() {
		return pgroup;
	}

	public void setPgroup(String pgroup) {
		this.pgroup = pgroup;
	}

	public int getPpriority() {
		return ppriority;
	}

	public void setPpriority(int ppriority) {
		this.ppriority = ppriority;
	}

	public int getPwaitingtime() {
		return pwaitingtime;
	}

	public void setPwaitingtime(int pwaitingtime) {
		this.pwaitingtime = pwaitingtime;
	}

	@Override
	public int compareTo(Process o) { //比较逻辑：根据优先级进行比较，当前Process对象this的优先级小于（或大于）o的优先级，则返回-1（或1），若相等则返回0
		if(ppriority < o.getPpriority()) {
			return -1;
		}else if(ppriority > o.getPpriority()) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) { //根据进程ppriority确定当前进程是否与obj所代表的进程相等（为保持与compareTo()一致性才使用priority，正常应使用pid）
		if(ppriority == ((Process)obj).getPpriority()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int hashCode() { //保持与equals()一致，两个对象在equals()上相等，则它们的hashCode()必须也相等
		return (ppriority << 8) & ppriority;
	}

	public void run() {
		System.out.print("Process#" + pid + "[name: " + pname + ", group: " + pgroup + ", priority: " + ppriority + ", waitingtime: " + pwaitingtime +"] is running: ");
		for(int i = 0; i < new Random().nextInt(10); i++) {
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(".");
		}
		System.out.println(" done!");
	}
}

class ComparatorOnWaitingTime implements Comparator<Process>{
	public int compare(Process p1, Process p2) { //比较逻辑：根据等待时间进行比较，当前p1的等待时间小于（或大于）p2的等待时间，则返回1（或-1），若相等则返回0
		long wt1 = p1.getPwaitingtime();
		long wt2 = p2.getPwaitingtime();
		if(wt1 < wt2) {
			return 1;
		}else if(wt1 > wt2) {
			return -1;
		}else {
			return 0;
		}
	}
}

public class App6_2 {
	public static void main(String[] args) {
		int pqInitialCapacity = 12;
		PriorityQueue<Process> pqWithComparable = new PriorityQueue<>(pqInitialCapacity);
		PriorityQueue<Process> pqWithComparator = new PriorityQueue<>(pqInitialCapacity, new ComparatorOnWaitingTime());
		for(int i = 0; i < pqInitialCapacity; i++) { //生成Process对象填充pqWithComparable和pqWithComparator
			Process p = Process.generateProcess(); //生成一个Process对象
			if(!pqWithComparable.contains(p)) {
				pqWithComparable.add(p);
			}
			
			p = Process.generateProcess(); //生成一个新的Process对象
			if(!pqWithComparator.contains(p)) {
				pqWithComparator.offer(Process.generateProcess()); //这里也可以用add()
			}
		}
		
		System.out.println("操作系统运行pqWithComparable中的进程，按优先级从小到大调度：");
		while(!pqWithComparable.isEmpty()) {
			Process p = pqWithComparable.remove(); //这里用poll()也可以
			p.run();
		}
		
		System.out.println("\n操作系统运行pqWithComparator中的进程，按等待时间从长到短调度：");
		while(!pqWithComparator.isEmpty()) {
			Process p = pqWithComparator.poll(); //这里用remove()也可以
			p.run();
		}
		
		System.out.println("\n操作系统运行进程结束！");
	}
}