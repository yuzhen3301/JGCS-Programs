package com.jgcs.chp4.p4;

import java.util.Objects;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

//专用于基本类型的可分割迭代器，
//等同于Spliterator.OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
interface PrimitiveSpliterator<T, T_CONS, T_SPLITR extends PrimitiveSpliterator<T, T_CONS, T_SPLITR>>
		extends Spliterator<T> {
	@Override
	T_SPLITR trySplit();

	boolean tryAdvance(T_CONS action);

	default void forEachRemaining(T_CONS action) {
		do {
		} while (tryAdvance(action));
	}
}

//专用于int基本类型的可分割迭代器，
//等同于Spliterator.OfInt extends Spliterator.OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
interface IntSpliterator extends PrimitiveSpliterator<Integer, IntConsumer, IntSpliterator> {
	@Override
	IntSpliterator trySplit();

	//专用于int基本类型的tryAdvance()方法，无需将int封装成Integer
	@Override
	boolean tryAdvance(IntConsumer action);

	//专用于int基本类型的forEachRemaing()方法，无需将int封装成Integer
	@Override
	default void forEachRemaining(IntConsumer action) {
		do {
		} while (tryAdvance(action));
	}
	
	@Override
	default boolean tryAdvance(Consumer<? super Integer> action) {
		if (action instanceof IntConsumer) {
			return tryAdvance((IntConsumer) action);
		} else {
			Objects.requireNonNull(action); //action不能是null
			return tryAdvance((IntConsumer) action::accept); //将Consumer类型的action转化为IntConsumer类型
		}
	}

	@Override
	default void forEachRemaining(Consumer<? super Integer> action) {
		if (action instanceof IntConsumer) {
			forEachRemaining((IntConsumer) action);
		} else {
			Objects.requireNonNull(action); //action不能是null
			forEachRemaining((IntConsumer) action::accept); //将Consumer类型的action转化为IntConsumer类型
		}
	}
}

//专用于double基本类型的可分割迭代器，
//等同于Spliterator.OfDouble extends Spliterator.OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
interface DoubleSpliterator extends PrimitiveSpliterator<Double, DoubleConsumer, DoubleSpliterator> {
	@Override
	DoubleSpliterator trySplit();

	//专用于double基本类型的tryAdvance()方法，无需将double封装成Double
	@Override
	boolean tryAdvance(DoubleConsumer action);

	//专用于double基本类型的forEachRemaing()方法，无需将double封装成Double
	@Override
	default void forEachRemaining(DoubleConsumer action) {
		do {
		} while (tryAdvance(action));
	}
	
	@Override
	default boolean tryAdvance(Consumer<? super Double> action) {
		if (action instanceof DoubleConsumer) {
			return tryAdvance((DoubleConsumer) action);
		} else {
			Objects.requireNonNull(action); //action不能是null
			return tryAdvance((DoubleConsumer) action::accept); //将Consumer类型的action转化为DoubleConsumer类型
		}
	}
	
	@Override
	default void forEachRemaining(Consumer<? super Double> action) {
		if (action instanceof DoubleConsumer) {
			forEachRemaining((DoubleConsumer) action);
		} else {
			Objects.requireNonNull(action); //action不能是null
			forEachRemaining((DoubleConsumer) action::accept); //将Consumer类型的action转化为DoubleConsumer类型
		}
	}
}

//专用于long基本类型的可分割迭代器，
//等同于Spliterator.OfLong extends Spliterator.OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
interface LongSpliterator extends PrimitiveSpliterator<Long, LongConsumer, LongSpliterator> {
	@Override
	LongSpliterator trySplit();

	//专用于long基本类型的tryAdvance()方法，无需将long封装成Long
	@Override
	boolean tryAdvance(LongConsumer action);

	//专用于long基本类型的forEachRemaing()方法，无需将long封装成Long
	@Override
	default void forEachRemaining(LongConsumer action) {
		do {
		} while (tryAdvance(action));
	}

