package com.jgcs.chp9.p4;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class MyString implements Comparable<MyString>{
	//�ַ������ڲ��洢��ʾ
    private final char value[];

    //�ַ����Ĺ�ϣ��ʾ��Ĭ��Ϊ0
    private int hash;
    
    public MyString() { //�Կ��ַ�������һ����MyString����
        this.value = "".toCharArray();
    }
    
    public MyString(MyString original) { //����һ��MyString���󴴽���ǰMyString����
        this.value = original.value;
        this.hash = original.hash;
    }
    
    public MyString(String original) { //��һ��String���󴴽���ǰMyString����
    	this.value = original.toCharArray();
    	this.hash = original.hashCode();
    }
    
    public MyString(char value[]) { //��һ��char���鴴����ǰMyString����
        this.value = Arrays.copyOf(value, value.length); //������valueִ�����
    }
    
    public int length() {
        return value.length;
    }
    
    public boolean isEmpty() {
        return value.length == 0;
    }
    
    @Override
    public int hashCode() { //���ַ������е��������ɵ�ǰMyString����Ĺ�ϣ��
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
    
    //����ǰMyString��������һ��MyString���������ȱȽ�
    @Override
    public boolean equals(Object anObject) { //equals()��ʵ��ʱӦ���������Ĺ��򣬼�������ȵ�MyString������hashCode()���صĽ��ҲӦ���
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof MyString) {
            MyString anotherString = (MyString)anObject;
            int n = value.length;
            if (n == anotherString.value.length) { //��������ַ����鳤����ͬ���ͱȽ�������
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i]) //ֻҪ������������ͬ�±괦���κ�һ���ַ����ȣ��򷵻�false
                        return false;
                    i++;
                }
                return true; //������������ȫ��ȵ�Ԫ�أ��򷵻�true
            }
        }
        return false; //�������鳤�Ȳ��ȣ�����false
    }
    
    //����ǰMyString��������һ��MyString������д�С�Ƚ�
    @Override
    public int compareTo(MyString anotherString) { //MyStringʵ����Comparable<MyString>�ӿڵ�compareTo()����
        int len1 = value.length;
        int len2 = anotherString.value.length;
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 0;
        while (k < lim) { //�Գ��Ƚ϶̵��ַ����ĳ���Ϊ���ޱȽ������ַ���
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) { //�ҵ���һ������ȵ��ַ���
                return c1 - c2; //���ص�һ���ַ���ȥ�ڶ����ַ��Ĳ�ֵ
            }
            k++;
        }
        return len1 - len2; //��������ַ�����ǰlim���ַ��϶���ȣ��򷵻ص�һ���ַ����ĳ��ȼ�ȥ�ڶ����ַ������ȵĲ�ֵ
    }
    
    @Override
    public String toString() { //���ص�ǰMyString�����String�����ʾ
        return new String(value);
    }
}

