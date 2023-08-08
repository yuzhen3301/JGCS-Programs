package com.jgcs.chp3.p4;

import java.util.LinkedList;
import java.util.List;

class Entry{
	String cityName;
	int cityCode;
	
	Entry(String cName, int cCode){
		cityName = cName;
		cityCode = cCode;
	}
	
	public String toString() {
		return "(" + cityName + ", " + cityCode + ")";
	}
}

public class App3_4 {
	public static void main(String[] args) {
		//将LinkedList<E>用作普通列表
		List<String> list = new LinkedList<>();
		list.add("0451"); //哈尔滨区号
		list.add("0452"); //齐齐哈尔区号
		list.add("0453"); //牡丹江区号
		list.add("0454"); //佳木斯区号
		list.add("0455"); //绥化区号
		list.add("0456"); //黑河区号
		list.add("0458"); //伊春区号
		list.add("0459"); //大庆区号
		list.add(6, "0457"); //加格达奇区号 //随机插入
		System.out.println("list的第1次输出：" + list);
		
		System.out.println("list的第2次输出：list的第4个元素是" + list.get(3));
		
		list.remove(2); //随机删除
		list.remove("0457"); //随机删除
		System.out.println("list的第3次输出：" + list);
		
		//将LinkedList<E>用作堆栈
		LinkedList<Entry> stack = new LinkedList<>();
		stack.push(new Entry("哈尔滨", 0451)); //0451是一个八进制数，表示十进制数297
		stack.push(new Entry("齐齐哈尔", 0452));
		stack.push(new Entry("牡丹江", 0453));
		stack.push(new Entry("佳木斯", 0454));
		stack.push(new Entry("绥化", 0455));
		System.out.println("\nstack的第1次输出："  + stack);
		
		stack.pop();
		System.out.println("stack的第2次输出："  + stack);
		
		System.out.println("stack的第3次输出：stack的当前栈顶元素是"  + stack.peek());
		
		//将LinkedList<E>用作队列或双端队列
		LinkedList<String> queue = new LinkedList<>();
		queue.addLast("哈尔滨"); //在队尾插入
		queue.addLast("齐齐哈尔");
		queue.addLast("牡丹江");
		queue.addLast("佳木斯");
		queue.addLast("绥化");
		System.out.println("\nqueue的第1次输出：" + queue);
		
		String head = queue.removeFirst(); //从队头取出
		head = head + ", " +queue.removeFirst();
		System.out.println("queue的第2次输出：" + queue + ", 从队头取出的元素是:" + head);
		
		queue.offerFirst("黑河"); //在队头插入
		queue.addFirst("加格达奇"); //在队头插入
		System.out.println("queue的第3次输出：" + queue);
		
		String tail = queue.pollLast(); //从队尾取出
		tail = tail + ", " + queue.removeLast(); //从队尾取出
		System.out.println("queue的第4次输出：" + queue + ", 从队尾取出的元素是:" + tail);
	}
}