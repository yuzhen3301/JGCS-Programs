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
		//��LinkedList<E>������ͨ�б�
		List<String> list = new LinkedList<>();
		list.add("0451"); //����������
		list.add("0452"); //�����������
		list.add("0453"); //ĵ��������
		list.add("0454"); //��ľ˹����
		list.add("0455"); //�绯����
		list.add("0456"); //�ں�����
		list.add("0458"); //��������
		list.add("0459"); //��������
		list.add(6, "0457"); //�Ӹ�������� //�������
		System.out.println("list�ĵ�1�������" + list);
		
		System.out.println("list�ĵ�2�������list�ĵ�4��Ԫ����" + list.get(3));
		
		list.remove(2); //���ɾ��
		list.remove("0457"); //���ɾ��
		System.out.println("list�ĵ�3�������" + list);
		
		//��LinkedList<E>������ջ
		LinkedList<Entry> stack = new LinkedList<>();
		stack.push(new Entry("������", 0451)); //0451��һ���˽���������ʾʮ������297
		stack.push(new Entry("�������", 0452));
		stack.push(new Entry("ĵ����", 0453));
		stack.push(new Entry("��ľ˹", 0454));
		stack.push(new Entry("�绯", 0455));
		System.out.println("\nstack�ĵ�1�������"  + stack);
		
		stack.pop();
		System.out.println("stack�ĵ�2�������"  + stack);
		
		System.out.println("stack�ĵ�3�������stack�ĵ�ǰջ��Ԫ����"  + stack.peek());
		
		//��LinkedList<E>�������л�˫�˶���
		LinkedList<String> queue = new LinkedList<>();
		queue.addLast("������"); //�ڶ�β����
		queue.addLast("�������");
		queue.addLast("ĵ����");
		queue.addLast("��ľ˹");
		queue.addLast("�绯");
		System.out.println("\nqueue�ĵ�1�������" + queue);
		
		String head = queue.removeFirst(); //�Ӷ�ͷȡ��
		head = head + ", " +queue.removeFirst();
		System.out.println("queue�ĵ�2�������" + queue + ", �Ӷ�ͷȡ����Ԫ����:" + head);
		
		queue.offerFirst("�ں�"); //�ڶ�ͷ����
		queue.addFirst("�Ӹ����"); //�ڶ�ͷ����
		System.out.println("queue�ĵ�3�������" + queue);
		
		String tail = queue.pollLast(); //�Ӷ�βȡ��
		tail = tail + ", " + queue.removeLast(); //�Ӷ�βȡ��
		System.out.println("queue�ĵ�4�������" + queue + ", �Ӷ�βȡ����Ԫ����:" + tail);
	}
}