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

public class App7_4 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD��������ѧԺ
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD���������赸ѧԺ
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH����ѧѧԺ
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI���˹�����ѧԺ
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS�������ѧԺ
		
		Student[] students = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		String[] universities = {"��������ҵ��ѧ", "���������̴�ѧ", "������ҵ��ѧ", "��������ѧ", "������ʦ����ѧ"};
		
		//����һ����ͨ��ϣӳ��hmap
		HashMap<Student, String> hmap = new HashMap<>();
		for(int i = 0; i < students.length; i++) {
			hmap.put(students[i], universities[i]);
		}
		System.out.println("hmap�ĵ�һ�������" + hmap + "\n");
		
		//����һ�����ι�ϣӳ��tmap_natural��ʹ�ü��ϵ���Ȼ˳��Լ�ֵ�Խ�������Ҫ���ʵ��Comparable<T>�ӿ�
		TreeMap<Student, String> tmap_natural = new TreeMap<>(); //natural ordering
		for(int i = 0; i < students.length; i++) {
			tmap_natural.put(students[i], universities[i]);
		}
		System.out.println("tmap_natural�ĵ�һ�������" + tmap_natural + "\n");
		
		//����һ�������ϣӳ��smap_cmptor��ʹ����Ƚ���MyComparatorOnName<Student>�����ڼ���ȷ����˳��Լ�ֵ�Խ�������
		SortedMap<Student, String> smap_cmptor = new TreeMap<>(new MyComparatorOnName<Student>());
		for(int i = 0; i < students.length; i++) {
			smap_cmptor.put(students[i], universities[i]);
		}
		System.out.println("smap_cmptor�ĵ�һ�������" + smap_cmptor + "\n");
		
		//����һ��������ϣӳ��nmap_with_hmap��ʹ����ͨ��ϣӳ��hmap������õ���ӳ�䣬�½��ĵ���ӳ�佫ʹ�ü��ϵ���Ȼ˳��Դ�hmap���ƹ����ļ�ֵ�Խ�������
		NavigableMap<Student, String> nmap_with_hmap = new TreeMap<>(hmap);
		System.out.println("nmap_with_hmap�ĵ�һ�������" + nmap_with_hmap + "\n");
		
		//����SortedMap<K, V>�еķ���
		System.out.println("smap_cmptor.firstKey()��" + smap_cmptor.firstKey());
		System.out.println("smap_cmptor.containsKey(stuSophia)��" + smap_cmptor.containsKey(stuSophia));
		System.out.println("smap_cmptor.subMap(stuCathy, stuJason)��" + smap_cmptor.subMap(stuCathy, stuJason));
		System.out.println("((NavigableMap<Student, String>)smap_cmptor).subMap(stuCathy, stuJason)��" + ((NavigableMap<Student, String>)smap_cmptor).subMap(stuCathy, false, stuJason, true));
		System.out.println("smap_cmptor.tailMap(stuIsabella)��" + smap_cmptor.tailMap(stuIsabella) + "\n");
		
		//����NavigableMap<K, V>�еķ���
		NavigableMap<Student, String> nmap = (NavigableMap<Student, String>)smap_cmptor;
		Map.Entry<Student, String> entry = nmap.ceilingEntry(stuCathy);
		System.out.println("nmap.ceilingEntry(stuCathy)��" + entry);
		System.out.println("nmap.higherEntry(stuCathy)��" + nmap.higherEntry(stuCathy));
		System.out.println("nmap.floorEntry(stuCathy)��" + nmap.floorEntry(stuCathy));
		System.out.println("nmap.lowerEntry(stuCathy)��" + nmap.lowerEntry(stuCathy));
		
		Student stu = nmap.ceilingKey(stuIsabella);
		System.out.println("nmap.ceilingKey(stuIsabella)��" + stu);
		System.out.println("nmap.higherKey(stuIsabella)��" + nmap.higherKey(stuIsabella));
		System.out.println("nmap.floorKey(stuIsabella)��" + nmap.floorKey(stuIsabella));
		System.out.println("nmap.lowerKey(stuIsabella)��" + nmap.lowerKey(stuIsabella) + "\n");
		
		//����TreeMap<K, V>�еķ�����ʵ������Щ����Ҳ��NavigableMap<K, V>��Map<K, V>�еķ���
		System.out.println("tmap_natural.descendingMap()��" + tmap_natural.descendingMap());
		System.out.println("tmap_natural.descendingKey()��" + tmap_natural.descendingKeySet());
		System.out.println("tmap_natural.pollFirstEntry()��" + tmap_natural.pollFirstEntry());
		System.out.println("tmap_natural.pollLastEntry()��" + tmap_natural.pollLastEntry());
		System.out.println("tmap_natural.remove(stuCathy)��" + tmap_natural.remove(stuCathy));
	}
}