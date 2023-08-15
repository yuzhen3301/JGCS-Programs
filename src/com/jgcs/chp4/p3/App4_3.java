package com.jgcs.chp4.p3;

import java.util.Arrays;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

class MySplitableArray<E>{
	private final Object[] elements; //immutable after construction
	MySplitableArray(Object[] data){
		int size = data.length;
		elements = new Object[size];
		for(int i = 0; i < size; i++) {
			elements[i] = data[i];
		}
	}
	
	public Spliterator<E> spliterator(){
		return new Splitr(elements, 0, elements.length);
	}
	
	private class Splitr implements Spliterator<E>{
		private final Object[] array;
		private int origin; //current index, advanced on split or traversal
		private final int fence; //one past the greatest index
		Splitr(Object[] array, int origin, int fence){
			this.array = array;
			this.origin = origin;
			this.fence = fence;
		}
		
		public void forEachRemaining(Consumer<? super E> action) {
			for(; origin < fence; origin++) {
				action.accept((E) array[origin]);
			}
		}
		
		public boolean tryAdvance(Consumer<? super E> action) {
			if(origin < fence) {
				action.accept((E) array[origin]);
				origin ++;
				return true;
			}else { //cannot advance
				return false;
			}
		}
		
		public Spliterator<E> trySplit(){
			int lo = origin; //divide range in half
			int mid = ((lo + fence) >>> 1) & ~1; //force midpoint to be even
			if(lo < mid) { //split out left half
				origin = mid; //reset this Spliterator's origin
				return new Splitr(array, lo, mid);
			}
			else {
				return null; //too small to split
			}
		}
		
		public long estimateSize() {
			return (long)(fence - origin);
		}
		
		public int characteristics() {
			return SORTED | ORDERED | IMMUTABLE | SIZED | SUBSIZED;
		}
	}
}

class MyConsumer<U> implements Consumer<U>{
	StringBuffer result = null;
	
	MyConsumer() {
		result = new StringBuffer();
	}
	
	public void accept(U t) {
		result.append(t + ", ");
	}
	
	public String getResult() {
		String delim = ", ";
		result.replace(result.length()-delim.length(), result.length(), ""); //[start, end)
		
		result.insert(0, "[");
		result.insert(result.length(), "]");
		return result.toString();
	}
	
	public void setResult(String str) {
		result.replace(0, result.length(), str);
	}
}

class MyRunnable<V> implements Runnable{
	String thrMsg = null; //information about the current thread's name and its owning spliterator’s name
	Spliterator<V> sp = null; //the spliterator of the current thread
	CountDownLatch latch = null; //the shared latch among multiple threads
	
	public MyRunnable(String thrMsg, Spliterator<V> sp, CountDownLatch latch) {
		this.thrMsg = thrMsg;
		this.sp = sp;
		this.latch = latch;
	}
	public void run() {
		if(sp != null) {
			MyConsumer<V> consumer = new MyConsumer<V>();
			sp.forEachRemaining(consumer);
			System.out.println(thrMsg + ":" + consumer.getResult());
		}
		
		if(latch != null) {
			latch.countDown();
		}
	}
}

public class App4_3 {
	public static void main(String[] args) {
		Integer[] ints = new Integer[20];
		for(int i = 0; i < ints.length; i++) {
			ints[i] = new Integer(new Random().nextInt(100));
		}
		Arrays.sort(ints);
		
		MySplitableArray<Integer> msa = new MySplitableArray<Integer>(ints);
		
		//利用sp顺序遍历msa中的全部元素
		Spliterator<Integer> sp = msa.spliterator(); //可分割迭代器迭代器sp
		MyConsumer<Integer> consumer = new MyConsumer<>();
		sp.forEachRemaining(consumer); //forEachRemaining()执行完后，sp的origin会等于fence
		System.out.println("使用可分割迭代器sp以forEachRemaining()方式顺序遍历msa：" + consumer.getResult() + "\n");
		
		sp = msa.spliterator(); //重新获得一个新Spliterator<Integer>对象，其origin为0，fence为ints.length
		consumer.setResult(""); //将consumer中的result置空
		while(sp.tryAdvance(consumer)) {
			;
		}
		System.out.println("使用可分割迭代器sp以tryAdvance()方式顺序遍历msa：" + consumer.getResult() + "\n");
		
		//利用多个可分割迭代器在多线程中并行遍历msa
		System.out.println("使用可分割迭代器sp1、sp2、sp3和sp4并行遍历msa：");
		Spliterator<Integer> sp1 = msa.spliterator(); //可分割迭代器sp1
		Spliterator<Integer> sp2 = sp1.trySplit(); //sp2是迭代器sp1进行一次分割后分割出来的迭代器
		Spliterator<Integer> sp3 = sp1.trySplit(); //sp3是迭代器sp1进行一次分割后再次进行分割后得到的迭代器
		Spliterator<Integer> sp4 = sp2.trySplit(); //sp4是sp2进行一次分割后分割出来的迭代器
		
		CountDownLatch latch = new CountDownLatch(4);
		new Thread(new MyRunnable<Integer>("Thread1遍历sp1", sp1, latch)).run();
		new Thread(new MyRunnable<Integer>("Thread2遍历sp2", sp2, latch)).run();
		new Thread(new MyRunnable<Integer>("Thread3遍历sp3", sp3, latch)).run();
		new Thread(new MyRunnable<Integer>("Thread4遍历sp4", sp4, latch)).run();
		
		try {
			latch.await();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("\nExit");
	}
}