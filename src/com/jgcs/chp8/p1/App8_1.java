package com.jgcs.chp8.p1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Spliterator;

public class App8_1 {
	public static void main(String[] args) {
		String[] rivers = {"������", "�ɻ���", "�۽�", "������", "�����", "���Ϻ�", "������", "ګĮ����", "��ԣ����", "���¶���", "�����ｭ", "���ֺ�", "ͨ�Ϻ�", "������", "ĵ����", "�������", "�˿���"};
		
		HashSet<String> hset = new HashSet<>(8);
		LinkedHashSet<String> lkset = new LinkedHashSet<>(32, 0.8f);
		
		for(int i = 0; i < rivers.length; i++) {
			hset.add(rivers[i]);
			lkset.add(rivers[i]);
		}
		System.out.println("hset�ĵ�һ���������" + hset.size() + "��Ԫ�أ���" + hset);
		System.out.println("lkset�ĵ�һ���������" + lkset.size() + "��Ԫ�أ���" + lkset + "\n");
		
		System.out.println("hset.containsAll(lkset)��" + hset.containsAll(lkset) + "\n");
		if(hset.contains("������") && lkset.contains("������")) {
			String strAmur = "���¶���";
			hset.remove(strAmur);
			lkset.remove(strAmur);
		}
		System.out.println("hset�ĵڶ����������" + hset.size() + "��Ԫ�أ���" + hset);
		System.out.println("lkset�ĵڶ����������" + lkset.size() + "��Ԫ�أ���" + lkset + "\n");
		
		Iterator<String> itr = hset.iterator();
		System.out.print("hset�ĵ����������ʹ�õ���������[");
		while(itr.hasNext()) {
			System.out.print(itr.next());
			System.out.print(itr.hasNext() ? ", " : "");
		}
		System.out.println("]");
		
		itr = lkset.iterator();
		System.out.print("lkset�ĵ����������ʹ�õ���������[");
		while(itr.hasNext()) {
			System.out.print(itr.next());
			System.out.print(itr.hasNext() ? ", " : "");
		}
		System.out.println("]\n");
		
		Spliterator<String> spitr = hset.spliterator();
		Spliterator<String> left_sp = spitr.trySplit();
		
		System.out.print("hset��left_sp��");
		left_sp.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		System.out.print("hset��spitr��");
		spitr.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		
//		lkset = new LinkedHashSet<>();
//		for(int i = 0; i < 1030; i++) {
//			lkset.add(""+i);
//		}
		spitr = lkset.spliterator();
		left_sp = spitr.trySplit();
		System.out.print("lkset��left_sp��");
		left_sp.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		System.out.print("lkset��spitr��");
		spitr.forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
	}
}