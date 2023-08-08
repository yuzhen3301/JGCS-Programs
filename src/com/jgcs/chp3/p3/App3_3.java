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
		System.out.println("Please input a positive integer as the d of the d-base system: "); //d-base system��d����ϵͳ
		int dbase = app.getPositiveInteger(sc);
		app.convertDecimalToDbaseNumber(decimal, dbase, st);
		
		String result = "";
		while(!st.empty()) {
			result += st.pop();
		}
		
		System.out.println("The decimal number " + decimal + " in the " + dbase + "-base system is " + result);
	}
	
	void convertDecimalToDbaseNumber(int decimal, int dbase, Stack<Integer> st) {
		int quotient = 0; //�����Ľ��������
		while((quotient = decimal/dbase) != 0) { //���̲�������Ƿ�Ϊ0
			st.push(decimal%dbase); //����̲�Ϊ0��������ѹ��ջst
			decimal = quotient; //���̸�ֵ��������������ѭ����ֱ����Ϊ0
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