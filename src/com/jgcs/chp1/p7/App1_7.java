package com.jgcs.chp1.p7;

class MyGeneralNode<T>{
	T obj;
	T getObj() { return obj; }
	void setObj(T obj) { this.obj = obj;}
}

public class App1_7 {
	public static void main(String[] args) {
		MyGeneralNode<Integer> mgni = new MyGeneralNode<>();
		mgni.setObj(12306);
		
		MyGeneralNode<String> mgns = new MyGeneralNode<>();
		mgns.setObj("Heilong River"); //黑龙江
		
		MyGeneralNode<Double> mgnd = new MyGeneralNode<>();
		mgnd.setObj(3.1415926d);
		
		App1_7 app = new App1_7();
		app.printGeneralNode1(mgni);
		app.printGeneralNode2(mgnd);
		app.printGeneralNode1(mgns);
		//The method printGeneralNode2(MyGeneralNode<? extends Number>) in the type App1_7 is not applicable for the arguments (MyGeneralNode<String>)
		//app.printGeneralNode2(mgns);
		
		app.printGeneralNode3(mgni);
		//The method printGeneralNode3(MyGeneralNode<? super Integer>) in the type App1_7 is not applicable for the arguments (MyGeneralNode<Double>)
		//app.printGeneralNode3(mgnd);
		
		//mgnu虽然指向mgnd，但是mgnu类型中的?并不会具体化为Double，而是仍然表示未知类型
		MyGeneralNode<? extends Number> mgnu = mgnd; 
		Number num1 = mgnu.getObj(); //mgnu.getObj()返回类型为Number
		mgnu = mgni; //mgnu在指向mgnd后可以重新指向mgni
		Number num2 = mgnu.getObj();
		System.out.println("num1 is: " + num1 + "\n" + "num2 is: " + num2);
		
		MyGeneralNode<? super Integer> mgnv = mgni;
		Number num3 = (Number)mgnv.getObj(); //mgnv.getObj()返回类型为Object，这里必须进行强制类型转换
		System.out.println("num3 is: " + num3);
		
		MyGeneralNode<?> mgnw = mgns;
		String str = (String)mgnw.getObj(); //mgnw.getObj()返回类型为Object，这里必须进行强制类型转换
		System.out.println("str is: " + str);
		
		MyGeneralNode<? extends Number> mgnx = new MyGeneralNode<>(); //相当于new MyGeneralNode<Number>();
		MyGeneralNode<? super Integer> mgny = new MyGeneralNode<>(); //相当于new MyGeneralNode<Object>()
		MyGeneralNode<?> mgnz = new MyGeneralNode<>(); //相当于new MyGeneralNode<Object>()
		//MyGeneralNode<?> mgnz = new MyGeneralNode<?>(); //非法，错误信息为：Cannot instantiate the type MyGeneralNode<?>
	}
	void printGeneralNode1(MyGeneralNode<? extends Object> mgn) {
		System.out.println(mgn.getObj());
	}
	void printGeneralNode2(MyGeneralNode<? extends Number> mgn) {
		System.out.println(mgn.getObj());
	}
	void printGeneralNode3(MyGeneralNode<? super Integer> mgn) {
		System.out.println(mgn.getObj());
	}
}