package com.jgcs.chp10.p4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class App10_4 {
	public static void main(String[] args) {
		//1.ʹ��SupportStream�������ڿɷָ����������
		System.out.println("1.ʹ��SupportStream�������ڿɷָ������������");
		List<String> strList = Arrays.asList("Many", "years", "later", "as", "he", "faced", "the", "firing", "squad", 
				"Colonel", "Aureliano", "Buendia", "was", "to", "remember", "that", "distant", "afternoon", "when", 
				"his", "father", "took", "him", "to", "discover", "ice");
		//strList.spliterator()���ص�Spliterator<String>�ɷָ����������������ԣ����Ҿ���SIZED|SUBSIZED|ORDERED���ԡ�
		//���ݸ�stream(Supplier<? extends Spliterator<T>> supplier, int characteristics, boolean parallel)������Ҫ�����ĵڶ�������������Ҫ���һ���������صĿɷָ��������������ͬ
		Stream<String> strStreamSeq = StreamSupport.stream(strList::spliterator, Spliterator.SIZED|Spliterator.SUBSIZED|Spliterator.ORDERED, false); //����һ�����ڿɷָ�������Ĵ���String��
		Stream<String> strStreamPar = StreamSupport.stream(strList.spliterator(), true); //����һ�����ڿɷָ�������Ĳ���String��
		System.out.println("�Ѵ�������String��strStreamSeq�Ͳ���String��strStreamPar");
		
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); //BD��������ѧԺ
		Student stuMathew = new Student("09007", "Mathew", 'M', 20, "BD");
		Student stuAlice = new Student("09010", "Alice", 'F', 19, "BD");
		
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); //MD���������赸ѧԺ
		Student stuEric = new Student("09008", "Eric", 'M', 21, "MD");
		Student stuPenny = new Student("09009", "Penny", 'F', 19, "MD");
		Student stuAnna = new Student("09011", "Anna", 'F', 20, "MD");
		
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); //MATH����ѧѧԺ
		Student stuLucas = new Student("09012", "Lucas", 'M', 20, "MATH");
		
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); //AI���˹�����ѧԺ
		Student stuElsa = new Student("09013", "Elsa", 'F', 19, "AI");
		Student stuDaniel = new Student("09014", "Daniel", 'M', 20, "AI");
		
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); //CS�������ѧԺ
		Student stuMoira = new Student("09015", "Moira", 'F', 21, "CS");
		Student stuLuke = new Student("09016", "Luke", 'M', 22, "CS");
		Student stuHarold = new Student("09017", "Harold", 'M', 19, "CS");
		
		Student stuKevin = new Student("09006", "Kevin", 'M', 21, "PD"); //PD������ѧԺ
		Student stuViola = new Student("09018", "Viola", 'F', 20, "PD");
		
		Student[] stuArray = {stuJason, stuMathew, stuAlice, stuIsabella, stuEric, stuPenny, stuAnna, stuCathy, stuLucas, 
				stuAndrew, stuElsa, stuDaniel, stuSophia, stuMoira, stuLuke, stuHarold, stuKevin, stuViola};
		MySplitableArray<Student> stuSplitableArray = new MySplitableArray<>(stuArray);
		
		//stuSplitableArray.spliterator()���ص�Spliterator<Student>�ɷָ����������������ԣ����Ҿ���SIZED|SUBSIZED|IMMUTABLE|ORDERED���ԡ�
		Stream<Student> stuStreamSeq = StreamSupport.stream(stuSplitableArray::spliterator, Spliterator.SIZED|Spliterator.SUBSIZED|Spliterator.IMMUTABLE|Spliterator.ORDERED, false); //����һ�����ڿɷָ�������Ĵ���Student��
		Stream<Student> stuStreamPar = StreamSupport.stream(stuSplitableArray.spliterator(), true); //����һ�����ڿɷָ�������Ĳ���Student��
		System.out.println("�Ѵ�������Student��stuStreamSeq�Ͳ���Student��stuStreamPar");
		
		//2.����Collector.of()�����ռ���ʵ��
		System.out.println("\n2.����Collector.of()�����ռ���ʵ����");
		//����static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher, Collector.Characteristics... characteristics)
		Collector<String, StringBuilder, String> strCollectorWithStringBuilder = Collector.of(StringBuilder::new, 
				(sb, str) -> sb.append(str + " "), 
				StringBuilder::append, 
				StringBuilder::toString, 
				Collector.Characteristics.UNORDERED); //���ַ�����������Ԫ��ƴ��Ϊ���ַ������ռ���
