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

class ListRemover implements Runnable{ //列表删除线程对应的可执行体类
	List<Student> stuLs = null;
	
	public ListRemover(List<Student> stuLs) {
		this.stuLs = stuLs;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < stuLs.size();) {
			System.out.println("被删除的元素是：" + stuLs.remove(i));
			
			try {
				Thread.sleep(new Random().nextInt(100)); //随机休眠
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ListTraverser implements Runnable{ //列表遍历线程对应的可执行体类
	List<Student> stuLs = null;
	
	public ListTraverser(List<Student> stuLs) {
		this.stuLs = stuLs;
	}
	
	@Override
	public void run() {
		synchronized(stuLs) {
			Iterator<Student> stuItr = stuLs.iterator(); //对stuLs的iterator()的调用必须在stuLs锁的保护下进行
			while(stuItr.hasNext()) {
				try {
					Thread.sleep(new Random().nextInt(1000)); //随机休眠
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
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); //BD：大数据学院
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); //MD：音乐与舞蹈学院
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); //MATH：数学学院
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); //AI：人工智能学院
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); //CS：计算机学院
		Student stuKevin = new Student("09006", "Kevin", 'M', 21, "PD"); //PD：物理学院
		
		Student[] students = {stuIsabella, stuCathy, stuSophia, stuJason, stuAndrew}; //顺序为09002 -> 09003 -> 09005 -> 09001 -> 09004
		
		//关于Collections.addAll()、Collections.sort()和Collections.binarySearch()的使用演示
		List<Student> stuLs = new ArrayList<Student>();
		Collections.addAll(stuLs, students); //填充stuLs
		System.out.println("进行升序排序前的stuLs是：" + stuLs.toString());
		Collections.sort(stuLs); //对stuLs调用Collections.binarySearch()进行二分查找前必须先要进行升序排序
		System.out.println("进行升序排序后的stuLs是：" + stuLs.toString());
		int elmIdx = Collections.binarySearch(stuLs, stuAndrew);
		if(elmIdx >= 0) {
			System.out.println("stuAndrew在stuLs中的下标是：" + elmIdx);
		}
		
		//关于Collections.max()、Collections.frequency()的使用演示
		Student stuMaxOnNo = Collections.max(stuLs);
		Student stuMaxOnAge = Collections.max(stuLs, (stu1, stu2) -> {return stu1.getSage() - stu2.getSage();}); //按年龄进行比较
		System.out.println("stuLs中学号最大的学生是：" + stuMaxOnNo);
		System.out.println("stuLs中年龄最大的学生是：" + stuMaxOnAge);
		System.out.println("stuMaxOnAge对应的学生在stuLs中的出现次数是：" + Collections.frequency(stuLs, stuMaxOnAge));
		
		//关于Collections.fill()、Collections.copy()、Collections.rotate()、Collections.reverse()、Collections.shuffle()、
		//Collections.reverseOrder()和Collections.swap()的使用演示
		List<Student> stuLsCopy = new ArrayList<Student>(stuLs.size()); //创建一个初始容量为stuLs.size()的
		stuLsCopy.addAll(stuLs); //如果注释掉该行，则Collections.copy()会触发IndexOutOfBoundsException异常，因为stuLsCopy实际元素个数为0
		Collections.fill(stuLsCopy, null);
		System.out.println("进行填充后的stuLsCopy是：" + stuLsCopy);
		Collections.copy(stuLsCopy, stuLs);
		System.out.println("进行拷贝后的stuLsCopy是：" + stuLsCopy);
		Collections.rotate(stuLsCopy, 3);
		System.out.println("进行3次右移后的stuLsCopy是：" + stuLsCopy);
		Collections.reverse(stuLsCopy);
		System.out.println("进行反序后的stuLsCopy是：" + stuLsCopy);
		Collections.shuffle(stuLsCopy, new Random(123456789));
		System.out.println("进行重洗后的stuLsCopy是：" + stuLsCopy);
		Collections.sort(stuLsCopy, Collections.reverseOrder());
		System.out.println("按照学号反序排序后的stuLsCopy是：" + stuLsCopy);
		Collections.sort(stuLsCopy, Collections.reverseOrder( (stu1, stu2) -> {return stu1.getSage() - stu2.getSage();} ));
		System.out.println("按照年龄反序排序后的stuLsCopy是：" + stuLsCopy);
		Collections.swap(stuLsCopy, 0, stuLsCopy.size()-1);
		System.out.println("将首尾元素对换后的stuLsCopy是：" + stuLsCopy);
		
		//关于Collections.repalceAll()、Collections.singletonList()、Collections.indexOfSubList()、
		//Collections.lastIndexOfSubList()、Collections.nCopies()、Collections.disjoint()的使用演示
		Collections.sort(stuLsCopy); //对stuLsCopy按照学号大小升序排序
		Collections.replaceAll(stuLsCopy, stuIsabella, stuCathy);
		Collections.replaceAll(stuLsCopy, stuAndrew, stuCathy);
		System.out.println("经过2次替换后的stuLsCopy是：" + stuLsCopy);
		
		List<Student> singletonLsOfCathy = Collections.singletonList(stuCathy);
		List<Student> stuLsOfCathy = Collections.nCopies(2, stuCathy);
		//stuLsOfCathy.set(stuLsOfCathy.size()-1, stuAndrew); //触发UnsupportedOperationException异常，stuLsOfCathy不支持set操作
		//stuLsOfCathy.add(stuJason); //触发UnsupportedOperationException异常, stuLsOfCathy不支持add操作
		int subLsIdx = Collections.indexOfSubList(stuLsCopy, singletonLsOfCathy);
		System.out.println("列表singletonLsOfCathy在stuLsCopy中第一次出现时的位置是：" + subLsIdx);
		subLsIdx = Collections.lastIndexOfSubList(stuLsCopy, stuLsOfCathy);
		System.out.println("列表stuLsOfCathy在stuLsCopy中最后一次出现时的位置是：" + subLsIdx);
		System.out.println("列表stuLsCopy与stuLs是否相交（即是否包含共同元素）：" + !Collections.disjoint(stuLsCopy, stuLs));
		
		//关于Collections.emptyList()、Collections.emptyIterator()、Collections.emptyListIterator()、
		//Collections.newSetFromMap()、Collections.unmodifiableList()和Collections.checkedList()的使用演示
		List<Student> empLs = Collections.emptyList();
		Iterator<Student> empItr = Collections.emptyIterator();
		ListIterator<Student> empLsItr = Collections.emptyListIterator();
		System.out.println("empLs中元素个数是：" + empLs.size());
		
		System.out.print("使用empItr遍历到的元素是：[");
		empItr.forEachRemaining(e -> System.out.print(e));
		System.out.println("]");
		
		System.out.print("使用empLsItr遍历到的元素是：[");
		empLsItr.forEachRemaining(e -> System.out.print(e));
		System.out.println("]");
		
		//Collections.newSetFromMap()的参数必须是空的Map<E, Boolean>对象
		Set<Student> stuSet = Collections.newSetFromMap(new HashMap<Student, Boolean>());
		stuSet.add(stuJason);
		stuSet.add(stuSophia);
		stuSet.add(stuKevin);
		System.out.println("stuSet是：" + stuSet);
		
		//下面这段代码会触发IllegalArgumentException异常！因为传递给Collections.newSetFromMap()的参数不是一个空Map<E, Boolean>对象
		//Map<Student, Boolean> stuHITMap = new HashMap<>(); //HIT大学的学生
		//for(int i = 0; i < students.length; i++) {
		//	stuHITMap.put(students[i], new Random().nextBoolean());
		//}
		//Set<Student> stuHITSet = Collections.newSetFromMap(stuHITMap);
		//System.out.println("stuHITSet是：" + stuHITSet);
		
		List<Student> unmodStuLs = Collections.unmodifiableList(stuLs);
		try { unmodStuLs.add(stuKevin);
		} catch(UnsupportedOperationException ex) {
			System.out.println("无法对unmodStuLs进行add操作");
		}
		
		List<Student> chkdStuLs = Collections.checkedList(stuLs, Student.class);
		List rawLs = chkdStuLs; //将chkdStuLs赋值给原始类型引用变量rawLs
		try {
			rawLs.add("Michael"); //向rawLs中添加一个String对象"Michael"
		} catch(ClassCastException ex) {
			System.out.println("无法向元素类型为Student的列表chkdStuLs中插入String类型的对象");
		}
		
		//关于Collections.asLifoQueue()的使用演示
		Deque<Student> stuDeque = new LinkedList<>();
		Collections.addAll(stuDeque, students);
		//Collections.sort((List<Student>)stuDeque);
		System.out.println("进行填充后的stuDeque是：" + stuDeque);
		Queue<Student> stuQue = Collections.asLifoQueue(stuDeque); //将双端队列stuDeque转化为单端队列stuQue，实际上变为一个后进先出的栈结构
		stuQue.remove(); //在队头进行删除，对stuQue的操作将会写透（write through）到stuDeque上
		stuQue.add(stuKevin); //在队头进行添加，对stuQue的操作将会写透到stuDeque上
		System.out.println("进行修改后的stuDeque是：" + stuDeque);
		
		
		//下列两行代码对stuLs的并发操作会触发ConcurrentModificationException异常，因为stuLs不是线程安全的
		new Thread(new ListTraverser(stuLs)).start();
		new Thread(new ListRemover(stuLs)).start();
		
		//关于Collections.synchronizedList()的使用演示
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
		System.out.println("程序结束！");
	}
}