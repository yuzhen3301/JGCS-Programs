package com.jgcs.chp10.p3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App10_3 {
	public static void main(String[] args) {
		//1.流的创建（即Collection.stream()、Collection.parallelStream()、Arrays.stream()、[Int]Stream.of()、[Int]Stream.iterate()、[Int]Stream.generate()、[Int]Stream.builder()、IntStream.range()和IntStream.rangeClosed()等）
		System.out.println("1.流的创建（即Collection.stream()、Collection.parallelStream()、Arrays.stream()、[Int]Stream.of()、[Int]Stream.iterate()、[Int]Stream.generate()、[Int]Stream.builder()、IntStream.range()和IntStream.rangeClosed()等）：");
		//调用java.util.Collection.stream()方法用容器创建流
		List<String> strList = Arrays.asList("Many", "years", "later", "as", "he", "faced", "the", "firing", "squad", 
				"Colonel", "Aureliano", "Buendia", "was", "to", "remember", "that", "distant", "afternoon", "when", 
				"his", "father", "took", "him", "to", "discover", "ice");
		Stream<String> strStream = strList.stream(); //创建一个以strList为源的字符串串行（顺序）流strStream
		System.out.println("已创建strStream流");
		Stream<String> strParallelStream = strList.parallelStream(); //创建一个以strList为源的字符串并行流strParallelStream
		
		//调用java.util.Arrays.stream(T[] array)方法用数组创建流
		int[] intArray = {39, 76, 5, 29, 92, 92, 2, 38, 12, 52, 97, 61, 90, 98, 90, 65, 62, 80, 43, 67};
		IntStream intArrayStream = Arrays.stream(intArray);
		System.out.println("已创建intArrayStream流");
		
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); //BD：大数据学院
		Student stuMathew = new Student("09007", "Mathew", 'M', 20, "BD");
		Student stuAlice = new Student("09010", "Alice", 'F', 19, "BD");
		
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); //MD：音乐与舞蹈学院
		Student stuEric = new Student("09008", "Eric", 'M', 21, "MD");
		Student stuPenny = new Student("09009", "Penny", 'F', 19, "MD");
		Student stuAnna = new Student("09011", "Anna", 'F', 20, "MD");
		
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); //MATH：数学学院
		Student stuLucas = new Student("09012", "Lucas", 'M', 20, "Math");
		
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
		Stream<Student> stuStream = Arrays.stream(stuArray);
		System.out.println("已创建stuStream流");
		
		//调用IntStream和Stream的静态方法如of()、iterate()、generate()和builder()等来创建流
		IntStream intStream1 = IntStream.of(1, 2, 3, 4, 5, 6); //调用IntStream.of()创建流
		IntStream intStream2 = IntStream.iterate(0, x -> x + 3).limit(6); //调用IntStream.iterate()创建流
		IntStream intStream3 = IntStream.generate(() -> new Random().nextInt(100)).limit(20); //调用IntStream.generate()创建流
		IntStream intStream4 = IntStream.range(1, 10); //调用IntStream.range()创建流
		IntStream intStream5 = IntStream.rangeClosed(1, 10); //调用IntStream.rangeClosed()创建流
		
		IntStream.Builder intStreamBuilder = IntStream.builder(); //调用IntStream.builder()创建流
		for(int i = 1; i <= 6; i++)
			intStreamBuilder.add(i);
		IntStream intStream6 = intStreamBuilder.build();
		
		Stream<Integer> integerStream1 = Stream.of(1, 2, 3, 4, 5, 6); //调用Stream.of()创建流
		Stream<Integer> integerStream2 = Stream.iterate(0, x -> x + 3).limit(6); //调用Stream.iterate()创建流
		Stream<Integer> integerStream3 = Stream.<Integer>generate(() -> Math.round((float)(Math.random()*100))).limit(20); //调用Stream.generate()创建流
		Stream<Integer> integerStream4 = Stream.<Integer>builder().add(1).add(2).add(3).add(4).add(5).add(6).build(); //调用Stream.builder()创建流
		Stream<Integer> integerStream = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20); //调用Stream.generate()创建流
		System.out.println("已创建integerStream流");
		
		System.out.println("strStream流、intArrayStream流、integerStream和stuStream流包含的元素个数分别是：" + strStream.count() + ", " + intArrayStream.count() + ", " + integerStream.count() + ", " + stuStream.count());
		
		//2.流的遍历（即forEach()和forEachOrdered()等）
		System.out.println("\n2.流的遍历（即forEach()和forEachOrdered()等）：");
		System.out.print("遍历串行字符串String流中内容（以forEach()方式）：");
		strList.stream().forEach(elm -> System.out.print(elm.toString() + " ")); //forEach()
		System.out.println();
		
		System.out.print("遍历串行字符串String流中内容（以forEachOrdered()方式）：");
		strList.stream().forEachOrdered(elm -> System.out.print(elm.toString() + " ")); //forEachOrdered()
		System.out.println();
		
		System.out.print("遍历并行字符串String流中内容（以forEach()方式）：");
		strList.parallelStream().forEach(elm -> System.out.print(elm.toString() + " ")); //forEach()
		System.out.println();
		
		System.out.print("遍历并行字符串String流中内容（以forEachOrdered()方式）：");
		strList.parallelStream().forEachOrdered(elm -> System.out.print(elm.toString() + " ")); //forEachOrdered()
		System.out.println();
		
		System.out.print("遍历整数int流中内容（以forEach()方式）：");
		Arrays.stream(intArray).forEach(num -> System.out.print("" + num + " ")); //forEach()
		System.out.println();
		
		System.out.print("遍历整数Integer流中内容（以forEach()方式）：");
		Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).forEach(num -> System.out.print("" + num + " ")); //forEach()
		System.out.println();
		
		System.out.print("遍历学生Student流中学生的学号（以forEach()方式）：");
		Arrays.stream(stuArray).forEach(stu -> System.out.print("" + stu.getSno() + " "));
		System.out.println();
		
		//3.流的过滤、并行化、串行化和匹配（即filter()、parallel()、sequential()、findFirst()、findAny()、anyMatch()、allMatch()和noneMatch()等）
		System.out.println("\n3.流的过滤、并行化、串行化和匹配（即filter()、parallel()、sequential()、findFirst()、findAny()、anyMatch()、allMatch()和noneMatch()等）：");
		Optional<String> strParallelFirst = strList.parallelStream().filter(str -> str.length() >= 6).findFirst(); //filter()和findFirst()
		Optional<String> strSequentialFirst = strList.parallelStream().sequential().filter(str -> str.length() >= 6).findFirst(); //将并行流转变为串行流，sequential()
		OptionalInt intAny = Arrays.stream(intArray).parallel().filter(x -> x % 4 == 3).findAny(); //将串行流转变为并行流，parallel()和findAny()
		Optional<Student> stuAny = Arrays.stream(stuArray).filter(stu -> stu.getSage() == 20).findAny();  //filter()和findAny()
		System.out.println("字符串并行流中第一个长度不小于6的字符串是：" + strParallelFirst.get());
		System.out.println("字符串串行流中第一个长度不小于6的字符串是：" + strSequentialFirst.get());
		System.out.println("并行int流中某个对4取模结果为3的元素是：" + intAny.getAsInt());
		System.out.println("学生Student流中是否存在年龄为20的学生：" + stuAny.isPresent() + ", 其中一个这样的学生的姓名是：" + stuAny.get().getSname());
		System.out.println("学生Student流中是否存在年龄大于等于23的学生：" + Arrays.stream(stuArray).anyMatch(stu -> stu.getSage() >= 23)); //anyMatch()
		System.out.println("学生Student流中的学生是否都具有有效姓名：" + Arrays.stream(stuArray).allMatch(stu -> stu.getSname() != null && stu.getSname().length() >= 0)); //allMatch
		System.out.println("学生Student流中的学生是否都具有有效性别：" + Arrays.stream(stuArray).noneMatch(stu -> stu.getSsex() == '\0')); //noneMatch()
		
		//4.流的聚合（即求最大值max()、求最小值min()、求平均值average()、求和sum()和统计元素个数count()等）
		System.out.println("\n4.流的聚合（即求最大值max()、求最小值min()、求平均值average()、求和sum()和统计元素个数count()等）：");
		System.out.println("整数int流中的最大值、最小值、平均值和元素个数分别是：" + Arrays.stream(intArray).max().getAsInt() + ", " + 
				Arrays.stream(intArray).min().getAsInt() + ", " + Arrays.stream(intArray).average().getAsDouble() + ", " +
				Arrays.stream(intArray).count());
		System.out.println("字符串String流中具有最大长度和最小长度的某两个字符串分别是：" + strList.stream().max(Comparator.comparingInt(String::length)).get() + ", " + 
				strList.stream().parallel().min(Comparator.comparingInt(String::length)).get());
		System.out.println("学生Student流中具有最大年龄和最小年龄的某两个学生分别是：" + Arrays.stream(stuArray).max(Comparator.comparing(Student::getSage)).orElseGet(() -> null) + 
				", " + Arrays.stream(stuArray).min(Comparator.comparing(Student::getSage)).orElseGet(() -> null));
		
		//5.流的映射（即map()和flatMap()等）
		System.out.println("\n5.流的映射（即map()和flatMap()等）：");
		System.out.print("将字符串String流中所有字符串全部转化为大写字符串后得到的流中的元素是：");
		strList.stream().map(String::toUpperCase).forEach(str -> System.out.print(str + " ")); //map()
		System.out.println();
		
		System.out.print("将学生Student流转化为姓名字符串String流后得到的流中的元素是：");
		Arrays.stream(stuArray).map(Student::getSname).forEach(str -> System.out.print(str + " ")); //map()
		System.out.println();
		
		System.out.print("将学生姓名流转化为字符流后得到的流中的元素是：");
		Arrays.stream(stuArray).map(Student::getSname).
				flatMap(str -> { //调用flatMap()将一个字符串str转变为由str的字符构成的字符Character流，flatMap()返回的流将是所有字符流合并而成的最终字符流
								char[] chars = str.toCharArray();
								Character[] characterArray = new Character[chars.length];
								for(int i = 0; i < chars.length; i++)
								characterArray[i] = Character.valueOf(chars[i]);
								return Arrays.stream(characterArray);
							   }
						).forEach(ch -> System.out.print(ch + " "));
		System.out.println();
		
		System.out.print("将学生Student流转化为年龄int流后得到的流中的元素是（使用mapToInt()方法）：");
		Arrays.stream(stuArray).mapToInt(Student::getSage).forEach(age -> System.out.print(age + " ")); //mapToInt()
		System.out.println();
		
		System.out.print("将学生Student流转化为年龄int流后得到的流中的元素是（使用flatMapToInt()方法）：");
		Arrays.stream(stuArray).flatMapToInt(stu -> IntStream.of(stu.getSage())).forEach(age -> System.out.print(age + " ")); //flatMapToInt()
		System.out.println();
		
		//6.流的归约（即reduce()和collect()等）
		System.out.println("\n6.流的归约（即reduce()和collect()等）：");		
		//调用reduce()进行普通归约或不可变归约
		OptionalInt intStreamSum1 = Arrays.stream(intArray).reduce((x, y) -> x + y); //int流求和方式1
		OptionalInt intStreamSum2 = Arrays.stream(intArray).reduce(Integer::sum); //int流求和方式2
		int intStreamSum3 = Arrays.stream(intArray).reduce(0, Integer::sum); //int流求和方式3
		OptionalInt intProduct = Arrays.stream(intArray).reduce((x, y) -> x * y / 4); //int流求积（这里让每个元素都除以4是为了避免溢出！）
		OptionalInt intMax1 = Arrays.stream(intArray).reduce((x, y) -> x > y ? x : y); //int流求最大值方式1
		int intMax2 = Arrays.stream(intArray).reduce(0, Integer::max); //int流求最大值方式2
		System.out.println("intStreamSum1, intStreamSum2, intStreamSum3, intProduct, intMax1 and intMax2 are respectively: " + intStreamSum1.getAsInt() + ", " + intStreamSum2.getAsInt() + 
				 ", " + intStreamSum3 + ", " + intProduct.getAsInt() + ", " + intMax1.getAsInt() + ", " + intMax2);
		
		Optional<Integer> integerStreamSum1 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce((x, y) -> x + y); //Integer流求和方式1
		Optional<Integer> integerStreamSum2 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce(Integer::sum); //Integer流求和方式2
		Integer integerStreamSum3 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce(0, Integer::sum); //Integer流求和方式3
		Optional<Integer> integerProduct = Stream.<Integer>generate(() -> new Random().nextInt(10) + 1).limit(20).reduce((x, y) -> x * y); //Integer流求积
		Optional<Integer> integerMax1 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce((x, y) -> x > y ? x : y); //Integer流求最大值方式1
		Integer integerMax2 = Arrays.stream(intArray).reduce(0, Integer::max); //Integer流求最大值方式2
		System.out.println("integerStreamSum1, integerStreamSum2, integerStreamSum3, integerProduct, integerMax1 and integerMax2 are respectively: " + integerStreamSum1.get() + ", " + integerStreamSum2.get() + 
					", " + integerStreamSum3 + ", " + integerProduct.get() + ", " + integerMax1.get() + ", " + integerMax2);
		
		Optional<Integer> stuAgeSum1 = Arrays.stream(stuArray).map(Student::getSage).reduce((x, y) -> x + y); //Student流求年龄之和方式1
		Optional<Integer> stuAgeSum2 = Arrays.stream(stuArray).map(Student::getSage).reduce(Integer::sum); //Student流求年龄之和方式2
		Integer stuAgeSum3 = Arrays.stream(stuArray).map(Student::getSage).reduce(0, Integer::sum); //Student流求年龄之和方式3
		Integer stuAgeSum4 = Arrays.stream(stuArray).reduce(0, (sum, stu) -> sum += stu.getSage(), (x, y) -> x + y); //Student流求年龄之和方式4
		Integer stuAgeSum5 = Arrays.stream(stuArray).reduce(0, (sum, stu) -> sum += stu.getSage(), Integer::sum); //Student流求年龄之和方式5
		System.out.println("stuAgeSum1, stuAgeSum2, stuAgeSum3, stuAgeSum4 and stuAgeSum5 are respectively: " + stuAgeSum1.get() + ", " + stuAgeSum2.get() + ", " + stuAgeSum3 + ", " + stuAgeSum4 + ", " + stuAgeSum5);
		
		Integer stuAgeMax1 = Arrays.stream(stuArray).reduce(0, (max, stu) -> max > stu.getSage() ? max : stu.getSage(), Integer::max); //Student流求最大年龄方式1
		Integer stuAgeMax2 = Arrays.stream(stuArray).reduce(0, (max, stu) -> max > stu.getSage() ? max : stu.getSage(), (max1, max2) -> max1 > max2 ? max1 : max2); //Student流求最大年龄方式2
		System.out.println("stuAgeMax1 and stuAgeMax2 are respectively: " + stuAgeMax1 + ", " + stuAgeMax2);
		
		//调用collect()进行可变归约
