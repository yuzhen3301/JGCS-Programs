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
	
	//声明和定义OfInt接口，继承MyPrimitiveIterator<Integer, IntConsumer>
	public static interface OfInt extends MyPrimitiveIterator<Integer, IntConsumer> {
        int nextInt(); //返回基本类型int的值
        
        default void forEachRemaining(IntConsumer action) { //实现MyPrimitiveIterator<T, T_CONS>的forEachRemaining(T_CONS)方法
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextInt());
        }
        
        @Override
        default Integer next() { //返回Integer类型的值
            return nextInt();
        }
        
        @Override
        default void forEachRemaining(Consumer<? super Integer> action) { //实现Iterator<T>的forEachRemaining(Consumer<? super T>)方法
            if (action instanceof IntConsumer) {
                forEachRemaining((IntConsumer) action);
            }
            else {
                Objects.requireNonNull(action); //action不能是null
                forEachRemaining((IntConsumer) action::accept); //用action::accept构造一个匿名IntConsumer对象
            }
        }

    }
    
	//声明和定义OfDouble接口，继承MyPrimitiveIterator<Integer, DoubleConsumer>
    public static interface OfDouble extends MyPrimitiveIterator<Double, DoubleConsumer> {
        double nextDouble(); //返回基本类型double的值
        
        default void forEachRemaining(DoubleConsumer action) { //实现MyPrimitiveIterator<T, T_CONS>的forEachRemaining(T_CONS)方法
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextDouble());
        }
        
        @Override
        default Double next() { //返回Double类型的值
            return nextDouble();
        }
        
        @Override
        default void forEachRemaining(Consumer<? super Double> action) { //实现Iterator<T>的forEachRemaining(Consumer<? super T>)方法
            if (action instanceof DoubleConsumer) {
                forEachRemaining((DoubleConsumer) action);
            }
            else {
                Objects.requireNonNull(action); //action不能是null
                forEachRemaining((DoubleConsumer) action::accept); //用action::accept构造一个匿名DoubleConsumer对象
            }
        }
    }
    
  //声明和定义OfLong接口，继承MyPrimitiveIterator<Integer, LongConsumer>
    public static interface OfLong extends MyPrimitiveIterator<Long, LongConsumer> {
        long nextLong(); //返回基本类型long的值
        
        default void forEachRemaining(LongConsumer action) { //实现MyPrimitiveIterator<T, T_CONS>的forEachRemaining(T_CONS)方法
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextLong());
        }
        
        @Override
        default Long next() { //返回Long类型的值
            return nextLong();
        }
        
        @Override
        default void forEachRemaining(Consumer<? super Long> action) { //实现Iterator<T>的forEachRemaining(Consumer<? super T>)方法
            if (action instanceof LongConsumer) {
                forEachRemaining((LongConsumer) action);
            }
            else {
                Objects.requireNonNull(action); //action不能是null
                forEachRemaining((LongConsumer) action::accept); //用action::accept构造一个匿名LongConsumer对象
            }
        }
    }
}

class MyIntArrayIterator implements MyPrimitiveIterator.OfInt{ //实现MyPrimitiveIterator.OfInt
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
		System.out.println("使用intArrItr1对intArr进行输出：");
		while(intArrItr1.hasNext()) { //使用hasNext()和nextInt输出intArr中的元素
			System.out.print(intArrItr1.nextInt());
			System.out.print(intArrItr1.hasNext() ? ", " : "\n");
		}
		
		MyPrimitiveIterator.OfInt intArrItr2 = new MyIntArrayIterator(intArr);
		System.out.println("使用intArrItr2对intArr进行输出：");
		intArrItr2.forEachRemaining(new IntConsumer() { //使用forEachRemaining输出intArr中的元素
			@Override
			public void accept(int value) {
				System.out.print(value + " ");
			}
		});
		System.out.println();
		
		//直接创建一个实现MyPrimitiveIterator.OfInt接口的匿名对象，并令intItr指向它
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
		
		System.out.println("使用intItr输出32个随机整数：");
		while(intItr.hasNext()) {
			System.out.print(intItr.nextInt());
			System.out.print(intItr.hasNext() ? ", " : "\n");
		}
	}
}