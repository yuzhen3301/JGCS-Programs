package com.jgcs.chp9.p3;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongConsumer;

public class App9_3 {
	public static void main(String[] args) {
		//关于Spliterators.AbstractIntSpliterator的使用演示
		Spliterators.AbstractIntSpliterator intSpliter = new Spliterators.AbstractIntSpliterator(3072, Spliterator.SIZED){
			int counter = 0;
			public boolean tryAdvance(IntConsumer action) {
				int num = intSupplier.getAsInt();
				action.accept(num);
				if(counter++ < 3072)
					return true;
				else
					return false;
			}
			
			private IntSupplier intSupplier = () -> {
			    return new Random().nextInt(new Random().nextInt(1000000)+1); //nextInt(0)会导致异常，所以要让外层nextInt()的参数大于0就要对内层nextInt()的返回结果加1
			};
			
//			public boolean tryAdvance(IntConsumer action) {
//				int num = intSupplier.getAsInt();
//				action.accept(num);
//				if(num == 999999)
//					return false;
//				else
//					return true;
//			}
//
//			public void forEachRemaining(IntConsumer action) {
//				long size = getExactSizeIfKnown();
//				if(size <= 0) return;
//				for(int i = 0; i < size; i++) {
//					tryAdvance(action);
//				}
//			}
		};
		
//		intSpliter.forEachRemaining((int e) -> {System.out.println(e);});
		
		System.out.println("The spliterator intSpliter's size is: " + intSpliter.getExactSizeIfKnown());
		Spliterator.OfInt intSpliter_left1 = intSpliter.trySplit();
		System.out.println("The spliterator intSpliter_left1's size is: " + intSpliter_left1.getExactSizeIfKnown());
		Spliterator.OfInt intSpliter_left2 = intSpliter.trySplit();
		System.out.println("The spliterator intSpliter_left2's size is: " + intSpliter_left2.getExactSizeIfKnown());
		System.out.print("The spliterator intSpliter_left1's content is: ");
		intSpliter_left1.forEachRemaining(new IntConsumer() {
			int counter = 0;
			public void accept(int value) {
				counter++;
				if(counter == 11)
					System.out.println();
				else if(counter > 11)
					return;
				else
					System.out.print(value + " ");
			}
		});
		System.out.println();
		
		//关于Spliterators.emptyIntSpliterator()的使用演示
		Spliterator.OfInt emptyIntSpliter = Spliterators.emptyIntSpliterator();
		System.out.println("emptyIntSpliter.estimateSize() returns: " + emptyIntSpliter.estimateSize());
		System.out.println("emptyIntSpliter.trySplit() returns: " + emptyIntSpliter.trySplit());
		System.out.println("emptyIntSpliter.tryAdvance() returns: " + emptyIntSpliter.tryAdvance((int e) -> System.out.println(e)));
		System.out.println();
		
		//关于Spliterators.spliterator(Collection<? extends T> c, int characteristics)的使用演示
		List<String> strList = new ArrayList<String>();
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //字母表
		for(int i = 0; i < 3072; i++) {
			StringBuffer sb = new StringBuffer();
		    for(int j = 0; j < 6; j++){
		       int index = new Random().nextInt(alphabet.length());
		       sb.append(alphabet.charAt(index));
		    }
		    strList.add(sb.toString());
		}
		
		Spliterator<String> strSpliter = Spliterators.spliterator(strList, Spliterator.SIZED|Spliterator.NONNULL|Spliterator.IMMUTABLE);
//		System.out.print("The spliterator strSpliter's content is: ");
//		strSpliter.forEachRemaining(new Consumer<String>() {
//			int counter = 0;
//			public void accept(String t) {
//				counter++;
//				if(counter == 11)
//					System.out.println();
//				else if(counter > 11)
//					return;
//				else
//					System.out.print(t + " ");
//			}
//		});
		
		System.out.println("The spliterator strSpliter's size is: " + strSpliter.getExactSizeIfKnown());
		Spliterator<String> strSpliter_left1 = strSpliter.trySplit();
		System.out.println("The spliterator strSpliter_left1's size is: " + strSpliter_left1.getExactSizeIfKnown());
		Spliterator<String> strSpliter_left2 = strSpliter.trySplit();
		System.out.println("The spliterator strSpliter_left2's size is: " + strSpliter_left2.getExactSizeIfKnown());
		System.out.print("The spliterator strSpliter_left1's content is: ");
		strSpliter_left1.forEachRemaining(new Consumer<String>() {
			int counter = 0;
			public void accept(String t) {
				counter++;
				if(counter == 11)
					System.out.println();
				else if(counter > 11)
					return;
				else
					System.out.print(t + " ");
			}
		});
		System.out.println();
		
		//关于Spliterators.spliterator(Iterator<? extends T> iterator, long size, int characteristics)的使用演示
		Spliterator<String> strItrSpliter = Spliterators.spliterator(strList.iterator(), strList.size(), Spliterator.SIZED);
		
		System.out.println("The spliterator strItrSpliter's size is: " + strItrSpliter.getExactSizeIfKnown());
		Spliterator<String> strItrSpliter_left1 = strItrSpliter.trySplit();
		System.out.println("The spliterator strItrSpliter_left1's size is: " + strItrSpliter_left1.getExactSizeIfKnown());
		Spliterator<String> strItrSpliter_left2 = strItrSpliter.trySplit();
		System.out.println("The spliterator strItrSpliter_left2's size is: " + strItrSpliter_left2.getExactSizeIfKnown());
		System.out.print("The spliterator strItrSpliter_left1's content is: ");
		strItrSpliter_left1.forEachRemaining(new Consumer<String>() {
			int counter = 0;
			public void accept(String t) {
				counter++;
				if(counter == 11)
					System.out.println();
				else if(counter > 11)
					return;
				else
					System.out.print(t + " ");
			}
		});
		System.out.println();
		
		//关于Spliterators.spliterator(long[] array, int additionalCharacteristics)的使用演示
		int longArrLength = 3072;
		long[] longArr = new long[longArrLength];
		for(int i = 0; i < longArrLength; i++) {
			longArr[i] = new Random().nextInt(new Random().nextInt(longArrLength)+1);
		}
		Spliterator.OfLong longArrSpliter = Spliterators.spliterator(longArr, Spliterator.SIZED|Spliterator.IMMUTABLE|Spliterator.NONNULL);
		
		System.out.println("The spliterator longArrSpliter's size is: " + longArrSpliter.getExactSizeIfKnown());
		Spliterator.OfLong longArrSpliter_left1 = longArrSpliter.trySplit();
		System.out.println("The spliterator longArrSpliter_left1's size is: " + longArrSpliter_left1.getExactSizeIfKnown());
		Spliterator.OfLong longArrSpliter_left2 = longArrSpliter.trySplit();
		System.out.println("The spliterator longArrSpliter_left2's size is: " + longArrSpliter_left2.getExactSizeIfKnown());
		System.out.print("The spliterator longArrSpliter_left1's content is: ");
		longArrSpliter_left1.forEachRemaining(new LongConsumer() {
			int counter = 0;
			public void accept(long value) {
				counter++;
				if(counter == 11)
					System.out.println();
				else if(counter > 11)
					return;
				else
					System.out.print(value + " ");
			}
		});
		System.out.println();
		
		//关于Spliterators.spliterator(PrimitiveIterator.OfLong iterator, long size, int characteristics)的使用演示
		PrimitiveIterator.OfLong longItr = new PrimitiveIterator.OfLong() {
			int counter = 0;
			public boolean hasNext() {
				if(counter++ < 3072) return true;
				else return false;
			}

			public long nextLong() {
				return new Random().nextInt(1000000);
			}
		};
		Spliterator.OfLong longItrSpliter = Spliterators.spliterator(longItr, 3072, Spliterator.SIZED);
		
		System.out.println("The spliterator longItrSpliter's size is: " + longItrSpliter.getExactSizeIfKnown());
		Spliterator.OfLong longItrSpliter_left1 = longItrSpliter.trySplit();
		System.out.println("The spliterator longItrSpliter_left1's size is: " + longItrSpliter_left1.getExactSizeIfKnown());
		Spliterator.OfLong longItrSpliter_left2 = longItrSpliter.trySplit();
		System.out.println("The spliterator longItrSpliter_left2's size is: " + longItrSpliter_left2.getExactSizeIfKnown());
		System.out.print("The spliterator longItrSpliter_left1's content is: ");
		longItrSpliter_left1.forEachRemaining(new LongConsumer() {
			int counter = 0;
			public void accept(long value) {
				counter++;
				if(counter == 11)
					System.out.println();
				else if(counter > 11)
					return;
				else
					System.out.print(value + " ");
			}
		});
		System.out.println();
		
		//关于Spliterators.spliteratorUnknownSize(PrimitiveIterator.OfDouble iterator, int characteristics)的使用演示
		PrimitiveIterator.OfDouble doubleItr = new PrimitiveIterator.OfDouble() {
			public boolean hasNext() {
				return true;
			}
			
			public double nextDouble() {
				return new Random().nextDouble() * 1000000;
			}
		};
		Spliterator.OfDouble doubleItrSpliter = Spliterators.spliteratorUnknownSize(doubleItr, 0);
		
		System.out.println("The spliterator doubleItrSpliter's size is: " + doubleItrSpliter.getExactSizeIfKnown());
		Spliterator.OfDouble doubleItrSpliter_left1 = doubleItrSpliter.trySplit();
		System.out.println("The spliterator doubleItrSpliter_left1's size is: " + doubleItrSpliter_left1.getExactSizeIfKnown());
		Spliterator.OfDouble doubleItrSpliter_left2 = doubleItrSpliter.trySplit();
		System.out.println("The spliterator doubleItrSpliter_left2's size is: " + doubleItrSpliter_left2.getExactSizeIfKnown());
		System.out.print("The spliterator doubleItrSpliter_left1's content is: ");
		doubleItrSpliter_left1.forEachRemaining(new DoubleConsumer() {
			int counter = 0;
			public void accept(double value) {
				counter++;
				if(counter == 6)
					System.out.println();
				else if(counter > 6)
					return;
				else
					System.out.print(value + " ");
			}
		});
		System.out.println();
		
		//关于Spliterator.iterator(Spliterator.OfDouble spliterator)的使用演示
		PrimitiveIterator.OfDouble doubleSpliterItr = Spliterators.iterator(doubleItrSpliter);
		System.out.print("The iterator doubleSpliterItr's first five elements are: ");
		int counter = 0;
		while(doubleSpliterItr.hasNext() && counter++ < 5) {
			System.out.print(doubleSpliterItr.nextDouble() + " ");
		}
	}
}