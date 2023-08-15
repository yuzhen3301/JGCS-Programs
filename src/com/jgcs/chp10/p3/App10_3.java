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
		//1.���Ĵ�������Collection.stream()��Collection.parallelStream()��Arrays.stream()��[Int]Stream.of()��[Int]Stream.iterate()��[Int]Stream.generate()��[Int]Stream.builder()��IntStream.range()��IntStream.rangeClosed()�ȣ�
		System.out.println("1.���Ĵ�������Collection.stream()��Collection.parallelStream()��Arrays.stream()��[Int]Stream.of()��[Int]Stream.iterate()��[Int]Stream.generate()��[Int]Stream.builder()��IntStream.range()��IntStream.rangeClosed()�ȣ���");
		//����java.util.Collection.stream()����������������
		List<String> strList = Arrays.asList("Many", "years", "later", "as", "he", "faced", "the", "firing", "squad", 
				"Colonel", "Aureliano", "Buendia", "was", "to", "remember", "that", "distant", "afternoon", "when", 
				"his", "father", "took", "him", "to", "discover", "ice");
		Stream<String> strStream = strList.stream(); //����һ����strListΪԴ���ַ������У�˳����strStream
		System.out.println("�Ѵ���strStream��");
		Stream<String> strParallelStream = strList.parallelStream(); //����һ����strListΪԴ���ַ���������strParallelStream
		
		//����java.util.Arrays.stream(T[] array)���������鴴����
		int[] intArray = {39, 76, 5, 29, 92, 92, 2, 38, 12, 52, 97, 61, 90, 98, 90, 65, 62, 80, 43, 67};
		IntStream intArrayStream = Arrays.stream(intArray);
		System.out.println("�Ѵ���intArrayStream��");
		
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); //BD��������ѧԺ
		Student stuMathew = new Student("09007", "Mathew", 'M', 20, "BD");
		Student stuAlice = new Student("09010", "Alice", 'F', 19, "BD");
		
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); //MD���������赸ѧԺ
		Student stuEric = new Student("09008", "Eric", 'M', 21, "MD");
		Student stuPenny = new Student("09009", "Penny", 'F', 19, "MD");
		Student stuAnna = new Student("09011", "Anna", 'F', 20, "MD");
		
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); //MATH����ѧѧԺ
		Student stuLucas = new Student("09012", "Lucas", 'M', 20, "Math");
		
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
		Stream<Student> stuStream = Arrays.stream(stuArray);
		System.out.println("�Ѵ���stuStream��");
		
		//����IntStream��Stream�ľ�̬������of()��iterate()��generate()��builder()����������
		IntStream intStream1 = IntStream.of(1, 2, 3, 4, 5, 6); //����IntStream.of()������
		IntStream intStream2 = IntStream.iterate(0, x -> x + 3).limit(6); //����IntStream.iterate()������
		IntStream intStream3 = IntStream.generate(() -> new Random().nextInt(100)).limit(20); //����IntStream.generate()������
		IntStream intStream4 = IntStream.range(1, 10); //����IntStream.range()������
		IntStream intStream5 = IntStream.rangeClosed(1, 10); //����IntStream.rangeClosed()������
		
		IntStream.Builder intStreamBuilder = IntStream.builder(); //����IntStream.builder()������
		for(int i = 1; i <= 6; i++)
			intStreamBuilder.add(i);
		IntStream intStream6 = intStreamBuilder.build();
		
		Stream<Integer> integerStream1 = Stream.of(1, 2, 3, 4, 5, 6); //����Stream.of()������
		Stream<Integer> integerStream2 = Stream.iterate(0, x -> x + 3).limit(6); //����Stream.iterate()������
		Stream<Integer> integerStream3 = Stream.<Integer>generate(() -> Math.round((float)(Math.random()*100))).limit(20); //����Stream.generate()������
		Stream<Integer> integerStream4 = Stream.<Integer>builder().add(1).add(2).add(3).add(4).add(5).add(6).build(); //����Stream.builder()������
		Stream<Integer> integerStream = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20); //����Stream.generate()������
		System.out.println("�Ѵ���integerStream��");
		
		System.out.println("strStream����intArrayStream����integerStream��stuStream��������Ԫ�ظ����ֱ��ǣ�" + strStream.count() + ", " + intArrayStream.count() + ", " + integerStream.count() + ", " + stuStream.count());
		
		//2.���ı�������forEach()��forEachOrdered()�ȣ�
		System.out.println("\n2.���ı�������forEach()��forEachOrdered()�ȣ���");
		System.out.print("���������ַ���String�������ݣ���forEach()��ʽ����");
		strList.stream().forEach(elm -> System.out.print(elm.toString() + " ")); //forEach()
		System.out.println();
		
		System.out.print("���������ַ���String�������ݣ���forEachOrdered()��ʽ����");
		strList.stream().forEachOrdered(elm -> System.out.print(elm.toString() + " ")); //forEachOrdered()
		System.out.println();
		
		System.out.print("���������ַ���String�������ݣ���forEach()��ʽ����");
		strList.parallelStream().forEach(elm -> System.out.print(elm.toString() + " ")); //forEach()
		System.out.println();
		
		System.out.print("���������ַ���String�������ݣ���forEachOrdered()��ʽ����");
		strList.parallelStream().forEachOrdered(elm -> System.out.print(elm.toString() + " ")); //forEachOrdered()
		System.out.println();
		
		System.out.print("��������int�������ݣ���forEach()��ʽ����");
		Arrays.stream(intArray).forEach(num -> System.out.print("" + num + " ")); //forEach()
		System.out.println();
		
		System.out.print("��������Integer�������ݣ���forEach()��ʽ����");
		Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).forEach(num -> System.out.print("" + num + " ")); //forEach()
		System.out.println();
		
		System.out.print("����ѧ��Student����ѧ����ѧ�ţ���forEach()��ʽ����");
		Arrays.stream(stuArray).forEach(stu -> System.out.print("" + stu.getSno() + " "));
		System.out.println();
		
		//3.���Ĺ��ˡ����л������л���ƥ�䣨��filter()��parallel()��sequential()��findFirst()��findAny()��anyMatch()��allMatch()��noneMatch()�ȣ�
		System.out.println("\n3.���Ĺ��ˡ����л������л���ƥ�䣨��filter()��parallel()��sequential()��findFirst()��findAny()��anyMatch()��allMatch()��noneMatch()�ȣ���");
		Optional<String> strParallelFirst = strList.parallelStream().filter(str -> str.length() >= 6).findFirst(); //filter()��findFirst()
		Optional<String> strSequentialFirst = strList.parallelStream().sequential().filter(str -> str.length() >= 6).findFirst(); //��������ת��Ϊ��������sequential()
		OptionalInt intAny = Arrays.stream(intArray).parallel().filter(x -> x % 4 == 3).findAny(); //��������ת��Ϊ��������parallel()��findAny()
		Optional<Student> stuAny = Arrays.stream(stuArray).filter(stu -> stu.getSage() == 20).findAny();  //filter()��findAny()
		System.out.println("�ַ����������е�һ�����Ȳ�С��6���ַ����ǣ�" + strParallelFirst.get());
		System.out.println("�ַ����������е�һ�����Ȳ�С��6���ַ����ǣ�" + strSequentialFirst.get());
		System.out.println("����int����ĳ����4ȡģ���Ϊ3��Ԫ���ǣ�" + intAny.getAsInt());
		System.out.println("ѧ��Student�����Ƿ��������Ϊ20��ѧ����" + stuAny.isPresent() + ", ����һ��������ѧ���������ǣ�" + stuAny.get().getSname());
		System.out.println("ѧ��Student�����Ƿ����������ڵ���23��ѧ����" + Arrays.stream(stuArray).anyMatch(stu -> stu.getSage() >= 23)); //anyMatch()
		System.out.println("ѧ��Student���е�ѧ���Ƿ񶼾�����Ч������" + Arrays.stream(stuArray).allMatch(stu -> stu.getSname() != null && stu.getSname().length() >= 0)); //allMatch
		System.out.println("ѧ��Student���е�ѧ���Ƿ񶼾�����Ч�Ա�" + Arrays.stream(stuArray).noneMatch(stu -> stu.getSsex() == '\0')); //noneMatch()
		
		//4.���ľۺϣ��������ֵmax()������Сֵmin()����ƽ��ֵaverage()�����sum()��ͳ��Ԫ�ظ���count()�ȣ�
		System.out.println("\n4.���ľۺϣ��������ֵmax()������Сֵmin()����ƽ��ֵaverage()�����sum()��ͳ��Ԫ�ظ���count()�ȣ���");
		System.out.println("����int���е����ֵ����Сֵ��ƽ��ֵ��Ԫ�ظ����ֱ��ǣ�" + Arrays.stream(intArray).max().getAsInt() + ", " + 
				Arrays.stream(intArray).min().getAsInt() + ", " + Arrays.stream(intArray).average().getAsDouble() + ", " +
				Arrays.stream(intArray).count());
		System.out.println("�ַ���String���о�����󳤶Ⱥ���С���ȵ�ĳ�����ַ����ֱ��ǣ�" + strList.stream().max(Comparator.comparingInt(String::length)).get() + ", " + 
				strList.stream().parallel().min(Comparator.comparingInt(String::length)).get());
		System.out.println("ѧ��Student���о�������������С�����ĳ����ѧ���ֱ��ǣ�" + Arrays.stream(stuArray).max(Comparator.comparing(Student::getSage)).orElseGet(() -> null) + 
				", " + Arrays.stream(stuArray).min(Comparator.comparing(Student::getSage)).orElseGet(() -> null));
		
		//5.����ӳ�䣨��map()��flatMap()�ȣ�
		System.out.println("\n5.����ӳ�䣨��map()��flatMap()�ȣ���");
		System.out.print("���ַ���String���������ַ���ȫ��ת��Ϊ��д�ַ�����õ������е�Ԫ���ǣ�");
		strList.stream().map(String::toUpperCase).forEach(str -> System.out.print(str + " ")); //map()
		System.out.println();
		
		System.out.print("��ѧ��Student��ת��Ϊ�����ַ���String����õ������е�Ԫ���ǣ�");
		Arrays.stream(stuArray).map(Student::getSname).forEach(str -> System.out.print(str + " ")); //map()
		System.out.println();
		
		System.out.print("��ѧ��������ת��Ϊ�ַ�����õ������е�Ԫ���ǣ�");
		Arrays.stream(stuArray).map(Student::getSname).
				flatMap(str -> { //����flatMap()��һ���ַ���strת��Ϊ��str���ַ����ɵ��ַ�Character����flatMap()���ص������������ַ����ϲ����ɵ������ַ���
								char[] chars = str.toCharArray();
								Character[] characterArray = new Character[chars.length];
								for(int i = 0; i < chars.length; i++)
								characterArray[i] = Character.valueOf(chars[i]);
								return Arrays.stream(characterArray);
							   }
						).forEach(ch -> System.out.print(ch + " "));
		System.out.println();
		
		System.out.print("��ѧ��Student��ת��Ϊ����int����õ������е�Ԫ���ǣ�ʹ��mapToInt()��������");
		Arrays.stream(stuArray).mapToInt(Student::getSage).forEach(age -> System.out.print(age + " ")); //mapToInt()
		System.out.println();
		
		System.out.print("��ѧ��Student��ת��Ϊ����int����õ������е�Ԫ���ǣ�ʹ��flatMapToInt()��������");
		Arrays.stream(stuArray).flatMapToInt(stu -> IntStream.of(stu.getSage())).forEach(age -> System.out.print(age + " ")); //flatMapToInt()
		System.out.println();
		
		//6.���Ĺ�Լ����reduce()��collect()�ȣ�
		System.out.println("\n6.���Ĺ�Լ����reduce()��collect()�ȣ���");		
		//����reduce()������ͨ��Լ�򲻿ɱ��Լ
		OptionalInt intStreamSum1 = Arrays.stream(intArray).reduce((x, y) -> x + y); //int����ͷ�ʽ1
		OptionalInt intStreamSum2 = Arrays.stream(intArray).reduce(Integer::sum); //int����ͷ�ʽ2
		int intStreamSum3 = Arrays.stream(intArray).reduce(0, Integer::sum); //int����ͷ�ʽ3
		OptionalInt intProduct = Arrays.stream(intArray).reduce((x, y) -> x * y / 4); //int�������������ÿ��Ԫ�ض�����4��Ϊ�˱����������
		OptionalInt intMax1 = Arrays.stream(intArray).reduce((x, y) -> x > y ? x : y); //int�������ֵ��ʽ1
		int intMax2 = Arrays.stream(intArray).reduce(0, Integer::max); //int�������ֵ��ʽ2
		System.out.println("intStreamSum1, intStreamSum2, intStreamSum3, intProduct, intMax1 and intMax2 are respectively: " + intStreamSum1.getAsInt() + ", " + intStreamSum2.getAsInt() + 
				 ", " + intStreamSum3 + ", " + intProduct.getAsInt() + ", " + intMax1.getAsInt() + ", " + intMax2);
		
		Optional<Integer> integerStreamSum1 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce((x, y) -> x + y); //Integer����ͷ�ʽ1
		Optional<Integer> integerStreamSum2 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce(Integer::sum); //Integer����ͷ�ʽ2
		Integer integerStreamSum3 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce(0, Integer::sum); //Integer����ͷ�ʽ3
		Optional<Integer> integerProduct = Stream.<Integer>generate(() -> new Random().nextInt(10) + 1).limit(20).reduce((x, y) -> x * y); //Integer�����
		Optional<Integer> integerMax1 = Stream.<Integer>generate(() -> new Random().nextInt(100)).limit(20).reduce((x, y) -> x > y ? x : y); //Integer�������ֵ��ʽ1
		Integer integerMax2 = Arrays.stream(intArray).reduce(0, Integer::max); //Integer�������ֵ��ʽ2
		System.out.println("integerStreamSum1, integerStreamSum2, integerStreamSum3, integerProduct, integerMax1 and integerMax2 are respectively: " + integerStreamSum1.get() + ", " + integerStreamSum2.get() + 
					", " + integerStreamSum3 + ", " + integerProduct.get() + ", " + integerMax1.get() + ", " + integerMax2);
		
		Optional<Integer> stuAgeSum1 = Arrays.stream(stuArray).map(Student::getSage).reduce((x, y) -> x + y); //Student��������֮�ͷ�ʽ1
		Optional<Integer> stuAgeSum2 = Arrays.stream(stuArray).map(Student::getSage).reduce(Integer::sum); //Student��������֮�ͷ�ʽ2
		Integer stuAgeSum3 = Arrays.stream(stuArray).map(Student::getSage).reduce(0, Integer::sum); //Student��������֮�ͷ�ʽ3
		Integer stuAgeSum4 = Arrays.stream(stuArray).reduce(0, (sum, stu) -> sum += stu.getSage(), (x, y) -> x + y); //Student��������֮�ͷ�ʽ4
		Integer stuAgeSum5 = Arrays.stream(stuArray).reduce(0, (sum, stu) -> sum += stu.getSage(), Integer::sum); //Student��������֮�ͷ�ʽ5
		System.out.println("stuAgeSum1, stuAgeSum2, stuAgeSum3, stuAgeSum4 and stuAgeSum5 are respectively: " + stuAgeSum1.get() + ", " + stuAgeSum2.get() + ", " + stuAgeSum3 + ", " + stuAgeSum4 + ", " + stuAgeSum5);
		
		Integer stuAgeMax1 = Arrays.stream(stuArray).reduce(0, (max, stu) -> max > stu.getSage() ? max : stu.getSage(), Integer::max); //Student����������䷽ʽ1
		Integer stuAgeMax2 = Arrays.stream(stuArray).reduce(0, (max, stu) -> max > stu.getSage() ? max : stu.getSage(), (max1, max2) -> max1 > max2 ? max1 : max2); //Student����������䷽ʽ2
		System.out.println("stuAgeMax1 and stuAgeMax2 are respectively: " + stuAgeMax1 + ", " + stuAgeMax2);
		
		//����collect()���пɱ��Լ
