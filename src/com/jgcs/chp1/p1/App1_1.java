package com.jgcs.chp1.p1;

interface MyList {
	void add(Object elm);
	Object get(int index);
	void set(int index, Object newElm);
	void remove(int index);
}

class MyArrayList implements MyList{
	Object[] arr = new Object[10];
	int indicator = 0;
	
	//Ϊ����������з�������������±��Ƿ�Խ��
	public void add(Object elm) { arr[indicator++] = elm; }
	public Object get(int index) { return arr[index]; }
	public void set(int index, Object newElm) { arr[index] = newElm; }
	public void remove(int index) { arr[index] = null; }
}

public class App1_1 {
	public static void main(String[] args) {
		MyList ms = new MyArrayList();
		ms.add(1);
		ms.add("Hello");
		ms.add(34.56);
		ms.add(ms);
		
		new App1_1().useList(ms);
	}
	
	void useList(MyList list) { //һ��ʹ��MyList����ķ���
		Object obj = list.get(1);
		Integer it = (Integer)obj; //����������������������
		System.out.println(it);
	}
}