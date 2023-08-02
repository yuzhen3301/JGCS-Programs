package com.jgcs.chp1.p3;

interface MyList<T> {
	void add(T elm);
	T get(int index);
	void set(int index, T newElm);
	void remove(int index);
}

interface MySortedList<ParamType> extends MyList<ParamType>{}

class MyArrayList1 implements MyList<String>{
	String[] arr = new String[10];
	int indicator = 0;
	
	//Ϊ����������з�������������±��Ƿ�Խ��
	public void add(String elm) { arr[indicator++] = elm; }
	public String get(int index) { return arr[index]; }
	public void set(int index, String newElm) { arr[index] = newElm; }
	public void remove(int index) { arr[index] = null; }
}

class MyArrayList2<E> implements MyList<E>{
	E[] arr; //E[] arr = new E[10];�Ǵ���ģ�new E[10]�ᱨ�棺Cannot create a generic array of E
	int indicator = 0;
	
	MyArrayList2(E[] arr){
		this.arr = arr;
	}
	//Ϊ����������з�������������±��Ƿ�Խ��
	public void add(E elm) { arr[indicator++] = elm; }
	public E get(int index) { return arr[index]; }
	public void set(int index, E newElm) { arr[index] = newElm; }
	public void remove(int index) { arr[index] = null; }
}

public class App1_3 {
	public static void main(String[] args) {
		MyArrayList1 mar1 = new MyArrayList1();
		mar1.add("Red");
		
		MyList<String> mls = new MyArrayList1(); //MyArrayList1�ɱ�MyList<String>�����ܱ��κ�����MyList<T>ָ��
		mls.add("Blue");
		
		Integer[] arr = new Integer[10];
		MyArrayList2<Integer> mar2 = new MyArrayList2<Integer>(arr);
		mar2.add(110);
		
		Integer[] ints = new Integer[10];
		MyList<Integer> mli = new MyArrayList2<Integer>(ints); //MyList<E>����ָ��MyArrayList2<E>
		mli.add(120);
		
		int elm = 0;
		System.out.println("mar1's first element is: " + mar1.get(0));
		System.out.println("mls's first element is: " + mls.get(0));
		System.out.println("mar2's first element is: " + (elm = mar2.get(0)));
		System.out.println("mli's first element is: " + (elm = mli.get(0)));
	}
}