package com.jgcs.chp6.p2;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

class Process implements Comparable<Process>{
	private int pid; //����id
	private String pname; //��������
	private String pgroup; //�������������������
	private int ppriority; //�������ȼ�
	private int pwaitingtime; //���̵ȴ�ʱ��
	
	Process(int id, String name, String group, int priority, int waitingtime){
		this.pid = id;
		this.pname = name;
		this.pgroup = group;
		this.ppriority = priority;
		this.pwaitingtime = waitingtime;
	}
	
	public static Process generateProcess() { //Process�Ĺ����������Զ�����Process����
		int id = new Random().nextInt() >>> 1; //�޷�������һλ��ʹid��ԶΪ������
		
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //��ĸ��
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < 6; i++){
	       int index = new Random().nextInt(alphabet.length());
	       sb.append(alphabet.charAt(index));
	    }
		String name = sb.toString();
		
		String[] groups = {"main", "user", "system", "daemon", "mysql", "hadoop", "spark", "office"};
		String group = groups[new Random().nextInt(groups.length)];
		
		int priority = new Random().nextInt(1000);
		
		int waitingtime = new Random().nextInt(10000) >>> 1; //�޷�������һλ��ʹwaitingtime��ԶΪ������
		
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
	public int compareTo(Process o) { //�Ƚ��߼����������ȼ����бȽϣ���ǰProcess����this�����ȼ�С�ڣ�����ڣ�o�����ȼ����򷵻�-1����1����������򷵻�0
		if(ppriority < o.getPpriority()) {
			return -1;
		}else if(ppriority > o.getPpriority()) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) { //���ݽ���ppriorityȷ����ǰ�����Ƿ���obj������Ľ�����ȣ�Ϊ������compareTo()һ���Բ�ʹ��priority������Ӧʹ��pid��
		if(ppriority == ((Process)obj).getPpriority()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int hashCode() { //������equals()һ�£�����������equals()����ȣ������ǵ�hashCode()����Ҳ���
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
	public int compare(Process p1, Process p2) { //�Ƚ��߼������ݵȴ�ʱ����бȽϣ���ǰp1�ĵȴ�ʱ��С�ڣ�����ڣ�p2�ĵȴ�ʱ�䣬�򷵻�1����-1����������򷵻�0
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
		for(int i = 0; i < pqInitialCapacity; i++) { //����Process�������pqWithComparable��pqWithComparator
			Process p = Process.generateProcess(); //����һ��Process����
			if(!pqWithComparable.contains(p)) {
				pqWithComparable.add(p);
			}
			
			p = Process.generateProcess(); //����һ���µ�Process����
			if(!pqWithComparator.contains(p)) {
				pqWithComparator.offer(Process.generateProcess()); //����Ҳ������add()
			}
		}
		
		System.out.println("����ϵͳ����pqWithComparable�еĽ��̣������ȼ���С������ȣ�");
		while(!pqWithComparable.isEmpty()) {
			Process p = pqWithComparable.remove(); //������poll()Ҳ����
			p.run();
		}
		
		System.out.println("\n����ϵͳ����pqWithComparator�еĽ��̣����ȴ�ʱ��ӳ����̵��ȣ�");
		while(!pqWithComparator.isEmpty()) {
			Process p = pqWithComparator.poll(); //������remove()Ҳ����
			p.run();
		}
		
		System.out.println("\n����ϵͳ���н��̽�����");
	}
}