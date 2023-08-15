package com.jgcs.chp7.p2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class App7_2 {
	public static void main(String[] args) {
		//黑龙江省各市车牌代码
		String[] cityCodes = {"黑A", "黑B", "黑C", "黑D", "黑E", "黑F", "黑G", "黑H", "黑J", "黑K", "黑L", "黑M", "黑N", "黑P", "黑R"};
		
		//黑龙江省各市名称
		String[] cityNames = {"哈尔滨市", "齐齐哈尔市", "牡丹江市", "佳木斯市", "大庆市", "伊春市", "鸡西市", "鹤岗市", "双鸭山市", "七台河市", "哈尔滨市", "绥化市", "黑河市", "大兴安岭地区", "农垦系统"};
		
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
		System.out.println("hm_code2name的第一次输出（共" + hm_code2name.size() + "个元素）：");
		System.out.println(hm_code2name);
		
		String cityCode = "黑L";
		String oldCity = "", newCity = "";
		System.out.println(cityCode + "对应的城市名称是： " + (oldCity = hm_code2name.get(cityCode)));
		
		newCity = "松花江地区";
		oldCity = hm_code2name.replace(cityCode, newCity);
		System.out.println(cityCode + "对应的城市名称已从 " + oldCity + " 改为 " + hm_code2name.get(cityCode));
		
		final String finalCity = oldCity;
		if(hm_code2name.merge(cityCode, oldCity, (v1, v2) -> {return finalCity;}) != null) {
			System.out.println(cityCode + "对应的城市名称已从 " + newCity + " 改回 " + hm_code2name.get(cityCode));
		}
		
		System.out.println("hm_code2name的第二次输出，使用映射的键集keys来打印其内容：");
		Set<String> keys = hm_code2name.keySet();
		Iterator<String> keyItr = keys.iterator();
		while(keyItr.hasNext()) {
			cityCode = keyItr.next();
			System.out.println(cityCode+"对应的城市名称是：" + hm_code2name.get(cityCode));
		}
		
		System.out.println("hm_code2name的第三次输出，使用映射的键值对集entries来打印其内容：");
		Set<Map.Entry<String, String>> entries = hm_code2name.entrySet();
		Iterator<Map.Entry<String, String>> entryItr = entries.iterator();
		while(entryItr.hasNext()) {
			Map.Entry<String, String> entry = entryItr.next();
			System.out.println(entry.getKey()+"对应的城市名称是：" + entry.getValue());
		}
	}
	
	void testOnLinkedHashMapWithInsertionOrder(String[] cityCodes, String[] cityNames) {
		LinkedHashMap<String, String> lm_io_code2name = new LinkedHashMap<>(32, 0.6f); //创建一个insertion-order的LinkedHashMap
		for(int i = 0; i < cityCodes.length; i++) {
			lm_io_code2name.put(cityCodes[i], cityNames[i]);
		}
		System.out.println("lm_io_code2name的第一次输出（共" + lm_io_code2name.size() + "个元素）：");
		System.out.println(lm_io_code2name);
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //字母表
		String cityName = "";
		for(int i = 0; i < new Random().nextInt(alphabet.length()); i++) { //循环m次，m位于[0, 25]之间
			String cityCode = "黑" + alphabet.charAt(new Random().nextInt(alphabet.length())); //随机产生一个城市代码
			System.out.println(cityCode + "对应的城市名称是：" + ((cityName = lm_io_code2name.get(cityCode)) == null ? "未知" : cityName));
		}
		
		System.out.println("lm_io_code2name的第二次输出（共" + lm_io_code2name.size() + "个元素）：");
		System.out.println(lm_io_code2name);
	}
	
	void testOnLinkedHashMapWithAccessOrder(String[] cityCodes, String[] cityNames) {
		LinkedHashMap<String, String> lm_ao_code2name = new LinkedHashMap<>(8, 0.9f, true); //创建一个access-order的LinkedHashMap
		for(int i = 0; i < cityCodes.length; i++) {
			lm_ao_code2name.put(cityCodes[i], cityNames[i]);
		}
		System.out.println("lm_ao_code2name的第一次输出（共" + lm_ao_code2name.size() + "个元素）：");
		System.out.println(lm_ao_code2name);
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //字母表
		String cityName = "";
		for(int i = 0; i < new Random().nextInt(alphabet.length()); i++) { //循环m次，m位于[0, 25]之间
			String cityCode = "黑" + alphabet.charAt(new Random().nextInt(alphabet.length())); //随机产生一个城市代码
			System.out.println(cityCode + "对应的城市名称是：" + ((cityName = lm_ao_code2name.get(cityCode)) == null ? "未知" : cityName));
		}
		
		System.out.println("lm_ao_code2name的第二次输出（共" + lm_ao_code2name.size() + "个元素）：");
		System.out.println(lm_ao_code2name);
	}
}