//		int intStreamSum_Collect = Arrays.stream(intArray).collect(() -> new Integer(0), (Integer result, int num) -> result += num, (Integer result1, Integer result2) -> result1 += result2); //Integer�����ǲ��ɱ�����������������intStreamSum_Collect��ԶΪ0
		int intStreamSum_Collect = Arrays.stream(intArray).collect(() -> new int[]{0}, (int[] result, int num) -> result[0] += num, (int[] result1, int[] result2) -> result1[0] += result2[0])[0]; //int[]�����ǿɱ����
		System.out.println("intStreamSum_Collect is: " + intStreamSum_Collect);
		String concatedStr1 = strList.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString(); //�ַ���String���ɱ��Լ1
		String concatedStr2 = strList.parallelStream().collect(StringBuilder::new, (StringBuilder resultSb, String str) -> resultSb.append(str + " "), (StringBuilder resultSb1, StringBuilder resultSb2) -> resultSb1.append(resultSb2.toString())).toString(); //�ַ���String���ɱ��Լ2
		System.out.println("concatedStr1 is: " + concatedStr1);
		System.out.println("concatedStr2 is: " + concatedStr2);
		ArrayList<Student> stuList1 = Arrays.stream(stuArray).collect(ArrayList<Student>::new, ArrayList::add, ArrayList::addAll); //ѧ��Student���ɱ��Լ1
		ArrayList<Student> stuList2 = Arrays.stream(stuArray).collect(() -> new ArrayList<Student>(), (ArrayList<Student> resultLs, Student stu) -> resultLs.add(stu), (ArrayList<Student> resultLs1, ArrayList<Student> resultLs2) -> resultLs1.addAll(resultLs2)); //ѧ��Student���ɱ��Լ2
		System.out.println("stuList1 is: " + stuList1);
		System.out.println("stuList2 is: " + stuList2);
		
		//7.��������ƴ�ӡ�ȥ�ء����ơ��鿴����������sorted()��unordered()��concat()��distinct()��limit()��peek()��skip()�ȣ�
		System.out.println("\n7.��������ƴ�ӡ�ȥ�ء����ƺ���������sorted()��unordered()��concat()��distinct()��limit()��skip()�ȣ���");
		System.out.print("δȥ��ǰ��int�������ǣ�");
		Arrays.stream(intArray).forEach(num -> System.out.print(num + " "));
		System.out.println();
		System.out.print("����distinct()����ȥ�غ��int�������ǣ�");
		Arrays.stream(intArray).distinct().forEach(num -> System.out.print(num + " ")); //distinct()
		System.out.println();
		
		System.out.print("����sorted()����������int�������ǣ�");
		Arrays.stream(intArray).sorted().forEach(num -> System.out.print(num + " ")); //sorted()
		System.out.println();
		
		//����intArray�����飬��Ȼ��������˳�򣬹�Arrays.stream(intArray)���ص�����������˳��
		//ע��forEach()�������������˳�򣬶�forEachOrdered()�ῼ����������˳�򡣶��ڴ��������������Ƿ��������˳��forEach()��forEachOrdered()�����������ͬ��
		//���ڲ��������������������˳����������forEach()��ӡ����Ԫ��һ���õ�������������������forEachOrdered()�ض���õ���������˳��һ�µ���������������������˳����
		//�������۵���forEach()����forEachOrdered()��һ�㶼��õ����������
		System.out.print("��int������parallel()�ٵ���forEach()��Ľ���ǣ�");
		Arrays.stream(intArray).parallel().forEach(num -> System.out.print(num + " ")); //forEach()
		System.out.println();
		
		System.out.print("��int������parallel()�ٵ���forEachOrdered()��Ľ���ǣ�");
		Arrays.stream(intArray).parallel().forEachOrdered(num -> System.out.print(num + " ")); //forEachOrdered()
		System.out.println();
		
		//����intArray�����飬��Ȼ��������˳�򣬹�Arrays.stream(intArray)���ص�����������˳�򣬶��Ը�������sorted()��õ�����Ҳ��������˳��
		//���ھ�������˳���������unordered()��ͱ�Ϊ����������˳�������
		//���ڴ��У���˳���������Ƿ��������˳�򲢲������ִ��������Ӱ�죬��ֻ��Ӱ����ִ��ȷ���ԡ������������������˳�����������Դ���ִ��ͬһ���ܵ�����󽫻�õ���ȫ��ͬ�Ľ����
		//���������������������˳�����������Դ���ִ��ͬһ���ܵ������õ��Ľ��������ͬҲ���ܲ���ͬ��
		System.out.print("�ȵ���sorted()�ٵ���unordered()���int���������ǣ�");
		Arrays.stream(intArray).sorted().unordered().forEach(num -> System.out.print(num + " ")); //unordered()
		System.out.println();
		
		System.out.print("�ȵ���unordered()�ٵ���parallel()���int���������ǣ�");
		Arrays.stream(intArray).unordered().parallel().forEach(num -> System.out.print(num + " ")); //unordered()
		System.out.println();
		
		System.out.print("�ȵ���parallel()�ٵ���unordered()���int���������ǣ�");
		Arrays.stream(intArray).parallel().unordered().forEach(num -> System.out.print(num + " ")); //unordered()
		System.out.println();
		
		System.out.print("����concat()������������ƴ��õ���int���������ǣ�");
		Stream.concat(Stream.of(1, 2, 3, 4, 5), Stream.iterate(6, num -> num + 1).limit(5)).forEach(num -> System.out.print(num + " ")); //concat
		System.out.println();
		
		System.out.print("��һ����1��ʼ����1Ϊ�������е�����int��������ʽ1����������limit()��õ���int���������ǣ�");
		Stream.generate(new AtomicInteger(1)::getAndIncrement).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.print("��һ����1��ʼ����1Ϊ�������е�����int��������ʽ2����������limit()��õ���int���������ǣ�");
		Stream.generate(new Supplier<Integer>(){
			AtomicInteger ai = new AtomicInteger(1);
			public Integer get() {
				return ai.getAndIncrement();
			}
		}).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.print("��һ��Ԫ��ȫΪ1��int��������ʽ1����������limit()��õ���int���������ǣ�");
		Stream.generate(() -> new AtomicInteger(1).getAndIncrement()).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.print("��һ��Ԫ��ȫΪ1��int��������ʽ2����������limit()��õ���int���������ǣ�");
		Stream.generate(new Supplier<Integer>() {
			public Integer get() {
				return new AtomicInteger(1).getAndIncrement();
			}
		}).limit(10).forEach(num -> System.out.print(num + " ")); //limit()
		System.out.println();
		
		System.out.println("��һ��String������peek()���в鿴�Ľ���ǣ�");
		strList.stream().filter(str -> str.length() > 8)
			.peek(e -> System.out.println("filtered value: " + e)) //peek()�����ڲ鿴���е����ݻ��߶��������ݽ���һ���������ʵĲ���
			.map(String::toUpperCase)
			.peek(e -> System.out.println("mapped value: " + e))
			.collect(ArrayList<String>::new, ArrayList<String>::add, ArrayList<String>::addAll);
		
		System.out.print("��һ����1��ʼ����1Ϊ�������е�����int������skip()�ٵ���limit()��õ���int���Ľ���ǣ�");
		Stream.iterate(1,  num -> num + 1).skip(5).limit(5).forEach(num -> System.out.print(num + " ")); //skip()
		System.out.println();
		
		System.out.print("��һ��String����������sorted()�����ֵ�˳��������ַ������������ǰ�����ַ����ǣ�");
		strList.stream().sorted(String::compareTo).limit(3).forEach(str -> System.out.print(str + " ")); //sorted()
		System.out.println();
		
		System.out.print("��һ��String����������sorted()�����ֵ�˳��������ַ������������ǰ�����ַ����ǣ�");
		strList.parallelStream().sorted(String::compareTo).limit(3).forEach(str -> System.out.print(str + " ")); //sorted()
		System.out.println();
		
		System.out.print("��һ��Student����������sorted()����ѧ�Ŵ�С�����˳�������ѧ�����������ǰ����ѧ���ǣ�");
		Arrays.stream(stuArray).sorted((stu1, stu2) -> stu1.compareTo(stu2)).limit(3).forEach(stu -> System.out.print(stu.toString() + " ")); //sorted()
		System.out.println();
		
		System.out.print("��һ��String����������sorted()���ճ��ȴӳ����̵�˳��������ַ������������ǰ�����ַ����ǣ�");
		strList.stream().sorted(Comparator.comparing(String::length).reversed()).limit(3).forEach(str -> System.out.print(str + " ")); //sorted()
		System.out.println();
		
		System.out.print("��һ��Student����������sorted()��������Ӵ�С�ķ�ʽ������ѧ�����������ǰ����ѧ���ǣ�");
		Arrays.stream(stuArray).sorted(Comparator.comparing(Student::getSage).reversed()).limit(3).forEach(stu -> System.out.print(stu.toString() + " ")); //sorted()
		System.out.println();
		
		//8.��������ת������toArray()�ȣ�
		System.out.println("\n8.��������ת������toArray()�ȣ���");
		int[] numArray = Arrays.stream(intArray).toArray();
		System.out.println("��һ��int������toArray()����ת����int��������������ǣ�" + Arrays.toString(numArray));
		
