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
	// 人可能有车，也可能没有车，故这里用Optional<Car_WithOptional>类型修饰相关成员变量和成员方法
	private Optional<Car_WithOptional> car;

	public Optional<Car_WithOptional> getCar() {
		return car;
	}

	public void setCar(Optional<Car_WithOptional> car) {
		this.car = car;
	}
}

class Car_WithOptional {
	// 车可能有保险，也可能没有保险，故这里用Optional<Insurance>类型修饰相关成员变量和成员方法
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
		if (new Random().nextInt(100) % 2 == 0) { // 车car有50%的可能有保险insurance
			car.setInsurance(insurance);
		}

		String carInsuranceName = getCarInsuranceName(person);
		System.out.println("第一次输出：" + carInsuranceName);

		Person_WithOptional personOptional = new Person_WithOptional();
		Car_WithOptional carOptional = new Car_WithOptional();

		personOptional.setCar(Optional.of(carOptional));
		if (new Random().nextInt(100) % 2 == 0) { // 车carOptional有50%的可能有保险Optional.of(insurance)
			carOptional.setInsurance(Optional.of(insurance));
		} else {
			carOptional.setInsurance(Optional.ofNullable(null));
		}

		carInsuranceName = getCarInsuranceName(personOptional);
		System.out.println("第二次输出：" + carInsuranceName);

		Optional<Insurance> carInsuranceOptional = carOptional.getInsurance();
		if (carInsuranceOptional.isPresent()) {
			System.out.println("第三次输出：" + carInsuranceOptional.get().getName());
		}

		carOptional.getInsurance().ifPresent(insur -> System.out.println("第四次输出：" + insur.getName()));

		Insurance emptyInsurance = new Insurance();
		emptyInsurance.setName("emptyInsurance");
		System.out.println("第五次输出：" + carOptional.getInsurance().orElse(emptyInsurance).getName());

		System.out.println("第六次输出：" + carOptional.getInsurance().orElseGet(() -> {
			Insurance nullInsurance = new Insurance();
			nullInsurance.setName("nullInsurance");
			return nullInsurance;
		}).getName());

		System.out.println("第七次输出：" + carOptional.getInsurance().map(insur -> insur.getName()).get());

		System.out.println("第八次输出：" + carOptional.getInsurance().flatMap(insur -> Optional.of(insur.getName())).get());

		Optional<Insurance> remainedOptional = carInsuranceOptional.filter(insur -> insur.getName().contains("sur"));
		System.out.println("第九次输出：" + remainedOptional.get().getName());

		OptionalInt optInt = OptionalInt.of(100);
		optInt.ifPresent(i -> System.out.println("第十次输出：" + i));
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
		//通过get()方法从Optional中取出值，如果当前Optional<T>对象不包含有效值，则抛出NoSuchElementException异常
		return person.getCar().get().getInsurance().get().getName();
	}
}