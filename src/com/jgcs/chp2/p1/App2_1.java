package com.jgcs.chp2.p1;

import java.util.function.Predicate;
import java.util.Scanner;
import java.util.function.Consumer;

class StringPredicate implements Predicate<String>{
	public boolean test(String t) {
		return (t != null) && (t.length() > 7) && (t.length() < 10);
	}
}
class StringConsumer implements Consumer<String>{
	public void accept(String t) {
		System.out.println(t);
	}
}
public class App2_1 {
	public static void main(String[] args) {
		StringPredicate sp = new StringPredicate();
		StringConsumer sc = new StringConsumer();
		
		int strLen = 5;
		String strs[] = new String[strLen];
		Scanner sn = new Scanner(System.in);
		System.out.println("Please input 5 strings into the String array strs: ");
		for(int i = 0; i < strLen; i++) {
			strs[i] = sn.nextLine();
		}
		
		System.out.println("The filtered strings are: ");
		new App2_1().filterStrings(strs, sp,  sc);
	}
	
	public void filterStrings(String[] strs, StringPredicate filter, StringConsumer consumer) {
		for(String str: strs) {
			if(filter.test(str)){
				consumer.accept(str);
			}
		}
	}
}