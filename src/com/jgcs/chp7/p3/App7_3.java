package com.jgcs.chp7.p3;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

class TomcatCache<K, V> {
	private final int size;
	private final Map<K, V> hots; //热集，将新创建的元素和最近使用的元素放在hots里
	private final Map<K, V> colds; //冷集，当hots满了后，将hots里的所有对象移动到colds里

	public TomcatCache(int size) {
		this.size = size;
		hots = new HashMap<K, V>(size);
		colds = new WeakHashMap<K, V>(); //colds是一个WeakHashMap，GC会及时清理其中的数据
	}

	public V get(K k) { //最新被使用的元素，必须要放在hots中
		V v = hots.get(k);
		if (v == null) {
			v = colds.get(k);
			if (v != null) {
				hots.put(k, v); //注意这里的k是参数中的k，与colds中与k匹配的(key, v)中的key不同，那里的key的类型是WeakReference<Object>
				colds.remove(k); //从colds中删除与k匹配的(key, v)键值对
			}
		}
		return v;
	}

	public void put(K k, V v) { //最新创建的元素，必须要放在hots中
		if (hots.size() >= size) {
			colds.putAll(hots);
			hots.clear();
		}
		hots.put(k, v);
	}

	@Override
	public String toString() {
		return "hots: " + hots + ", colds: " + colds;
	}
}

class Image{
	int no, width, height;
	byte[] data;
	public Image(int no) {
		this.no = no;
		width = 32;
		height = 32;
		data = new byte[1024];
	}
	
	@Override
	public String toString() {
		return "图片"+no;
	}
}

