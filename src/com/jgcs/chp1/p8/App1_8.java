package com.jgcs.chp1.p8;

class MyGeneralNode<T>{
	T obj;
	T getObj() { return obj; }
	void setObj(T obj) { this.obj = obj;}
}
class A{}
class B extends A{}
class C extends B{}

public class App1_8 {
	public static void main(String[] args) {
		MyGeneralNode<Integer> mgni = new MyGeneralNode<>();
		mgni.setObj(12345); //12345市级政务服务便民热线
		
		MyGeneralNode<? extends Number> mgnu = mgni;
		//The method setObj(capture#1-of ? extends Number) in the type MyGeneralNode<capture#1-of ? extends Number> is not applicable for the arguments (Integer)
		//mgnu.setObj(new Integer(12346)); //12346省级政务服务便民热线
		System.out.println("mgnu.getObj() is: " + mgnu.getObj());
		
		MyGeneralNode<String> mgns = new MyGeneralNode<String>();
		mgns.setObj("Greater Khingan Mountains"); //大兴安岭
		
		MyGeneralNode<?> mgnq = mgns;
		//The method setObj(capture#1-of ?) in the type MyGeneralNode<capture#1-of ?> is not applicable for the arguments (String)
		//mgnq.setObj("Lesser Khingan Mountains"); //小兴安岭
		System.out.println("mgnq.getObj() is: " + mgnq.getObj());
		
		mgnq = mgni;
		//The method setObj(capture#2-of ?) in the type MyGeneralNode<capture#2-of ?> is not applicable for the arguments (Integer)
		//mgnq.setObj(new Integer(12346));
		System.out.println("mgnq.getObj() is: " + mgnq.getObj());
		
		MyGeneralNode<? super B> mgnp = new MyGeneralNode<A>();
		mgnp.setObj(new B());
		mgnp.setObj(new C());
		//The method setObj(capture#4-of ? super B) in the type MyGeneralNode<capture#4-of ? super B> is not applicable for the arguments (A)
		//mgnp.setObj(new A());
		System.out.println("mgnp.getObj() is: " + mgnp.getObj());
		
		mgnu.setObj(null); //OK
		mgnq.setObj(null); //OK
		mgnp.setObj(null); //OK
	}
}