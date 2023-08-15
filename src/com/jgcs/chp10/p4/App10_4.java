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
		//1.使用SupportStream创建基于可分割迭代器的流
		System.out.println("1.使用SupportStream创建基于可分割迭代器的流：");
		List<String> strList = Arrays.asList("Many", "years", "later", "as", "he", "faced", "the", "firing", "squad", 
				"Colonel", "Aureliano", "Buendia", "was", "to", "remember", "that", "distant", "afternoon", "when", 
				"his", "father", "took", "him", "to", "discover", "ice");
		//strList.spliterator()返回的Spliterator<String>可分割迭代器具有晚绑定特性，并且具有SIZED|SUBSIZED|ORDERED特性。
		//根据该stream(Supplier<? extends Spliterator<T>> supplier, int characteristics, boolean parallel)方法的要求，它的第二个参数的特性要与第一个参数返回的可分割迭代器的特性相同
		Stream<String> strStreamSeq = StreamSupport.stream(strList::spliterator, Spliterator.SIZED|Spliterator.SUBSIZED|Spliterator.ORDERED, false); //创建一个基于可分割迭代器的串行String流
		Stream<String> strStreamPar = StreamSupport.stream(strList.spliterator(), true); //创建一个基于可分割迭代器的并行String流
		System.out.println("已创建串行String流strStreamSeq和并行String流strStreamPar");
		
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); //BD：大数据学院
		Student stuMathew = new Student("09007", "Mathew", 'M', 20, "BD");
		Student stuAlice = new Student("09010", "Alice", 'F', 19, "BD");
		
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); //MD：音乐与舞蹈学院
		Student stuEric = new Student("09008", "Eric", 'M', 21, "MD");
		Student stuPenny = new Student("09009", "Penny", 'F', 19, "MD");
		Student stuAnna = new Student("09011", "Anna", 'F', 20, "MD");
		
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); //MATH：数学学院
		Student stuLucas = new Student("09012", "Lucas", 'M', 20, "MATH");
		
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); //AI：人工智能学院
		Student stuElsa = new Student("09013", "Elsa", 'F', 19, "AI");
		Student stuDaniel = new Student("09014", "Daniel", 'M', 20, "AI");
		
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); //CS：计算机学院
		Student stuMoira = new Student("09015", "Moira", 'F', 21, "CS");
		Student stuLuke = new Student("09016", "Luke", 'M', 22, "CS");
		Student stuHarold = new Student("09017", "Harold", 'M', 19, "CS");
		
		Student stuKevin = new Student("09006", "Kevin", 'M', 21, "PD"); //PD：物理学院
		Student stuViola = new Student("09018", "Viola", 'F', 20, "PD");
		
		Student[] stuArray = {stuJason, stuMathew, stuAlice, stuIsabella, stuEric, stuPenny, stuAnna, stuCathy, stuLucas, 
				stuAndrew, stuElsa, stuDaniel, stuSophia, stuMoira, stuLuke, stuHarold, stuKevin, stuViola};
		MySplitableArray<Student> stuSplitableArray = new MySplitableArray<>(stuArray);
		
		//stuSplitableArray.spliterator()返回的Spliterator<Student>可分割迭代器具有晚绑定特性，并且具有SIZED|SUBSIZED|IMMUTABLE|ORDERED特性。
		Stream<Student> stuStreamSeq = StreamSupport.stream(stuSplitableArray::spliterator, Spliterator.SIZED|Spliterator.SUBSIZED|Spliterator.IMMUTABLE|Spliterator.ORDERED, false); //创建一个基于可分割迭代器的串行Student流
		Stream<Student> stuStreamPar = StreamSupport.stream(stuSplitableArray.spliterator(), true); //创建一个基于可分割迭代器的并行Student流
		System.out.println("已创建串行Student流stuStreamSeq和并行Student流stuStreamPar");
		
		//2.调用Collector.of()创建收集器实例
		System.out.println("\n2.调用Collector.of()创建收集器实例：");
		//调用static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher, Collector.Characteristics... characteristics)
		Collector<String, StringBuilder, String> strCollectorWithStringBuilder = Collector.of(StringBuilder::new, 
				(sb, str) -> sb.append(str + " "), 
				StringBuilder::append, 
				StringBuilder::toString, 
				Collector.Characteristics.UNORDERED); //将字符串流中所有元素拼接为长字符串的收集器
