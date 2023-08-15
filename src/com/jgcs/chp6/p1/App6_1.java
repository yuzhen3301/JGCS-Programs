package com.jgcs.chp6.p1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class PascalTriangle{ //杨辉三角形也叫Pascal's Triangle，杨辉比Pascal早近400年发现此三角形
	Queue<Integer> queue = null; //使用队列来存储和计算杨辉三角形中的元素
	
	PascalTriangle(){
		queue = new LinkedList<Integer>();
	}
	
	//triangleDepth控制三角形层数，bIndented控制是否以缩进形式打印三角形
	void ComputeAndPrintTriangle(int triangleDepth, boolean bIndented) { 
		int i, j, e1 = 0, e2 = 0;
		
		if(triangleDepth <= 0) //深度小于等于0，则直接返回，什么也不输出
			return;
		
		if(bIndented) { //如果bIndented为true，则在第一行的1前面打印triangleDepth-1个空格
			for(i = 1; i <= triangleDepth - 1; i++) {
				System.out.print(" ");
			}
		}
		System.out.println(1); //杨辉三角形的第一行肯定是1
		
		queue.clear(); //清空队列
		queue.offer(0); //初始化队列
		queue.offer(1);
		queue.offer(0);
		
		for(i = 2; i <= triangleDepth; i++) { //从第二行开始计算和打印
			if(bIndented) { //如果bIndented为true，则在第i行第一个元素前面打印triangleDepth-i个空格
				for(j = 1; j <= triangleDepth - i; j++) {
					System.out.print(" ");
				}
			}
			
			for(j = 1; j <= i; j++) {
				e1 = queue.poll(); //这里用queue.remove()也可以
				e2 = queue.element(); //这里用queue.peek()也可以
				int result = e1 + e2;
				queue.offer(result);
				System.out.print(result); //打印第i行的第j个元素
				if(j == i) { //如果当前result是第i行的最后一个元素，就向队尾添加一个0，以便进行下一行元素的计算
					queue.add(0); //这里用queue.offer(0)也可以
					System.out.println(); //当前行结束，换行以打印新行元素
				}else {
					System.out.print(" "); //当前行没有结束，就在当前元素后面打印一个空格
				}
			}
		}
	}
}

public class App6_1 {
	public static void main(String[] args) {
		PascalTriangle pt = new PascalTriangle();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("请以正整数形式输入拟打印杨辉三角形的深度：");
		int triangleDepth = sc.nextInt();
		sc.close();
		
		System.out.println("\n以无缩进形式打印深度为"+triangleDepth + "层的杨辉三角形如下：");
		pt.ComputeAndPrintTriangle(triangleDepth, false);
		
		System.out.println("\n以有缩进形式打印深度为"+triangleDepth + "层的杨辉三角形如下：");
		pt.ComputeAndPrintTriangle(triangleDepth, true);
	}
}