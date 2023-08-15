package com.jgcs.chp9.p5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

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

class ListRemover implements Runnable{ //�б�ɾ���̶߳�Ӧ�Ŀ�ִ������
	List<Student> stuLs = null;
	
	public ListRemover(List<Student> stuLs) {
		this.stuLs = stuLs;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < stuLs.size();) {
			System.out.println("��ɾ����Ԫ���ǣ�" + stuLs.remove(i));
			
			try {
				Thread.sleep(new Random().nextInt(100)); //�������
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ListTraverser implements Runnable{ //�б�����̶߳�Ӧ�Ŀ�ִ������
	List<Student> stuLs = null;
	
	public ListTraverser(List<Student> stuLs) {
		this.stuLs = stuLs;
	}
	
	@Override
	public void run() {
		synchronized(stuLs) {
			Iterator<Student> stuItr = stuLs.iterator(); //��stuLs��iterator()�ĵ��ñ�����stuLs���ı����½���
			while(stuItr.hasNext()) {
				try {
					Thread.sleep(new Random().nextInt(1000)); //�������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println(stuItr.next());
			}
		}
	}
}

public class App9_5 {
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); //BD��������ѧԺ
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); //MD���������赸ѧԺ
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); //MATH����ѧѧԺ
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); //AI���˹�����ѧԺ
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); //CS�������ѧԺ
		Student stuKevin = new Student("09006", "Kevin", 'M', 21, "PD"); //PD������ѧԺ
		
		Student[] students = {stuIsabella, stuCathy, stuSophia, stuJason, stuAndrew}; //˳��Ϊ09002 -> 09003 -> 09005 -> 09001 -> 09004
		
		//����Collections.addAll()��Collections.sort()��Collections.binarySearch()��ʹ����ʾ
		List<Student> stuLs = new ArrayList<Student>();
		Collections.addAll(stuLs, students); //���stuLs
		System.out.println("������������ǰ��stuLs�ǣ�" + stuLs.toString());
		Collections.sort(stuLs); //��stuLs����Collections.binarySearch()���ж��ֲ���ǰ������Ҫ������������
		System.out.println("��������������stuLs�ǣ�" + stuLs.toString());
		int elmIdx = Collections.binarySearch(stuLs, stuAndrew);
		if(elmIdx >= 0) {
			System.out.println("stuAndrew��stuLs�е��±��ǣ�" + elmIdx);
		}
		
		//����Collections.max()��Collections.frequency()��ʹ����ʾ
		Student stuMaxOnNo = Collections.max(stuLs);
		Student stuMaxOnAge = Collections.max(stuLs, (stu1, stu2) -> {return stu1.getSage() - stu2.getSage();}); //��������бȽ�
		System.out.println("stuLs��ѧ������ѧ���ǣ�" + stuMaxOnNo);
		System.out.println("stuLs����������ѧ���ǣ�" + stuMaxOnAge);
		System.out.println("stuMaxOnAge��Ӧ��ѧ����stuLs�еĳ��ִ����ǣ�" + Collections.frequency(stuLs, stuMaxOnAge));
		
		//����Collections.fill()��Collections.copy()��Collections.rotate()��Collections.reverse()��Collections.shuffle()��
		//Collections.reverseOrder()��Collections.swap()��ʹ����ʾ
		List<Student> stuLsCopy = new ArrayList<Student>(stuLs.size()); //����һ����ʼ����ΪstuLs.size()��
		stuLsCopy.addAll(stuLs); //���ע�͵����У���Collections.copy()�ᴥ��IndexOutOfBoundsException�쳣����ΪstuLsCopyʵ��Ԫ�ظ���Ϊ0
		Collections.fill(stuLsCopy, null);
		System.out.println("���������stuLsCopy�ǣ�" + stuLsCopy);
		Collections.copy(stuLsCopy, stuLs);
		System.out.println("���п������stuLsCopy�ǣ�" + stuLsCopy);
		Collections.rotate(stuLsCopy, 3);
		System.out.println("����3�����ƺ��stuLsCopy�ǣ�" + stuLsCopy);
		Collections.reverse(stuLsCopy);
		System.out.println("���з�����stuLsCopy�ǣ�" + stuLsCopy);
		Collections.shuffle(stuLsCopy, new Random(123456789));
		System.out.println("������ϴ���stuLsCopy�ǣ�" + stuLsCopy);
		Collections.sort(stuLsCopy, Collections.reverseOrder());
		System.out.println("����ѧ�ŷ���������stuLsCopy�ǣ�" + stuLsCopy);
		Collections.sort(stuLsCopy, Collections.reverseOrder( (stu1, stu2) -> {return stu1.getSage() - stu2.getSage();} ));
		System.out.println("�������䷴��������stuLsCopy�ǣ�" + stuLsCopy);
		Collections.swap(stuLsCopy, 0, stuLsCopy.size()-1);
		System.out.println("����βԪ�ضԻ����stuLsCopy�ǣ�" + stuLsCopy);
		
		//����Collections.repalceAll()��Collections.singletonList()��Collections.indexOfSubList()��
		//Collections.lastIndexOfSubList()��Collections.nCopies()��Collections.disjoint()��ʹ����ʾ
		Collections.sort(stuLsCopy); //��stuLsCopy����ѧ�Ŵ�С��������
		Collections.replaceAll(stuLsCopy, stuIsabella, stuCathy);
		Collections.replaceAll(stuLsCopy, stuAndrew, stuCathy);
		System.out.println("����2���滻���stuLsCopy�ǣ�" + stuLsCopy);
		
		List<Student> singletonLsOfCathy = Collections.singletonList(stuCathy);
		List<Student> stuLsOfCathy = Collections.nCopies(2, stuCathy);
		//stuLsOfCathy.set(stuLsOfCathy.size()-1, stuAndrew); //����UnsupportedOperationException�쳣��stuLsOfCathy��֧��set����
		//stuLsOfCathy.add(stuJason); //����UnsupportedOperationException�쳣, stuLsOfCathy��֧��add����
		int subLsIdx = Collections.indexOfSubList(stuLsCopy, singletonLsOfCathy);
		System.out.println("�б�singletonLsOfCathy��stuLsCopy�е�һ�γ���ʱ��λ���ǣ�" + subLsIdx);
		subLsIdx = Collections.lastIndexOfSubList(stuLsCopy, stuLsOfCathy);
		System.out.println("�б�stuLsOfCathy��stuLsCopy�����һ�γ���ʱ��λ���ǣ�" + subLsIdx);
		System.out.println("�б�stuLsCopy��stuLs�Ƿ��ཻ�����Ƿ������ͬԪ�أ���" + !Collections.disjoint(stuLsCopy, stuLs));
		
		//����Collections.emptyList()��Collections.emptyIterator()��Collections.emptyListIterator()��
		//Collections.newSetFromMap()��Collections.unmodifiableList()��Collections.checkedList()��ʹ����ʾ
		List<Student> empLs = Collections.emptyList();
		Iterator<Student> empItr = Collections.emptyIterator();
		ListIterator<Student> empLsItr = Collections.emptyListIterator();
		System.out.println("empLs��Ԫ�ظ����ǣ�" + empLs.size());
		
		System.out.print("ʹ��empItr��������Ԫ���ǣ�[");
		empItr.forEachRemaining(e -> System.out.print(e));
		System.out.println("]");
		
		System.out.print("ʹ��empLsItr��������Ԫ���ǣ�[");
		empLsItr.forEachRemaining(e -> System.out.print(e));
		System.out.println("]");
		
		//Collections.newSetFromMap()�Ĳ��������ǿյ�Map<E, Boolean>����
		Set<Student> stuSet = Collections.newSetFromMap(new HashMap<Student, Boolean>());
		stuSet.add(stuJason);
		stuSet.add(stuSophia);
		stuSet.add(stuKevin);
		System.out.println("stuSet�ǣ�" + stuSet);
		
		//������δ���ᴥ��IllegalArgumentException�쳣����Ϊ���ݸ�Collections.newSetFromMap()�Ĳ�������һ����Map<E, Boolean>����
		//Map<Student, Boolean> stuHITMap = new HashMap<>(); //HIT��ѧ��ѧ��
		//for(int i = 0; i < students.length; i++) {
		//	stuHITMap.put(students[i], new Random().nextBoolean());
		//}
		//Set<Student> stuHITSet = Collections.newSetFromMap(stuHITMap);
		//System.out.println("stuHITSet�ǣ�" + stuHITSet);
		
		List<Student> unmodStuLs = Collections.unmodifiableList(stuLs);
		try { unmodStuLs.add(stuKevin);
		} catch(UnsupportedOperationException ex) {
			System.out.println("�޷���unmodStuLs����add����");
		}
		
		List<Student> chkdStuLs = Collections.checkedList(stuLs, Student.class);
		List rawLs = chkdStuLs; //��chkdStuLs��ֵ��ԭʼ�������ñ���rawLs
		try {
			rawLs.add("Michael"); //��rawLs�����һ��String����"Michael"
		} catch(ClassCastException ex) {
			System.out.println("�޷���Ԫ������ΪStudent���б�chkdStuLs�в���String���͵Ķ���");
		}
		
		//����Collections.asLifoQueue()��ʹ����ʾ
		Deque<Student> stuDeque = new LinkedList<>();
		Collections.addAll(stuDeque, students);
		//Collections.sort((List<Student>)stuDeque);
		System.out.println("���������stuDeque�ǣ�" + stuDeque);
		Queue<Student> stuQue = Collections.asLifoQueue(stuDeque); //��˫�˶���stuDequeת��Ϊ���˶���stuQue��ʵ���ϱ�Ϊһ������ȳ���ջ�ṹ
		stuQue.remove(); //�ڶ�ͷ����ɾ������stuQue�Ĳ�������д͸��write through����stuDeque��
		stuQue.add(stuKevin); //�ڶ�ͷ������ӣ���stuQue�Ĳ�������д͸��stuDeque��
		System.out.println("�����޸ĺ��stuDeque�ǣ�" + stuDeque);
		
		
		//�������д����stuLs�Ĳ��������ᴥ��ConcurrentModificationException�쳣����ΪstuLs�����̰߳�ȫ��
		new Thread(new ListTraverser(stuLs)).start();
		new Thread(new ListRemover(stuLs)).start();
		
		//����Collections.synchronizedList()��ʹ����ʾ
		List<Student> syncStuLs = Collections.synchronizedList(stuLs);
		Thread t1, t2;
		(t1 = new Thread(new ListTraverser(syncStuLs))).start();
		(t2 = new Thread(new ListRemover(syncStuLs))).start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("���������");
	}
}