//		Collector<String, StringBuilder, String> strCollectorWithStringBuilder = Collector.of(() -> new StringBuilder(), 
//				(sb, str) -> sb.append(str), 
//				(sb1, sb2) -> sb1.append(sb2), 
//				sb -> sb.toString(), 
//				Collector.Characteristics.valueOf("UNORDERED")); //将字符串流中所有元素拼接为长字符串的收集器，等同于第68~72行的代码
		
		//调用static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<String, ArrayList<String>, ArrayList<String>> strCollectorWithArrayList = Collector.of(ArrayList<String>::new, 
				(al, str) -> al.add(str), 
				(al1, al2) -> {al1.addAll(al2); return al1;}, 
				Collector.Characteristics.UNORDERED); //将字符串流中所有元素添加到一个列表的收集器
		
		//调用static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<String, List<String>, List<String>> strConcurrentCollectorWithSynchronizedList = Collector.of(() -> Collections.synchronizedList(new ArrayList<String>()), 
				List<String>::add, 
				(al1, al2) -> {al1.addAll(al2); return al1;}, 
				Collector.Characteristics.valueOf("UNORDERED"), Collector.Characteristics.CONCURRENT); //将字符串流中所有元素添加到一个列表的并发收集器
		System.out.println("收集器实例strCollectorWithStringBuilder、strCollectorWithArrayList和strConcurrentCollectorWithSynchronizedList已成功创建");
		
		//调用static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher, Collector.Characteristics... characteristics)
		Collector<Student, Integer[], Integer> stuCollectorMaxOnAge = Collector.of(() -> new Integer[]{0}, 
				(result, stu) -> {
					if(result[0] < stu.getSage())
						result[0] = stu.getSage();
				},
				(result1, result2) -> result1[0] > result2[0] ? result1 : result2, 
				result -> result[0], 
				Collector.Characteristics.UNORDERED); //求取学生流中所有学生的年龄的最大值的收集器
		
		//调用static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		Collector<Student, ArrayList<Integer>, Integer> stuCollectorSumOnAge = Collector.of(ArrayList<Integer>::new, 
				(al, stu) -> al.add(stu.getSage()), 
				(al1, al2) -> {al1.addAll(al2); return al1;}, 
				al -> {int sum = 0; for(Integer age: al) {sum += age;} return sum;}, 
				Collector.Characteristics.valueOf("UNORDERED")); //求取学生流中所有学生的年龄的总和的收集器
		
		//调用static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> accumulator, BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
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
				Collector.Characteristics.UNORDERED); //对学生流中所有学生按照学院进行分组的收集器
		System.out.println("收集器实例stuCollectorMaxOnAge、stuCollectorSumOnAge和stuCollectorGroupbyDepartment已成功创建");
		
		//3.使用步骤2创建的收集器对步骤1创建的流进行可变归约
		System.out.println("\n3.使用步骤2创建的收集器对步骤1创建的流进行可变归约：");
		String concatedStr = strStreamSeq.collect(strCollectorWithStringBuilder); //使用strstrCollectorWithStringBuilder对strStreamSeq进行可变归约
		List<String> strLs = strStreamPar.collect(strConcurrentCollectorWithSynchronizedList); //使用strConcurrentCollectorWithSynchronizedList对strStreamPar进行可变归约
		System.out.println("concatedStr is: " + concatedStr);
		System.out.println("strLs is: " + strLs);
		
		int stuMaxAge = stuStreamSeq.collect(stuCollectorMaxOnAge); //使用stuCollectorMaxOnAge对stuStreamSeq求取学生年龄的最大值
		Map<String, ArrayList<Student>> stuGroupbyDepartment = stuStreamPar.collect(stuCollectorGroupbyDepartment); //使用stuCollectorGroupbyDepartment对stuStreamPar按照学院进行分组
		System.out.println("stuMaxAge is: " + stuMaxAge);
		System.out.println("stuGroupbyDepartment is: " + stuGroupbyDepartment);
		
		//4.调用Collectors方法创建收集器实例并使用它们对流进行可变归约
		System.out.println("\n4.调用Collectors方法创建收集器实例并使用它们对流进行可变归约：");
		//调用Collectors.averagingInt()
		System.out.println("所有学生的年龄的平均值是：" + Arrays.stream(stuArray).collect(Collectors.averagingInt(Student::getSage)));
		
		//调用Collectors.counting()
		System.out.println("字符串总数是：" + strList.stream().collect(Collectors.counting()));
		
		//调用Collectors.collectingAndThen()
		System.out.println("对字符串进行拼接后得到的长字符串是：" + strList.parallelStream().collect(Collectors.collectingAndThen(strCollectorWithArrayList, 
				al -> {
					StringBuilder sb = new StringBuilder();
					for(String str: al)
						sb.append(str + " ");
					return sb.toString();
				})));
		
		//调用static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
		System.out.println("按性别对学生进行分组的结果是：" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(stu -> new Character(stu.getSsex()))));
		
		//调用static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
		System.out.println("按性别对学生进行分组后每个性别组中学生的最大年龄是：" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(stu -> new Character(stu.getSsex()), stuCollectorMaxOnAge)));
		
		//调用static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
		System.out.println("按性别对学生进行分组后每个性别组的学生的年龄总和是：" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(stu -> new Character(stu.getSsex()), HashMap<Character, Integer>::new, stuCollectorSumOnAge)));
		
		//调用static <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
		//调用Collectors.minBy()
		System.out.println("按学院对学生进行分组后每个学院中学生的最小年龄是：" + Arrays.stream(stuArray).collect(
				Collectors.groupingByConcurrent(stu -> stu.getSdept(), () -> new ConcurrentHashMap<String, Integer>(),
						Collectors.collectingAndThen(
								Collectors.minBy(Comparator.comparingInt(Student::getSage)), //或者Collectors.minBy((stu1, stu2) -> Integer.min(stu1.getSage(), stu2.getSage()))
								optStu -> optStu.get().getSage())))); //minBy返回的是一个Optional<Student>，这里将此类型转化为Integer类型
		
		//调用static Collector<CharSequence, ?, String> joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
		System.out.println("用\", \"作为分隔符来连接strList中的字符串并加上前缀\"[\"和后缀\"]\"后得到的字符串是：" + strList.stream().collect(
				Collectors.joining(", ", "[", "]")));
		
		//调用Collectors.mapping()和Collectors.summingInt()
		System.out.println("所有学生的年龄总和是（仅使用summingInt()）：" + Arrays.stream(stuArray).collect(
				Collectors.summingInt(stu -> stu.getSage())));
		System.out.println("所有学生的年龄总和是（使用mapping()和summingInt()）：" + Arrays.stream(stuArray).collect(
				Collectors.mapping(Student::getSage, Collectors.summingInt(age -> age))));
		System.out.println("按学院对学生进行分组后每个学院中学生的年龄总和是：" + Arrays.stream(stuArray).collect(
				Collectors.groupingBy(Student::getSdept, Collectors.summingInt(Student::getSage))));
		
		//调用static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)
		System.out.println("长度大于6和小于等于6的两组字符串分别是：" + strList.stream().collect(Collectors.partitioningBy(str -> str.length() <= 6)));
		System.out.println("年龄大于20和小于等于20的两组学生的学生姓名分别是：" + Arrays.stream(stuArray).collect(
				Collectors.partitioningBy(stu -> stu.getSage() <= 20, Collectors.mapping(Student::getSname, Collectors.joining(", ")))));
		
		//调用static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op)
		System.out.println("具有最大年龄的其中一个学生的姓名是：" + Arrays.stream(stuArray).collect(
				Collectors.reducing((stu1, stu2) -> stu1.getSage() > stu2.getSage() ? stu1 : stu2)).get().getSname()); //或者Collectors.reducing(BinaryOperator.maxBy(Comparator.comparingInt(Student::getSage)))

		//调用static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op)
		System.out.println("具有最大长度的其中一个字符串是：" + strList.stream().collect(
				Collectors.reducing("", BinaryOperator.maxBy(Comparator.comparingInt(String::length)))));
		
		//调用static <T, U> Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op)
		System.out.println("所有学生的最大年龄是：" + Arrays.stream(stuArray).collect(
				Collectors.reducing(0, Student::getSage, Integer::max))); //这里第三个参数使用BinaryOperator.maxBy(Integer::max)就会出现错误，因为Integer.max(a, b)函数返回最大的那一个，而不是比较结果如-1， 0和1
		
		//调用static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)
		System.out.println("将字符串流转化为字符串列表后的列表内容是（使用Collectors.toCollection()）：" + strList.stream().collect(Collectors.toCollection(ArrayList<String>::new)));
		
		//调用static <T> Collector<T, ?, List<T>> toList()
		System.out.println("将字符串流转化为字符串列表后的列表内容是（使用Collectors.toList()）：" + strList.parallelStream().collect(Collectors.toList()));
		
		//调用static <T> Collector<T, ?, Set<T>> toSet()
		System.out.println("将字符串流转化为字符串无序集合后的集合内容是：" + strList.stream().collect(Collectors.toSet()));
		
		//调用static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
		System.out.println("将所有学生按照学院分组后每个学院的学生姓名是（使用Collectors.toMap()）：" + Arrays.stream(stuArray).collect(
				Collectors.toMap(Student::getSdept, 
						stu -> {ArrayList<String> ls = new ArrayList<String>(); ls.add(stu.getSname()); return ls;}, 
						(ls1, ls2) -> {ls1.addAll(ls2); return ls1;}, 
						HashMap<String, ArrayList<String>>::new)));
		
		//调用static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
		System.out.println("将所有学生按照学院分组后每个学院的学生姓名是（使用Collectors.toConcurrentMap()）：" + Arrays.stream(stuArray).parallel().collect(
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
	public boolean equals(Object obj) { //根据学号进行比较，在返回true时要与compareTo()在语义上保持一致
		if(obj instanceof Student) {
			Student stu = (Student)obj;
			return this.sno.equals(stu.getSno()) ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Student stu) { //根据学号进行比较，在返回0时要与equals()在语义上保持一致
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