//		int intStreamSum_Collect = Arrays.stream(intArray).collect(() -> new Integer(0), (Integer result, int num) -> result += num, (Integer result1, Integer result2) -> result1 += result2); //Integer对象是不可变对象，这样计算出来的intStreamSum_Collect永远为0
		int intStreamSum_Collect = Arrays.stream(intArray).collect(() -> new int[]{0}, (int[] result, int num) -> result[0] += num, (int[] result1, int[] result2) -> result1[0] += result2[0])[0]; //int[]对象是可变对象
		System.out.println("intStreamSum_Collect is: " + intStreamSum_Collect);
		String concatedStr1 = strList.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString(); //字符串String流可变归约1
		String concatedStr2 = strList.parallelStream().collect(StringBuilder::new, (StringBuilder resultSb, String str) -> resultSb.append(str + " "), (StringBuilder resultSb1, StringBuilder resultSb2) -> resultSb1.append(resultSb2.toString())).toString(); //字符串String流可变归约2
		System.out.println("concatedStr1 is: " + concatedStr1);
		System.out.println("concatedStr2 is: " + concatedStr2);
		ArrayList<Student> stuList1 = Arrays.stream(stuArray).collect(ArrayList<Student>::new, ArrayList::add, ArrayList::addAll); //学生Student流可变归约1
		ArrayList<Student> stuList2 = Arrays.stream(stuArray).collect(() -> new ArrayList<Student>(), (ArrayList<Student> resultLs, Student stu) -> resultLs.add(stu), (ArrayList<Student> resultLs1, ArrayList<Student> resultLs2) -> resultLs1.addAll(resultLs2)); //学生Student流可变归约2
		System.out.println("stuList1 is: " + stuList1);
		System.out.println("stuList2 is: " + stuList2);
		
		//7.流的排序、拼接、去重、限制、查看和跳过（即sorted()、unordered()、concat()、distinct()、limit()、peek()和skip()等）
		System.out.println("\n7.流的排序、拼接、去重、限制和跳过（即sorted()、unordered()、concat()、distinct()、limit()和skip()等）：");
		System.out.print("未去重前的int流内容是：");
		Arrays.stream(intArray).forEach(num -> System.out.print(num + " "));
		System.out.println();
		System.out.print("调用distinct()进行去重后的int流内容是：");
		Arrays.stream(intArray).distinct().forEach(num -> System.out.print(num + " ")); //distinct()
		System.out.println();
		
		System.out.print("调用sorted()进行排序后的int流内容是：");
		Arrays.stream(intArray).sorted().forEach(num -> System.out.print(num + " ")); //sorted()
		System.out.println();
		
		//由于intArray是数组，天然具有相遇顺序，故Arrays.stream(intArray)返回的流具有相遇顺序
		//注意forEach()会忽略流的相遇顺序，而forEachOrdered()会考虑流的相遇顺序。对于串行流，无论它是否具有相遇顺序，forEach()和forEachOrdered()的输出可能相同。
		//对于并行流，如果它具有相遇顺序，则对其调用forEach()打印其中元素一般会得到乱序输出，而对其调用forEachOrdered()必定会得到跟其相遇顺序一致的输出。如果它不具有相遇顺序，则
		//对其无论调用forEach()还是forEachOrdered()，一般都会得到乱序输出。
		System.out.print("对int流调用parallel()再调用forEach()后的结果是：");
		Arrays.stream(intArray).parallel().forEach(num -> System.out.print(num + " ")); //forEach()
		System.out.println();
		
		System.out.print("对int流调用parallel()再调用forEachOrdered()后的结果是：");
		Arrays.stream(intArray).parallel().forEachOrdered(num -> System.out.print(num + " ")); //forEachOrdered()
		System.out.println();
		
		//由于intArray是数组，天然具有相遇顺序，故Arrays.stream(intArray)返回的流具有相遇顺序，而对该流调用sorted()后得到的流也具有相遇顺序。
		//对于具有相遇顺序的流调用unordered()其就变为不具有相遇顺序的流。
		//对于串行（或顺序）流，流是否具有相遇顺序并不会对其执行性能有影响，而只是影响其执行确定性。如果串行流具有相遇顺序，则对其数据源多次执行同一流管道计算后将会得到完全相同的结果；
		//但如果串行流不具有相遇顺序，则对其数据源多次执行同一流管道计算后得到的结果可能相同也可能不相同。
		System.out.print("先调用sorted()再调用unordered()后的int流的内容是：");
		Arrays.stream(intArray).sorted().unordered().forEach(num -> System.out.print(num + " ")); //unordered()
		System.out.println();
		
		System.out.print("先调用unordered()再调用parallel()后的int流的内容是：");
		Arrays.stream(intArray).unordered().parallel().forEach(num -> System.out.print(num + " ")); //unordered()
		System.out.println();
		
		System.out.print("先调用parallel()再调用unordered()后的int流的内容是：");
		Arrays.stream(intArray).parallel().unordered().forEach(num -> System.out.print(num + " ")); //unordered()
		System.out.println();
		
		System.out.print("调用concat()对两个流进行拼后得到的int流的内容是：");
		Stream.concat(Stream.of(1, 2, 3, 4, 5), Stream.iterate(6, num -> num + 1).limit(5)).forEach(num -> System.out.print(num + " ")); //concat
		System.out.println();
		
		System.out.print("对一个从1开始且以1为步进进行递增的int流（按方式1产生）调用limit()后得到的int流的内容是：");
		Stream.generate(new AtomicInteger(1)::getAndIncrement).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.print("对一个从1开始且以1为步进进行递增的int流（按方式2产生）调用limit()后得到的int流的内容是：");
		Stream.generate(new Supplier<Integer>(){
			AtomicInteger ai = new AtomicInteger(1);
			public Integer get() {
				return ai.getAndIncrement();
			}
		}).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.print("对一个元素全为1的int流（按方式1产生）调用limit()后得到的int流的内容是：");
		Stream.generate(() -> new AtomicInteger(1).getAndIncrement()).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.print("对一个元素全为1的int流（按方式2产生）调用limit()后得到的int流的内容是：");
		Stream.generate(new Supplier<Integer>() {
			public Integer get() {
				return new AtomicInteger(1).getAndIncrement();
			}
		}).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.println("对一个String流调用peek()进行查看的结果是：");
		strList.stream().filter(str -> str.length() > 8)
			.peek(e -> System.out.println("filtered value: " + e)) //peek()，用于查看流中的内容或者对流中内容进行一个消费性质的操作
			.map(String::toUpperCase)
			.peek(e -> System.out.println("mapped value: " + e))
			.collect(ArrayList<String>::new, ArrayList<String>::add, ArrayList<String>::addAll);
		
		System.out.print("对一个从1开始且以1为步进进行递增的int流调用skip()再调用limit()后得到的int流的结果是：");
		Stream.iterate(1,  num -> num + 1).skip(5).limit(5).forEach(num -> System.out.print(num + " ")); //skip()
		System.out.println();
		
		System.out.print("对一个String串行流调用sorted()按照字典顺序对流中字符串进行排序后前三个字符串是：");
		strList.stream().sorted(String::compareTo).limit(3).forEach(str -> System.out.print(str + " ")); //sorted()
		System.out.println();
		
		System.out.print("对一个String并行流调用sorted()按照字典顺序对流中字符串进行排序后前三个字符串是：");
		strList.parallelStream().sorted(String::compareTo).limit(3).forEach(str -> System.out.print(str + " ")); //sorted()
		System.out.println();
		
		System.out.print("对一个Student串行流调用sorted()按照学号从小到大的顺序对流中学生进行排序后前三个学生是：");
		Arrays.stream(stuArray).sorted((stu1, stu2) -> stu1.compareTo(stu2)).limit(3).forEach(stu -> System.out.print(stu.toString() + " ")); //sorted()
		System.out.println();
		
		System.out.print("对一个String串行流调用sorted()按照长度从长到短的顺序对流中字符串进行排序后前三个字符串是：");
		strList.stream().sorted(Comparator.comparing(String::length).reversed()).limit(3).forEach(str -> System.out.print(str + " ")); //sorted()
		System.out.println();
		
		System.out.print("对一个Student串行流调用sorted()按照年龄从大到小的方式对流中学生进行排序后前三个学生是：");
		Arrays.stream(stuArray).sorted(Comparator.comparing(Student::getSage).reversed()).limit(3).forEach(stu -> System.out.print(stu.toString() + " ")); //sorted()
		System.out.println();
		
		//8.流的数组转化（即toArray()等）
		System.out.println("\n8.流的数组转化（即toArray()等）：");
		int[] numArray = Arrays.stream(intArray).toArray();
		System.out.println("对一个int流调用toArray()将其转化成int数组后数组内容是：" + Arrays.toString(numArray));
		
