package com.jgcs.chp8.p2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

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

public class App8_2 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD：大数据学院
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD：音乐与舞蹈学院
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH：数学学院
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI：人工智能学院
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS：计算机学院
		
		Student[] students = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		
		//创建一个普通哈希集合hset
		HashSet<Student> hset = new HashSet<>();
		for(int i = 0; i < students.length; i++) {
			hset.add(students[i]);
		}
		System.out.println("hset的第一次输出：" + hset + "\n");
		
		//创建一个树形哈希集合tset_natural，使用自然顺序对元素进行排序，要求元素类型E实现Comparable<T>接口
		TreeSet<Student> tset_natural = new TreeSet<>();
		for(int i = 0; i < students.length; i++) {
			tset_natural.add(students[i]);
		}
		System.out.println("tset_natural的第一次输出：" + hset + "\n");
		
		//创建一个有序哈希集合sset_cmptor，使用外比较器MyComparatorOnName<Student>对象确定的顺序对元素进行排序
		SortedSet<Student> sset_cmptor = new TreeSet<>(new MyComparatorOnName<Student>());
		for(int i = 0; i < students.length; i++) {
			sset_cmptor.add(students[i]);
		}
		System.out.println("sset_cmptor的第一次输出：" + sset_cmptor + "\n");
		
		//创建一个导航哈希集合nset_with_hset，使用普通哈希集合hset来构造该导航集合，新建的导航集合将使用自然顺序对从hset复制过来的元素进行排序
		NavigableSet<Student> nset_with_hset = new TreeSet<>(hset);
		System.out.println("nset_with_hset的第一次输出：" + nset_with_hset + "\n");
		
		//调用SortedSet<E>中的方法
		System.out.println("sset_cmptor.first()：" + sset_cmptor.first());
		System.out.println("sset_cmptor.contains(stuSophia)：" + sset_cmptor.contains(stuSophia));
		System.out.println("sset_cmptor.subSet(stuCathy, stuJason)：" + sset_cmptor.subSet(stuCathy, stuJason));
		System.out.println("((NavigableSet<Student>)sset_cmptor).subSet(stuCathy, stuJason)：" + ((NavigableSet<Student>)sset_cmptor).subSet(stuCathy, false, stuJason, true));
		System.out.println("sset_cmptor.tailSet(stuIsabella)：" + sset_cmptor.tailSet(stuIsabella) + "\n");
		
		//调用NavigableSet<E>中的方法
		NavigableSet<Student> nset = (NavigableSet<Student>)sset_cmptor;
		Student student = nset.ceiling(stuCathy);
		System.out.println("nset.ceiling(stuCathy)：" + student);
		System.out.println("nset.higher(stuCathy)：" + nset.higher(stuCathy));
		System.out.println("nset.floor(stuCathy)：" + nset.floor(stuCathy));
		System.out.println("nset.lower(stuCathy)：" + nset.lower(stuCathy));
		
		//调用TreeSet<E>中的方法，实际上这些方法也是NavigableMap<E>和Map<E>中的方法
		System.out.println("tset_natural.descendingSet()：" + tset_natural.descendingSet());
		
		System.out.print("tset_natural.descendingIterator()：");
		tset_natural.descendingIterator().forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		
		System.out.println("tset_natural.pollFirst()：" + tset_natural.pollFirst());
		System.out.println("tset_natural.pollLast()：" + tset_natural.pollLast());
		System.out.println("tset_natural.remove(stuCathy)：" + tset_natural.remove(stuCathy));
	}
}