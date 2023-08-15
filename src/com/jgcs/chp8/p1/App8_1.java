package com.jgcs.chp8.p1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Spliterator;

public class App8_1 {
	public static void main(String[] args) {
		String[] rivers = {"黑龙江", "松花江", "嫩江", "呼兰河", "穆棱河", "倭肯河", "汤旺河", "讷漠尔河", "乌裕尔河", "阿穆尔河", "乌苏里江", "拉林河", "通肯河", "挠力河", "牡丹江", "五大连池", "兴凯湖"};
		
		HashSet<String> hset = new HashSet<>(8);
		LinkedHashSet<String> lkset = new LinkedHashSet<>(32, 0.8f);
		
		for(int i = 0; i < rivers.length; i++) {
			hset.add(rivers[i]);
			lkset.add(rivers[i]);
		}
		System.out.println("hset的第一次输出（共" + hset.size() + "个元素）：" + hset);
		System.out.println("lkset的第一次输出（共" + lkset.size() + "个元素）：" + lkset + "\n");
		
		System.out.println("hset.containsAll(lkset)：" + hset.containsAll(lkset) + "\n");
		if(hset.contains("黑龙江") && lkset.contains("黑龙江")) {
			String strAmur = "阿穆尔河";
			hset.remove(strAmur);
			lkset.remove(strAmur);
		}
		System.out.println("hset的第二次输出（共" + hset.size() + "个元素）：" + hset);
		System.out.println("lkset的第二次输出（共" + lkset.size() + "个元素）：" + lkset + "\n");
		
		Iterator<String> itr = hset.iterator();
		System.out.print("hset的第三次输出（使用迭代器）：[");
		while(itr.hasNext()) {
			System.out.print(itr.next());
			System.out.print(itr.hasNext() ? ", " : "");
		}
		System.out.println("]");
		
		itr = lkset.iterator();
		System.out.print("lkset的第三次输出（使用迭代器）：[");
		while(itr.hasNext()) {
			System.out.print(itr.next());
			System.out.print(itr.hasNext() ? ", " : "");
		}
		System.out.println("]\n");
		
		Spliterator<String> spitr = hset.spliterator();
		Spliterator<String> left_sp = spitr.trySplit();
		
		System.out.print("hset的left_sp：");
		left_sp.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		System.out.print("hset的spitr：");
		spitr.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		
//		lkset = new LinkedHashSet<>();
//		for(int i = 0; i < 1030; i++) {
//			lkset.add(""+i);
//		}
		spitr = lkset.spliterator();
		left_sp = spitr.trySplit();
		System.out.print("lkset的left_sp：");
		left_sp.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		System.out.print("lkset的spitr：");
		spitr.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
	}
}