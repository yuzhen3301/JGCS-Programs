package com.jgcs.chp1.p2;

class MyPlasticBox extends MyBox<Integer>{
}

class MyMetalBox<E> extends MyBox<E>{
}

public class App1_2 {
	public static void main(String[] args) {
		MyBox<String> mbs = new MyBox<String>();
		mbs.setObj("Hello");
		//mbs.setObj(10); //Error: The method setObj(String) in the type MyBox<String> is not applicable for the arguments (int)
		//mbs = new MyBox<Integer>(); //Error: Type mismatch: cannot convert from MyBox<Integer> to MyBox<String>
		
		MyBox<Integer> mbi = new MyBox<>(); //<> is called the Generics Diamond
		mbi.setObj(10); //This line is OK
		//mbi.setObj("One World"); //Error: The method setObj(Integer) in the type MyBox<Integer> is not applicable for the arguments (String)
		
		MyEntry<String, Integer> mes = new MyEntry<String, Integer>("Beijing Olympics", 2008);
		mes.setValue(2022);
		MyEntry<Integer, String> mei = new MyEntry<>(2022, "Beijing Winter Olympics");
		
		MyPlasticBox mpb = new MyPlasticBox();
		mpb.setObj(10);
		//mpb.setObj("One Dream"); //Error: The method setObj(Integer) in the type MyBox<Integer> is not applicable for the arguments (String)
		
		MyMetalBox<String> mmbs = new MyMetalBox<String>();
		mmbs.setObj("World");
		MyMetalBox<Integer> mmbi = new MyMetalBox<>();
		mmbi.setObj(10);
		
		MyBox mb1 = new MyBox(); //MyBox mb1 = new MyBox<>(); is also OK
		mb1.setObj(10); //Warning: the raw type MyBox should be parameterized
		mb1.setObj("Hello World!"); //Warning: the raw type MyBox should be parameterized
		
		MyBox mb2 = new MyBox<String>();
		mb2.setObj(10); //The line is OK, surprisingly!
		
		MyBox<String> mbt = new MyBox();
		mbt.setObj("Hello World!");
		//mbt.setObj(10);//Error: The method setObj(String) in the type MyBox<String> is not applicable for the arguments (int)
		
		System.out.println("We are good!");
	}
}