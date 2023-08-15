package com.jgcs.chp6.p3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

enum UniversityClass{
	//��ͨ��ʡ���ص㣬211��˫һ����985
	ClassOrdinary, ClassProvincialKey, Class211, ClassDoubleFirst, Class985
}

class University{
	int uno; //��ѧ����
	String uname; //��ѧ����
	UniversityClass[] uclasses; //��ѧ����
	
	public University(int uno, String uname, UniversityClass[] uclasses) {
		this.uno = uno;
		this.uname = uname;
		this.uclasses = uclasses;
		
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("University [uno=" + uno + ", uname=" + uname + ", uclasses=(");
		String[] classnames = {"��ͨ�ߵ�ԺУ", "ʡ���ص��ѧ", "211��ѧ", "˫һ����ѧ", "985��ѧ"};
		for(int i = 0; i < uclasses.length; i++) {
			sb.append(classnames[uclasses[i].ordinal()] + ((i == uclasses.length - 1) ? "" : ", "));
		}
		sb.append(")]");
		
		return sb.toString();
	}
}

public class App6_3 {
	public static void main(String[] args) {
		//��Deque<E>������ͨ����
		Deque<String> queue = new ArrayDeque<>();
		queue.add("��������ҵ��ѧ��10213"); //��������ҵ��ѧѧУ���룺10213
		queue.addLast("���������̴�ѧ��10217"); //���������̴�ѧѧУ���룺10217
		queue.offer("������ҵ��ѧ��10225"); //������ҵ��ѧѧУ���룺10225
		queue.add("��������ѧ��10212"); //��������ѧѧУ���룺10212
		queue.offer("������ʦ����ѧ��10231"); //������ʦ����ѧѧУ���룺10231
		queue.add("���������ѧ��10232"); //���������ѧѧУ���룺10232
		queue.offer("�绯ѧԺ��10236"); //�绯ѧԺѧУ���룺10236
		System.out.println("queue�ĵ�1�������" + queue);
		
		System.out.print("queue�ĵ�2�������[");
		Iterator<String> descItr = queue.descendingIterator();
		while(descItr.hasNext()) {
			String university = descItr.next();
			System.out.print(university);
			System.out.print(descItr.hasNext() ? ", " : "");
		}
		System.out.println("]");
		
		System.out.print("queue�ĵ�3�������[");
		while(queue.peek() != null) { //������зǿգ�peek()ֻ���ض�ͷԪ�أ�������ɾ����
			System.out.print(queue.poll()); //���г��Ӳ���
			System.out.print((queue.peekFirst() != null) ? ", " : "");
		}
		System.out.println("]");
		
		//��Deque<E>������ջ
		Deque<University> stack = new ArrayDeque<>();
		UniversityClass[] classes = {UniversityClass.Class985, UniversityClass.Class211, UniversityClass.ClassDoubleFirst};
		stack.push(new University(10213, "��������ҵ��ѧ", classes)); //985��211��˫һ����ѧ
		
		classes = new UniversityClass[] {UniversityClass.Class211, UniversityClass.ClassDoubleFirst};
		stack.push(new University(10217, "���������̴�ѧ", classes)); //211��˫һ����ѧ
		
		classes = new UniversityClass[] {UniversityClass.Class211, UniversityClass.ClassDoubleFirst};
		stack.push(new University(10225, "������ҵ��ѧ", classes)); //211��˫һ����ѧ

		stack.push(new University(10212, "��������ѧ", new UniversityClass[] {UniversityClass.ClassProvincialKey})); //ʡ���ص��ѧ
		stack.push(new University(10231, "������ʦ����ѧ", new UniversityClass[] {UniversityClass.ClassProvincialKey})); //ʡ���ص��ѧ
		stack.push(new University(10232, "���������ѧ", new UniversityClass[] {UniversityClass.ClassProvincialKey})); //ʡ���ص��ѧ
		stack.push(new University(10236, "�绯ѧԺ", new UniversityClass[] {UniversityClass.ClassOrdinary})); //��ͨ�ߵ�ԺУ
		System.out.println("\nstack�ĵ�1�������");
		while(stack.peek() != null) { //�����ջ�ǿգ�peek()ֻ����ջ��Ԫ�أ�������ɾ����
			System.out.println(stack.pop());
		}
		System.out.println();
		
		//��Deque<E>����˫�˶���
		Deque<String> deque = new ArrayDeque<>(32);
		deque.addFirst("��������ҵ��ѧ��10213"); //�ڶ�ͷ����
		deque.offerFirst("���������̴�ѧ��10217"); //�ڶ�ͷ����
		deque.addFirst("������ʦ����ѧ��10231"); //�ڶ�ͷ����
		System.out.println("deque�ĵ�1�������" + deque);
		
		deque.removeLast(); //�ڶ�βɾ�������ض�βԪ��
		System.out.println("deque�ĵ�2�������" + deque);
		
		deque.pollLast(); //�ڶ�βɾ�������ض�βԪ��
		System.out.println("deque�ĵ�3�������" + deque);
		
		deque.add("���������̴�ѧ��10217"); //�ڶ�β���룬������offer()Ҳ����
		deque.offer("��������ҵ��ѧ��10213"); //�ڶ�β���룬������add()Ҳ����
		System.out.println("deque�ĵ�4�������" + deque);
		
		deque.remove(); //�ڶ�ͷɾ�������ض�ͷԪ��
		System.out.println("deque�ĵ�5�������" + deque);
		
		deque.poll(); //�ڶ�ͷɾ�������ض�ͷԪ��
		System.out.println("deque�ĵ�6�������" + deque);
	}
}