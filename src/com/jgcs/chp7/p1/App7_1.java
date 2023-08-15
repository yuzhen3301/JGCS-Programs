package com.jgcs.chp7.p1;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

class HaphaMap<K, V> { //haphazard map�����Ӵ�ӳ��
	//��̬��Ա��������̬��Ա��;�̬��Ա����
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; //Ĭ�ϳ�ʼ����Ϊ16
	static final int MAXIMUM_CAPACITY = 1 << 30; //�������Ϊ2^30��1073741824
	static final float DEFAULT_LOAD_FACTOR = 0.75f; //Ĭ�ϸ�������Ϊ0.75
	
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
	
	//ʵ����Ա����
	Node<K,V>[] table; //HaphaMap���ڲ����ݽṹNode<K, V>����table
	int size; //ʵ��Ԫ�ظ���
	int capacity; //����
	float loadFactor; //��������
	
	//ʵ����Ա����
	//���췽��
	public HaphaMap() {
		this.capacity = DEFAULT_INITIAL_CAPACITY;
		this.loadFactor = DEFAULT_LOAD_FACTOR; //������Ա����ȫ��ȡĬ��ֵ
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
	
	public V get(Object key) { //����key��ȡ��֮��Ӧ��value
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
	
	//����hashȷ��Ͱ��λ�ã�Ȼ����Ͱ�е�ÿ��Ԫ��e��e.hash��e.key�Ƿ��ܹ�ƥ�����hash��key
	final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab;
        Node<K,V> first, e;
        int n;
        K k;
        
        //����hash�ҵ�Ͱtab[(n-1)&hash]��firstָ��Ͱ�е�һ��Ԫ��
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
        	//���ȼ��first.hash��first.key�Ƿ��ܹ���hash��keyƥ��
            if (first.hash == hash &&
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            
            //���first��ƥ�䣬�ͱ�����firstΪ�׵������е�����Ԫ�أ�����ֵ��Map.Entry<K, V>�����Ƿ���hash��keyƥ��
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        //���tableΪnull��tableΪ�ջ��������ӦͰ������Ԫ�����Ҳ���ƥ��ļ�ֵ�ԣ��򷵻�null
        return null;
    }
	
	public boolean containsKey(Object key) { //��鵱ǰӳ���Ƿ���ĳ����ֵ�Եļ�����key
        return getNode(hash(key), key) != null;
    }
	
	public V put(K key, V value) { //��鵱ǰӳ���Ƿ���ĳ����ֵ�Ե�ֵ����value
        return putVal(hash(key), key, value, false);
    }
	
	//�������¼�ֵ��(key, value)��onlyIfAbsent���������ڵ�ǰӳ���Ѿ�������key��Ӧ�ļ�ֵ��(key, oldValue)ʱ�Ƿ���value�滻oldValue
	final V putVal(int hash, K key, V value, boolean onlyIfAbsent) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, i;
		
		//tableΪnull��tableΪ�գ�����Ҫ����resize()�����ڴ����չ����
		if ((tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length; //��n��Ϊ��ǰtable�ĳ��ȼ�����
		
		//����hash��λͰ��λ�ü�tab[i=(n-1)&hash]�����Ͱ�е�һ��Ԫ��pΪnull����ֱ����hash��key��value����һ��Node���ֵ�ԣ���������뵽tab[i]λ�ô�
		if ((p = tab[i = (n - 1) & hash]) == null)
			tab[i] = new Node<>(hash, key, value, null);
		else { //���Ͱ�е�һ��Ԫ��p��Ϊnull���������pΪ�׵������е�Ԫ�أ�����Ƿ���Ԫ��e��e.hash��e.key�ܹ�ƥ��hash��key
			Node<K, V> e;
			K k;
			
			//��������ͷԪ��p�ܹ�ƥ��hash��key
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
				e = p;
			else { //������pΪ�׵������е�ʣ��Ԫ��
				while(true) {
					//������ҵ������ĩβ��Ȼ�Ҳ�����hash��keyƥ���Ԫ�أ�����hash��key��value�½�һ��Node�����뵽����ĩβ
					if ((e = p.next) == null) {
						p.next = new Node<>(hash, key, value, null);
						break;
					}
					
					//��������е�ĳ��Ԫ��e��hash��keyƥ��
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
						break;
					
					p = e; //���e����hash��keyƥ�䣬��p��Ϊe��������һ��ѭ��
				}
			}
			
			//���e��Ϊnull������ζ��e�����ܹ�ƥ��hash��keyƥ���Ԫ��
			if (e != null) {
				V oldValue = e.value;
				
				//���onlyIfAbsentΪfalse����oldValueΪnull������value�滻oldValue
				if (!onlyIfAbsent || oldValue == null)
					e.value = value;
				
				return oldValue;
			}
		}
		
		//���ִ���������������ζ���ڵ�ǰӳ�����Ҳ�����hash��keyƥ���Ԫ�أ������������Ѿ���hash��key��value�½���һ��Node���ѽ�����뵽��ǰӳ��
		
		int threshold = getThreshold();
		
		//Ԫ�ظ�����1��������Ƿ񳬹���capacity��loadFactor��ͬȷ��������ֵthreshold
		if (++size > threshold)
			resize(); //��������������resize()��չ����
		
		return null;
	}
	
	//�����ǰӳ���д�����key��Ӧ�ļ�ֵ��(key, oldValue)����ɾ���ü�ֵ�ԣ�������oldValue�����򷵻�null
	public V remove(Object key) {
        Node<K,V> e;
        return (e = removeNode(hash(key), key, null, false)) == null ?
            null : e.value;
    }
	
	//ɾ����ֵ��(key, value)��matchValue���ڿ����ڲ��ҵ���hash��keyƥ���Ԫ��eʱ���Ƿ���e.value��value�Ƿ�ƥ��
	final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue) {
		Node<K, V>[] tab;
		Node<K, V> p;
		int n, index;
		
		//���table��Ϊnull��table.length>0����hashȷ����Ͱtable[index=(n-1)&hash]�ĵ�һ��Ԫ��p��Ϊnull�������pΪ�׵������������
		if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
			Node<K, V> node = null, e;
			K k;
			V v;
			
			//��������ͷԪ��p��Ϊ����
			if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
				node = p;
			else if ((e = p.next) != null) { //�����ͷԪ��p��ƥ��hash��key������������е�ʣ��Ԫ��
				do {
					if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
						node = e;
						break;
					}
					
					//��p��Ϊe��������ÿ��ѭ����p��ָ��ǰԪ��e��ǰһ��Ԫ��
					p = e;
				} while ((e = e.next) != null);
			}
			//���node��Ϊnull����node��Ϊ����ִ������ɾ������
			//���������matchValueΪfalse�������ֵ�Ƿ�ƥ��Ļ�����&&�������ĵڶ�������ֱ��Ϊtrue��������ȥ���node.value==value��(value != null && value.equals(v))��
			if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
				//���node==p������ζ��node�Ǳ�ͷԪ�أ�����ͷԪ����Ϊnode����һ��Ԫ�أ���ɾ��node
				if (node == p)
					tab[index] = node.next;
				else
					p.next = node.next;
				
				--size; //Ԫ�ظ�����1
				return node;
			}
		}
		//����ֱ�ӷ���null
		return null;
	}
	
	public void clear() { //��յ�ǰӳ�䣬��ɾ�����м�ֵ��
        Node<K,V>[] tab;
        
        if ((tab = table) != null && size > 0) {
            size = 0;
            
            //ͨ����tab[i]Ͱ����Ӧ�������ͷԪ���ÿգ�ʹ�����ڸ������е�Ԫ�ر�ɲ��ɴ�Ԫ�أ�����������GC�����ʵ�ʱ���������ռ�õ��ڴ�
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }
	
	public boolean containsValue(Object value) {
        Node<K,V>[] tab;
        V v;
        
        if ((tab = table) != null && size > 0) { //table��Ϊnull�Ҳ�Ϊ��
            for (int i = 0; i < tab.length; ++i) { //��������Ͱ
            	//����ÿ��Ͱ��Ӧ�������е�����Ԫ��
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
		//putVal()��onlyIfAbsentΪtrue����ֻ���ڵ�ǰӳ�����Ҳ�����key��Ӧ�ļ�ֵ��ʱ���Ž�(key, value)����
        return putVal(hash(key), key, value, true);
    }
	
	public boolean remove(Object key, Object value) {
		//removeNode()��matchValueΪtrue�����ڵ�ǰӳ�����ҵ���key��Ӧ�ļ�ֵ��(key, v)ʱ����Ҫ���v�Ƿ���valueƥ�䣬ֻ��ƥ��ʱ��ɾ��(key, v)
        return removeNode(hash(key), key, value, true) != null;
    }
	
	public boolean replace(K key, V oldValue, V newValue) {
        Node<K,V> e;
        V v;
        
        if ((e = getNode(hash(key), key)) != null &&
            ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) { //==�Ƚ��ڴ��ַ��equals()�Ƚ�����
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
	
	//����ڵ�ǰӳ�������key��Ӧ��oldValue�������������key�����ֵ������������keyΪ���ļ�ֵ�ԣ���oldValueȡֵnull����
	//�����ü�ֵ��old=(key, oldValue)��ΪremappingFunctionΪkey������ֵnewValue������newValue
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		if (remappingFunction == null)
			throw new NullPointerException();
		int hash = hash(key); //����key�Ĺ�ϣ��
		Node<K, V>[] tab;
		Node<K, V> first;
		int n, i;
		Node<K, V> old = null;
		
		//���tableΪnull��tableΪ�գ������resize()�����ڴ����չ����
		if ((tab = table) == null || (n = tab.length) == 0)
			n = (tab = resize()).length;
		
		//����key�Ĺ�ϣ��hash��λͰ��λ��tab[i=(n-1)&hash]�����Ͱ�е�һ��Ԫ��first��Ϊnull
		if ((first = tab[i = (n - 1) & hash]) != null) {
			Node<K, V> e = first;
			K k;
			
			//�������Ͱ����Ӧ�������е�����Ԫ�أ�Ѱ���ܹ���keyƥ��ļ�ֵ��e=(key, value)
			do {
				if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
					old = e;
					break;
				}
			} while ((e = e.next) != null);
		}
		
		//oldָ����keyƥ��ļ�ֵ��e=(key, value)�����oldΪnull����e�����ڣ���ʱ��oldValueΪnull
		V oldValue = (old == null) ? null : old.value;
		
		//����key��oldValue��ΪremappingFunctiond������Ϊkey������ֵv
		V v = remappingFunction.apply(key, oldValue); 
		
		
		if (old != null) { //�����ǰӳ���д�����keyƥ��ļ�ֵ��(key, value)
			if (v != null) { //�����ֵv��Ϊnull������v�滻value
				old.value = v;
			} else { //�����ֵvΪnull����ɾ����ֵ��(key, value)
				removeNode(hash, key, null, false);
			}
		} else if (v != null) { //���(key, value)����������ֵv��Ϊnull������hash��key����ֵv����һ��Node�����뵽Ͱtab[i]��Ӧ�������ͷ��
			tab[i] = new Node<>(hash, key, v, first); //��Node��nextΪfirst����first��ʱΪnull
			++size; //Ԫ�ظ�����1�������ü���Ƿ񳬹�����ֵ����Ϊ��Node������table��ԭ��Ϊnull��Ͱ�У���ֻ��ռ��һ���Ѿ����ڵ��ڴ�ռ�
		}
		return v;
	}
	
	//���key��Ӧ��ֵΪnull��������mappingFunctionΪkey����һ����ֵnewValue�����newValue��Ϊ�գ�
	//�򽫼�ֵ��(key, newValue)���뵱ǰӳ��
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
	
	//���key��Ӧ��ֵoldValue����null��������(key, oldValue)��ΪremappingFunction������Ϊkey������ֵnewValue��
	//�������null���������滻oldValue������ɾ��(key, oldValue)��
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
	
	//���key��Ӧ��ֵoldValue�����ڻ���Ϊnull������value���֮������value������oldValue�����Ҳ�Ϊnull��
	//������oldValue��value��ΪremappingFunction������Ϊkey������ֵnewValue��
	//���newValueΪnull����ɾ��(key, oldValue)������null��������newValue�滻oldValue������newValue
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
            for (int i = 0; i < tab.length; ++i) { //��������Ͱ
            	//ֻҪͰ���գ���ΪͰ����Ӧ�������е�ÿ����ֵ��(key, value)������ֵv������v�滻value
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    e.value = function.apply(e.key, e.value);
                }
            }
        }
    }
	
	private int getThreshold() {
		return (int)(loadFactor * capacity);
	}
	
	//Ϊtable�����ڴ����չ����
	private Node<K, V>[] resize() {
		Node<K, V>[] oldTab = table;
		
		//��oldCap��Ϊ0����table��Ԫ�صĸ���
		int oldCap = (oldTab == null) ? 0 : oldTab.length;
		
		int newCap = 0;
		if (oldCap > 0) { //���table���յĻ���oldCap�����0
			if(oldCap >= Integer.MAX_VALUE) {
				String msg = "The current capacity is larger than or equal to Integer.MAX_VALUE. Further capacity expansion is not allowed!";
				throw new Error(msg);
			} else if(oldCap >= MAXIMUM_CAPACITY) {
				newCap = Integer.MAX_VALUE;
			} else
				newCap = oldCap << 1; // ��������������Ϊ������
		}
		else { //���tableΪ�ռ�oldCapΪ0����ʱnewCapʹ��Ĭ��ֵ DEFAULT_INITIAL_CAPACITY
			newCap = DEFAULT_INITIAL_CAPACITY;
		}
		
		@SuppressWarnings({"unchecked"})
		//ʹ��������newCap�����µ�Node<K, V>����
		Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap]; 
		table = newTab; //����ǰӳ����ڲ����ݽṹtable��Ϊ�½�������
		capacity = newCap; //��������Ϊ������
		
		if (oldTab != null) { //���������е�Ԫ�ظ��Ƶ�������
			for (int j = 0; j < oldCap; ++j) { //�������о�Ͱ
				Node<K, V> e;
				if ((e = oldTab[j]) != null) { //�����ǰ��Ͱ����
					oldTab[j] = null;
					
					//�����ǰͰֻ��һ��Ԫ��e��������e.hash��λe��������newTab����Ӧ���õ�Ͱ��newTab[e.hash&(newCap-1)]������e���������������ӦͰ��
					if (e.next == null)
						newTab[e.hash & (newCap - 1)] = e;
					//�����ǰ��Ͱ���ж��Ԫ�أ��򽫸þ�Ͱ����Ӧ��������ѳ�������������������newTab��λ��[0, oldCap-1]��Χ�ڵ�ĳ��newTab[i]Ͱ�е�����lowLink
					//�ͷ���������newTab��λ��[oldCap, newCap-1]��Χ�ڵ�ĳ��newTab[i]Ͱ�е�����highLink
					else {
						Node<K, V> loHead = null, loTail = null;
						Node<K, V> hiHead = null, hiTail = null;
						Node<K, V> next;
						
						//������ǰ��Ͱ��Ӧ�������е�����Ԫ�أ�eָ��ǰԪ�أ�nextָ��e����һ��Ԫ��
						do {
							next = e.next;
							
							//���e.hash&oldCap == 0����eӦ����lowLink������
							if ((e.hash & oldCap) == 0) {
								if (loTail == null) //���lowLink�ı�βloTailΪnull
									loHead = e; //��lowLink�ı�ͷloHead��Ϊe
								else //���loTail��Ϊnull
									loTail.next = e; //��e��ΪloTail����һ��Ԫ��
								loTail = e; //��loTailָ��ǰԪ��e
							} else { //����eӦ����highLink������
								if (hiTail == null)
									hiHead = e;
								else
									hiTail.next = e;
								hiTail = e;
							}
						} while ((e = next) != null);
						
						//���lowLink���յĻ�����loTail��Ϊnull����lowLink�ı�ͷԪ��loHead����newTab[j]Ͱ��
						if (loTail != null) {
							loTail.next = null;
							newTab[j] = loHead;
						}
						
						//���highLink���յĻ�����hiTail��Ϊnull����highLink�ı�ͷԪ��hiHead����newTab[j + oldCap]Ͱ��
						if (hiTail != null) {
							hiTail.next = null;
							newTab[j + oldCap] = hiHead;
						}
					}
				}
			}
		}
		return newTab; //����������
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
		//�й���ʡ��������Codes for Administrative Divisions
		Integer[] divisionCodes = {110000, 120000, 130000, 140000, 150000, 
				210000, 220000, 230000, 
				310000, 320000, 330000, 340000, 350000, 360000, 370000, 
				410000, 420000, 430000, 440000, 450000, 460000,
				500000, 510000, 520000, 530000, 540000, 
				610000, 620000, 630000, 640000, 650000, 660000, 
				710000, 810000, 820000};
		
		//�й���ʡ��������Names for Administrative Divisions
		String[] divisionNames = {"������", "�����", "�ӱ�ʡ", "ɽ��ʡ", "���ɹ�������", 
				"����ʡ", "����ʡ", "������ʡ", 
				"�Ϻ���", "����ʡ", "�㽭ʡ", "����ʡ", "����ʡ", "����ʡ", "ɽ��ʡ", 
				"����ʡ", "����ʡ", "����ʡ", "�㶫ʡ", "����׳��������", "����ʡ", 
				"������", "�Ĵ�ʡ", "����ʡ", "����ʡ", "����������", 
				"����ʡ", "����ʡ", "�ຣʡ", "���Ļ���������", "�½�ά���������", "�½������������", 
				"̨��ʡ", "����ر�������", "�����ر�������"};
		
		HaphaMap<Integer, String> code2name = new HaphaMap<>();
		for(int i = 0; i < divisionCodes.length; i++) {
			code2name.put(divisionCodes[i], divisionNames[i]);
		}
		System.out.println("code2name�ĵ�һ���������" + code2name.size() + "��Ԫ�أ���");
		System.out.println(code2name + "\n");
		
		HaphaMap<String, Integer> name2code = new HaphaMap<>(32, 0.8f);
		for(int i = 0; i < divisionCodes.length; i++) {
			name2code.put(divisionNames[i], divisionCodes[i]);
		}
		System.out.println("name2code�ĵ�һ���������" + name2code.size() + "��Ԫ�أ���");
		System.out.println(name2code + "\n");
		
		String province = "������ʡ";
		Integer oldCode = -1, newCode = -1;
		System.out.println(province + "�����������ǣ�" + (oldCode = name2code.get(province)) + "\n");
		
		newCode = 280000;
		oldCode = name2code.replace(province, newCode);
		System.out.println(province + "�����������Ѵ�" + oldCode + "��Ϊ" + name2code.get(province) + "\n");
		
		if(name2code.replace(province, newCode, oldCode)) {
			System.out.println(province + "�����������Ѵ�" + newCode + "�Ļ�" + name2code.get(province) + "\n");
		}
		
		name2code.compute(province, (k, v) -> {return (k.hashCode() + v.hashCode()) & 200000;});
		System.out.println(province + "�����������ǣ�" + (oldCode = name2code.get(province)) + "\n");
		
		name2code.remove(province);
		System.out.println(province + "�����������ǣ�" + ((oldCode = name2code.get(province)) == null ? "δ֪" : oldCode) + "\n");
		
		System.out.println("name2code�ĵڶ����������" + name2code.size() + "��Ԫ�أ���");
		System.out.println(name2code + "\n");
		
		oldCode = 230000;
		name2code.putIfAbsent(province, oldCode);
		System.out.println(province + "�����������ǣ�" + ((oldCode = name2code.get(province)) == null ? "δ֪" : oldCode) + "\n");
		
		final int finalCode = newCode; //280000
		name2code.computeIfAbsent(province, k -> {return finalCode;});
		System.out.println(province + "�����������ǣ�" + ((oldCode = name2code.get(province)) == null ? "δ֪" : oldCode) + "\n");
		
		name2code.computeIfPresent(province, (k, v) -> {return finalCode;});
		System.out.println(province + "�����������ǣ�" + ((oldCode = name2code.get(province)) == null ? "δ֪" : oldCode) + "\n");
	}
}