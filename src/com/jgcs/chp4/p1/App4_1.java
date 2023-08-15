package com.jgcs.chp4.p1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

class MyArrayList<E> implements Iterable<E> {
	Object[] elementData; // non-private to simplify nested class access
	private int size;
	private static final Object[] EMPTY_ELEMENTDATA = {};

	public MyArrayList() {
		this.elementData = EMPTY_ELEMENTDATA;
	}

	public MyArrayList(int initialCapacity) {
		if (initialCapacity > 0) {
			this.elementData = new Object[initialCapacity];
		} else if (initialCapacity == 0) {
			this.elementData = EMPTY_ELEMENTDATA;
		} else {
			throw new IllegalArgumentException("Illegeal Capacity: " + initialCapacity);
		}
	}

	private void rangeCheck(int index) {
		if (index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity > elementData.length) {
			int oldCapacity = elementData.length;
			int newCapacity = oldCapacity + (oldCapacity >> 1);
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	E elementData(int index) {
		return (E) elementData[index];
	}

	public E get(int index) {
		rangeCheck(index);
		return elementData(index);
	}

	public E set(int index, E element) {
		rangeCheck(index);
		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	public boolean add(E e) {
		ensureCapacity(size + 1);
		elementData[size++] = e;
		return true;
	}

	public void add(int index, E element) {
		rangeCheck(index);
		ensureCapacity(size + 1);

		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	public E remove(int index) {
		rangeCheck(index);
		E oldValue = elementData(index);

		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		}

		elementData[--size] = null;
		return oldValue;
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			elementData[i] = null;
		}
		size = 0;
	}

	public Iterator<E> iterator() {
		return new Itr();
	}

	public ListIterator<E> listIterator() {
		return new ListItr(0);
	}

	private class Itr implements Iterator<E> { //内部类Itr实现了Iterator<E>接口
		int cursor; // index of next element to return
		int lastRet = -1; // index of last element returned; -1 if no such

		Itr() {
		}

		public boolean hasNext() {
			return cursor != size;
		}

		public E next() {
			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			Object[] elementData = MyArrayList.this.elementData;
			cursor = i + 1;
			return (E) elementData[lastRet = i];
		}

		public void remove() {
			if (lastRet < 0) {
				throw new IllegalStateException();
			}

			try {
				MyArrayList.this.remove(lastRet);
				cursor = lastRet;
				lastRet = -1;
			} catch (IndexOutOfBoundsException ex) {
				throw ex;
			}
		}

		public void forEachRemaining(Consumer<? super E> consumer) {
			if (consumer == null)
				return;
			final int size = MyArrayList.this.size;
			int i = cursor;
			if (i >= size)
				return;

			final Object[] elementData = MyArrayList.this.elementData;
			while (i != size) {
				consumer.accept((E) elementData[i++]);
			}

			cursor = i;
			lastRet = i - 1;
		}
	}
	//内部类ListItr继承了Itr类并实现了ListIterator<E>接口
	private class ListItr extends Itr implements ListIterator<E> {
		ListItr(int index) {
			super();
			cursor = index;
		}

		public boolean hasPrevious() {
			return cursor != 0;
		}

		public int nextIndex() {
			return cursor;
		}

		public int previousIndex() {
			return cursor - 1;
		}

		public E previous() {
			int i = cursor - 1;
			if (i < 0) {
				throw new NoSuchElementException();
			}

			Object[] elementData = MyArrayList.this.elementData;
			cursor = i;
			return (E) elementData[lastRet = i];
		}

		public void set(E e) {
			if (lastRet < 0)
				throw new IllegalStateException();

			try {
				MyArrayList.this.set(lastRet, e);
			} catch (IndexOutOfBoundsException ex) {
				throw ex;
			}
		}

		public void add(E e) {
			try {
				int i = cursor;
				MyArrayList.this.add(i, e);
				cursor = i + 1;
				lastRet = -1;
			} catch (IndexOutOfBoundsException ex) {
				throw ex;
			}
		}
	}

	public String toString() {
		Iterator<E> it = iterator();
		if (!it.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			E e = it.next();
			sb.append(e == this ? "(this Collection)" : e);
			if (!it.hasNext())
				return sb.append(']').toString();
			sb.append(',').append(' ');
		}
	}
}

public class App4_1 {
	public static void main(String[] args) {
		String[] cities = { "哈尔滨", "大庆", "齐齐哈尔", "绥化", "牡丹江", "佳木斯", "黑河", "鸡西", "双鸭山", "鹤岗", "伊春", "七台河", "加格达奇" };
		MyArrayList<String> mal = new MyArrayList<>(32);
		for (int i = 0; i < cities.length; i++) {
			mal.add(cities[i]);
		}

		//利用size()和get()方法打印mal中的内容
		System.out.println("第一次输出，利用size()和get()方法打印mal中的内容：");
		System.out.print("黑龙江省的城市有：[");
		for (int i = 0; i < mal.size(); i++) {
			System.out.print(mal.get(i) + (i == mal.size() - 1 ? "" : ", "));
		}
		System.out.print("]\n");

		//利用iterator()返回的正向迭代器打印mal中的内容
		System.out.println("\n第二次输出，利用iterator()返回的正向迭代器打印mal中的内容：");
		Iterator<String> itr = mal.iterator();
		System.out.print("黑龙江省的城市有：[");
		while (itr.hasNext()) {
			System.out.print(itr.next() + (!itr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");

		//利用foreach循环打印mal中的内容
		System.out.println("\n第三次输出，利用foreach循环打印mal中的内容：");
		StringBuilder sb = new StringBuilder("黑龙江省的城市有：[");
		String delim = ", ";
		for (String city : mal) { // 底层使用iterable<E>接口的iterator()方法返回的正向迭代器对mal进行遍历
			sb.append(city + delim);
		}
		int lastDelimIndex = sb.lastIndexOf(delim); // 最后一个分隔符", "的位置
		sb.replace(lastDelimIndex, lastDelimIndex + delim.length() - 1, "]\n"); // 将最后一个分隔符替换成结束符"]"并换行
		System.out.print(sb);

		//利用listIterator()返回的线性迭代器双向遍历mal中的内容
		System.out.println("\n第四次输出，利用listIterator()返回的线性迭代器双向遍历mal中的内容：");
		ListIterator<String> lsitr = mal.listIterator();
		System.out.print("正向遍历，黑龙江省的城市有：[");//正向遍历
		while (lsitr.hasNext()) {
			System.out.print(lsitr.next() + (!lsitr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");
		System.out.print("反向遍历，黑龙江省的城市有：[");//反向遍历
		while (lsitr.hasPrevious()) {
			System.out.print(lsitr.previous() + (!lsitr.hasPrevious() ? "" : ", "));
		}
		System.out.print("]\n");
		
		//利用iterator()返回的普通迭代器删除mal中的内容
		System.out.println("\n第五次输出，利用iterator()返回的普通迭代器删除mal中的'鹤岗'：");
		itr = mal.iterator();
		System.out.print("黑龙江省的城市有：[");
		while (itr.hasNext()) {
			String currentElement = itr.next();
			if(currentElement == "鹤岗") {
				itr.remove(); //删除当前元素
				currentElement = itr.next(); //将迭代器的游标向后移动一位
			}
			System.out.print(currentElement + (!itr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");
		
		//利用listIterator()返回的线性迭代器修改mal中的内容
		System.out.println("\n第六次输出，利用listIterator()返回的线性迭代器修改mal中的'加格达奇'为'大兴安岭'：");
		lsitr = mal.listIterator();
		System.out.print("黑龙江省的城市有：[");
		while (lsitr.hasNext()) {
			String currentElement = lsitr.next();
			if(currentElement.equals("加格达奇")) {
				lsitr.set("大兴安岭"); //修改当前元素
				currentElement = "大兴安岭";
			}
			System.out.print(currentElement + (!lsitr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");
	}
}