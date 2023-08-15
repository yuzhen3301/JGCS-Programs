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
				String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //��ĸ��
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
			ex.printStackTrace(System.out); //���쳣��ջ�����System.out������System.err��System.errΪĬ��Ŀ�꣩
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
	//AbstractSpliterator������ɷָ��������
    public static abstract class AbstractSpliterator<T> implements Spliterator<T> {
        static final int BATCH_UNIT = 1 << 10; //batch array size increment���������飨��trySplit()�е�Object����a����С������
        static final int MAX_BATCH = 1 << 25; //max batch array size���������������С
        private final int characteristics;
        private long est; //size estimate����ǰ�ɷָ��������Ԥ����С
        private int batch; //batch size for splits���ϴε���trySplit()���ָ��ȥ��Ԫ�ص���������ʼΪ0

        //���additionalCharacteristics����SIZED���ԣ���SUBSIZED����Ҳ��ӵ�����
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

        //��n�Σ�n���ڵ���1������trySplit()ʱ�������ȼ��1024*n�Ƿ�С�ڵ���est������ǵĻ��ͷ���һ������Ϊ1024*n��ArraySpliterator��
        //���򷵻�һ������Ϊest��ArraySpliterator��ͬʱestҲ���޸�Ϊest-1024*n��0�����tryAdvance(holder)ʼ��Ϊtrue�Ļ���
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
                batch = j; //�޸�batchΪj
                if (est != Long.MAX_VALUE) //���est�������޴�Long.MAX_VALUE������ʾ���޴�
                    est -= j; //��est��Ϊest-j
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
	
	//AbstractDoubleSpliterator��ר����double�������͵ĳ���ɷָ��������
	public static abstract class AbstractDoubleSpliterator implements Spliterator.OfDouble {
        static final int MAX_BATCH = AbstractSpliterator.MAX_BATCH; //�������飨��trySplit()�е�double����a����С������
        static final int BATCH_UNIT = AbstractSpliterator.BATCH_UNIT; //�������������С
        private final int characteristics;
        private long est; //��ǰ�ɷָ��������Ԥ����С
        private int batch; //�ϴε���trySplit()���ָ��ȥ��Ԫ�ص���������ʼΪ0

        //���additionalCharacteristics����SIZED���ԣ���SUBSIZED����Ҳ��ӵ�����
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

        //��n�Σ�n���ڵ���1������trySplit()ʱ�������ȼ��1024*n�Ƿ�С�ڵ���est������ǵĻ��ͷ���һ������Ϊ1024*n��DoubleArraySpliterator��
        //���򷵻�һ������Ϊest��DoubleArraySpliterator��ͬʱestҲ���޸�Ϊest-1024*n��0�����tryAdvance(holder)ʼ��Ϊtrue�Ļ���
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
	
	//����EmptySpliterator<T, S extends Spliterator<T>, C>ʵ����ط���
    public static <T> Spliterator<T> emptySpliterator() {
        return new EmptySpliterator.OfRef<>();
    }
    
    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return new EmptySpliterator.OfDouble();
    }
	
	//EmptySpliterator���տɷָ��������
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
	
	//����IteratorSpliterator<T>��DoubleIteratorSpliteratorʵ����ط���
	public static <T> Spliterator<T> spliterator(Collection<? extends T> c, int characteristics) {
		return new IteratorSpliterator<>(Objects.requireNonNull(c), characteristics);
	}
	
	public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble iterator, long size, int characteristics) {
		return new DoubleIteratorSpliterator(Objects.requireNonNull(iterator), size, characteristics);
	}
	
	//IteratorSpliterator����������������Ŀɷָ��������
	static class IteratorSpliterator<T> implements Spliterator<T> {
        static final int BATCH_UNIT = 1 << 10; //batch array size increment���������飨��trySplit()�е�Object����a����С������
        static final int MAX_BATCH = 1 << 25; //max batch array size���������������С
        private final Collection<? extends T> collection;
        private Iterator<? extends T> it;
        private final int characteristics;
        private long est; //size estimate����ǰ�ɷָ��������Ԥ����С
        private int batch; //batch size for splits���ϴε���trySplit()���ָ��ȥ��Ԫ�ص���������ʼΪ0

        //���characteristics������CONCURRENT���ԣ���SIZED��SUBSIZEDҲ��ӵ�characteristics��ȥ��
        //���ڱ�ʾ��ǰ�ɷָ�����������ӿɷָ�����������й̶���С�ģ���Ϊû�������̲߳������޸���Щ�����������õ�����Դ
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

        //���ڵ�ǰ�ɷָ������û����ȷ��С���ʽ����С��Ϊ���޴���Long.MAX_VALUE����ʾ���޴󡣴�ʱ����ǰ�ɷָ�����������������Ҿ�ȷ�Ĵ�С��
        //�������Լ��в��ܰ���SIZED��SUBSIZED����
        public IteratorSpliterator(Iterator<? extends T> iterator, int characteristics) {
            this.collection = null;
            this.it = iterator;
            this.est = Long.MAX_VALUE; //�õ�ǰ�ɷָ�������Ĵ�СΪ���޴�
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        //��n�Σ�n���ڵ���1������trySplit()ʱ�������ȼ��1024*n�Ƿ�С�ڵ���est������ǵĻ��ͷ���һ������Ϊ1024*n��ArraySpliterator��
        //���򷵻�һ������Ϊest��ArraySpliterator��ͬʱestҲ���޸�Ϊest-1024*n��0�����hasNext()ʼ��Ϊtrue�Ļ���
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
	
	//DoubleIteratorSpliterator������double������������������Ŀɷָ�������࣬��������������������PrimitiveIterator.OfDouble
	static final class DoubleIteratorSpliterator implements Spliterator.OfDouble {
        static final int BATCH_UNIT = IteratorSpliterator.BATCH_UNIT; //batch array size increment���������飨��trySplit()�е�double����a����С������
        static final int MAX_BATCH = IteratorSpliterator.MAX_BATCH; //max batch array size���������������С
        private PrimitiveIterator.OfDouble it;
        private final int characteristics;
        private long est; //size estimate����ǰ�ɷָ��������Ԥ����С
        private int batch; //batch size for splits���ϴε���trySplit()���ָ��ȥ��Ԫ�ص���������ʼΪ0

        //���characteristics������CONCURRENT���ԣ���SIZED��SUBSIZEDҲ��ӵ�characteristics��ȥ��
        //���ڱ�ʾ��ǰ�ɷָ�����������ӿɷָ�����������й̶���С�ģ���Ϊû�������̲߳������޸���Щ�����������õ�����Դ
        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble iterator, long size, int characteristics) {
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        //���ڵ�ǰ�ɷָ������û����ȷ��С���ʽ����С��Ϊ���޴���Long.MAX_VALUE����ʾ���޴󡣴�ʱ����ǰ�ɷָ�����������������Ҿ�ȷ�Ĵ�С��
        //�������Լ��в��ܰ���SIZED��SUBSIZED����
        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble iterator, int characteristics) {
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        //��n�Σ�n���ڵ���1������trySplit()ʱ�������ȼ��1024*n�Ƿ�С�ڵ���est������ǵĻ��ͷ���һ������Ϊ1024*n��DoubleArraySpliterator��
        //���򷵻�һ������Ϊest��DoubleArraySpliterator��ͬʱestҲ���޸�Ϊest-1024*n��0�����hasNext()ʼ��Ϊtrue�Ļ���
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
	
	//����ArraySpliterator<T>��DoubleArraySpliteratorʵ����ط���
	public static <T> Spliterator<T> spliterator(Object[] array, int additionalCharacteristics) {
		return new ArraySpliterator<>(Objects.requireNonNull(array), additionalCharacteristics);
	}
	
	public static Spliterator.OfDouble spliterator(double[] array, int additionalCharacteristics) {
		return new DoubleArraySpliterator(Objects.requireNonNull(array), additionalCharacteristics);
	}
	
	//ArraySpliterator����������Ŀɷָ��������
	static final class ArraySpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int index; //��ǰ������ÿ�ε���tryAdvance()��trySplit()����ʱ���������޸�
        private final int fence; //դ�������������������1
        private final int characteristics;

        public ArraySpliterator(Object[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }
        
        //����double����array��С�������ҹ̶��ģ������С���󣩣��ʵ�ǰ�ɷָ������Ӧ����SIZED��SUBSIZED����
        public ArraySpliterator(Object[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        //ÿ�ε���trySplit()���ͽ���ǰ�ɷָ����������벿��Ԫ�طָ��ȥ������һ���µ�ArraySpliterator����
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
                (i = index) >= 0 && i < (index = hi)) { //��i��Ϊ��ǰ����index��ָ���δ֪������indexΪhi
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
	
	//DoubleArraySpliterator������double����Ŀɷָ��������
	static final class DoubleArraySpliterator implements Spliterator.OfDouble {
        private final double[] array;
        private int index;        //��ǰ������ÿ�ε���tryAdvance()��trySplit()����ʱ���������޸�
        private final int fence;  //դ�������������������1
        private final int characteristics;

        public DoubleArraySpliterator(double[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }

        //����double����array��С�������ҹ̶��ģ������С���󣩣��ʵ�ǰ�ɷָ������Ӧ����SIZED��SUBSIZED����
        public DoubleArraySpliterator(double[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        //ÿ�ε���trySplit()���ͽ���ǰ�ɷָ����������벿��Ԫ�طָ��ȥ������һ���µ�DoubleArraySpliterator����
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
                (i = index) >= 0 && i < (index = hi)) { //��i��Ϊ��ǰ����index��ָ���δ֪������indexΪhi
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