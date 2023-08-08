package com.jgcs.chp3.p1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.UnaryOperator;
import java.util.function.Predicate;

class Add32ToInt implements UnaryOperator<Integer>{
	public Integer apply(Integer i) {
		return i+32;
	}
}
class OddFilter implements Predicate<Integer>{
	public boolean test(Integer t) {
		return t%2 != 0;
	}
}

public class App3_1 {
	public static void main(String[] args) {
		final int listLen = 20;
		List<Integer> lin = new ArrayList<Integer>();
		for(int i = 0; i < listLen; i++) {
			lin.add(i);
		}
		System.out.println("lin的第1次输出：" + lin); //call to lin.toString()
		
		int oldValAt10 = lin.remove(10); //call to lin.remove(int index)
		int oldValAtEnd = lin.get(lin.size()-1); //call to lin.get(19) here will trigger an IndexOutOfBoundsException
		boolean bDeleted = lin.remove(new Integer(19)); //call to lin.remove(Object o)
		System.out.println("lin的第2次输出：" + lin);
		
		ArrayList<Integer> ain = (ArrayList<Integer>)lin;
		ain.set(10,  oldValAt10);
		if(bDeleted) {
			ain.add(oldValAtEnd); //call to ain.set(19, oldValAtEnd) or ain.add(19, oldValAtEnd) here will trigger an IndexOutOfBoundsException
		}
		System.out.println("lin的第3次输出：" + lin.toString());
		
		lin.replaceAll(new Add32ToInt());
		System.out.println("lin的第4次输出：" + lin);
		
		lin.removeIf(new OddFilter());
		System.out.println("lin的第5次输出：" + lin);
		
		Vector<Integer> vin = new Vector<Integer>(lin); //call to Vector(Collection<? extends E> c)
		System.out.println("\nvin的第1次输出：" + vin);
		
		for(int j = 0; j < listLen/2; j++) {
			if(new OddFilter().test(j)) {
				vin.add(vin.size(), j);
			}
		}
		System.out.println("vin的第2次输出：" + vin);
		
		System.out.println("vin的第3次输出：vin" + (vin.contains(lin) ? "包含" : "不包含") + "lin");
		System.out.println("vin的第4次输出：vin" + (vin.contains(42) ? "包含" : "不包含") + "42");
		System.out.println("vin的第5次输出：vin" + (vin.containsAll(lin) ? "包含" : "不包含") + "lin");
		
		vin.set(vin.size()-1, 10);
		vin.setElementAt(8, vin.size()-2);
		vin.removeElementAt(vin.size()-3);
		System.out.println("vin的第6次输出："+ vin);
	}
}