public class App7_3 {
	public static void main(String[] args) {
		HashMap<String, String> hm = new HashMap<>(32, 0.8f); //创建一个普通哈希映射
		hm.put("哈尔滨", "南岗区");
		hm.put("哈尔滨", "香坊区");
		hm.put("哈尔滨", "平房区");
		//哈希映射基于对象相等性即key1.equals(key2)来比较键，且重复插入具有“相同”键的不同键值对，会导致后插入的键值对覆盖先插入的键值对，因此hm中只有一个键值对：{哈尔滨=平房区}
		System.out.println("hm的第一次输出：" + hm);
		
		hm.clear();
		hm.put(new String("哈尔滨"), "南岗区");
		hm.put(new String("哈尔滨"), "香坊区");
		hm.put(new String("哈尔滨"), "平房区"); //哈希映射基于对象相等性来比较键
		System.out.println("hm的第二次输出：" + hm);
		
		hm.clear();
		hm.put("哈尔滨", "南岗区");
		hm.put(new String("哈尔滨"), "香坊区");
		hm.put(new String("哈尔滨"), "平房区"); //哈希映射基于对象相等性来比较键
		System.out.println("hm的第三次输出：" + hm + "\n");
		
		IdentityHashMap<String, String> ihm = new IdentityHashMap<>(8); //创建一个同一哈希映射
		ihm.put("哈尔滨", "南岗区");
		ihm.put("哈尔滨", "香坊区");
		ihm.put("哈尔滨", "平房区");
		//同一哈希映射基于引用相等性即key1==key2来比较键，且重复插入具有“相同”键的不同键值对，会导致后插入的键值对覆盖先插入的键值对。
		//由于"哈尔滨"是常量字符串，它有唯一的内存地址，因此这里ihm中只有一个键值对：{哈尔滨=平房区}
		System.out.println("ihm的第一次输出：" + ihm);
		
		ihm.clear();
		ihm.put(new String("哈尔滨"), "南岗区");
		ihm.put(new String("哈尔滨"), "香坊区");
		ihm.put(new String("哈尔滨"), "平房区");
		//同一哈希映射基于引用相等性即key1==key2来比较键，由于每次调用new String("哈尔滨")都会返回一个占据唯一内存地址的String对象，
		//因此这里ihm有三个键值对：{哈尔滨=香坊区, 哈尔滨=南岗区, 哈尔滨=平房区}
		System.out.println("ihm的第二次输出：" + ihm);
		
		ihm.clear();
		String str1 = null;
		ihm.put("哈尔滨", "南岗区");
		ihm.put(str1 = new String("哈尔滨"), "香坊区"); //将新建的String对象地址保存在str1中
		ihm.put(new String("哈尔滨"), "平房区");
		ihm.put("哈尔滨", "呼兰区");
		//由于"哈尔滨"是常量字符串，它占据固定且唯一的内存地址，而每次调用new String("哈尔滨")都会返回一个占据唯一内存地址的String对象，
		//且重复插入具有“相同”键的不同键值对，会导致后插入的键值对覆盖先插入的键值对，因此这里ihm有三个键值对：{哈尔滨=呼兰区, 哈尔滨=平房区, 哈尔滨=香坊区}
		System.out.println("ihm的第三次输出：" + ihm);
		
		//同一哈希映射基于引用相等性即key1==key2来比较键
		System.out.println("ihm.containsKey(\"哈尔滨\")：" + ihm.containsKey("哈尔滨"));
		System.out.println("ihm.containsKey(new String(\"哈尔滨\")：" + ihm.containsKey(new String("哈尔滨")));
		System.out.println("ihm.containsKey(str1)：" + ihm.containsKey(str1));
		
		//哈希映射基于对象相等性即key1.equals(key2)来比较键
		System.out.println("hm.containsKey(\"哈尔滨\")：" + hm.containsKey("哈尔滨"));
		System.out.println("hm.containsKey(new String(\"哈尔滨\")：" + hm.containsKey(new String("哈尔滨")));
		System.out.println("hm.containsKey(str1)：" + hm.containsKey(str1) + "\n");
		
		WeakHashMap<String, String> whm = new WeakHashMap<>(); //创建一个弱引用哈希映射
		whm.put("哈尔滨", "南岗区");
		whm.put(new String("哈尔滨"), "香坊区");
		whm.put(new String("哈尔滨"), "平房区");
		whm.put("哈尔滨", "呼兰区");
		//WeakHashMap<K, V>与HashMap<K, V>一样，都基于对象相等性即key1.equals(key2)来比较键，且重复插入具有“相同”键的不同键值对，
		//会导致后插入的键值对覆盖先插入的键值对，因此这里whm中只有一个键值对：{哈尔滨=呼兰区}
		System.out.println("whm的第一次输出：" + whm);
		
		whm.clear();
		whm.put("南岗区", "哈尔滨"); //用常量字符串"南岗区"作为键
		whm.put(new String("香坊区"), "哈尔滨"); //这里创建的String对象没有强引用变量指向它，只有弱引用变量key指向它
		whm.put(new String("平房区"), "哈尔滨"); //这里创建的String对象没有强引用变量指向它，只有弱引用变量key指向它
		whm.put("呼兰区", "哈尔滨"); //用常量字符串"呼兰区"作为键
		//同一哈希映射基于对象相等性即key1.equals(key2)来比较键，因此这里whm有四个键值对：{香坊区=哈尔滨, 呼兰区=哈尔滨, 平房区=哈尔滨, 南岗区=哈尔滨}
		System.out.println("whm的第二次输出：" + whm);
		
		//建议垃圾回收机制进行回收。由于垃圾回收机制并不是强制执行的，这里多调用几次，以此增加其执行概率
		for(int i = 0; i < 10; i++)
			System.gc();
		
		//进行垃圾回收后，没有强引用变量指向的键对象会被回收，进而导致该键所对应的键值对被删除，因此这里whm只有两个键值对：{呼兰区=哈尔滨, 南岗区=哈尔滨}
		System.out.println("whm的第三次输出（调用System.gc()后）：" + whm + "\n");
		
		TomcatCache<String, Image> tcache = new TomcatCache<>(3);
		
		tcache.put(new String("image1.jpg"), new Image(1));
		tcache.put(new String("image2.jpg"), new Image(2));
		tcache.put(new String("image3.jpg"), new Image(3));
		//现在所有键值对在tcache的hots中，colds为空
		System.out.println("tcache的第一次输出：" + tcache);
		
		//现在hots中内容被移动到colds中，hots中新插入一个(image4.jpg, 图片4)键值对
		tcache.put(new String("image4.jpg"), new Image(4));
		System.out.println("tcache的第二次输出：" + tcache);
		
		tcache.get(new String("image1.jpg"));
		//键值对(image1.jpg, 图片1)被访问，因此将它从colds中删除，并向hots中插入由新键k和旧值v构成的键值对(k, v)
		System.out.println("tcache的第三次输出：" + tcache);
		
		for(int i = 0; i < 10; i++)//建议垃圾回收机制进行回收
			System.gc();
		
		//进行垃圾回收后，colds中的所有键值对被删除，因为没有强引用变量指向的键对象会被回收，进而导致该键所对应的键值对被删除
		System.out.println("tcache的第四次输出（调用System.gc()后）：" + tcache);
		
		//如果在tcache中查找某个图片找不到，则意味着hots和colds中都没有与该图片对应的键值对，此时直接重建并插入tcache即可
		if(tcache.get("image2.jpg") == null) {
			tcache.put(new String("image2.jpg"), new Image(2));
		}
		System.out.println("tcache的第五次输出：" + tcache);
	}
}