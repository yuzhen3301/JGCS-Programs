package com.jgcs.chp4.p4;

import java.util.Objects;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

//ר���ڻ������͵Ŀɷָ��������
//��ͬ��Spliterator.OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
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

//ר����int�������͵Ŀɷָ��������
//��ͬ��Spliterator.OfInt extends Spliterator.OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
interface IntSpliterator extends PrimitiveSpliterator<Integer, IntConsumer, IntSpliterator> {
	@Override
	IntSpliterator trySplit();

	//ר����int�������͵�tryAdvance()���������轫int��װ��Integer
	@Override
	boolean tryAdvance(IntConsumer action);

	//ר����int�������͵�forEachRemaing()���������轫int��װ��Integer
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
			Objects.requireNonNull(action); //action������null
			return tryAdvance((IntConsumer) action::accept); //��Consumer���͵�actionת��ΪIntConsumer����
		}
	}

	@Override
	default void forEachRemaining(Consumer<? super Integer> action) {
		if (action instanceof IntConsumer) {
			forEachRemaining((IntConsumer) action);
		} else {
			Objects.requireNonNull(action); //action������null
			forEachRemaining((IntConsumer) action::accept); //��Consumer���͵�actionת��ΪIntConsumer����
		}
	}
}

//ר����double�������͵Ŀɷָ��������
//��ͬ��Spliterator.OfDouble extends Spliterator.OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
interface DoubleSpliterator extends PrimitiveSpliterator<Double, DoubleConsumer, DoubleSpliterator> {
	@Override
	DoubleSpliterator trySplit();

	//ר����double�������͵�tryAdvance()���������轫double��װ��Double
	@Override
	boolean tryAdvance(DoubleConsumer action);

	//ר����double�������͵�forEachRemaing()���������轫double��װ��Double
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
			Objects.requireNonNull(action); //action������null
			return tryAdvance((DoubleConsumer) action::accept); //��Consumer���͵�actionת��ΪDoubleConsumer����
		}
	}
	
	@Override
	default void forEachRemaining(Consumer<? super Double> action) {
		if (action instanceof DoubleConsumer) {
			forEachRemaining((DoubleConsumer) action);
		} else {
			Objects.requireNonNull(action); //action������null
			forEachRemaining((DoubleConsumer) action::accept); //��Consumer���͵�actionת��ΪDoubleConsumer����
		}
	}
}

//ר����long�������͵Ŀɷָ��������
//��ͬ��Spliterator.OfLong extends Spliterator.OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
interface LongSpliterator extends PrimitiveSpliterator<Long, LongConsumer, LongSpliterator> {
	@Override
	LongSpliterator trySplit();

	//ר����long�������͵�tryAdvance()���������轫long��װ��Long
	@Override
	boolean tryAdvance(LongConsumer action);

	//ר����long�������͵�forEachRemaing()���������轫long��װ��Long
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
			Objects.requireNonNull(action); //action������null
			return tryAdvance((LongConsumer) action::accept); //��Consumer���͵�actionת��ΪLongConsumer����
		}
	}
	
	@Override
	default void forEachRemaining(Consumer<? super Long> action) {
		if (action instanceof LongConsumer) {
			forEachRemaining((LongConsumer) action);
		} else {
			Objects.requireNonNull(action); //action������null
			forEachRemaining((LongConsumer) action::accept); //��Consumer���͵�actionת��ΪLongConsumer����
		}
	}
}

class IntArraySpliterator implements IntSpliterator{
	private final int[] intArr; //����Դ����
	private int origin; //�ɷָ�������ĵ�ǰָ��λ��
	private final int fence; //�ɷָ��������դ�������������һ�����Է��ʵ�Ԫ�ص�������1��
	
	IntArraySpliterator(int[] intArr, int origin, int fence) {
		this.intArr = intArr;
		this.origin = origin;
		this.fence = fence;
	}

	@Override
	public IntArraySpliterator trySplit() {
		int lo = origin; //����ǰ�ɷָ����������Ͻ��Ԫ�ط�Ϊ����
		int mid = ((lo + fence) >>> 1) & ~1; //���ְ����Ϊż��
		if(lo < mid) { //���loС��mid������벿�ַ��ѳ�ȥ
			origin = mid; //��origin��Ϊmid���Ӷ���ʣ���Ұ벿�����ڵ�ǰ�ɷָ��������
			return new IntArraySpliterator(intArr, lo, mid);
		}
		else { //���򣬵�ǰ�ɷָ������ֻ��һ��Ԫ�أ����ٷ���
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
		System.out.println("ʹ��int�������Ϳɷָ������ias��forEachRemaining()��ʽ˳�����intArr��" + consumer.getResult());
		
		//�ٴ���һ���µĹ���intArr��IntArraySpliterator����right����originΪ0��fenceΪintArr.length
		IntArraySpliterator right = new IntArraySpliterator(intArr, 0, intArr.length);
		
		//left��int�������Ϳɷָ������right���е�һ�ηָ��ָ�����ĵ�����������ԭ�ɷָ������right����벿�֣��ָ���ʣ��Ԫ������right��
		IntArraySpliterator left = right.trySplit();
		
		//����left��Ԫ�ػ���������Ͻ��λ������ԴintArr�е�Ԫ��
		consumer.setResult(""); //��consumer�е�result�ÿ�
		left.forEachRemaining(consumer);
		System.out.println("int�������Ϳɷָ������left��Ԫ���ǣ�" + consumer.getResult());
		
		//����right��Ԫ�ػ���������Ͻ��λ������ԴintArr�е�Ԫ��
		consumer.setResult(""); //��consumer�е�result�ÿ�
		right.forEachRemaining(consumer);
		System.out.println("int�������Ϳɷָ������right��Ԫ���ǣ�" + consumer.getResult());
		
	}
}