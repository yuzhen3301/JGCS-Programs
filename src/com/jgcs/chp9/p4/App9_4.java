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
	//字符串的内部存储表示
    private final char value[];

    //字符串的哈希表示，默认为0
    private int hash;
    
    public MyString() { //以空字符串创建一个空MyString对象
        this.value = "".toCharArray();
    }
    
    public MyString(MyString original) { //以另一个MyString对象创建当前MyString对象
        this.value = original.value;
        this.hash = original.hash;
    }
    
    public MyString(String original) { //以一个String对象创建当前MyString对象
    	this.value = original.toCharArray();
    	this.hash = original.hashCode();
    }
    
    public MyString(char value[]) { //以一个char数组创建当前MyString对象
        this.value = Arrays.copyOf(value, value.length); //对数组value执行深拷贝
    }
    
    public int length() {
        return value.length;
    }
    
    public boolean isEmpty() {
        return value.length == 0;
    }
    
    @Override
    public int hashCode() { //以字符数组中的内容生成当前MyString对象的哈希码
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
    
    //将当前MyString对象与另一个MyString对象进行相等比较
    @Override
    public boolean equals(Object anObject) { //equals()在实现时应遵守这样的规则，即两个相等的MyString对象，其hashCode()返回的结果也应相等
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof MyString) {
            MyString anotherString = (MyString)anObject;
            int n = value.length;
            if (n == anotherString.value.length) { //如果两个字符数组长度相同，就比较其内容
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i]) //只要两个数组在相同下标处有任何一对字符不等，则返回false
                        return false;
                    i++;
                }
                return true; //两个数组有完全相等的元素，则返回true
            }
        }
        return false; //两个数组长度不等，返回false
    }
    
    //将当前MyString对象与另一个MyString对象进行大小比较
    @Override
    public int compareTo(MyString anotherString) { //MyString实现了Comparable<MyString>接口的compareTo()方法
        int len1 = value.length;
        int len2 = anotherString.value.length;
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 0;
        while (k < lim) { //以长度较短的字符串的长度为上限比较两个字符串
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) { //找到第一个不相等的字符对
                return c1 - c2; //返回第一个字符减去第二个字符的差值
            }
            k++;
        }
        return len1 - len2; //如果两个字符串在前lim个字符上都相等，则返回第一个字符串的长度减去第二个字符串长度的差值
    }
    
    @Override
    public String toString() { //返回当前MyString对象的String对象表示
        return new String(value);
    }
}

