package com.jgcs.chp7.p3;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

class TomcatCache<K, V> {
	private final int size;
	private final Map<K, V> hots; //�ȼ������´�����Ԫ�غ����ʹ�õ�Ԫ�ط���hots��
	private final Map<K, V> colds; //�伯����hots���˺󣬽�hots������ж����ƶ���colds��

	public TomcatCache(int size) {
		this.size = size;
		hots = new HashMap<K, V>(size);
		colds = new WeakHashMap<K, V>(); //colds��һ��WeakHashMap��GC�ἰʱ�������е�����
	}

	public V get(K k) { //���±�ʹ�õ�Ԫ�أ�����Ҫ����hots��
		V v = hots.get(k);
		if (v == null) {
			v = colds.get(k);
			if (v != null) {
				hots.put(k, v); //ע�������k�ǲ����е�k����colds����kƥ���(key, v)�е�key��ͬ�������key��������WeakReference<Object>
				colds.remove(k); //��colds��ɾ����kƥ���(key, v)��ֵ��
			}
		}
		return v;
	}

	public void put(K k, V v) { //���´�����Ԫ�أ�����Ҫ����hots��
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
		return "ͼƬ"+no;
	}
}

public class App7_3 {
	public static void main(String[] args) {
		HashMap<String, String> hm = new HashMap<>(32, 0.8f); //����һ����ͨ��ϣӳ��
		hm.put("������", "�ϸ���");
		hm.put("������", "�㷻��");
		hm.put("������", "ƽ����");
		//��ϣӳ����ڶ�������Լ�key1.equals(key2)���Ƚϼ������ظ�������С���ͬ�����Ĳ�ͬ��ֵ�ԣ��ᵼ�º����ļ�ֵ�Ը����Ȳ���ļ�ֵ�ԣ����hm��ֻ��һ����ֵ�ԣ�{������=ƽ����}
		System.out.println("hm�ĵ�һ�������" + hm);
		
		hm.clear();
		hm.put(new String("������"), "�ϸ���");
		hm.put(new String("������"), "�㷻��");
		hm.put(new String("������"), "ƽ����"); //��ϣӳ����ڶ�����������Ƚϼ�
		System.out.println("hm�ĵڶ��������" + hm);
		
		hm.clear();
		hm.put("������", "�ϸ���");
		hm.put(new String("������"), "�㷻��");
		hm.put(new String("������"), "ƽ����"); //��ϣӳ����ڶ�����������Ƚϼ�
		System.out.println("hm�ĵ����������" + hm + "\n");
		
		IdentityHashMap<String, String> ihm = new IdentityHashMap<>(8); //����һ��ͬһ��ϣӳ��
		ihm.put("������", "�ϸ���");
		ihm.put("������", "�㷻��");
		ihm.put("������", "ƽ����");
		//ͬһ��ϣӳ�������������Լ�key1==key2���Ƚϼ������ظ�������С���ͬ�����Ĳ�ͬ��ֵ�ԣ��ᵼ�º����ļ�ֵ�Ը����Ȳ���ļ�ֵ�ԡ�
		//����"������"�ǳ����ַ���������Ψһ���ڴ��ַ���������ihm��ֻ��һ����ֵ�ԣ�{������=ƽ����}
		System.out.println("ihm�ĵ�һ�������" + ihm);
		
		ihm.clear();
		ihm.put(new String("������"), "�ϸ���");
		ihm.put(new String("������"), "�㷻��");
		ihm.put(new String("������"), "ƽ����");
		//ͬһ��ϣӳ�������������Լ�key1==key2���Ƚϼ�������ÿ�ε���new String("������")���᷵��һ��ռ��Ψһ�ڴ��ַ��String����
		//�������ihm��������ֵ�ԣ�{������=�㷻��, ������=�ϸ���, ������=ƽ����}
		System.out.println("ihm�ĵڶ��������" + ihm);
		
		ihm.clear();
		String str1 = null;
		ihm.put("������", "�ϸ���");
		ihm.put(str1 = new String("������"), "�㷻��"); //���½���String�����ַ������str1��
		ihm.put(new String("������"), "ƽ����");
		ihm.put("������", "������");
		//����"������"�ǳ����ַ�������ռ�ݹ̶���Ψһ���ڴ��ַ����ÿ�ε���new String("������")���᷵��һ��ռ��Ψһ�ڴ��ַ��String����
		//���ظ�������С���ͬ�����Ĳ�ͬ��ֵ�ԣ��ᵼ�º����ļ�ֵ�Ը����Ȳ���ļ�ֵ�ԣ��������ihm��������ֵ�ԣ�{������=������, ������=ƽ����, ������=�㷻��}
		System.out.println("ihm�ĵ����������" + ihm);
		
		//ͬһ��ϣӳ�������������Լ�key1==key2���Ƚϼ�
		System.out.println("ihm.containsKey(\"������\")��" + ihm.containsKey("������"));
		System.out.println("ihm.containsKey(new String(\"������\")��" + ihm.containsKey(new String("������")));
		System.out.println("ihm.containsKey(str1)��" + ihm.containsKey(str1));
		
		//��ϣӳ����ڶ�������Լ�key1.equals(key2)���Ƚϼ�
		System.out.println("hm.containsKey(\"������\")��" + hm.containsKey("������"));
		System.out.println("hm.containsKey(new String(\"������\")��" + hm.containsKey(new String("������")));
		System.out.println("hm.containsKey(str1)��" + hm.containsKey(str1) + "\n");
		
		WeakHashMap<String, String> whm = new WeakHashMap<>(); //����һ�������ù�ϣӳ��
		whm.put("������", "�ϸ���");
		whm.put(new String("������"), "�㷻��");
		whm.put(new String("������"), "ƽ����");
		whm.put("������", "������");
		//WeakHashMap<K, V>��HashMap<K, V>һ���������ڶ�������Լ�key1.equals(key2)���Ƚϼ������ظ�������С���ͬ�����Ĳ�ͬ��ֵ�ԣ�
		//�ᵼ�º����ļ�ֵ�Ը����Ȳ���ļ�ֵ�ԣ��������whm��ֻ��һ����ֵ�ԣ�{������=������}
		System.out.println("whm�ĵ�һ�������" + whm);
		
		whm.clear();
		whm.put("�ϸ���", "������"); //�ó����ַ���"�ϸ���"��Ϊ��
		whm.put(new String("�㷻��"), "������"); //���ﴴ����String����û��ǿ���ñ���ָ������ֻ�������ñ���keyָ����
		whm.put(new String("ƽ����"), "������"); //���ﴴ����String����û��ǿ���ñ���ָ������ֻ�������ñ���keyָ����
		whm.put("������", "������"); //�ó����ַ���"������"��Ϊ��
		//ͬһ��ϣӳ����ڶ�������Լ�key1.equals(key2)���Ƚϼ����������whm���ĸ���ֵ�ԣ�{�㷻��=������, ������=������, ƽ����=������, �ϸ���=������}
		System.out.println("whm�ĵڶ��������" + whm);
		
		//�����������ջ��ƽ��л��ա������������ջ��Ʋ�����ǿ��ִ�еģ��������ü��Σ��Դ�������ִ�и���
		for(int i = 0; i < 10; i++)
			System.gc();
		
		//�����������պ�û��ǿ���ñ���ָ��ļ�����ᱻ���գ��������¸ü�����Ӧ�ļ�ֵ�Ա�ɾ�����������whmֻ��������ֵ�ԣ�{������=������, �ϸ���=������}
		System.out.println("whm�ĵ��������������System.gc()�󣩣�" + whm + "\n");
		
		TomcatCache<String, Image> tcache = new TomcatCache<>(3);
		
		tcache.put(new String("image1.jpg"), new Image(1));
		tcache.put(new String("image2.jpg"), new Image(2));
		tcache.put(new String("image3.jpg"), new Image(3));
		//�������м�ֵ����tcache��hots�У�coldsΪ��
		System.out.println("tcache�ĵ�һ�������" + tcache);
		
		//����hots�����ݱ��ƶ���colds�У�hots���²���һ��(image4.jpg, ͼƬ4)��ֵ��
		tcache.put(new String("image4.jpg"), new Image(4));
		System.out.println("tcache�ĵڶ��������" + tcache);
		
		tcache.get(new String("image1.jpg"));
		//��ֵ��(image1.jpg, ͼƬ1)�����ʣ���˽�����colds��ɾ��������hots�в������¼�k�;�ֵv���ɵļ�ֵ��(k, v)
		System.out.println("tcache�ĵ����������" + tcache);
		
		for(int i = 0; i < 10; i++)//�����������ջ��ƽ��л���
			System.gc();
		
		//�����������պ�colds�е����м�ֵ�Ա�ɾ������Ϊû��ǿ���ñ���ָ��ļ�����ᱻ���գ��������¸ü�����Ӧ�ļ�ֵ�Ա�ɾ��
		System.out.println("tcache�ĵ��Ĵ����������System.gc()�󣩣�" + tcache);
		
		//�����tcache�в���ĳ��ͼƬ�Ҳ���������ζ��hots��colds�ж�û�����ͼƬ��Ӧ�ļ�ֵ�ԣ���ʱֱ���ؽ�������tcache����
		if(tcache.get("image2.jpg") == null) {
			tcache.put(new String("image2.jpg"), new Image(2));
		}
		System.out.println("tcache�ĵ���������" + tcache);
	}
}