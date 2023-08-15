package com.jgcs.chp7.p1;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

class HaphaMap<K, V> { //haphazard map，即杂凑映射
	//静态成员变量、静态成员类和静态成员方法
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; //默认初始容量为16
	static final int MAXIMUM_CAPACITY = 1 << 30; //最大容量为2^30即1073741824
	static final float DEFAULT_LOAD_FACTOR = 0.75f; //默认负载因子为0.75
	
	static class Node<K,V> implements Map.Entry<K,V> {
	    final int hash;
	    final K key;
	    V value;
	    Node<K,V> next;

	    Node(int hash, K key, V value, Node<K,V> next) {
	        this.hash = hash;
	        this.key = key;
	        this.value = value;
	        this.next = next;
	    }

	    public final K getKey()        { return key; }
	    public final V getValue()      { return value; }
	    public final String toString() { return key + "=" + value; }

	    public final int hashCode() {
	        return  hash(key)^ hash(value);
	    }
	    
	    public final V setValue(V newValue) {
	        V oldValue = value;
	        value = newValue;
	        return oldValue;
	    }

	    public final boolean equals(Object o) {
	        if (o == this)
	            return true;
	        if (o instanceof Map.Entry) {
	            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
	            if (isEqual(key, e.getKey()) && isEqual(value, e.getValue()))
	                return true;
	        }
	        return false;
	    }
	    
	    private int hash(Object o) {
	    	return o != null ? o.hashCode() : 0;
	    }
	    
	    private boolean isEqual(Object a, Object b) {
	        return (a == b) || (a != null && a.equals(b));
	    }
	}
	
	static final int hash(Object key) {
		int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
	
	//实例成员变量
	Node<K,V>[] table; //HaphaMap的内部数据结构Node<K, V>数组table
	int size; //实际元素个数
	int capacity; //容量
	float loadFactor; //负载因子
	
	//实例成员方法
	//构造方法
	public HaphaMap() {
		this.capacity = DEFAULT_INITIAL_CAPACITY;
		this.loadFactor = DEFAULT_LOAD_FACTOR; //其他成员变量全部取默认值
	}
	
	public HaphaMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}
	
