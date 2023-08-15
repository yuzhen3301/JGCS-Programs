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
	public boolean equals(Object obj) { //�����������бȽϣ��ڷ���trueʱҪ��compareTo()�������ϱ���һ��
		if(obj instanceof Student) {
			Student stu = (Student)obj;
			return this.sname.equals(stu.getSname()) ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Student stu) { //�����������бȽϣ��ڷ���0ʱҪ��equals()�������ϱ���һ��
		return sname.compareTo(stu.getSname());
	}
}

//MyComparatorOnAge<T extends Student>�������������Student������бȽ�
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

//MyFunction��ʾһ������Student����Ϊ����������String����ĺ���
class MyFunction implements Function<Student, String> {
	@Override
	public String apply(Student t) {
		return t.getSdept();
	}
}

public class App5_1 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD��������ѧԺ
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD���������赸ѧԺ
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH����ѧѧԺ
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI���˹�����ѧԺ
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS�������ѧԺ

		Student[] stuArr = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		System.out.println("stuArr�ĵ�һ���������ѧ�����򣩣�");
		printArray(stuArr);
		System.out.println();

		System.out.println("stuArr�ĵڶ��������ʹ��Arrays.sort��stuArr���������򣩣�");
		Arrays.sort(stuArr);
		printArray(stuArr);
		System.out.println();

		System.out.println("stuArr�ĵ����������ʹ��Arrays.sort��MyComparatorOnAge<Student>�����stuArr���������򣩣�");
		Arrays.sort(stuArr, new MyComparatorOnAge<Student>());
		printArray(stuArr);
		System.out.println();

		System.out.println("stuArr�ĵ��Ĵ������ʹ��Arrays.sort��Comparator.comparing(MyFunction����)������Comparator<Student>�����stuArr��ѧԺ���򣩣�");
		Arrays.sort(stuArr, Comparator.comparing(new MyFunction()));
		printArray(stuArr);
		System.out.println();
		
		System.out.println("stuArr�ĵ���������ʹ��MyComparatorOnAge<Student>�����thenComparing()������Comparator.comparing(MyFunction����)������Comparator<Student>�����stuArr�Ȱ������ٰ�ѧԺ���򣩣�");
		Arrays.sort(stuArr, new MyComparatorOnAge<Student>().thenComparing(Comparator.comparing(new MyFunction())));
		printArray(stuArr);
		System.out.println();

		List<Student> stuList = new ArrayList<>();
		stuList.add(stuJason);
		stuList.add(stuIsabella);
		stuList.add(stuCathy);
		stuList.add(stuAndrew);
		stuList.add(stuSophia);

		System.out.println("stuList�ĵ�һ���������ѧ�����򣩣�");
		printList(stuList);
		System.out.println();

		System.out.println("stuList�ĵڶ��������ʹ��Collections.sort��stuList���������򣩣�");
		Collections.sort(stuList);
		printList(stuList);
		System.out.println();

		System.out.println("stuList�ĵ����������ʹ��Collections.sort��MyComparatorOnAge<Student>�����stuList���������򣩣�");
		Collections.sort(stuList, new MyComparatorOnAge<Student>());
		printList(stuList);
		System.out.println();

		System.out.println("stuList�ĵ��Ĵ������ʹ��Collections.sort��Comparator.comparing(MyFunction����)������Comparator<Student>�����stuList��ѧԺ���򣩣�");
		Collections.sort(stuList, Comparator.comparing(new MyFunction()));
		printList(stuList);
		System.out.println();
		
		System.out.println("stuList�ĵ���������ʹ��MyComparatorOnAge<Student>�����thenComparing()������Comparator.comparing(MyFunction����)������Comparator<Student>�����stuList�Ȱ������ٰ�ѧԺ���򣩣�");
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