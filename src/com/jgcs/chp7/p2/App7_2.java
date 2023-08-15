package com.jgcs.chp7.p2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class App7_2 {
	public static void main(String[] args) {
		//������ʡ���г��ƴ���
		String[] cityCodes = {"��A", "��B", "��C", "��D", "��E", "��F", "��G", "��H", "��J", "��K", "��L", "��M", "��N", "��P", "��R"};
		
		//������ʡ��������
		String[] cityNames = {"��������", "���������", "ĵ������", "��ľ˹��", "������", "������", "������", "�׸���", "˫Ѽɽ��", "��̨����", "��������", "�绯��", "�ں���", "���˰������", "ũ��ϵͳ"};
		
		App7_2 app = new App7_2();
		app.testOnHashMap(cityCodes, cityNames);
		System.out.println();
		
		app.testOnLinkedHashMapWithInsertionOrder(cityCodes, cityNames);
		System.out.println();
		
		app.testOnLinkedHashMapWithAccessOrder(cityCodes, cityNames);
	}
	
	void testOnHashMap(String[] cityCodes, String[] cityNames) {
		HashMap<String, String> hm_code2name = new HashMap<>(16, 0.8f);
		for(int i = 0; i < cityCodes.length; i++) {
			hm_code2name.put(cityCodes[i], cityNames[i]);
		}
		System.out.println("hm_code2name�ĵ�һ���������" + hm_code2name.size() + "��Ԫ�أ���");
		System.out.println(hm_code2name);
		
		String cityCode = "��L";
		String oldCity = "", newCity = "";
		System.out.println(cityCode + "��Ӧ�ĳ��������ǣ� " + (oldCity = hm_code2name.get(cityCode)));
		
		newCity = "�ɻ�������";
		oldCity = hm_code2name.replace(cityCode, newCity);
		System.out.println(cityCode + "��Ӧ�ĳ��������Ѵ� " + oldCity + " ��Ϊ " + hm_code2name.get(cityCode));
		
		final String finalCity = oldCity;
		if(hm_code2name.merge(cityCode, oldCity, (v1, v2) -> {return finalCity;}) != null) {
			System.out.println(cityCode + "��Ӧ�ĳ��������Ѵ� " + newCity + " �Ļ� " + hm_code2name.get(cityCode));
		}
		
		System.out.println("hm_code2name�ĵڶ��������ʹ��ӳ��ļ���keys����ӡ�����ݣ�");
		Set<String> keys = hm_code2name.keySet();
		Iterator<String> keyItr = keys.iterator();
		while(keyItr.hasNext()) {
			cityCode = keyItr.next();
			System.out.println(cityCode+"��Ӧ�ĳ��������ǣ�" + hm_code2name.get(cityCode));
		}
		
		System.out.println("hm_code2name�ĵ����������ʹ��ӳ��ļ�ֵ�Լ�entries����ӡ�����ݣ�");
		Set<Map.Entry<String, String>> entries = hm_code2name.entrySet();
		Iterator<Map.Entry<String, String>> entryItr = entries.iterator();
		while(entryItr.hasNext()) {
			Map.Entry<String, String> entry = entryItr.next();
			System.out.println(entry.getKey()+"��Ӧ�ĳ��������ǣ�" + entry.getValue());
		}
	}
	
	void testOnLinkedHashMapWithInsertionOrder(String[] cityCodes, String[] cityNames) {
		LinkedHashMap<String, String> lm_io_code2name = new LinkedHashMap<>(32, 0.6f); //����һ��insertion-order��LinkedHashMap
		for(int i = 0; i < cityCodes.length; i++) {
			lm_io_code2name.put(cityCodes[i], cityNames[i]);
		}
		System.out.println("lm_io_code2name�ĵ�һ���������" + lm_io_code2name.size() + "��Ԫ�أ���");
		System.out.println(lm_io_code2name);
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //��ĸ��
		String cityName = "";
		for(int i = 0; i < new Random().nextInt(alphabet.length()); i++) { //ѭ��m�Σ�mλ��[0, 25]֮��
			String cityCode = "��" + alphabet.charAt(new Random().nextInt(alphabet.length())); //�������һ�����д���
			System.out.println(cityCode + "��Ӧ�ĳ��������ǣ�" + ((cityName = lm_io_code2name.get(cityCode)) == null ? "δ֪" : cityName));
		}
		
		System.out.println("lm_io_code2name�ĵڶ����������" + lm_io_code2name.size() + "��Ԫ�أ���");
		System.out.println(lm_io_code2name);
	}
	
	void testOnLinkedHashMapWithAccessOrder(String[] cityCodes, String[] cityNames) {
		LinkedHashMap<String, String> lm_ao_code2name = new LinkedHashMap<>(8, 0.9f, true); //����һ��access-order��LinkedHashMap
		for(int i = 0; i < cityCodes.length; i++) {
			lm_ao_code2name.put(cityCodes[i], cityNames[i]);
		}
		System.out.println("lm_ao_code2name�ĵ�һ���������" + lm_ao_code2name.size() + "��Ԫ�أ���");
		System.out.println(lm_ao_code2name);
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //��ĸ��
		String cityName = "";
		for(int i = 0; i < new Random().nextInt(alphabet.length()); i++) { //ѭ��m�Σ�mλ��[0, 25]֮��
			String cityCode = "��" + alphabet.charAt(new Random().nextInt(alphabet.length())); //�������һ�����д���
			System.out.println(cityCode + "��Ӧ�ĳ��������ǣ�" + ((cityName = lm_ao_code2name.get(cityCode)) == null ? "δ֪" : cityName));
		}
		
		System.out.println("lm_ao_code2name�ĵڶ����������" + lm_ao_code2name.size() + "��Ԫ�أ���");
		System.out.println(lm_ao_code2name);
	}
}