	public HaphaMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
	}
	
	public int size() {
        return size;
    }
	
	public boolean isEmpty() {
        return size == 0;
    }
	
	public V get(Object key) { //根据key获取与之对应的value
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
	
	//根据hash确定桶的位置，然后检查桶中的每个元素e的e.hash和e.key是否能够匹配参数hash和key
	final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab;
        Node<K,V> first, e;
        int n;
        K k;
        
        //根据hash找到桶tab[(n-1)&hash]，first指向桶中第一个元素
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
        	//首先检查first.hash和first.key是否能够与hash和key匹配
            if (first.hash == hash &&
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            
            //如果first不匹配，就遍历以first为首的链表中的其他元素（即键值对Map.Entry<K, V>对象）是否与hash和key匹配
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        //如果table为null或table为空或遍历完相应桶中所有元素仍找不到匹配的键值对，则返回null
        return null;
    }
	
	public boolean containsKey(Object key) { //检查当前映射是否有某个键值对的键等于key
        return getNode(hash(key), key) != null;
    }
	
	public V put(K key, V value) { //检查当前映射是否有某个键值对的值等于value
        return putVal(hash(key), key, value, false);
    }
	
	//插入或更新键值对(key, value)，onlyIfAbsent用来控制在当前映射已经包含与key对应的键值对(key, oldValue)时是否用value替换oldValue
	final V putVal(int hash, K key, V value, boolean onlyIfAbsent) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, i;
		
		//table为null或table为空，则需要调用resize()分配内存或扩展容量
		if ((tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length; //将n置为当前table的长度即容量
		
		//根据hash定位桶的位置即tab[i=(n-1)&hash]，如果桶中第一个元素p为null，则直接用hash、key和value创建一个Node或键值对，并将其插入到tab[i]位置处
		if ((p = tab[i = (n - 1) & hash]) == null)
			tab[i] = new Node<>(hash, key, value, null);
		else { //如果桶中第一个元素p不为null，则遍历以p为首的链表中的元素，检查是否有元素e其e.hash和e.key能够匹配hash和key
			Node<K, V> e;
			K k;
			
			//如果链表表头元素p能够匹配hash和key
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
				e = p;
			else { //遍历以p为首的链表中的剩余元素
				while(true) {
					//如果查找到链表的末尾仍然找不到与hash和key匹配的元素，则用hash、key和value新建一个Node，插入到链表末尾
					if ((e = p.next) == null) {
						p.next = new Node<>(hash, key, value, null);
						break;
					}
					
					//如果链表中的某个元素e与hash和key匹配
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
						break;
					
					p = e; //如果e不与hash和key匹配，则将p置为e，继续下一次循环
				}
			}
			
			//如果e不为null，则意味着e就是能够匹配hash和key匹配的元素
			if (e != null) {
				V oldValue = e.value;
				
				//如果onlyIfAbsent为false或者oldValue为null，则用value替换oldValue
				if (!onlyIfAbsent || oldValue == null)
					e.value = value;
				
				return oldValue;
			}
		}
		
		//如果执行流到达这里，则意味着在当前映射中找不到与hash和key匹配的元素，并上述代码已经用hash、key和value新建了一个Node且已将其插入到当前映射
		
		int threshold = getThreshold();
		
		//元素个数增1，并检查是否超过有capacity和loadFactor共同确定的门限值threshold
		if (++size > threshold)
			resize(); //如果超过，则调用resize()扩展容量
		
		return null;
	}
	
	//如果当前映射中存在与key对应的键值对(key, oldValue)，则删除该键值对，并返回oldValue，否则返回null
	public V remove(Object key) {
        Node<K,V> e;
        return (e = removeNode(hash(key), key, null, false)) == null ?
            null : e.value;
    }
	
	//删除键值对(key, value)。matchValue用于控制在查找到与hash和key匹配的元素e时，是否检查e.value与value是否匹配
	final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, index;
		
		//如果table不为null且table.length>0且由hash确定的桶table[index=(n-1)&hash]的第一个元素p不为null，则对以p为首的链表进行搜索
		if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
			Node<K, V> node = null, e;
			K k;
			V v;
			
			//如果链表表头元素p即为所求
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
				node = p;
			else if ((e = p.next) != null) { //如果表头元素p不匹配hash和key，则遍历链表中的剩余元素
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						node = e;
						break;
					}
					
					//将p置为e，这样在每次循环中p都指向当前元素e的前一个元素
					p = e;
				} while ((e = e.next) != null);
			}
			//如果node不为null，则node即为所求，执行链表删除操作
			//（这里如果matchValue为false即不检查值是否匹配的话，则&&操作符的第二个条件直接为true，不会再去检查node.value==value和(value != null && value.equals(v))）
			if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
				//如果node==p，则意味着node是表头元素，将表头元素置为node的下一个元素，即删除node
				if (node == p)
					tab[index] = node.next;
				else
					p.next = node.next;
				
				--size; //元素个数减1
				return node;
			}
		}
		//否则，直接返回null
		return null;
	}
	
	public void clear() { //清空当前映射，即删除所有键值对
        Node<K,V>[] tab;
        
        if ((tab = table) != null && size > 0) {
            size = 0;
            
            //通过将tab[i]桶所对应的链表表头元素置空，使所有在该链表中的元素变成不可达元素，垃圾回收器GC会在适当时候回收它们占用的内存
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }
	
	public boolean containsValue(Object value) {
        Node<K,V>[] tab;
        V v;
        
        if ((tab = table) != null && size > 0) { //table不为null且不为空
            for (int i = 0; i < tab.length; ++i) { //遍历所有桶
            	//遍历每个桶对应的链表中的所有元素
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    if ((v = e.value) == value ||
                        (value != null && value.equals(v)))
                        return true;
                }
            }
        }
        
        return false;
    }
	
	public V getOrDefault(Object key, V defaultValue) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }
	
	public V putIfAbsent(K key, V value) {
		//putVal()的onlyIfAbsent为true，则只有在当前映射中找不到与key对应的键值对时，才将(key, value)插入
        return putVal(hash(key), key, value, true);
    }
	
	public boolean remove(Object key, Object value) {
		//removeNode()的matchValue为true，则在当前映射中找到与key对应的键值对(key, v)时，还要检查v是否与value匹配，只有匹配时才删除(key, v)
        return removeNode(hash(key), key, value, true) != null;
    }
	
	public boolean replace(K key, V oldValue, V newValue) {
        Node<K,V> e;
        V v;
        
        if ((e = getNode(hash(key), key)) != null &&
            ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) { //==比较内存地址，equals()比较内容
            e.value = newValue;
            return true;
        }
        
        return false;
    }
	
	public V replace(K key, V value) {
        Node<K,V> e;
        
        if ((e = getNode(hash(key), key)) != null) {
            V oldValue = e.value;
            e.value = value;
            return oldValue;
        }
        
        return null;
    }
	
	//如果在当前映射存在与key对应的oldValue（如果不存在与key对象的值，即不存在以key为键的键值对，则oldValue取值null），
	//则利用键值对old=(key, oldValue)作为remappingFunction为key计算新值newValue并返回newValue
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		if (remappingFunction == null)
			throw new NullPointerException();
		int hash = hash(key); //计算key的哈希码
		Node<K, V>[] tab;
		Node<K, V> first;
		int n, i;
		Node<K, V> old = null;
		
		//如果table为null或table为空，则调用resize()分配内存或扩展容量
		if ((tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length;
		
		//根据key的哈希码hash定位桶的位置tab[i=(n-1)&hash]，如果桶中第一个元素first不为null
		if ((first = tab[i = (n - 1) & hash]) != null) {
			Node<K, V> e = first;
			K k;
			
			//则遍历该桶所对应的链表中的所有元素，寻找能够与key匹配的键值对e=(key, value)
			do {
				if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
					old = e;
					break;
				}
			} while ((e = e.next) != null);
		}
		
		//old指向与key匹配的键值对e=(key, value)。如果old为null，则e不存在，此时令oldValue为null
		V oldValue = (old == null) ? null : old.value;
		
		//利用key和oldValue作为remappingFunctiond的输入为key计算新值v
		V v = remappingFunction.apply(key, oldValue); 
		
		
		if (old != null) { //如果当前映射中存在与key匹配的键值对(key, value)
			if (v != null) { //如果新值v不为null，则用v替换value
				old.value = v;
			} else { //如果新值v为null，则删除键值对(key, value)
				removeNode(hash, key, null, false);
			}
		} else if (v != null) { //如果(key, value)不存在且新值v不为null，则用hash、key和新值v创建一个Node并插入到桶tab[i]对应的链表表头处
			tab[i] = new Node<>(hash, key, v, first); //该Node的next为first，而first此时为null
			++size; //元素个数增1，但不用检查是否超过门限值，因为该Node放置在table中原本为null的桶中，即只是占用一个已经存在的内存空间
		}
		return v;
	}
	
	//如果key对应的值为null，则利用mappingFunction为key计算一个新值newValue，如果newValue不为空，
	//则将键值对(key, newValue)插入当前映射
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		if (mappingFunction == null)
			throw new NullPointerException();
		V v;
		if ((v = get(key)) == null) {
			V newValue;
			if ((newValue = mappingFunction.apply(key)) != null) {
				put(key, newValue);
				return newValue;
			}
		}
		return v;
	}
	
	//如果key对应的值oldValue不是null，则利用(key, oldValue)作为remappingFunction的输入为key计算新值newValue，
	//如果它非null，则用它替换oldValue；否则删除(key, oldValue)对
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		if (remappingFunction == null)
			throw new NullPointerException();
		
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }
	
	//如果key对应的值oldValue不存在或者为null，则用value替代之并返回value；否则即oldValue存在且不为null，
	//则利用oldValue和value作为remappingFunction的输入为key计算新值newValue。
	//如果newValue为null，则删除(key, oldValue)并返回null，否则用newValue替换oldValue并返回newValue
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if(remappingFunction == null || value == null)
        	throw new NullPointerException();
        
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                   remappingFunction.apply(oldValue, value);
        if(newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
    }
	
	public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K,V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next)
                    action.accept(e.key, e.value);
            }
        }
    }
	
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Node<K,V>[] tab;
        if (function == null)
            throw new NullPointerException();
        
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) { //遍历所有桶
            	//只要桶不空，就为桶所对应的链表中的每个键值对(key, value)计算新值v，并用v替换value
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    e.value = function.apply(e.key, e.value);
                }
            }
        }
    }
	
	private int getThreshold() {
		return (int)(loadFactor * capacity);
	}
	
	//为table分配内存或扩展容量
	private Node<K, V>[] resize() {
		Node<K, V>[] oldTab = table;
		
		//将oldCap置为0或者table中元素的个数
		int oldCap = (oldTab == null) ? 0 : oldTab.length;
		
		int newCap = 0;
		if (oldCap > 0) { //如果table不空的话，oldCap会大于0
			if(oldCap >= Integer.MAX_VALUE) {
				String msg = "The current capacity is larger than or equal to Integer.MAX_VALUE. Further capacity expansion is not allowed!";
				throw new Error(msg);
			} else if(oldCap >= MAXIMUM_CAPACITY) {
				newCap = Integer.MAX_VALUE;
			} else
				newCap = oldCap << 1; // 将旧容量翻倍作为新容量
		}
		else { //如果table为空即oldCap为0，此时newCap使用默认值 DEFAULT_INITIAL_CAPACITY
			newCap = DEFAULT_INITIAL_CAPACITY;
		}
		
		@SuppressWarnings({"unchecked"})
		//使用新容量newCap创建新的Node<K, V>数组
		Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap]; 
		table = newTab; //将当前映射的内部数据结构table置为新建的数组
		capacity = newCap; //将容量置为新容量
		
		if (oldTab != null) { //将旧数组中的元素复制到新数组
			for (int j = 0; j < oldCap; ++j) { //遍历所有旧桶
				Node<K, V> e;
				if ((e = oldTab[j]) != null) { //如果当前旧桶不空
					oldTab[j] = null;
					
					//如果当前桶只有一个元素e，则利用e.hash定位e在新数组newTab中所应放置的桶即newTab[e.hash&(newCap-1)]，并将e放置在新数组的相应桶中
					if (e.next == null)
						newTab[e.hash & (newCap - 1)] = e;
					//如果当前旧桶中有多个元素，则将该旧桶所对应的链表分裂成两个链表：放在新数组newTab中位于[0, oldCap-1]范围内的某个newTab[i]桶中的链表lowLink
					//和放在新数组newTab中位于[oldCap, newCap-1]范围内的某个newTab[i]桶中的链表highLink
					else {
						Node<K, V> loHead = null, loTail = null;
						Node<K, V> hiHead = null, hiTail = null;
						Node<K, V> next;
						
						//遍历当前旧桶对应的链表中的所有元素，e指向当前元素，next指向e的下一个元素
						do {
							next = e.next;
							
							//如果e.hash&oldCap == 0，则e应放在lowLink链表中
							if ((e.hash & oldCap) == 0) {
								if (loTail == null) //如果lowLink的表尾loTail为null
									loHead = e; //则将lowLink的表头loHead置为e
								else //如果loTail不为null
									loTail.next = e; //则将e置为loTail的下一个元素
								loTail = e; //让loTail指向当前元素e
							} else { //否则，e应放在highLink链表中
								if (hiTail == null)
									hiHead = e;
								else
									hiTail.next = e;
								hiTail = e;
							}
						} while ((e = next) != null);
						
						//如果lowLink不空的话，即loTail不为null，则将lowLink的表头元素loHead放在newTab[j]桶中
						if (loTail != null) {
							loTail.next = null;
							newTab[j] = loHead;
						}
						
						//如果highLink不空的话，即hiTail不为null，则将highLink的表头元素hiHead放在newTab[j + oldCap]桶中
						if (hiTail != null) {
							hiTail.next = null;
							newTab[j + oldCap] = hiHead;
						}
					}
				}
			}
		}
		return newTab; //返回新数组
	}

	@Override
	public String toString() {
		if(table == null || table.length == 0) {
			return "{}";
		}
		Node<K, V> node = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(int i = 0; i < table.length; i++) {
			if((node = table[i]) != null) {
				do {
					sb.append(node.getKey() + "=" + node.getValue() + ", ");
				} while((node = node.next) != null);
			}
		}
		sb.replace(sb.length()-", ".length(), sb.length(), "");
		sb.append("}");
		return sb.toString();
	}
}

