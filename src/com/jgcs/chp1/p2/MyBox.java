package com.jgcs.chp1.p2;

public class MyBox<T>{ //����������Է�һ�����͵���Ʒ
	T obj;
	T getObj() {
		return obj;
	}
	void setObj(T obj) {
		this.obj = obj;
	}
}

class MyEntry<K, V>{ //����������Է�һ�����͵���Ʒ
	K key;
	V value;
	MyEntry(K k, V v){
		this.key = k;
		this.value = v;
	}
	V getValue() {
		return value;
	}
	void setValue(V v) {
		this.value = v;
	}
}
