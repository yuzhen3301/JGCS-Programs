package com.jgcs.chp9.p2;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.Supplier;

public class App9_2 {
	public static void main(String[] args) {
		MySpliterators.AbstractSpliterator<String> SplitStrs = new MySpliterators.AbstractSpliterator<String>(3072, Spliterator.SIZED){
			public boolean tryAdvance(Consumer<? super String> action) {
				String str = strSupplier.get();
				action.accept(str);
				if(str.equals("WHOAMI"))
					return false;
				else
					return true;
			}
			
			private Supplier<String> strSupplier = () -> {
				String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //字母表
			    StringBuffer sb = new StringBuffer();
			    for(int i = 0; i < 6; i++){
			       int index = new Random().nextInt(alphabet.length());
			       sb.append(alphabet.charAt(index));
			    }
			    
			    return sb.toString();
			};
		};
		
		Spliterator<String> left1 = SplitStrs.trySplit();
		System.out.println("The spliterator left1's size is: " + left1.getExactSizeIfKnown());
		Spliterator<String> left2 = SplitStrs.trySplit();
		System.out.println("The spliterator left2's size is: " + left2.getExactSizeIfKnown());
		try {
			Spliterator<String> left3 = SplitStrs.trySplit();
			System.out.println("The spliterator left2's size is: " + left3.getExactSizeIfKnown());
		}catch(Exception ex) {
			ex.printStackTrace(System.out); //将异常堆栈输出到System.out而不是System.err（System.err为默认目标）
		}
		System.out.println();
		
		Spliterator<String> left1_left = left1.trySplit();
		System.out.println("The spliterator left1_left's size is: " + left1_left.getExactSizeIfKnown());
		System.out.println("The spliterator left1's size is: " + left1.getExactSizeIfKnown());
		
//		System.out.println("The spliterator left1_left's content is: ");
//		left1_left.forEachRemaining((e) -> System.out.println(e));
	}
}

class MySpliterators {
	//AbstractSpliterator，抽象可分割迭代器类
    public static abstract class AbstractSpliterator<T> implements Spliterator<T> {
        static final int BATCH_UNIT = 1 << 10; //batch array size increment，批量数组（即trySplit()中的Object数组a）大小的增量
        static final int MAX_BATCH = 1 << 25; //max batch array size，批量数组的最大大小
        private final int characteristics;
        private long est; //size estimate，当前可分割迭代器的预估大小
        private int batch; //batch size for splits，上次调用trySplit()被分割出去的元素的数量，初始为0

        //如果additionalCharacteristics包含SIZED特性，则将SUBSIZED特性也添加到其中
        protected AbstractSpliterator(long est, int additionalCharacteristics) {
            this.est = est;
            this.characteristics = ((additionalCharacteristics & Spliterator.SIZED) != 0)
                                   ? additionalCharacteristics | Spliterator.SUBSIZED
                                   : additionalCharacteristics;
        }

        static final class HoldingConsumer<T> implements Consumer<T> {
            Object value;

            @Override
            public void accept(T value) {
                this.value = value;
            }
        }

        //第n次（n大于等于1）调用trySplit()时，它首先检查1024*n是否小于等于est，如果是的话就返回一个长度为1024*n的ArraySpliterator，
        //否则返回一个长度为est的ArraySpliterator；同时est也被修改为est-1024*n或0（如果tryAdvance(holder)始终为true的话）
        @Override
        public Spliterator<T> trySplit() {
            HoldingConsumer<T> holder = new HoldingConsumer<>();
            long s = est;
            if (s > 1 && tryAdvance(holder)) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                Object[] a = new Object[n];
                int j = 0;
                do { a[j] = holder.value; } while (++j < n && tryAdvance(holder));
                batch = j; //修改batch为j
                if (est != Long.MAX_VALUE) //如果est不是无限大，Long.MAX_VALUE用来表示无限大
                    est -= j; //将est置为est-j
                return new ArraySpliterator<>(a, 0, j, characteristics());
            }
            return null;
        }
        
        @Override
        public long estimateSize() {
            return est;
        }

