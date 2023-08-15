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

	private class Itr implements Iterator<E> { //�ڲ���Itrʵ����Iterator<E>�ӿ�
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
	//�ڲ���ListItr�̳���Itr�ಢʵ����ListIterator<E>�ӿ�
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
		String[] cities = { "������", "����", "�������", "�绯", "ĵ����", "��ľ˹", "�ں�", "����", "˫Ѽɽ", "�׸�", "����", "��̨��", "�Ӹ����" };
		MyArrayList<String> mal = new MyArrayList<>(32);
		for (int i = 0; i < cities.length; i++) {
			mal.add(cities[i]);
		}

		//����size()��get()������ӡmal�е�����
		System.out.println("��һ�����������size()��get()������ӡmal�е����ݣ�");
		System.out.print("������ʡ�ĳ����У�[");
		for (int i = 0; i < mal.size(); i++) {
			System.out.print(mal.get(i) + (i == mal.size() - 1 ? "" : ", "));
		}
		System.out.print("]\n");

		//����iterator()���ص������������ӡmal�е�����
		System.out.println("\n�ڶ������������iterator()���ص������������ӡmal�е����ݣ�");
		Iterator<String> itr = mal.iterator();
		System.out.print("������ʡ�ĳ����У�[");
		while (itr.hasNext()) {
			System.out.print(itr.next() + (!itr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");

		//����foreachѭ����ӡmal�е�����
		System.out.println("\n���������������foreachѭ����ӡmal�е����ݣ�");
		StringBuilder sb = new StringBuilder("������ʡ�ĳ����У�[");
		String delim = ", ";
		for (String city : mal) { // �ײ�ʹ��iterable<E>�ӿڵ�iterator()�������ص������������mal���б���
			sb.append(city + delim);
		}
		int lastDelimIndex = sb.lastIndexOf(delim); // ���һ���ָ���", "��λ��
		sb.replace(lastDelimIndex, lastDelimIndex + delim.length() - 1, "]\n"); // �����һ���ָ����滻�ɽ�����"]"������
		System.out.print(sb);

		//����listIterator()���ص����Ե�����˫�����mal�е�����
		System.out.println("\n���Ĵ����������listIterator()���ص����Ե�����˫�����mal�е����ݣ�");
		ListIterator<String> lsitr = mal.listIterator();
		System.out.print("���������������ʡ�ĳ����У�[");//�������
		while (lsitr.hasNext()) {
			System.out.print(lsitr.next() + (!lsitr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");
		System.out.print("���������������ʡ�ĳ����У�[");//�������
		while (lsitr.hasPrevious()) {
			System.out.print(lsitr.previous() + (!lsitr.hasPrevious() ? "" : ", "));
		}
		System.out.print("]\n");
		
		//����iterator()���ص���ͨ������ɾ��mal�е�����
		System.out.println("\n��������������iterator()���ص���ͨ������ɾ��mal�е�'�׸�'��");
		itr = mal.iterator();
		System.out.print("������ʡ�ĳ����У�[");
		while (itr.hasNext()) {
			String currentElement = itr.next();
			if(currentElement == "�׸�") {
				itr.remove(); //ɾ����ǰԪ��
				currentElement = itr.next(); //�����������α�����ƶ�һλ
			}
			System.out.print(currentElement + (!itr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");
		
		//����listIterator()���ص����Ե������޸�mal�е�����
		System.out.println("\n���������������listIterator()���ص����Ե������޸�mal�е�'�Ӹ����'Ϊ'���˰���'��");
		lsitr = mal.listIterator();
		System.out.print("������ʡ�ĳ����У�[");
		while (lsitr.hasNext()) {
			String currentElement = lsitr.next();
			if(currentElement.equals("�Ӹ����")) {
				lsitr.set("���˰���"); //�޸ĵ�ǰԪ��
				currentElement = "���˰���";
			}
			System.out.print(currentElement + (!lsitr.hasNext() ? "" : ", "));
		}
		System.out.print("]\n");
	}
}