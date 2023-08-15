package com.jgcs.chp9.p1;

import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

class People{
	String pname;
	char psex;
	int page;
	
	public People(String pname, char psex, int page) {
		this.pname = pname;
		this.psex = psex;
		this.page = page;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public char getPsex() {
		return psex;
	}

	public void setPsex(char psex) {
		this.psex = psex;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}

class Student extends People{
	String sno;
	String sdept;

	public Student(String sno, String sname, char ssex, int sage, String sdept) {
		super(sname, ssex, sage);
		this.sno = sno;
		this.sdept = sdept;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
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
		sb.append("no='").append(sno).append('\'');
		sb.append(", name='").append(getPname()).append('\'');
		sb.append(", sex='").append(getPsex()).append('\'');
		sb.append(", age=").append(getPage());
		sb.append(", dept='").append(sdept).append('\'');
		sb.append(')');
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return sno.hashCode() >>> 1;
	}

	@Override
	public boolean equals(Object obj) { //根据学号进行相等比较
		if(obj instanceof Student) {
			Student stu = (Student)obj;
			return this.sno.equals(stu.getSno()) ? true : false;
		} else {
			return false;
		}
	}
}

public class App9_1 {	
	public static void main(String[] args) {
		Student stuJason = new Student("09001", "Jason", 'M', 21, "BD"); // BD：大数据学院
		Student stuIsabella = new Student("09002", "Isabella", 'F', 20, "MD"); // MD：音乐与舞蹈学院
		Student stuCathy = new Student("09003", "Cathy", 'F', 19, "MATH"); // MATH：数学学院
		Student stuAndrew = new Student("09004", "Andrew", 'M', 22, "AI"); // AI：人工智能学院
		Student stuSophia = new Student("09005", "Sophia", 'F', 20, "CS"); // CS：计算机学院
		
		App9_1 app = new App9_1();
		
		int result = app.compareStudentOnAge(stuJason, stuCathy);
		System.out.println("stuJason.age is " + (result > 0 ?  "greater than " : result == 0 ? "equal to " : "less than ") + "stuCathy");
		System.out.println();
		
		try{
			app.compareStudentOnAge(null, stuAndrew);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println();
		
		try{
			app.compareStudentOnAge(stuIsabella, null);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println();
		
		try{
			app.compareStudentOnName(null, stuSophia);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println();
		
		Student[] students = new Student[10];
		for(int i = 0; i < students.length; i++) {
			switch(new Random().nextInt(10) % 4) {
			case 0:
				students[i] = null;
				break;
			case 1:
				students[i] = stuCathy;
				break;
			case 2:
				students[i] = stuSophia;
				break;
			case 3:
				students[i] = stuIsabella;
				break;
			}
		}
		
		for(int i = 0; i < students.length; i++) {
			boolean isNull = Objects.isNull(students[i]);
			boolean nonNull = Objects.nonNull(students[i]);
			
			if(isNull) {
				System.out.println("students[" + i + "] is " + Objects.toString(students[i], "Student(null, null, null, null, null)"));
			}
			if(nonNull) {
				System.out.println("students[" + i + "] is " + Objects.toString(students[i]));
			}
		}
		System.out.println();
		
		System.out.println("stuJason.hashCode() is" + stuJason.hashCode());
		System.out.println("Objects.hashCode(stuJason) is " + Objects.hashCode(stuJason));
		System.out.println("Objects.hashCode(null) is " + Objects.hashCode(null));
		System.out.println("Objects.hash(stuJason, stuAndrew, stuIsabella) is " +  Objects.hash(stuJason, stuAndrew, stuIsabella));
		System.out.println();
		
		Student[][] stuArr1 = { {stuCathy, stuJason, stuAndrew}, {stuIsabella, stuSophia, stuJason} };
		Student[][] stuArr2 = { {stuCathy, stuJason, stuAndrew}, {stuIsabella, stuSophia, stuJason} };
		Student[][] stuArr3 = { {stuIsabella, stuSophia, stuJason}, {stuCathy, stuJason, stuAndrew} };
		Student[][] stuArr4 = { {stuIsabella, stuSophia}, {stuJason, stuCathy}, {stuJason, stuAndrew} };
		Student[] stuArr5 = {stuJason, stuIsabella, stuCathy, stuAndrew, stuSophia};
		System.out.println("stuArr1 " + (Objects.deepEquals(stuArr1, stuArr1) ? "is " : "is not ") + "deeply equals to stuArr1");
		System.out.println("stuArr1 " + (Objects.deepEquals(stuArr1, stuArr2) ? "is " : "is not ") + "deeply equals to stuArr2");
		System.out.println("stuArr1 " + (Objects.deepEquals(stuArr1, stuArr3) ? "is " : "is not ") + "deeply equals to stuArr3");
		System.out.println("stuArr1 " + (Objects.deepEquals(stuArr1, stuArr4) ? "is " : "is not ") + "deeply equals to stuArr4");
		System.out.println("stuArr1 " + (Objects.deepEquals(stuArr1, stuArr5) ? "is " : "is not ") + "deeply equals to stuArr5");
		System.out.println();
		
		System.out.println("stuArr1 " + (Objects.equals(stuArr1, stuArr1) ? "is " : "is not ") + "equals to stuArr1");
		System.out.println("stuArr1 " + (Objects.equals(stuArr1, stuArr2) ? "is " : "is not ") + "equals to stuArr2");
		System.out.println("stuArr1 " + (Objects.equals(stuArr1, stuArr3) ? "is " : "is not ") + "equals to stuArr3");
		System.out.println("stuArr1 " + (Objects.equals(stuArr1, stuArr4) ? "is " : "is not ") + "equals to stuArr4");
		System.out.println("stuArr1 " + (Objects.equals(stuArr1, stuArr5) ? "is " : "is not ") + "equals to stuArr5");
		System.out.println();
		
		stuAndrew.setSno("09001");
		System.out.println("stuJason " + (Objects.equals(stuJason, stuAndrew) ? "is " : "is not ") + "deeply equals to stuAndrew");
		System.out.println("stuJason " + (Objects.equals(stuJason, stuAndrew) ? "is " : "is not ") + "equals to stuAndrew");
	}
	
	int compareStudentOnAge(Student s1, Student s2) {
		Objects.requireNonNull(s1);
		Objects.requireNonNull(s2, "s2 shouldn't be null!");
		return Objects.compare(s1, s2, new Comparator<People>() {
			public int compare(People p1, People p2) {
				return p1.getPage() - p2.getPage();
			}
		});
	}
	
	int compareStudentOnName(Student s1, Student s2) {
		Objects.requireNonNull(s1, () -> "s1 shouldn't be null!");
		Objects.requireNonNull(s2, "s2 shouldn't be null!");
		return Objects.compare(s1, s2, new Comparator<People>() {
			public int compare(People p1, People p2) {
				return p1.getPname().compareTo(p2.getPname());
			}
		});
	}
}