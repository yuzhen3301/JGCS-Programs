package com.jgcs.chp1.p5;

class A{}
class B1 extends A{}
class B2 extends A{}
class C extends B2{}
class D{}
class MyEntry<K extends A, V>{
	K key;
	V value;
	MyEntry(K k, V v){
		this.key = k;
		this.value = v;
	}
	V getValue() {
		return value;
	}
	void setValue(V v) {
		this.value = v;
	}
}

public class App1_5 {
	public static void main(String[] args) {
		MyEntry<A, String> me1 = new MyEntry<>(new A(), "Harbin"); //������
		MyEntry<B1, String> me2 = new MyEntry<>(new B1(), "Nangang District"); //�ϸ���
		MyEntry<B2, Integer> me3 = new MyEntry<>(new C(), 88); //������͸����88��ʥ�����ǹ㳡
		//Bound mismatch: The type D is not a valid substitute for the bounded parameter <K extends A> of the type MyEntry<K,V>
		//MyEntry<D, String> me4 = new MyEntry<>(new D(), "Songhua River"); //�ɻ���
		
		App1_5 app = new App1_5();
		app.printMyEntry("me1's value is: ", me1);
		app.printMyEntry("me2's value is: ", me2);
		app.printMyEntry("me3's value is: ", me3);
	}
	<T extends A, E> void printMyEntry(String str, MyEntry<T, E> me){
		System.out.println(str + me.getValue());
	}
}