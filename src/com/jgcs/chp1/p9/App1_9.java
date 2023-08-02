package com.jgcs.chp1.p9;

class MyGeneralNode<T extends Object>{
	T obj;
	T getObj() { return obj; }
	void setObj(T obj) { this.obj = obj;}
}

public class App1_9 {
	public static void main(String[] args) {
		MyGeneralNode<Integer> mgni = new MyGeneralNode<>();
		MyGeneralNode<?> mgnx = new MyGeneralNode<>();
		mgni.setObj(12306);
		
		MyGeneralNode<String> mgns = new MyGeneralNode<>();
		mgns.setObj("Heilong River"); //ºÚÁú½­
		
		MyGeneralNode<Double> mgnd = new MyGeneralNode<>();
		mgnd.setObj(3.1415926d);
		
		System.out.println("mgni.getObj(), mgns.getObj() and mgnd.getObj() are respectively: " + mgni.getObj() + ", " + mgns.getObj() + ", " + mgnd.getObj());
		
		MyGeneralNode<? extends Number> mgnu = mgni;
		MyGeneralNode<?> mgnq = mgns;
		MyGeneralNode<? super Double> mgnp = mgnd;
		System.out.println("mgnu.getObj(), mgnq.getObj() and mgnp.getObj() are respectively: " + mgnu.getObj() + ", " + mgnq.getObj() + ", " + mgnp.getObj());
	}
}