public class App9_4 {
	public static void main(String[] args) {
		//����Arrays.asList()��ʹ����ʾ
		MyString[] strCities = {new MyString("Harbin"), new MyString("Qiqihar"), new MyString("Jagdaqi"), new MyString("Kiamusze")};
		List<MyString> strList = Arrays.asList(strCities);
		System.out.println("�޸�ǰstrCities�������ǣ�" + Arrays.toString(strCities));
		
		//���ܶ�strList������ӻ�ɾ�������������޸�������Ԫ��
		//strList.add(new MyString("Jiamusi")); //strList.add(...)�ᴥ��UnsupportedOperationException�쳣
		//System.out.println(Arrays.toString(strCities));
		strList.set(3, new MyString("Jiamusi")); //strList.set(4, "Jiamusi")�ᴥ��ArrayIndexOutOfBoundsException�쳣
		System.out.println("�޸ĺ�strCities�������ǣ�" + Arrays.toString(strCities));
		
		//����Arrays.sort()��Arrays.parallelSort()��Arrays.binarySearch()��ʹ����ʾ
		int totalNum = 1 << 16; //1024*64����65536
		int[] randInts = new int[totalNum]; //���intֵ������
		MyString[] randStrs = new MyString[totalNum]; //���MyString��������
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //��ĸ��
		StringBuffer sb = new StringBuffer();
		int randInt = 0;
		MyString randStr = null;
		
		for(int i = 0; i < totalNum; i++) {
			//��������һ��[0, totalNum*4)��Χ���������randInt��Ȼ����randInt�Ƿ�����randInts�У�
			//����ǵĻ��������������µ��������randInt��ֱ������������randInts��
			do {
				randInt = new Random().nextInt(totalNum << 2); //totalNum<<2�ȼ���totalNum*4
			} while(Arrays.binarySearch(randInts, 0, i, randInt) >= 0);
			
			randInts[i] = randInt; //����randInts�����е�ǰԪ�ز�ͬ��randInt��ӵ�randInts��
			Arrays.sort(randInts, 0, i+1); //�Ե�ǰrandInts�е�Ԫ�ؽ�������
			
			//��������һ������Ϊ6������ַ���randStr��Ȼ����randStr�Ƿ�����randStrs�У�
			//����ǵĻ��������������µ�����ַ���randStr��ֱ�����ַ�������randStrs��
			do {
				for (int j = 0; j < 6; j++) { //���ɳ���Ϊ6������ַ���
					int index = new Random().nextInt(alphabet.length());
					sb.append(alphabet.charAt(index));
				}
				randStr = new MyString(sb.toString());
				sb.replace(0, sb.length(), ""); //���sb
			}while(Arrays.binarySearch(randStrs, 0, i, randStr) >= 0); //ʹ��MyString�е�compareTo()������ȷ�����߼����Ƚ�����MyString����Ĵ�С
			
			randStrs[i] = randStr; //����randStrs�����е�ǰԪ�ز�ͬ��randStr��ӵ�randStrs��
			Arrays.parallelSort(randStrs, 0, i+1); //�Ե�ǰrandStrs�е�Ԫ�ؽ��в�������Arrays.binarySearch()Ҫ����ж��ֲ��ҵ��������������������
		}
		
		//���randInts
		//System.out.println("randInts is: " + Arrays.toString(randInts));
		
		//����Arrays.copyOf()��Arrays.copyOfRange()��ʹ����ʾ
		int[] randInts2 = Arrays.copyOf(randInts, totalNum >> 11); //totalNum>>11�ȼ���totalNum/2048
		int[] randInts3 = Arrays.copyOfRange(randInts, 0, totalNum >> 11);
		Object[] randStrs2 = Arrays.copyOf(randStrs, totalNum >> 11, Object[].class);
		
		//��String[].class��Ϊʵ�δ��ݹ�ȥ�ᴥ��ArrayStoreException�쳣��String����MyString�ĸ���
		//String[] randStrs3 = Arrays.copyOf(randStrs, totalNum >> 11, String[].class); 
		//System.out.println(randStrs3);
		
		//����Arrays.toString()��Arrays.equals()��ʹ����ʾ
		System.out.println("randInts2�������ǣ�" + Arrays.toString(randInts2));
		System.out.println("randInts3�������ǣ�" + Arrays.toString(randInts3));
		System.out.println("randInts2��randInts3�Ƿ���ȣ���" + Arrays.equals(randInts2, randInts3));
		System.out.println("randStrs2�������ǣ�" + Arrays.toString(randStrs2));
		
		//����Arrays.parallelPrefix()��ʹ����ʾ
		//����IntBinaryOperator����Ҳ������"(int left, int right) -> (right-left)"����lambda���ʽ������
		Arrays.parallelPrefix(randInts2, new IntBinaryOperator() {
			@Override
			public int applyAsInt(int left, int right) {
				return right-left;
			}
		});
		//Arrays.parallelPrefix(randInts3, (int left, int right) -> (right-left));
		System.out.println("�ڶ�randInts2ִ��parallelPrefix()���������ǣ�" + Arrays.toString(randInts2));
		
		//����BinaryOperator<Object>����Ҳ��������Ӧ��lambda���ʽ������
		Arrays.parallelPrefix(randStrs2, new BinaryOperator<Object>() {
			@Override
			public Object apply(Object left, Object right) {
				//����left��right�����ݹ���һ��MyString����
				int leftLen = left.toString().length(), rightLen = right.toString().length();
				sb.replace(0, sb.length(), ""); //���sb
				for(int i = 0; i < 6; i++) { //����һ��6�ַ����ַ���
					if(new Random().nextInt(leftLen + rightLen) % 2 == 0) { //����������ż�������left��ѡһ���ַ�
						sb.append(left.toString().charAt(new Random().nextInt(leftLen)));
					} else { //���򣬾ʹ�right��ѡһ���ַ�
						sb.append(right.toString().charAt(new Random().nextInt(rightLen)));
					}
				}
				return new MyString(sb.toString());
				//return randStrs[new Random().nextInt(randStrs.length)]; //��randStrs�����ȡһ��Ԫ����Ϊ�������
			}
		});
		sb.replace(0, sb.length(), ""); //���sb
		System.out.println("�ڶ�randStrs2ִ��parallelPrefix()���������ǣ�" + Arrays.toString(randStrs2));
		
		//����Arrays.fill()��Arrays.setAll()��Arrays.parallelSetAll()��ʹ����ʾ
		Arrays.fill(randInts2, new Random().nextInt(randInts2.length));
		Arrays.setAll(randInts3, new IntUnaryOperator() {
			@Override
			public int applyAsInt(int arrIdx) {
				return arrIdx;
			}
		});
		System.out.println("�ڶ�randInts3ִ��setAll()���������ǣ�" + Arrays.toString(randInts3));
		
		int[] shuffledInts = new int[randInts.length];
		MyString[] shuffledStrs = new MyString[randStrs.length];
		
		//��randInts�����ѡһ��Ԫ�أ�������ΪshuffledInts��arrIdx����shuffledInts[arrIdx]��Ԫ��
		Arrays.parallelSetAll(shuffledInts, arrIdx -> {return randInts[new Random().nextInt(randInts.length)];});
		
		//��randStrs�����ѡһ��Ԫ�أ�������ΪshuffledStrs��arrIdx����shuffledStrs[arrIdx]��Ԫ��
		Arrays.parallelSetAll(shuffledStrs, new IntFunction<MyString>() {
			@Override
			public MyString apply(int arrIdx) {
				return (MyString)randStrs[new Random().nextInt(randStrs.length)];
			}
		});
		
		//����Arrays.deepHashCode()��Arrays.deepToString()��Arrays.deepEquals()��ʹ����ʾ
		//shuffledStrs��ÿ��Ԫ�ض��������飬�ʶ������Arrays.hashCode()��Arrays.deepHashCode()�Ľ����ͬ
		System.out.println("����shuffledStrs�Ĺ�ϣ���ǣ�" + Arrays.hashCode(shuffledStrs));
		System.out.println("����shuffledStrs����ȹ�ϣ���ǣ�" + Arrays.deepHashCode(shuffledStrs));
		
		int[][] randInts4 = new int[4][8];
		for(int i = 0; i < randInts4.length; i++) {
			for(int j = 0; j < randInts4[0].length; j++) {
				randInts4[i][j] = new Random().nextInt(randInts4.length * randInts4[0].length * randInts4[0].length);
			}
		}
		//randInts4��ÿ��Ԫ�ض������飬�ʶ������Arrays.hashCode()��Arrays.deepHashCode()�Ľ����ͬ
		System.out.println("����randInts4�Ĺ�ϣ���ǣ�" + Arrays.hashCode(randInts4));
		System.out.println("����randInts4����ȹ�ϣ���ǣ�" + Arrays.deepHashCode(randInts4));
		
		int objNum = 10;
		Object[] objArr = new Object[objNum];
		for(int i = 0; i < objNum; i++) {
			if(new Random().nextInt(objNum) % 2 == 0) { //�����objArr��ĳ��Ԫ����Ϊ��������objArr
				objArr[i] = objArr;
			} else { //����randStrs�����ѡȡ��һ��Ԫ����ΪobjArr��ĳ��Ԫ��
				objArr[i] = randStrs[new Random().nextInt(randStrs.length)];
			}
		}
		System.out.println("����objArr���ַ�����ʾ�ǣ�" + Arrays.toString(objArr));
		System.out.println("����objArr������ַ�����ʾ�ǣ�" + Arrays.deepToString(objArr));
		
		System.out.println("����objArr�Ĺ�ϣ���ǣ�" + Arrays.hashCode(objArr));
		//��objArr���ְ���ָ�������Ԫ�ص��������deepHashCode()�ᴥ��StackOverflowError�쳣
		//System.out.println("����objArr����ȹ�ϣ���ǣ�" + Arrays.deepHashCode(objArr));
		
		Object[] objGrp = new Object[objArr.length]; //�½�һ����objArr�ȳ�������objGrp��object group��
		Object[] objChk = new Object[objArr.length]; //�½�һ����objArr�ȳ�������objChk��object chunk��
		for(int i = 0; i < objArr.length; i++) {
			if(objArr[i] == objArr) { //���objArr[i]��objArr����Ļ�����objGrp[i]��ΪobjGrp������objChk[i]��ΪobjArr
				objGrp[i] = objGrp;
				objChk[i] = objArr;
			} else {
				objGrp[i] = objChk[i] = new MyString(objArr[i].toString()); //�������objArr[i]����һ���µ�MyString���󲢽���ֱ�ֵ��objGrp[i]��objChk[i]
			}
		}
		System.out.println("����objArr�Ƿ���objGrp��ȣ�" + Arrays.equals(objArr, objGrp));
		//��objArr��objGrp��������ͬλ�ô�Ԫ�ض����԰���ָ�������Ԫ�ص��������deepEquals()�ᴥ��StackOverflowError�쳣
		//System.out.println("����objArr�Ƿ���objGrp�����ȣ�" + Arrays.deepEquals(objArr, objGrp));
		
		//objChk��objArr����ͬλ�ô���Ԫ��e1��e2Ҫô��==�����ϵ�ͬҪô��e1.equals(e2)��������ȣ����Զ������������Arrays.equals()��Arrays.deepEquals()������true
		System.out.println("����objArr�Ƿ���objChk��ȣ�" + Arrays.equals(objArr, objChk));
		System.out.println("����objArr�Ƿ���objChk�����ȣ�" + Arrays.deepEquals(objArr, objChk));
		//��objChk���ְ���ָ�������Ԫ�أ�objChk��ĳ��Ԫ��������objArr�����������ָ�������Ԫ�أ����������deepHashCode()�ᴥ��StackOverflowError�쳣
		//System.out.println("����objChk����ȹ�ϣ���ǣ�" + Arrays.deepHashCode(objChk));
		
		//����Arrays.spliterator()��ʹ����ʾ
		Spliterator.OfInt intArrSpliter = Arrays.spliterator(randInts);
		System.out.println("�ڷָ�ǰ��intArrSpliter�Ĵ�С�ǣ� " + intArrSpliter.estimateSize());
		Spliterator.OfInt intArrLefter = intArrSpliter.trySplit(); //intArrLefter��ʾintArrSpliter���ѳ�ȥ����벿��
		System.out.println("�ڷָ��intArrSpliter�Ĵ�С�ǣ� " + intArrSpliter.estimateSize() + "��intArrLefter�Ĵ�С�ǣ�" + intArrLefter.estimateSize());
		
		//����Arrays.stream()��ʹ����ʾ
		IntStream intStm = Arrays.stream(shuffledInts);
		System.out.println("����shuffledInts��Ԫ�ص�ƽ��ֵ�ǣ�" + intStm.average().getAsDouble());
		Stream<MyString> strStm = Arrays.stream(shuffledStrs);
		System.out.println("����shuffledStrs��Ԫ�صĸ����ǣ�" + strStm.count());
	}
}