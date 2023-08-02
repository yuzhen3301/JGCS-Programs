package com.jgcs.chp1.p10;

class MyGeneralNode{
	Object obj;
	Object getObj() { return obj; }
	void setObj(Object obj) { this.obj = obj;}
}

public class App1_10 {
	public static void main(String[] args) {
		MyGeneralNode mgni = new MyGeneralNode();
		mgni.setObj(12306);
		
		MyGeneralNode mgns = new MyGeneralNode();
		mgns.setObj("Heilong River"); //ºÚÁú½­
		
		MyGeneralNode mgnd = new MyGeneralNode();
		mgnd.setObj(3.1415926d);
		
		System.out.println("mgni.getObj(), mgns.getObj() and mgnd.getObj() are respectively: " + (Integer)mgni.getObj() + ", " + (String)mgns.getObj() + ", " + (Double)mgnd.getObj());
		
		MyGeneralNode mgnu = mgni;
		MyGeneralNode mgnq = mgns;
		MyGeneralNode mgnp = mgnd;
		System.out.println("mgnu.getObj(), mgnq.getObj() and mgnp.getObj() are respectively: " + (Number)mgnu.getObj() + ", " + (Object)mgnq.getObj() + ", " + (Object)mgnp.getObj());
	}
}