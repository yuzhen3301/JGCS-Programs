package com.jgcs.chp5.p1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * [Student(sno=09001, sname=Jason, ssex=M, sage=21, sdept=BD),
 * Student(sno=09002, sname=Isabella, ssex=F, sage=20, sdept=MD),
 * Student(sno=09003, sname=Cathy, ssex=F, sage=19, sdept=MATH),
 * Student(sno=09004, sname=Andrew, ssex=M, sage=22, sdept=AI),
 * Student(sno=09005, sname=Sophia, ssex=F, sage=20, sdept=CS)]
 */
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
		return sname.hashCode() >>> 1;
	}

	@Override
	public boolean equals(Object obj) { //根据姓名进行比较，在返回true时要与compareTo()在语义上保持一致
		if(obj instanceof Student) {
			Student stu = (Student)obj;
			return this.sname.equals(stu.getSname()) ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Student stu) { //根据姓名进行比较，在返回0时要与equals()在语义上保持一致
		return sname.compareTo(stu.getSname());
	}
}

//MyComparatorOnAge<T extends Student>按照年龄对两个Student对象进行比较
class MyComparatorOnAge<T extends Student> implements Comparator<T> {
	@Override
	public int compare(T o1, T o2) {
		int age1 = o1.getSage();
		int age2 = o2.getSage();
		if (age1 == age2) {
			return 0;
		} else if (age1 > age2) {
			return 1;
		} else {
			return -1;
		}
	}
}

//MyFunction表示一个接受Student对象为参数并返回String对象的函数
class MyFunction implements Function<Student, String> {
	@Override
	public String apply(Student t) {
		return t.getSdept();
	}
}

public class App5_1 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD：大数据学院
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD：音乐与舞蹈学院
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH：数学学院
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI：人工智能学院
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS：计算机学院

		Student[] stuArr = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		System.out.println("stuArr的第一次输出（按学号排序）：");
		printArray(stuArr);
		System.out.println();

		System.out.println("stuArr的第二次输出（使用Arrays.sort对stuArr按姓名排序）：");
		Arrays.sort(stuArr);
		printArray(stuArr);
		System.out.println();

		System.out.println("stuArr的第三次输出（使用Arrays.sort和MyComparatorOnAge<Student>对象对stuArr按年龄排序）：");
		Arrays.sort(stuArr, new MyComparatorOnAge<Student>());
		printArray(stuArr);
		System.out.println();

		System.out.println("stuArr的第四次输出（使用Arrays.sort和Comparator.comparing(MyFunction对象)产生的Comparator<Student>对象对stuArr按学院排序）：");
		Arrays.sort(stuArr, Comparator.comparing(new MyFunction()));
		printArray(stuArr);
		System.out.println();
		
		System.out.println("stuArr的第五次输出（使用MyComparatorOnAge<Student>对象的thenComparing()方法和Comparator.comparing(MyFunction对象)产生的Comparator<Student>对象对stuArr先按年龄再按学院排序）：");
		Arrays.sort(stuArr, new MyComparatorOnAge<Student>().thenComparing(Comparator.comparing(new MyFunction())));
		printArray(stuArr);
		System.out.println();

		List<Student> stuList = new ArrayList<>();
		stuList.add(stuJason);
		stuList.add(stuIsabella);
		stuList.add(stuCathy);
		stuList.add(stuAndrew);
		stuList.add(stuSophia);

		System.out.println("stuList的第一次输出（按学号排序）：");
		printList(stuList);
		System.out.println();

		System.out.println("stuList的第二次输出（使用Collections.sort对stuList按姓名排序）：");
		Collections.sort(stuList);
		printList(stuList);
		System.out.println();

		System.out.println("stuList的第三次输出（使用Collections.sort和MyComparatorOnAge<Student>对象对stuList按年龄排序）：");
		Collections.sort(stuList, new MyComparatorOnAge<Student>());
		printList(stuList);
		System.out.println();

		System.out.println("stuList的第四次输出（使用Collections.sort和Comparator.comparing(MyFunction对象)产生的Comparator<Student>对象对stuList按学院排序）：");
		Collections.sort(stuList, Comparator.comparing(new MyFunction()));
		printList(stuList);
		System.out.println();
		
		System.out.println("stuList的第五次输出（使用MyComparatorOnAge<Student>对象的thenComparing()方法和Comparator.comparing(MyFunction对象)产生的Comparator<Student>对象对stuList先按年龄再按学院排序）：");
		Collections.sort(stuList, new MyComparatorOnAge<Student>().thenComparing(Comparator.comparing(new MyFunction())));
		printList(stuList);
		System.out.println();
	}

	static <T> void printArray(T[] arr) {
		System.out.print("[");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + (i == arr.length - 1 ? "" : "\n"));
		}
		System.out.print("]\n");
	}

	static <T> void printList(List<T> list) {
		System.out.print("[");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + (i == list.size() - 1 ? "" : "\n"));
		}
		System.out.print("]\n");
	}
}