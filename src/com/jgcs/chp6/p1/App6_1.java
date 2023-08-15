package com.jgcs.chp6.p1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class PascalTriangle{ //���������Ҳ��Pascal's Triangle����Ա�Pascal���400�귢�ִ�������
	Queue<Integer> queue = null; //ʹ�ö������洢�ͼ�������������е�Ԫ��
	
	PascalTriangle(){
		queue = new LinkedList<Integer>();
	}
	
	//triangleDepth���������β�����bIndented�����Ƿ���������ʽ��ӡ������
	void ComputeAndPrintTriangle(int triangleDepth, boolean bIndented) { 
		int i, j, e1 = 0, e2 = 0;
		
		if(triangleDepth <= 0) //���С�ڵ���0����ֱ�ӷ��أ�ʲôҲ�����
			return;
		
		if(bIndented) { //���bIndentedΪtrue�����ڵ�һ�е�1ǰ���ӡtriangleDepth-1���ո�
			for(i = 1; i <= triangleDepth - 1; i++) {
				System.out.print(" ");
			}
		}
		System.out.println(1); //��������εĵ�һ�п϶���1
		
		queue.clear(); //��ն���
		queue.offer(0); //��ʼ������
		queue.offer(1);
		queue.offer(0);
		
		for(i = 2; i <= triangleDepth; i++) { //�ӵڶ��п�ʼ����ʹ�ӡ
			if(bIndented) { //���bIndentedΪtrue�����ڵ�i�е�һ��Ԫ��ǰ���ӡtriangleDepth-i���ո�
				for(j = 1; j <= triangleDepth - i; j++) {
					System.out.print(" ");
				}
			}
			
			for(j = 1; j <= i; j++) {
				e1 = queue.poll(); //������queue.remove()Ҳ����
				e2 = queue.element(); //������queue.peek()Ҳ����
				int result = e1 + e2;
				queue.offer(result);
				System.out.print(result); //��ӡ��i�еĵ�j��Ԫ��
				if(j == i) { //�����ǰresult�ǵ�i�е����һ��Ԫ�أ������β���һ��0���Ա������һ��Ԫ�صļ���
					queue.add(0); //������queue.offer(0)Ҳ����
					System.out.println(); //��ǰ�н����������Դ�ӡ����Ԫ��
				}else {
					System.out.print(" "); //��ǰ��û�н��������ڵ�ǰԪ�غ����ӡһ���ո�
				}
			}
		}
	}
}

public class App6_1 {
	public static void main(String[] args) {
		PascalTriangle pt = new PascalTriangle();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("������������ʽ�������ӡ��������ε���ȣ�");
		int triangleDepth = sc.nextInt();
		sc.close();
		
		System.out.println("\n����������ʽ��ӡ���Ϊ"+triangleDepth + "���������������£�");
		pt.ComputeAndPrintTriangle(triangleDepth, false);
		
		System.out.println("\n����������ʽ��ӡ���Ϊ"+triangleDepth + "���������������£�");
		pt.ComputeAndPrintTriangle(triangleDepth, true);
	}
}