//		int[] numArray2 = IntStream.iterate(1, num -> num += 1).toArray(); //无限流无法转换为有限数组
//		System.out.println("对一个int型无限流调用toArray()将其转化成int数组后数组内容是：" + Arrays.toString(numArray2));
		
		String[] restStrs1 = strList.stream().filter(str -> str.length() > 8).toArray(String[]::new); //String数组生成方式1，等价于String数组生成方式2
		System.out.println("对一个String流调用filter()生成一个由长度大于8的字符串构成的流，将该流转化成字符串数组后数组内容是（方式1）：" + Arrays.toString(restStrs1));
		
		String[] restStrs2 = strList.stream().filter(str -> str.length() > 8).toArray(size -> new String[size]); //String数组生成方式2
		System.out.println("对一个String流调用filter()生成一个由长度大于8的字符串构成的流，将该流转化成字符串数组后数组内容是（方式2）：" + Arrays.toString(restStrs2));
		
		Student[] restStus1 = Arrays.stream(stuArray).filter(stu -> stu.getSage() == 22).toArray(Student[]::new); //Student数组生成方式1，等价于Student数组生成方式2
		System.out.println("对一个Student流调用filter()生成一个由年龄等于22的学生构成的流，将该流转化成Student数组后数组内容是（方式1）：" + Arrays.toString(restStus1));
		
		Student[] restStus2 = Arrays.stream(stuArray).filter(stu -> stu.getSage() == 22).toArray((size) -> {return new Student[size];}); //Student数组生成方式2
		System.out.println("对一个Student流调用filter()生成一个由年龄等于22的学生构成的流，将该流转化成Student数组后数组内容是（方式2）：" + Arrays.toString(restStus2));
		
		//9.流关闭（即close()）
		System.out.println("\n9.流关闭（即close()）：");
		strStream = strList.stream().distinct();
		System.out.println("已生成一个新的字符串流strStream");
//		strStream.close(); //如果在这里关闭strStream，则后续对strStream调用sorted()等流操作将会触发java.lang.IllegalStateException异常
		System.out.print("字符串流strStream中内容是：");
		strStream.sorted(String::compareTo).forEach(str -> System.out.print(str + " "));
		System.out.println();
		System.out.println("关闭字符串流strStream");
		strStream.close();
		
		System.out.print("使用try-with-resources语句块包裹和自动关闭流："); //另一种更好的关闭流的方式
		try(IntStream is = IntStream.generate(() -> new Random().nextInt(100))){
			int sum = is.limit(20).reduce(Integer::sum).getAsInt();
			System.out.print("sum is " + sum);
		}catch(Exception exp) {
			exp.printStackTrace();
		}
		System.out.println();
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