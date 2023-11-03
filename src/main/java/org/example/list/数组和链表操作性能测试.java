package org.example.list;


public class 数组和链表操作性能测试 {


	public static void main( String[] args ) {

		int dataSize = 30_0000;
		//头部添加
    	ArrayListTest.addFromHeaderTest(dataSize);
    	LinkedListTest.addFromHeaderTest(dataSize);
    	
    	//中间添加
    	ArrayListTest.addFromMidTest(dataSize);
    	LinkedListTest.addFromMidTest(dataSize);

    	//尾部添加
    	ArrayListTest.addFromTailTest(dataSize);
    	LinkedListTest.addFromTailTest(dataSize);

		System.out.println();

		//头部删除
    	ArrayListTest.deleteFromHeaderTest(dataSize);
    	LinkedListTest.deleteFromHeaderTest(dataSize);

		//中间删除
    	ArrayListTest.deleteFromMidTest(dataSize);
    	LinkedListTest.deleteFromMidTest(dataSize);

		//尾部删除
    	ArrayListTest.deleteFromTailTest(dataSize);
    	LinkedListTest.deleteFromTailTest(dataSize);

		System.out.println();
		//for循环
    	ArrayListTest.getByForTest(dataSize);
    	LinkedListTest.getByForTest(dataSize);

		//迭代器循环
    	ArrayListTest.getByIteratorTest(dataSize);
    	LinkedListTest.getByIteratorTest(dataSize);
    }
}