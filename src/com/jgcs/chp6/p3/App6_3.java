package com.jgcs.chp6.p3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

enum UniversityClass{
	//普通，省属重点，211，双一流，985
	ClassOrdinary, ClassProvincialKey, Class211, ClassDoubleFirst, Class985
}

class University{
	int uno; //大学代码
	String uname; //大学名称
	UniversityClass[] uclasses; //大学级别
	
	public University(int uno, String uname, UniversityClass[] uclasses) {
		this.uno = uno;
		this.uname = uname;
		this.uclasses = uclasses;
		
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("University [uno=" + uno + ", uname=" + uname + ", uclasses=(");
		String[] classnames = {"普通高等院校", "省属重点大学", "211大学", "双一流大学", "985大学"};
		for(int i = 0; i < uclasses.length; i++) {
			sb.append(classnames[uclasses[i].ordinal()] + ((i == uclasses.length - 1) ? "" : ", "));
		}
		sb.append(")]");
		
		return sb.toString();
	}
}

public class App6_3 {
	public static void main(String[] args) {
		//将Deque<E>用作普通队列
		Deque<String> queue = new ArrayDeque<>();
		queue.add("哈尔滨工业大学：10213"); //哈尔滨工业大学学校代码：10213
		queue.addLast("哈尔滨工程大学：10217"); //哈尔滨工程大学学校代码：10217
		queue.offer("东北林业大学：10225"); //东北林业大学学校代码：10225
		queue.add("黑龙江大学：10212"); //黑龙江大学学校代码：10212
		queue.offer("哈尔滨师范大学：10231"); //哈尔滨师范大学学校代码：10231
		queue.add("齐齐哈尔大学：10232"); //齐齐哈尔大学学校代码：10232
		queue.offer("绥化学院：10236"); //绥化学院学校代码：10236
		System.out.println("queue的第1次输出：" + queue);
		
		System.out.print("queue的第2次输出：[");
		Iterator<String> descItr = queue.descendingIterator();
		while(descItr.hasNext()) {
			String university = descItr.next();
			System.out.print(university);
			System.out.print(descItr.hasNext() ? ", " : "");
		}
		System.out.println("]");
		
		System.out.print("queue的第3次输出：[");
		while(queue.peek() != null) { //如果队列非空，peek()只返回队头元素，但并不删除它
			System.out.print(queue.poll()); //进行出队操作
			System.out.print((queue.peekFirst() != null) ? ", " : "");
		}
		System.out.println("]");
		
		//将Deque<E>用作堆栈
		Deque<University> stack = new ArrayDeque<>();
		UniversityClass[] classes = {UniversityClass.Class985, UniversityClass.Class211, UniversityClass.ClassDoubleFirst};
		stack.push(new University(10213, "哈尔滨工业大学", classes)); //985、211、双一流大学
		
		classes = new UniversityClass[] {UniversityClass.Class211, UniversityClass.ClassDoubleFirst};
		stack.push(new University(10217, "哈尔滨工程大学", classes)); //211、双一流大学
		
		classes = new UniversityClass[] {UniversityClass.Class211, UniversityClass.ClassDoubleFirst};
		stack.push(new University(10225, "东北林业大学", classes)); //211、双一流大学

		stack.push(new University(10212, "黑龙江大学", new UniversityClass[] {UniversityClass.ClassProvincialKey})); //省属重点大学
		stack.push(new University(10231, "哈尔滨师范大学", new UniversityClass[] {UniversityClass.ClassProvincialKey})); //省属重点大学
		stack.push(new University(10232, "齐齐哈尔大学", new UniversityClass[] {UniversityClass.ClassProvincialKey})); //省属重点大学
		stack.push(new University(10236, "绥化学院", new UniversityClass[] {UniversityClass.ClassOrdinary})); //普通高等院校
		System.out.println("\nstack的第1次输出：");
		while(stack.peek() != null) { //如果堆栈非空，peek()只返回栈顶元素，但并不删除它
			System.out.println(stack.pop());
		}
		System.out.println();
		
		//将Deque<E>用作双端队列
		Deque<String> deque = new ArrayDeque<>(32);
		deque.addFirst("哈尔滨工业大学：10213"); //在队头插入
		deque.offerFirst("哈尔滨工程大学：10217"); //在队头插入
		deque.addFirst("哈尔滨师范大学：10231"); //在队头插入
		System.out.println("deque的第1次输出：" + deque);
		
		deque.removeLast(); //在队尾删除并返回队尾元素
		System.out.println("deque的第2次输出：" + deque);
		
		deque.pollLast(); //在队尾删除并返回队尾元素
		System.out.println("deque的第3次输出：" + deque);
		
		deque.add("哈尔滨工程大学：10217"); //在队尾插入，这里用offer()也可以
		deque.offer("哈尔滨工业大学：10213"); //在队尾插入，这里用add()也可以
		System.out.println("deque的第4次输出：" + deque);
		
		deque.remove(); //在队头删除并返回队头元素
		System.out.println("deque的第5次输出：" + deque);
		
		deque.poll(); //在队头删除并返回队头元素
		System.out.println("deque的第6次输出：" + deque);
	}
}