//		int[] numArray2 = IntStream.iterate(1, num -> num += 1).toArray(); //�������޷�ת��Ϊ��������
//		System.out.println("��һ��int������������toArray()����ת����int��������������ǣ�" + Arrays.toString(numArray2));
		
		String[] restStrs1 = strList.stream().filter(str -> str.length() > 8).toArray(String[]::new); //String�������ɷ�ʽ1���ȼ���String�������ɷ�ʽ2
		System.out.println("��һ��String������filter()����һ���ɳ��ȴ���8���ַ������ɵ�����������ת�����ַ�����������������ǣ���ʽ1����" + Arrays.toString(restStrs1));
		
		String[] restStrs2 = strList.stream().filter(str -> str.length() > 8).toArray(size -> new String[size]); //String�������ɷ�ʽ2
		System.out.println("��һ��String������filter()����һ���ɳ��ȴ���8���ַ������ɵ�����������ת�����ַ�����������������ǣ���ʽ2����" + Arrays.toString(restStrs2));
		
		Student[] restStus1 = Arrays.stream(stuArray).filter(stu -> stu.getSage() == 22).toArray(Student[]::new); //Student�������ɷ�ʽ1���ȼ���Student�������ɷ�ʽ2
		System.out.println("��һ��Student������filter()����һ�����������22��ѧ�����ɵ�����������ת����Student��������������ǣ���ʽ1����" + Arrays.toString(restStus1));
		
		Student[] restStus2 = Arrays.stream(stuArray).filter(stu -> stu.getSage() == 22).toArray((size) -> {return new Student[size];}); //Student�������ɷ�ʽ2
		System.out.println("��һ��Student������filter()����һ�����������22��ѧ�����ɵ�����������ת����Student��������������ǣ���ʽ2����" + Arrays.toString(restStus2));
		
		//9.���رգ���close()��
		System.out.println("\n9.���رգ���close()����");
		strStream = strList.stream().distinct();
		System.out.println("������һ���µ��ַ�����strStream");
//		strStream.close(); //���������ر�strStream���������strStream����sorted()�����������ᴥ��java.lang.IllegalStateException�쳣
		System.out.print("�ַ�����strStream�������ǣ�");
		strStream.sorted(String::compareTo).forEach(str -> System.out.print(str + " "));
		System.out.println();
		System.out.println("�ر��ַ�����strStream");
		strStream.close();
		
		System.out.print("ʹ��try-with-resources����������Զ��ر�����"); //��һ�ָ��õĹر����ķ�ʽ
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