        @Override
        public int characteristics() {
            return characteristics;
        }
    }
	
	//AbstractDoubleSpliterator，专用于double基本类型的抽象可分割迭代器类
	public static abstract class AbstractDoubleSpliterator implements Spliterator.OfDouble {
        static final int MAX_BATCH = AbstractSpliterator.MAX_BATCH; //批量数组（即trySplit()中的double数组a）大小的增量
        static final int BATCH_UNIT = AbstractSpliterator.BATCH_UNIT; //批量数组的最大大小
        private final int characteristics;
        private long est; //当前可分割迭代器的预估大小
        private int batch; //上次调用trySplit()被分割出去的元素的数量，初始为0

        //如果additionalCharacteristics包含SIZED特性，则将SUBSIZED特性也添加到其中
        protected AbstractDoubleSpliterator(long est, int additionalCharacteristics) {
            this.est = est;
            this.characteristics = ((additionalCharacteristics & Spliterator.SIZED) != 0)
                                   ? additionalCharacteristics | Spliterator.SUBSIZED
                                   : additionalCharacteristics;
        }

        static final class HoldingDoubleConsumer implements DoubleConsumer {
            double value;

            @Override
            public void accept(double value) {
                this.value = value;
            }
        }

        //第n次（n大于等于1）调用trySplit()时，它首先检查1024*n是否小于等于est，如果是的话就返回一个长度为1024*n的DoubleArraySpliterator，
        //否则返回一个长度为est的DoubleArraySpliterator；同时est也被修改为est-1024*n或0（如果tryAdvance(holder)始终为true的话）
        @Override
        public Spliterator.OfDouble trySplit() {
            HoldingDoubleConsumer holder = new HoldingDoubleConsumer();
            long s = est;
            if (s > 1 && tryAdvance(holder)) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                double[] a = new double[n];
                int j = 0;
                do { a[j] = holder.value; } while (++j < n && tryAdvance(holder));
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new DoubleArraySpliterator(a, 0, j, characteristics());
            }
            return null;
        }
        
        @Override
        public long estimateSize() {
            return est;
        }

        @Override
        public int characteristics() {
            return characteristics;
        }
    }
	
	//利用EmptySpliterator<T, S extends Spliterator<T>, C>实现相关方法
    public static <T> Spliterator<T> emptySpliterator() {
        return new EmptySpliterator.OfRef<>();
    }
    
    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return new EmptySpliterator.OfDouble();
    }
	
	//EmptySpliterator，空可分割迭代器类
	private static abstract class EmptySpliterator<T, S extends Spliterator<T>, C> {
        EmptySpliterator() { }

        public S trySplit() {
            return null;
        }

        public boolean tryAdvance(C consumer) {
            Objects.requireNonNull(consumer);
            return false;
        }

        public void forEachRemaining(C consumer) {
            Objects.requireNonNull(consumer);
        }

        public long estimateSize() {
            return 0;
        }

        public int characteristics() {
            return Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        private static final class OfRef<T>
                extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
                implements Spliterator<T> {
            OfRef() { }
        }

        private static final class OfInt
                extends EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
                implements Spliterator.OfInt {
            OfInt() { }
        }

        private static final class OfLong
                extends EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
                implements Spliterator.OfLong {
            OfLong() { }
        }

        private static final class OfDouble
                extends EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
                implements Spliterator.OfDouble {
            OfDouble() { }
        }
    }
	
	//利用IteratorSpliterator<T>和DoubleIteratorSpliterator实现相关方法
	public static <T> Spliterator<T> spliterator(Collection<? extends T> c, int characteristics) {
		return new IteratorSpliterator<>(Objects.requireNonNull(c), characteristics);
	}
	
	public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble iterator, long size, int characteristics) {
		return new DoubleIteratorSpliterator(Objects.requireNonNull(iterator), size, characteristics);
	}
	
	//IteratorSpliterator，基于正向迭代器的可分割迭代器类
	static class IteratorSpliterator<T> implements Spliterator<T> {
        static final int BATCH_UNIT = 1 << 10; //batch array size increment，批量数组（即trySplit()中的Object数组a）大小的增量
        static final int MAX_BATCH = 1 << 25; //max batch array size，批量数组的最大大小
        private final Collection<? extends T> collection;
        private Iterator<? extends T> it;
        private final int characteristics;
        private long est; //size estimate，当前可分割迭代器的预估大小
        private int batch; //batch size for splits，上次调用trySplit()被分割出去的元素的数量，初始为0

        //如果characteristics不包含CONCURRENT特性，则将SIZED和SUBSIZED也添加到characteristics中去，
        //用于表示当前可分割迭代器及其子可分割迭代器都是有固定大小的，因为没有其他线程并发地修改这些迭代器所引用的数据源
        public IteratorSpliterator(Collection<? extends T> collection, int characteristics) {
            this.collection = collection;
            this.it = null;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        public IteratorSpliterator(Iterator<? extends T> iterator, long size, int characteristics) {
            this.collection = null;
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        //由于当前可分割迭代器没有明确大小，故将其大小置为无限大，用Long.MAX_VALUE来表示无限大。此时，当前可分割迭代器不具有有限且精确的大小，
        //故其特性集中不能包含SIZED和SUBSIZED特性
        public IteratorSpliterator(Iterator<? extends T> iterator, int characteristics) {
            this.collection = null;
            this.it = iterator;
            this.est = Long.MAX_VALUE; //置当前可分割迭代器的大小为无限大
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        //第n次（n大于等于1）调用trySplit()时，它首先检查1024*n是否小于等于est，如果是的话就返回一个长度为1024*n的ArraySpliterator，
        //否则返回一个长度为est的ArraySpliterator；同时est也被修改为est-1024*n或0（如果hasNext()始终为true的话）
        @Override
        public Spliterator<T> trySplit() {
            Iterator<? extends T> i;
            long s;
            if ((i = it) == null) {
                i = it = collection.iterator();
                s = est = (long) collection.size();
            }
            else
                s = est;
            if (s > 1 && i.hasNext()) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                Object[] a = new Object[n];
                int j = 0;
                do { a[j] = i.next(); } while (++j < n && i.hasNext());
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new ArraySpliterator<>(a, 0, j, characteristics);
            }
            return null;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            if (action == null) throw new NullPointerException();
            Iterator<? extends T> i;
            if ((i = it) == null) {
                i = it = collection.iterator();
                est = (long)collection.size();
            }
            i.forEachRemaining(action);
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null) throw new NullPointerException();
            if (it == null) {
                it = collection.iterator();
                est = (long) collection.size();
            }
            if (it.hasNext()) {
                action.accept(it.next());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            if (it == null) {
                it = collection.iterator();
                return est = (long)collection.size();
            }
            return est;
        }

        @Override
        public int characteristics() { return characteristics; }

        @Override
        public Comparator<? super T> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }
	
	//DoubleIteratorSpliterator，基于double基本类型正向迭代器的可分割迭代器类，这里的正向迭代器类型是PrimitiveIterator.OfDouble
	static final class DoubleIteratorSpliterator implements Spliterator.OfDouble {
        static final int BATCH_UNIT = IteratorSpliterator.BATCH_UNIT; //batch array size increment，批量数组（即trySplit()中的double数组a）大小的增量
        static final int MAX_BATCH = IteratorSpliterator.MAX_BATCH; //max batch array size，批量数组的最大大小
        private PrimitiveIterator.OfDouble it;
        private final int characteristics;
        private long est; //size estimate，当前可分割迭代器的预估大小
        private int batch; //batch size for splits，上次调用trySplit()被分割出去的元素的数量，初始为0

        //如果characteristics不包含CONCURRENT特性，则将SIZED和SUBSIZED也添加到characteristics中去，
        //用于表示当前可分割迭代器及其子可分割迭代器都是有固定大小的，因为没有其他线程并发地修改这些迭代器所引用的数据源
        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble iterator, long size, int characteristics) {
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        //由于当前可分割迭代器没有明确大小，故将其大小置为无限大，用Long.MAX_VALUE来表示无限大。此时，当前可分割迭代器不具有有限且精确的大小，
        //故其特性集中不能包含SIZED和SUBSIZED特性
        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble iterator, int characteristics) {
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        //第n次（n大于等于1）调用trySplit()时，它首先检查1024*n是否小于等于est，如果是的话就返回一个长度为1024*n的DoubleArraySpliterator，
        //否则返回一个长度为est的DoubleArraySpliterator；同时est也被修改为est-1024*n或0（如果hasNext()始终为true的话）
        @Override
        public OfDouble trySplit() {
            PrimitiveIterator.OfDouble i = it;
            long s = est;
            if (s > 1 && i.hasNext()) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                double[] a = new double[n];
                int j = 0;
                do { a[j] = i.nextDouble(); } while (++j < n && i.hasNext());
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new DoubleArraySpliterator(a, 0, j, characteristics);
            }
            return null;
        }

        @Override
        public void forEachRemaining(DoubleConsumer action) {
            if (action == null) throw new NullPointerException();
            it.forEachRemaining(action);
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            if (action == null) throw new NullPointerException();
            if (it.hasNext()) {
                action.accept(it.nextDouble());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            return est;
        }

        @Override
        public int characteristics() { return characteristics; }

        @Override
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }
	
	//利用ArraySpliterator<T>和DoubleArraySpliterator实现相关方法
	public static <T> Spliterator<T> spliterator(Object[] array, int additionalCharacteristics) {
		return new ArraySpliterator<>(Objects.requireNonNull(array), additionalCharacteristics);
	}
	
	public static Spliterator.OfDouble spliterator(double[] array, int additionalCharacteristics) {
		return new DoubleArraySpliterator(Objects.requireNonNull(array), additionalCharacteristics);
	}
	
	//ArraySpliterator，基于数组的可分割迭代器类
	static final class ArraySpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int index; //当前索引，每次调用tryAdvance()或trySplit()方法时对它进行修改
        private final int fence; //栅栏索引，比最大索引大1
        private final int characteristics;

        public ArraySpliterator(Object[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }
        
        //由于double数组array大小是有限且固定的（不会变小或变大），故当前可分割迭代器应具有SIZED和SUBSIZED特性
        public ArraySpliterator(Object[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        //每次调用trySplit()，就将当前可分割迭代器的左半部分元素分割出去，构成一个新的ArraySpliterator对象
        @Override
        public Spliterator<T> trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                   ? null
                   : new ArraySpliterator<>(array, lo, index = mid, characteristics);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Object[] a; int i, hi;
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) { //将i置为当前索引index所指向的未知，并置index为hi
                do { action.accept((T)a[i]); } while (++i < hi);
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                @SuppressWarnings("unchecked") T e = (T) array[index++];
                action.accept(e);
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() { return (long)(fence - index); }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super T> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }
	
	//DoubleArraySpliterator，基于double数组的可分割迭代器类
	static final class DoubleArraySpliterator implements Spliterator.OfDouble {
        private final double[] array;
        private int index;        //当前索引，每次调用tryAdvance()或trySplit()方法时对它进行修改
        private final int fence;  //栅栏索引，比最大索引大1
        private final int characteristics;

        public DoubleArraySpliterator(double[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }

        //由于double数组array大小是有限且固定的（不会变小或变大），故当前可分割迭代器应具有SIZED和SUBSIZED特性
        public DoubleArraySpliterator(double[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        //每次调用trySplit()，就将当前可分割迭代器的左半部分元素分割出去，构成一个新的DoubleArraySpliterator对象
        @Override
        public OfDouble trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                   ? null
                   : new DoubleArraySpliterator(array, lo, index = mid, characteristics);
        }

        @Override
        public void forEachRemaining(DoubleConsumer action) {
            double[] a; int i, hi; // hoist accesses and checks from loop
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) { //将i置为当前索引index所指向的未知，并置index为hi
                do { action.accept(a[i]); } while (++i < hi);
            }
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() { return (long)(fence - index); }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }
}