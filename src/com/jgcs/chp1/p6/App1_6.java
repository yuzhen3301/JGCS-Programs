package com.jgcs.chp1.p6;

class MyGeneralNode<T>{
	T obj;
	T getObj() { return obj; }
	void setObj(T obj) { this.obj = obj;}
}

public class App1_6 {
	public static void main(String[] args) {
		Object[] nums = new Integer[10];
		nums[0] = 1;
		nums[1] = "abcd"; //编译时没有问题，运行时发生java.lang.ArrayStoreException异常
		
		//Type mismatch: cannot convert from MyGeneralNode<Integer> to MyGeneralNode<Object>
		MyGeneralNode<Object> mgno = new MyGeneralNode<Integer>(); 
		mgno.setObj("Hello World!");
		
		MyGeneralNode<Integer> mgni = new MyGeneralNode<>();
		mgni.setObj(12306);
		
		App1_6 app = new App1_6();
		//The method printGeneralNode1(MyGeneralNode<Object>) in the type App1_6 is not applicable for the arguments (MyGeneralNode<Integer>)
		app.printGeneralNode(mgni);
	}
	void printGeneralNode(MyGeneralNode<Object> mgn) {
		System.out.println(mgn.getObj());
	}
}