//		Collector<String, StringBuilder, String> strCollectorWithStringBuilder = Collector.of(() -> new StringBuilder(), 
//				(sb, str) -> sb.append(str), 
//				(sb1, sb2) -> sb1.append(sb2), 
//				sb -> sb.toString(), 
//				Collector.Characteristics.valueOf("UNORDERED")); //���ַ�����������Ԫ��ƴ��Ϊ���ַ������ռ�������ͬ�ڵ�68~72�еĴ���
		
		//����static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<String, ArrayList<String>, ArrayList<String>> strCollectorWithArrayList = Collector.of(ArrayList<String>::new, 
				(al, str) -> al.add(str), 
				(al1, al2) -> {al1.addAll(al2); return al1;}, 
				Collector.Characteristics.UNORDERED); //���ַ�����������Ԫ����ӵ�һ���б���ռ���
		
		//����static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<String, List<String>, List<String>> strConcurrentCollectorWithSynchronizedList = Collector.of(() -> Collections.synchronizedList(new ArrayList<String>()), 
				List<String>::add, 
				(al1, al2) -> {al1.addAll(al2); return al1;}, 
				Collector.Characteristics.valueOf("UNORDERED"), Collector.Characteristics.CONCURRENT); //���ַ�����������Ԫ����ӵ�һ���б�Ĳ����ռ���
		System.out.println("�ռ���ʵ��strCollectorWithStringBuilder��strCollectorWithArrayList��strConcurrentCollectorWithSynchronizedList�ѳɹ�����");
		
		//����static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher, Collector.Characteristics... characteristics)
		Collector<Student, Integer[], Integer> stuCollectorMaxOnAge = Collector.of(() -> new Integer[]{0}, 
				(result, stu) -> {
					if(result[0] < stu.getSage())
						result[0] = stu.getSage();
				},
				(result1, result2) -> result1[0] > result2[0] ? result1 : result2, 
				result -> result[0], 
				Collector.Characteristics.UNORDERED); //��ȡѧ����������ѧ������������ֵ���ռ���
		
		//����static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<Student, ArrayList<Integer>, Integer> stuCollectorSumOnAge = Collector.of(ArrayList<Integer>::new, 
				(al, stu) -> al.add(stu.getSage()), 
				(al1, al2) -> {al1.addAll(al2); return al1;}, 
				al -> {int sum = 0; for(Integer age: al) {sum += age;} return sum;}, 
				Collector.Characteristics.valueOf("UNORDERED")); //��ȡѧ����������ѧ����������ܺ͵��ռ���
		
		//����static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<Student, Map<String, ArrayList<Student>>, Map<String, ArrayList<Student>>> stuCollectorGroupbyDepartment = Collector.of(HashMap<String, ArrayList<Student>>::new, 
				(map, stu) -> {
					String dept = stu.getSdept();
					if(map.containsKey(dept)) {
						map.get(dept).add(stu);
					} else {
						ArrayList<Student> ls = new ArrayList<>();
						ls.add(stu);
						map.put(dept, ls);
					}
				},
				(map1, map2) -> {map1.putAll(map2); return map1;}, 
				Collector.Characteristics.UNORDERED); //��ѧ����������ѧ������ѧԺ���з�����ռ���
		System.out.println("�ռ���ʵ��stuCollectorMaxOnAge��stuCollectorSumOnAge��stuCollectorGroupbyDepartment�ѳɹ�����");
		
		//3.ʹ�ò���2�������ռ����Բ���1�����������пɱ��Լ
		System.out.println("\n3.ʹ�ò���2�������ռ����Բ���1�����������пɱ��Լ��");
		String concatedStr = strStreamSeq.collect(strCollectorWithStringBuilder); //ʹ��strstrCollectorWithStringBuilder��strStreamSeq���пɱ��Լ
		List<String> strLs = strStreamPar.collect(strConcurrentCollectorWithSynchronizedList); //ʹ��strConcurrentCollectorWithSynchronizedList��strStreamPar���пɱ��Լ
		System.out.println("concatedStr is: " + concatedStr);
		System.out.println("strLs is: " + strLs);
		
		int stuMaxAge = stuStreamSeq.collect(stuCollectorMaxOnAge); //ʹ��stuCollectorMaxOnAge��stuStreamSeq��ȡѧ����������ֵ
		Map<String, ArrayList<Student>> stuGroupbyDepartment = stuStreamPar.collect(stuCollectorGroupbyDepartment); //ʹ��stuCollectorGroupbyDepartment��stuStreamPar����ѧԺ���з���
		System.out.println("stuMaxAge is: " + stuMaxAge);
		System.out.println("stuGroupbyDepartment is: " + stuGroupbyDepartment);
		
		//4.����Collectors���������ռ���ʵ����ʹ�����Ƕ������пɱ��Լ
		System.out.println("\n4.����Collectors���������ռ���ʵ����ʹ�����Ƕ������пɱ��Լ��");
		//����Collectors.averagingInt()
		System.out.println("����ѧ���������ƽ��ֵ�ǣ�" + Arrays.stream(stuArray).collect(Collectors.averagingInt(Student::getSage)));
		
		//����Collectors.counting()
		System.out.println("�ַ��������ǣ�" + strList.stream().collect(Collectors.counting()));
		
		//����Collectors.collectingAndThen()
		System.out.println("���ַ�������ƴ�Ӻ�õ��ĳ��ַ����ǣ�" + strList.parallelStream().collect(Collectors.collectingAndThen(strCollectorWithArrayList, 
				al -> {
					StringBuilder sb = new StringBuilder();
					for(String str: al)
						sb.append(str + " ");
					return sb.toString();
				})));
		
		//����static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
		System.out.println("���Ա��ѧ�����з���Ľ���ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(stu -> new Character(stu.getSsex()))));
		
		//����static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
		System.out.println("���Ա��ѧ�����з����ÿ���Ա�����ѧ������������ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(stu -> new Character(stu.getSsex()), stuCollectorMaxOnAge)));
		
		//����static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
		System.out.println("���Ա��ѧ�����з����ÿ���Ա����ѧ���������ܺ��ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(stu -> new Character(stu.getSsex()), HashMap<Character, Integer>::new, stuCollectorSumOnAge)));
		
		//����static <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
		//����Collectors.minBy()
		System.out.println("��ѧԺ��ѧ�����з����ÿ��ѧԺ��ѧ������С�����ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.groupingByConcurrent(stu -> stu.getSdept(), () -> new ConcurrentHashMap<String, Integer>(),
						Collectors.collectingAndThen(
								Collectors.minBy(Comparator.comparingInt(Student::getSage)), //����Collectors.minBy((stu1, stu2) -> Integer.min(stu1.getSage(), stu2.getSage()))
								optStu -> optStu.get().getSage())))); //minBy���ص���һ��Optional<Student>�����ｫ������ת��ΪInteger����
		
		//����static Collector<CharSequence, ?, String> joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
		System.out.println("��\", \"��Ϊ�ָ���������strList�е��ַ���������ǰ׺\"[\"�ͺ�׺\"]\"��õ����ַ����ǣ�" + strList.stream().collect(
				Collectors.joining(", ", "[", "]")));
		
		//����Collectors.mapping()��Collectors.summingInt()
		System.out.println("����ѧ���������ܺ��ǣ���ʹ��summingInt()����" + Arrays.stream(stuArray).collect(
				Collectors.summingInt(stu -> stu.getSage())));
		System.out.println("����ѧ���������ܺ��ǣ�ʹ��mapping()��summingInt()����" + Arrays.stream(stuArray).collect(
				Collectors.mapping(Student::getSage, Collectors.summingInt(age -> age))));
		System.out.println("��ѧԺ��ѧ�����з����ÿ��ѧԺ��ѧ���������ܺ��ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(Student::getSdept, Collectors.summingInt(Student::getSage))));
		
		//����static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)
		System.out.println("���ȴ���6��С�ڵ���6�������ַ����ֱ��ǣ�" + strList.stream().collect(Collectors.partitioningBy(str -> str.length() <= 6)));
		System.out.println("�������20��С�ڵ���20������ѧ����ѧ�������ֱ��ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.partitioningBy(stu -> stu.getSage() <= 20, Collectors.mapping(Student::getSname, Collectors.joining(", ")))));
		
		//����static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op)
		System.out.println("����������������һ��ѧ���������ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.reducing((stu1, stu2) -> stu1.getSage() > stu2.getSage() ? stu1 : stu2)).get().getSname()); //����Collectors.reducing(BinaryOperator.maxBy(Comparator.comparingInt(Student::getSage)))

		//����static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op)
		System.out.println("������󳤶ȵ�����һ���ַ����ǣ�" + strList.stream().collect(
				Collectors.reducing("", BinaryOperator.maxBy(Comparator.comparingInt(String::length)))));
		
		//����static <T, U> Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op)
		System.out.println("����ѧ������������ǣ�" + Arrays.stream(stuArray).collect(
				Collectors.reducing(0, Student::getSage, Integer::max))); //�������������ʹ��BinaryOperator.maxBy(Integer::max)�ͻ���ִ�����ΪInteger.max(a, b)��������������һ���������ǱȽϽ����-1�� 0��1
		
		//����static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)
		System.out.println("���ַ�����ת��Ϊ�ַ����б����б������ǣ�ʹ��Collectors.toCollection()����" + strList.stream().collect(Collectors.toCollection(ArrayList<String>::new)));
		
		//����static <T> Collector<T, ?, List<T>> toList()
		System.out.println("���ַ�����ת��Ϊ�ַ����б����б������ǣ�ʹ��Collectors.toList()����" + strList.parallelStream().collect(Collectors.toList()));
		
		//����static <T> Collector<T, ?, Set<T>> toSet()
		System.out.println("���ַ�����ת��Ϊ�ַ������򼯺Ϻ�ļ��������ǣ�" + strList.stream().collect(Collectors.toSet()));
		
		//����static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
		System.out.println("������ѧ������ѧԺ�����ÿ��ѧԺ��ѧ�������ǣ�ʹ��Collectors.toMap()����" + Arrays.stream(stuArray).collect(
				Collectors.toMap(Student::getSdept, 
						stu -> {ArrayList<String> ls = new ArrayList<String>(); ls.add(stu.getSname()); return ls;}, 
						(ls1, ls2) -> {ls1.addAll(ls2); return ls1;}, 
						HashMap<String, ArrayList<String>>::new)));
		
		//����static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
		System.out.println("������ѧ������ѧԺ�����ÿ��ѧԺ��ѧ�������ǣ�ʹ��Collectors.toConcurrentMap()����" + Arrays.stream(stuArray).parallel().collect(
				Collectors.toConcurrentMap(Student::getSdept, 
						stu -> {ArrayList<String> ls = new ArrayList<String>(); ls.add(stu.getSname()); return ls;}, 
						(ls1, ls2) -> {ls1.addAll(ls2); return ls1;}, 
						ConcurrentHashMap<String, ArrayList<String>>::new)));
	}
}

class Student implements Comparable<Student>{
	String sno;
	String sname;
	char ssex;
	int sage;
	String sdept;

	public Student(String sno, String sname, char ssex, int sage, String sdept) {
		this.sno = sno;
		this.sname = sname;
		this.ssex = ssex;
		this.sage = sage;
		this.sdept = sdept;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public char getSsex() {
		return ssex;
	}

	public void setSsex(char ssex) {
		this.ssex = ssex;
	}

	public int getSage() {
		return sage;
	}

	public void setSage(int sage) {
		this.sage = sage;
	}

	public String getSdept() {
		return sdept;
	}

	public void setSdept(String sdept) {
		this.sdept = sdept;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Student(");
		sb.append("sno='").append(sno).append('\'');
		sb.append(", sname='").append(sname).append('\'');
		sb.append(", ssex='").append(ssex).append('\'');
		sb.append(", sage=").append(sage);
		sb.append(", sdept='").append(sdept).append('\'');
		sb.append(')');
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return sno.hashCode() >>> 1;
	}

	@Override
	public boolean equals(Object obj) { //����ѧ�Ž��бȽϣ��ڷ���trueʱҪ��compareTo()�������ϱ���һ��
		if(obj instanceof Student) {
			Student stu = (Student)obj;
			return this.sno.equals(stu.getSno()) ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Student stu) { //����ѧ�Ž��бȽϣ��ڷ���0ʱҪ��equals()�������ϱ���һ��
		return sno.compareTo(stu.getSno());
	}
}

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
			return SIZED | SUBSIZED | IMMUTABLE | ORDERED;
		}
	}
}