public class App9_4 {
	public static void main(String[] args) {
		//关于Arrays.asList()的使用演示
		MyString[] strCities = {new MyString("Harbin"), new MyString("Qiqihar"), new MyString("Jagdaqi"), new MyString("Kiamusze")};
		List<MyString> strList = Arrays.asList(strCities);
		System.out.println("修改前strCities的内容是：" + Arrays.toString(strCities));
		
		//不能对strList进行添加或删除操作，但可修改其现有元素
		//strList.add(new MyString("Jiamusi")); //strList.add(...)会触发UnsupportedOperationException异常
		//System.out.println(Arrays.toString(strCities));
		strList.set(3, new MyString("Jiamusi")); //strList.set(4, "Jiamusi")会触发ArrayIndexOutOfBoundsException异常
		System.out.println("修改后strCities的内容是：" + Arrays.toString(strCities));
		
		//关于Arrays.sort()、Arrays.parallelSort()、Arrays.binarySearch()的使用演示
		int totalNum = 1 << 16; //1024*64，即65536
		int[] randInts = new int[totalNum]; //随机int值的数组
		MyString[] randStrs = new MyString[totalNum]; //随机MyString对象数组
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //字母表
		StringBuffer sb = new StringBuffer();
		int randInt = 0;
		MyString randStr = null;
		
		for(int i = 0; i < totalNum; i++) {
			//首先生成一个[0, totalNum*4)范围内随机整数randInt，然后检查randInt是否已在randInts中，
			//如果是的话，就重新生成新的随机整数randInt，直到该整数不在randInts中
			do {
				randInt = new Random().nextInt(totalNum << 2); //totalNum<<2等价于totalNum*4
			} while(Arrays.binarySearch(randInts, 0, i, randInt) >= 0);
			
			randInts[i] = randInt; //将与randInts中所有当前元素不同的randInt添加到randInts中
			Arrays.sort(randInts, 0, i+1); //对当前randInts中的元素进行排序
			
			//首先生成一个长度为6的随机字符串randStr，然后检查randStr是否已在randStrs中，
			//如果是的话，就重新生成新的随机字符串randStr，直到该字符串不在randStrs中
			do {
				for (int j = 0; j < 6; j++) { //生成长度为6的随机字符串
					int index = new Random().nextInt(alphabet.length());
					sb.append(alphabet.charAt(index));
				}
				randStr = new MyString(sb.toString());
				sb.replace(0, sb.length(), ""); //清空sb
			}while(Arrays.binarySearch(randStrs, 0, i, randStr) >= 0); //使用MyString中的compareTo()方法所确定的逻辑来比较两个MyString对象的大小
			
			randStrs[i] = randStr; //将与randStrs中所有当前元素不同的randStr添加到randStrs中
			Arrays.parallelSort(randStrs, 0, i+1); //对当前randStrs中的元素进行并行排序。Arrays.binarySearch()要求进行二分查找的数组是升序的有序数组
		}
		
		//输出randInts
		//System.out.println("randInts is: " + Arrays.toString(randInts));
		
		//关于Arrays.copyOf()和Arrays.copyOfRange()的使用演示
		int[] randInts2 = Arrays.copyOf(randInts, totalNum >> 11); //totalNum>>11等价于totalNum/2048
		int[] randInts3 = Arrays.copyOfRange(randInts, 0, totalNum >> 11);
		Object[] randStrs2 = Arrays.copyOf(randStrs, totalNum >> 11, Object[].class);
		
		//将String[].class作为实参传递过去会触发ArrayStoreException异常，String不是MyString的父类
		//String[] randStrs3 = Arrays.copyOf(randStrs, totalNum >> 11, String[].class); 
		//System.out.println(randStrs3);
		
		//关于Arrays.toString()和Arrays.equals()的使用演示
		System.out.println("randInts2的内容是：" + Arrays.toString(randInts2));
		System.out.println("randInts3的内容是：" + Arrays.toString(randInts3));
		System.out.println("randInts2与randInts3是否相等？：" + Arrays.equals(randInts2, randInts3));
		System.out.println("randStrs2的内容是：" + Arrays.toString(randStrs2));
		
		//关于Arrays.parallelPrefix()的使用演示
		//匿名IntBinaryOperator对象也可以用"(int left, int right) -> (right-left)"这种lambda表达式来代替
		Arrays.parallelPrefix(randInts2, new IntBinaryOperator() {
			@Override
			public int applyAsInt(int left, int right) {
				return right-left;
			}
		});
		//Arrays.parallelPrefix(randInts3, (int left, int right) -> (right-left));
		System.out.println("在对randInts2执行parallelPrefix()后，其内容是：" + Arrays.toString(randInts2));
		
		//匿名BinaryOperator<Object>对象也可以用相应的lambda表达式来代替
		Arrays.parallelPrefix(randStrs2, new BinaryOperator<Object>() {
			@Override
			public Object apply(Object left, Object right) {
				//利用left和right的内容构建一个MyString对象
				int leftLen = left.toString().length(), rightLen = right.toString().length();
				sb.replace(0, sb.length(), ""); //清空sb
				for(int i = 0; i < 6; i++) { //构造一个6字符的字符串
					if(new Random().nextInt(leftLen + rightLen) % 2 == 0) { //如果随机数是偶数，则从left中选一个字符
						sb.append(left.toString().charAt(new Random().nextInt(leftLen)));
					} else { //否则，就从right中选一个字符
						sb.append(right.toString().charAt(new Random().nextInt(rightLen)));
					}
				}
				return new MyString(sb.toString());
				//return randStrs[new Random().nextInt(randStrs.length)]; //从randStrs中随机取一个元素作为结果返回
			}
		});
		sb.replace(0, sb.length(), ""); //清空sb
		System.out.println("在对randStrs2执行parallelPrefix()后，其内容是：" + Arrays.toString(randStrs2));
		
		//关于Arrays.fill()、Arrays.setAll()和Arrays.parallelSetAll()的使用演示
		Arrays.fill(randInts2, new Random().nextInt(randInts2.length));
		Arrays.setAll(randInts3, new IntUnaryOperator() {
			@Override
			public int applyAsInt(int arrIdx) {
				return arrIdx;
			}
		});
		System.out.println("在对randInts3执行setAll()后，其内容是：" + Arrays.toString(randInts3));
		
		int[] shuffledInts = new int[randInts.length];
		MyString[] shuffledStrs = new MyString[randStrs.length];
		
		//从randInts中随机选一个元素，将其作为shuffledInts在arrIdx处即shuffledInts[arrIdx]的元素
		Arrays.parallelSetAll(shuffledInts, arrIdx -> {return randInts[new Random().nextInt(randInts.length)];});
		
		//从randStrs中随机选一个元素，将其作为shuffledStrs在arrIdx处即shuffledStrs[arrIdx]的元素
		Arrays.parallelSetAll(shuffledStrs, new IntFunction<MyString>() {
			@Override
			public MyString apply(int arrIdx) {
				return (MyString)randStrs[new Random().nextInt(randStrs.length)];
			}
		});
		
		//关于Arrays.deepHashCode()、Arrays.deepToString()和Arrays.deepEquals()的使用演示
		//shuffledStrs中每个元素都不是数组，故对其调用Arrays.hashCode()和Arrays.deepHashCode()的结果相同
		System.out.println("数组shuffledStrs的哈希码是：" + Arrays.hashCode(shuffledStrs));
		System.out.println("数组shuffledStrs的深度哈希码是：" + Arrays.deepHashCode(shuffledStrs));
		
		int[][] randInts4 = new int[4][8];
		for(int i = 0; i < randInts4.length; i++) {
			for(int j = 0; j < randInts4[0].length; j++) {
				randInts4[i][j] = new Random().nextInt(randInts4.length * randInts4[0].length * randInts4[0].length);
			}
		}
		//randInts4中每个元素都是数组，故对其调用Arrays.hashCode()和Arrays.deepHashCode()的结果不同
		System.out.println("数组randInts4的哈希码是：" + Arrays.hashCode(randInts4));
		System.out.println("数组randInts4的深度哈希码是：" + Arrays.deepHashCode(randInts4));
		
		int objNum = 10;
		Object[] objArr = new Object[objNum];
		for(int i = 0; i < objNum; i++) {
			if(new Random().nextInt(objNum) % 2 == 0) { //随机将objArr中某个元素置为它自身，即objArr
				objArr[i] = objArr;
			} else { //将从randStrs中随机选取的一个元素作为objArr的某个元素
				objArr[i] = randStrs[new Random().nextInt(randStrs.length)];
			}
		}
		System.out.println("数组objArr的字符串表示是：" + Arrays.toString(objArr));
		System.out.println("数组objArr的深度字符串表示是：" + Arrays.deepToString(objArr));
		
		System.out.println("数组objArr的哈希码是：" + Arrays.hashCode(objArr));
		//对objArr这种包含指向自身的元素的数组调用deepHashCode()会触发StackOverflowError异常
		//System.out.println("数组objArr的深度哈希码是：" + Arrays.deepHashCode(objArr));
		
		Object[] objGrp = new Object[objArr.length]; //新建一个与objArr等长的数组objGrp（object group）
		Object[] objChk = new Object[objArr.length]; //新建一个与objArr等长的数组objChk（object chunk）
		for(int i = 0; i < objArr.length; i++) {
			if(objArr[i] == objArr) { //如果objArr[i]是objArr本身的话，则将objGrp[i]置为objGrp，而将objChk[i]置为objArr
				objGrp[i] = objGrp;
				objChk[i] = objArr;
			} else {
				objGrp[i] = objChk[i] = new MyString(objArr[i].toString()); //否则就用objArr[i]构造一个新的MyString对象并将其分别赋值给objGrp[i]和objChk[i]
			}
		}
		System.out.println("数组objArr是否与objGrp相等：" + Arrays.equals(objArr, objGrp));
		//对objArr和objGrp这种在相同位置处元素都各自包含指向自身的元素的数组调用deepEquals()会触发StackOverflowError异常
		//System.out.println("数组objArr是否与objGrp深度相等：" + Arrays.deepEquals(objArr, objGrp));
		
		//objChk与objArr在相同位置处的元素e1和e2要么在==意义上等同要么在e1.equals(e2)意义上相等，所以对两个数组调用Arrays.equals()和Arrays.deepEquals()都返回true
		System.out.println("数组objArr是否与objChk相等：" + Arrays.equals(objArr, objChk));
		System.out.println("数组objArr是否与objChk深度相等：" + Arrays.deepEquals(objArr, objChk));
		//对objChk这种包含指向自身的元素（objChk的某个元素是数组objArr，该数组包含指向自身的元素）的数组调用deepHashCode()会触发StackOverflowError异常
		//System.out.println("数组objChk的深度哈希码是：" + Arrays.deepHashCode(objChk));
		
		//关于Arrays.spliterator()的使用演示
		Spliterator.OfInt intArrSpliter = Arrays.spliterator(randInts);
		System.out.println("在分割前，intArrSpliter的大小是： " + intArrSpliter.estimateSize());
		Spliterator.OfInt intArrLefter = intArrSpliter.trySplit(); //intArrLefter表示intArrSpliter分裂出去的左半部分
		System.out.println("在分割后，intArrSpliter的大小是： " + intArrSpliter.estimateSize() + "且intArrLefter的大小是：" + intArrLefter.estimateSize());
		
		//关于Arrays.stream()的使用演示
		IntStream intStm = Arrays.stream(shuffledInts);
		System.out.println("数组shuffledInts中元素的平均值是：" + intStm.average().getAsDouble());
		Stream<MyString> strStm = Arrays.stream(shuffledStrs);
		System.out.println("数组shuffledStrs中元素的个数是：" + strStm.count());
	}
}