	@Override
	default boolean tryAdvance(Consumer<? super Long> action) {
		if (action instanceof LongConsumer) {
			return tryAdvance((LongConsumer) action);
		} else {
			Objects.requireNonNull(action); //action不能是null
			return tryAdvance((LongConsumer) action::accept); //将Consumer类型的action转化为LongConsumer类型
		}
	}
	
	@Override
	default void forEachRemaining(Consumer<? super Long> action) {
		if (action instanceof LongConsumer) {
			forEachRemaining((LongConsumer) action);
		} else {
			Objects.requireNonNull(action); //action不能是null
			forEachRemaining((LongConsumer) action::accept); //将Consumer类型的action转化为LongConsumer类型
		}
	}
}

class IntArraySpliterator implements IntSpliterator{
	private final int[] intArr; //数据源引用
	private int origin; //可分割迭代器的当前指针位置
	private final int fence; //可分割迭代器的栅栏索引（比最后一个可以访问的元素的索引大1）
	
	IntArraySpliterator(int[] intArr, int origin, int fence) {
		this.intArr = intArr;
		this.origin = origin;
		this.fence = fence;
	}

	@Override
	public IntArraySpliterator trySplit() {
		int lo = origin; //将当前可分割迭代器所管辖的元素分为两半
		int mid = ((lo + fence) >>> 1) & ~1; //将分半点置为偶数
		if(lo < mid) { //如果lo小于mid，则将左半部分分裂出去
			origin = mid; //将origin置为mid，从而将剩余右半部分留在当前可分割迭代器中
			return new IntArraySpliterator(intArr, lo, mid);
		}
		else { //否则，当前可分割迭代器只有一个元素，不再分裂
			return null;
		}
	}

	@Override
	public boolean tryAdvance(IntConsumer action) {
		if(origin < fence) {
			action.accept(intArr[origin]);
			origin ++;
			return true;
		}else {
			return false;
		}
	}

	@Override
	public long estimateSize() {
		return (long)(fence - origin);
	}
	
	@Override
	public int characteristics() {
		return ORDERED | SIZED | IMMUTABLE |SUBSIZED;
	}
}

class MyIntConsumer implements IntConsumer{
	StringBuffer result = null;
	
	MyIntConsumer() {
		result = new StringBuffer();
	}
	
	public void accept(int t) {
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

public class App4_4 {
	public static void main(String[] args) {
		int[] intArr = new int[16];
		for(int i = 0; i < intArr.length; i++) {
			intArr[i] = new Random().nextInt(100);
		}
		
		IntArraySpliterator ias = new IntArraySpliterator(intArr, 0, intArr.length);
		MyIntConsumer consumer = null;
		ias.forEachRemaining((consumer = new MyIntConsumer()));
		System.out.println("使用int基本类型可分割迭代器ias以forEachRemaining()方式顺序遍历intArr：" + consumer.getResult());
		
		//再创建一个新的关于intArr的IntArraySpliterator对象right，其origin为0，fence为intArr.length
		IntArraySpliterator right = new IntArraySpliterator(intArr, 0, intArr.length);
		
		//left是int基本类型可分割迭代器right进行第一次分割后分割出来的迭代器，代表原可分割迭代器right的左半部分，分割后的剩余元素留在right中
		IntArraySpliterator left = right.trySplit();
		
		//遍历left中元素或者它所管辖的位于数据源intArr中的元素
		consumer.setResult(""); //将consumer中的result置空
		left.forEachRemaining(consumer);
		System.out.println("int基本类型可分割迭代器left中元素是：" + consumer.getResult());
		
		//遍历right中元素或者它所管辖的位于数据源intArr中的元素
		consumer.setResult(""); //将consumer中的result置空
		right.forEachRemaining(consumer);
		System.out.println("int基本类型可分割迭代器right中元素是：" + consumer.getResult());
		
	}
}