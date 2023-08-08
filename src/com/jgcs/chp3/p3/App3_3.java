package com.jgcs.chp3.p3;

import java.util.Scanner;
import java.util.Stack;

public class App3_3 {
	public static void main(String[] args) {
		App3_3 app = new App3_3();
		Scanner sc = new Scanner(System.in);
		Stack<Integer> st = new Stack<>();
		
		System.out.println("Please input a positive decimal integer: ");
		int decimal = app.getPositiveInteger(sc);
		System.out.println("Please input a positive integer as the d of the d-base system: "); //d-base system，d进制系统
		int dbase = app.getPositiveInteger(sc);
		app.convertDecimalToDbaseNumber(decimal, dbase, st);
		
		String result = "";
		while(!st.empty()) {
			result += st.pop();
		}
		
		System.out.println("The decimal number " + decimal + " in the " + dbase + "-base system is " + result);
	}
	
	void convertDecimalToDbaseNumber(int decimal, int dbase, Stack<Integer> st) {
		int quotient = 0; //除法的结果，即商
		while((quotient = decimal/dbase) != 0) { //求商并检查商是否为0
			st.push(decimal%dbase); //如果商不为0，则将余数压入栈st
			decimal = quotient; //将商赋值给被除数，继续循环，直到商为0
		}
		st.push(decimal%dbase);
	}
	
	int getPositiveInteger(Scanner sc) {
		int num = 0;
		while(true) {
			if(sc.hasNextInt()) {
				num = sc.nextInt();
				if (num > 0) {
					break;
				}else {
					System.out.println("Please input an positive integer: ");
				}
			}
		}
		return num;
	}
}