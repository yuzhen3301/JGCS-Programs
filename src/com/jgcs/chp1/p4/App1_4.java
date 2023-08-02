package com.jgcs.chp1.p4;

class MyIntegerNode{
	Integer obj;
	Integer getObj() { return obj; }
	void setObj(Integer obj) { this.obj = obj;}
	void printNode() {
		System.out.println("This node's obj is: " + obj);
	}
	<T> void printGeneralNode1(MyGeneralNode<T> mgn1) {
		mgn1.printNode("MyIntegerNode's <T>printGeneralNode1".replace("T", mgn1.getTName()));
	}
	//printGeneralNode2ǰ���<T>��printGeneralNode1ǰ���<T>����Ȼ���Ͳ�������T��������T�Ƕ����ġ�һ��Ϊ������⣬Ӧ�ֱ�ʹ�ò�ͬ�����Ͳ���������T��E��
	static <T> void printGeneralNode2(MyGeneralNode<T> mgn2) { 
		mgn2.printNode("MyIntegerNode's <T>printGeneralNode2".replace("T", mgn2.getTName()));
	}
}

class MyGeneralNode<T>{
	T obj;
	T getObj() { return obj; }
	void setObj(T obj) { this.obj = obj;}
	void printNode(String printer) {
		System.out.println("This node's obj is: " + obj + ", printed by " + printer);
	}
	String getTName() { //��ȡ��������MyGeneralNode<T>��T�ľ�������
		return obj.getClass().getName();
	}
	<T> void printGeneralObject1(T obj1) {
		System.out.println(obj1.toString());
	}
	static <T> void printGeneralObject2(T obj2) {
		System.out.println(obj2.toString());
	}
	<T> void printGeneralObject3(T obj3) { //��֤���ͷ����뷺��������Ͳ����Ƕ�����
		System.out.println("The T in MyGeneralNode<T> is: " + getTName());
		System.out.println("The T in <T>printGeneralNode5 is: " + obj3.getClass().getName());
	}
}

public class App1_4 {
	public static void main(String[] args) {
		MyIntegerNode min = new MyIntegerNode();
		min.setObj(119);
		
		MyGeneralNode<Integer> mgni = new MyGeneralNode<>();
		mgni.setObj(122);
		MyGeneralNode<String> mgns = new MyGeneralNode<>();
		mgns.setObj("Harbin"); //������
		
		min.printGeneralNode1(mgni);
		MyIntegerNode.printGeneralNode2(mgns);
		
		mgni.printGeneralObject1(114);
		mgns.<String>printGeneralObject1("QiqiHar"); //������������ʹ��mgns.<String>printGeneralObject(121);��ᴥ��һ��������type-mismatch����
				
		mgni.printGeneralObject2(12306);
		mgns.printGeneralObject2("Daqing"); //����
		
		mgni.printGeneralObject3("Songhua River"); //�ɻ���
	}
}