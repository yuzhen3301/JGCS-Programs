package com.jgcs.chp10.p1;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

class Person {
	private Car car;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}

class Car {
	private Insurance insurance;

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}
}

class Insurance {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Person_WithOptional {
	// �˿����г���Ҳ����û�г�����������Optional<Car_WithOptional>����������س�Ա�����ͳ�Ա����
	private Optional<Car_WithOptional> car;

	public Optional<Car_WithOptional> getCar() {
		return car;
	}

	public void setCar(Optional<Car_WithOptional> car) {
		this.car = car;
	}
}

class Car_WithOptional {
	// �������б��գ�Ҳ����û�б��գ���������Optional<Insurance>����������س�Ա�����ͳ�Ա����
	private Optional<Insurance> insurance;

	public Optional<Insurance> getInsurance() {
		return insurance;
	}

	public void setInsurance(Optional<Insurance> insurance) {
		this.insurance = insurance;
	}
}

public class App10_1 {
	public static void main(String[] args) {
		Person person = new Person();
		Car car = new Car();
		Insurance insurance = new Insurance();
		insurance.setName("insurance");

		person.setCar(car);
		if (new Random().nextInt(100) % 2 == 0) { // ��car��50%�Ŀ����б���insurance
			car.setInsurance(insurance);
		}

		String carInsuranceName = getCarInsuranceName(person);
		System.out.println("��һ�������" + carInsuranceName);

		Person_WithOptional personOptional = new Person_WithOptional();
		Car_WithOptional carOptional = new Car_WithOptional();

		personOptional.setCar(Optional.of(carOptional));
		if (new Random().nextInt(100) % 2 == 0) { // ��carOptional��50%�Ŀ����б���Optional.of(insurance)
			carOptional.setInsurance(Optional.of(insurance));
		} else {
			carOptional.setInsurance(Optional.ofNullable(null));
		}

		carInsuranceName = getCarInsuranceName(personOptional);
		System.out.println("�ڶ��������" + carInsuranceName);

		Optional<Insurance> carInsuranceOptional = carOptional.getInsurance();
		if (carInsuranceOptional.isPresent()) {
			System.out.println("�����������" + carInsuranceOptional.get().getName());
		}

		carOptional.getInsurance().ifPresent(insur -> System.out.println("���Ĵ������" + insur.getName()));

		Insurance emptyInsurance = new Insurance();
		emptyInsurance.setName("emptyInsurance");
		System.out.println("����������" + carOptional.getInsurance().orElse(emptyInsurance).getName());

		System.out.println("�����������" + carOptional.getInsurance().orElseGet(() -> {
			Insurance nullInsurance = new Insurance();
			nullInsurance.setName("nullInsurance");
			return nullInsurance;
		}).getName());

		System.out.println("���ߴ������" + carOptional.getInsurance().map(insur -> insur.getName()).get());

		System.out.println("�ڰ˴������" + carOptional.getInsurance().flatMap(insur -> Optional.of(insur.getName())).get());

		Optional<Insurance> remainedOptional = carInsuranceOptional.filter(insur -> insur.getName().contains("sur"));
		System.out.println("�ھŴ������" + remainedOptional.get().getName());

		OptionalInt optInt = OptionalInt.of(100);
		optInt.ifPresent(i -> System.out.println("��ʮ�������" + i));
	}

	public static String getCarInsuranceName(Person person) {
		return person.getCar().getInsurance().getName();
//		if (person != null) {
//			Car car = person.getCar();
//			if (car != null) {
//				Insurance insurance = car.getInsurance();
//				if (insurance != null) {
//					return insurance.getName();
//				}
//			}
//		}
//		return "Unknown";
	}

	public static String getCarInsuranceName(Person_WithOptional person) {
		//ͨ��get()������Optional��ȡ��ֵ�������ǰOptional<T>���󲻰�����Чֵ�����׳�NoSuchElementException�쳣
		return person.getCar().get().getInsurance().get().getName();
	}
}