public class App7_1 {
	public static void main(String[] args) {
		//中国各省行政代码Codes for Administrative Divisions
		Integer[] divisionCodes = {110000, 120000, 130000, 140000, 150000, 
				210000, 220000, 230000, 
				310000, 320000, 330000, 340000, 350000, 360000, 370000, 
				410000, 420000, 430000, 440000, 450000, 460000,
				500000, 510000, 520000, 530000, 540000, 
				610000, 620000, 630000, 640000, 650000, 660000, 
				710000, 810000, 820000};
		
		//中国各省行政名称Names for Administrative Divisions
		String[] divisionNames = {"北京市", "天津市", "河北省", "山西省", "内蒙古自治区", 
				"辽宁省", "吉林省", "黑龙江省", 
				"上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", 
				"河南省", "湖北省", "湖南省", "广东省", "广西壮族自治区", "海南省", 
				"重庆市", "四川省", "贵州省", "云南省", "西藏自治区", 
				"陕西省", "甘肃省", "青海省", "宁夏回族自治区", "新疆维吾尔自治区", "新疆生产建设兵团", 
				"台湾省", "香港特别行政区", "澳门特别行政区"};
		
		HaphaMap<Integer, String> code2name = new HaphaMap<>();
		for(int i = 0; i < divisionCodes.length; i++) {
			code2name.put(divisionCodes[i], divisionNames[i]);
		}
		System.out.println("code2name的第一次输出（共" + code2name.size() + "个元素）：");
		System.out.println(code2name + "\n");
		
		HaphaMap<String, Integer> name2code = new HaphaMap<>(32, 0.8f);
		for(int i = 0; i < divisionCodes.length; i++) {
			name2code.put(divisionNames[i], divisionCodes[i]);
		}
		System.out.println("name2code的第一次输出（共" + name2code.size() + "个元素）：");
		System.out.println(name2code + "\n");
		
		String province = "黑龙江省";
		Integer oldCode = -1, newCode = -1;
		System.out.println(province + "的行政代码是：" + (oldCode = name2code.get(province)) + "\n");
		
		newCode = 280000;
		oldCode = name2code.replace(province, newCode);
		System.out.println(province + "的行政代码已从" + oldCode + "改为" + name2code.get(province) + "\n");
		
		if(name2code.replace(province, newCode, oldCode)) {
			System.out.println(province + "的行政代码已从" + newCode + "改回" + name2code.get(province) + "\n");
		}
		
		name2code.compute(province, (k, v) -> {return (k.hashCode() + v.hashCode()) & 200000;});
		System.out.println(province + "的行政代码是：" + (oldCode = name2code.get(province)) + "\n");
		
		name2code.remove(province);
		System.out.println(province + "的行政代码是：" + ((oldCode = name2code.get(province)) == null ? "未知" : oldCode) + "\n");
		
		System.out.println("name2code的第二次输出（共" + name2code.size() + "个元素）：");
		System.out.println(name2code + "\n");
		
		oldCode = 230000;
		name2code.putIfAbsent(province, oldCode);
		System.out.println(province + "的行政代码是：" + ((oldCode = name2code.get(province)) == null ? "未知" : oldCode) + "\n");
		
		final int finalCode = newCode; //280000
		name2code.computeIfAbsent(province, k -> {return finalCode;});
		System.out.println(province + "的行政代码是：" + ((oldCode = name2code.get(province)) == null ? "未知" : oldCode) + "\n");
		
		name2code.computeIfPresent(province, (k, v) -> {return finalCode;});
		System.out.println(province + "的行政代码是：" + ((oldCode = name2code.get(province)) == null ? "未知" : oldCode) + "\n");
	}
}