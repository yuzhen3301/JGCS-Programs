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

//MyComparatorOnName<T extends Student>��������������Student����s1��s2���бȽϣ�
//������ĸ˳����бȽϣ����s1.name < s2.name������-1�����������ȣ�����0�����s1.name > s2.name������1
class MyComparatorOnName<T extends Student> implements Comparator<T> {
	@Override
	public int compare(T o1, T o2) {
		return o1.getSname().compareTo(o2.getSname());
	}
}

public class App8_2 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD��������ѧԺ
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD���������赸ѧԺ
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH����ѧѧԺ
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI���˹�����ѧԺ
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS�������ѧԺ
		
		Student[] students = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		
		//����һ����ͨ��ϣ����hset
		HashSet<Student> hset = new HashSet<>();
		for(int i = 0; i < students.length; i++) {
			hset.add(students[i]);
		}
		System.out.println("hset�ĵ�һ�������" + hset + "\n");
		
		//����һ�����ι�ϣ����tset_natural��ʹ����Ȼ˳���Ԫ�ؽ�������Ҫ��Ԫ������Eʵ��Comparable<T>�ӿ�
		TreeSet<Student> tset_natural = new TreeSet<>();
		for(int i = 0; i < students.length; i++) {
			tset_natural.add(students[i]);
		}
		System.out.println("tset_natural�ĵ�һ�������" + hset + "\n");
		
		//����һ�������ϣ����sset_cmptor��ʹ����Ƚ���MyComparatorOnName<Student>����ȷ����˳���Ԫ�ؽ�������
		SortedSet<Student> sset_cmptor = new TreeSet<>(new MyComparatorOnName<Student>());
		for(int i = 0; i < students.length; i++) {
			sset_cmptor.add(students[i]);
		}
		System.out.println("sset_cmptor�ĵ�һ�������" + sset_cmptor + "\n");
		
		//����һ��������ϣ����nset_with_hset��ʹ����ͨ��ϣ����hset������õ������ϣ��½��ĵ������Ͻ�ʹ����Ȼ˳��Դ�hset���ƹ�����Ԫ�ؽ�������
		NavigableSet<Student> nset_with_hset = new TreeSet<>(hset);
		System.out.println("nset_with_hset�ĵ�һ�������" + nset_with_hset + "\n");
		
		//����SortedSet<E>�еķ���
		System.out.println("sset_cmptor.first()��" + sset_cmptor.first());
		System.out.println("sset_cmptor.contains(stuSophia)��" + sset_cmptor.contains(stuSophia));
		System.out.println("sset_cmptor.subSet(stuCathy, stuJason)��" + sset_cmptor.subSet(stuCathy, stuJason));
		System.out.println("((NavigableSet<Student>)sset_cmptor).subSet(stuCathy, stuJason)��" + ((NavigableSet<Student>)sset_cmptor).subSet(stuCathy, false, stuJason, true));
		System.out.println("sset_cmptor.tailSet(stuIsabella)��" + sset_cmptor.tailSet(stuIsabella) + "\n");
		
		//����NavigableSet<E>�еķ���
		NavigableSet<Student> nset = (NavigableSet<Student>)sset_cmptor;
		Student student = nset.ceiling(stuCathy);
		System.out.println("nset.ceiling(stuCathy)��" + student);
		System.out.println("nset.higher(stuCathy)��" + nset.higher(stuCathy));
		System.out.println("nset.floor(stuCathy)��" + nset.floor(stuCathy));
		System.out.println("nset.lower(stuCathy)��" + nset.lower(stuCathy));
		
		//����TreeSet<E>�еķ�����ʵ������Щ����Ҳ��NavigableMap<E>��Map<E>�еķ���
		System.out.println("tset_natural.descendingSet()��" + tset_natural.descendingSet());
		
		System.out.print("tset_natural.descendingIterator()��");
		tset_natural.descendingIterator().forEachRemaining(e -> {System.out.print(e + " ");});
		System.out.println();
		
		System.out.println("tset_natural.pollFirst()��" + tset_natural.pollFirst());
		System.out.println("tset_natural.pollLast()��" + tset_natural.pollLast());
		System.out.println("tset_natural.remove(stuCathy)��" + tset_natural.remove(stuCathy));
	}
}