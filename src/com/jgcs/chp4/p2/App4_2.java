package com.jgcs.chp4.p2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

interface MyPrimitiveIterator<T, T_CONS> extends Iterator<T> {
	void forEachRemaining(T_CONS action);
	
	//�����Ͷ���OfInt�ӿڣ��̳�MyPrimitiveIterator<Integer, IntConsumer>
	public static interface OfInt extends MyPrimitiveIterator<Integer, IntConsumer> {
        int nextInt(); //���ػ�������int��ֵ
        
        default void forEachRemaining(IntConsumer action) { //ʵ��MyPrimitiveIterator<T, T_CONS>��forEachRemaining(T_CONS)����
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextInt());
        }
        
        @Override
        default Integer next() { //����Integer���͵�ֵ
            return nextInt();
        }
        
        @Override
        default void forEachRemaining(Consumer<? super Integer> action) { //ʵ��Iterator<T>��forEachRemaining(Consumer<? super T>)����
            if (action instanceof IntConsumer) {
                forEachRemaining((IntConsumer) action);
            }
            else {
                Objects.requireNonNull(action); //action������null
                forEachRemaining((IntConsumer) action::accept); //��action::accept����һ������IntConsumer����
            }
        }

    }
    
	//�����Ͷ���OfDouble�ӿڣ��̳�MyPrimitiveIterator<Integer, DoubleConsumer>
    public static interface OfDouble extends MyPrimitiveIterator<Double, DoubleConsumer> {
        double nextDouble(); //���ػ�������double��ֵ
        
        default void forEachRemaining(DoubleConsumer action) { //ʵ��MyPrimitiveIterator<T, T_CONS>��forEachRemaining(T_CONS)����
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextDouble());
        }
        
        @Override
        default Double next() { //����Double���͵�ֵ
            return nextDouble();
        }
        
        @Override
        default void forEachRemaining(Consumer<? super Double> action) { //ʵ��Iterator<T>��forEachRemaining(Consumer<? super T>)����
            if (action instanceof DoubleConsumer) {
                forEachRemaining((DoubleConsumer) action);
            }
            else {
                Objects.requireNonNull(action); //action������null
                forEachRemaining((DoubleConsumer) action::accept); //��action::accept����һ������DoubleConsumer����
            }
        }
    }
    
  //�����Ͷ���OfLong�ӿڣ��̳�MyPrimitiveIterator<Integer, LongConsumer>
    public static interface OfLong extends MyPrimitiveIterator<Long, LongConsumer> {
        long nextLong(); //���ػ�������long��ֵ
        
        default void forEachRemaining(LongConsumer action) { //ʵ��MyPrimitiveIterator<T, T_CONS>��forEachRemaining(T_CONS)����
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextLong());
        }
        
        @Override
        default Long next() { //����Long���͵�ֵ
            return nextLong();
        }
        
        @Override
        default void forEachRemaining(Consumer<? super Long> action) { //ʵ��Iterator<T>��forEachRemaining(Consumer<? super T>)����
            if (action instanceof LongConsumer) {
                forEachRemaining((LongConsumer) action);
            }
            else {
                Objects.requireNonNull(action); //action������null
                forEachRemaining((LongConsumer) action::accept); //��action::accept����һ������LongConsumer����
            }
        }
    }
}

class MyIntArrayIterator implements MyPrimitiveIterator.OfInt{ //ʵ��MyPrimitiveIterator.OfInt
	private int[] intArr = null;
	private int counter = 0;
	
	public MyIntArrayIterator(int[] intArr) {
		Objects.requireNonNull(intArr);
		this.intArr = intArr;
	}

	@Override
	public boolean hasNext() {
		return counter < intArr.length - 1;
	}

	@Override
	public int nextInt() {
		return intArr[counter++];
	}
}

public class App4_2 {
	public static void main(String[] args) {
		int[] intArr = new int[16];
		for(int i = 0; i < intArr.length; i++) {
			intArr[i] = new Random().nextInt(100);
		}
		Arrays.sort(intArr);
		
		MyPrimitiveIterator.OfInt intArrItr1 = new MyIntArrayIterator(intArr);
		System.out.println("ʹ��intArrItr1��intArr���������");
		while(intArrItr1.hasNext()) { //ʹ��hasNext()��nextInt���intArr�е�Ԫ��
			System.out.print(intArrItr1.nextInt());
			System.out.print(intArrItr1.hasNext() ? ", " : "\n");
		}
		
		MyPrimitiveIterator.OfInt intArrItr2 = new MyIntArrayIterator(intArr);
		System.out.println("ʹ��intArrItr2��intArr���������");
		intArrItr2.forEachRemaining(new IntConsumer() { //ʹ��forEachRemaining���intArr�е�Ԫ��
			@Override
			public void accept(int value) {
				System.out.print(value + " ");
			}
		});
		System.out.println();
		
		//ֱ�Ӵ���һ��ʵ��MyPrimitiveIterator.OfInt�ӿڵ��������󣬲���intItrָ����
		MyPrimitiveIterator.OfInt intItr = new MyPrimitiveIterator.OfInt() {
			private int counter = 0;
			
			@Override
			public int nextInt() {
				counter++;
				return new Random().nextInt(100);
			}
			
			@Override
			public boolean hasNext() {
				return counter < 32;
			}
		};
		
		System.out.println("ʹ��intItr���32�����������");
		while(intItr.hasNext()) {
			System.out.print(intItr.nextInt());
			System.out.print(intItr.hasNext() ? ", " : "\n");
		}
	}
}