package com.jgcs.chp7.p4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

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

//MyComparatorOnName<T extends Student>按照姓名对两个Student对象s1和s2进行比较，
//按照字母顺序进行比较，如果s1.name < s2.name，返回-1，如果两者相等，返回0，如果s1.name > s2.name，返回1
class MyComparatorOnName<T extends Student> implements Comparator<T> {
	@Override
	public int compare(T o1, T o2) {
		return o1.getSname().compareTo(o2.getSname());
	}
}

public class App7_4 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD：大数据学院
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD：音乐与舞蹈学院
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH：数学学院
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI：人工智能学院
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS：计算机学院
		
		Student[] students = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		String[] universities = {"哈尔滨工业大学", "哈尔滨工程大学", "东北林业大学", "黑龙江大学", "哈尔滨师范大学"};
		
		//创建一个普通哈希映射hmap
		HashMap<Student, String> hmap = new HashMap<>();
		for(int i = 0; i < students.length; i++) {
			hmap.put(students[i], universities[i]);
		}
		System.out.println("hmap的第一次输出：" + hmap + "\n");
		
		//创建一个树形哈希映射tmap_natural，使用键上的自然顺序对键值对进行排序，要求键实现Comparable<T>接口
		TreeMap<Student, String> tmap_natural = new TreeMap<>(); //natural ordering
		for(int i = 0; i < students.length; i++) {
			tmap_natural.put(students[i], universities[i]);
		}
		System.out.println("tmap_natural的第一次输出：" + tmap_natural + "\n");
		
		//创建一个有序哈希映射smap_cmptor，使用外比较器MyComparatorOnName<Student>对象在键上确定的顺序对键值对进行排序
		SortedMap<Student, String> smap_cmptor = new TreeMap<>(new MyComparatorOnName<Student>());
		for(int i = 0; i < students.length; i++) {
			smap_cmptor.put(students[i], universities[i]);
		}
		System.out.println("smap_cmptor的第一次输出：" + smap_cmptor + "\n");
		
		//创建一个导航哈希映射nmap_with_hmap，使用普通哈希映射hmap来构造该导航映射，新建的导航映射将使用键上的自然顺序对从hmap复制过来的键值对进行排序
		NavigableMap<Student, String> nmap_with_hmap = new TreeMap<>(hmap);
		System.out.println("nmap_with_hmap的第一次输出：" + nmap_with_hmap + "\n");
		
		//调用SortedMap<K, V>中的方法
		System.out.println("smap_cmptor.firstKey()：" + smap_cmptor.firstKey());
		System.out.println("smap_cmptor.containsKey(stuSophia)：" + smap_cmptor.containsKey(stuSophia));
		System.out.println("smap_cmptor.subMap(stuCathy, stuJason)：" + smap_cmptor.subMap(stuCathy, stuJason));
		System.out.println("((NavigableMap<Student, String>)smap_cmptor).subMap(stuCathy, stuJason)：" + ((NavigableMap<Student, String>)smap_cmptor).subMap(stuCathy, false, stuJason, true));
		System.out.println("smap_cmptor.tailMap(stuIsabella)：" + smap_cmptor.tailMap(stuIsabella) + "\n");
		
		//调用NavigableMap<K, V>中的方法
		NavigableMap<Student, String> nmap = (NavigableMap<Student, String>)smap_cmptor;
		Map.Entry<Student, String> entry = nmap.ceilingEntry(stuCathy);
		System.out.println("nmap.ceilingEntry(stuCathy)：" + entry);
		System.out.println("nmap.higherEntry(stuCathy)：" + nmap.higherEntry(stuCathy));
		System.out.println("nmap.floorEntry(stuCathy)：" + nmap.floorEntry(stuCathy));
		System.out.println("nmap.lowerEntry(stuCathy)：" + nmap.lowerEntry(stuCathy));
		
		Student stu = nmap.ceilingKey(stuIsabella);
		System.out.println("nmap.ceilingKey(stuIsabella)：" + stu);
		System.out.println("nmap.higherKey(stuIsabella)：" + nmap.higherKey(stuIsabella));
		System.out.println("nmap.floorKey(stuIsabella)：" + nmap.floorKey(stuIsabella));
		System.out.println("nmap.lowerKey(stuIsabella)：" + nmap.lowerKey(stuIsabella) + "\n");
		
		//调用TreeMap<K, V>中的方法，实际上这些方法也是NavigableMap<K, V>和Map<K, V>中的方法
		System.out.println("tmap_natural.descendingMap()：" + tmap_natural.descendingMap());
		System.out.println("tmap_natural.descendingKey()：" + tmap_natural.descendingKeySet());
		System.out.println("tmap_natural.pollFirstEntry()：" + tmap_natural.pollFirstEntry());
		System.out.println("tmap_natural.pollLastEntry()：" + tmap_natural.pollLastEntry());
		System.out.println("tmap_natural.remove(stuCathy)：" + tmap_natural.remove(stuCathy));
	}
}