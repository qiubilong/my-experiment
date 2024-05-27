package org.example.ok.agro.树;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2023/11/29
 * 小顶堆
 */
public class 小顶堆 {


    public static void main(String[] args) {
        int[] nums = new int[]{99,5,36,7,22,17,46,12,2,19,25,28,1,92,14};

        MinHeap heap = MinHeap.buildHeap(nums);
        System.out.println(Arrays.toString(heap.data));

        heapSort(MinHeap.buildHeap2(nums));
    }

    public static void heapSort(MinHeap heap){
        System.out.println(Arrays.toString(heap.data));

        List<Integer> nums = new ArrayList<>(heap.size);
        while (heap.size >0) {
            nums.add(heap.data[0]);

            heap.data[0] = heap.data[heap.size - 1];
            heap.size --;

            heap.shiftDown(0);

        }
        System.out.println(nums);
    }



    public static class MinHeap{
        int[] data;
        int size;

        public MinHeap(int size) {
            this.size = size;
            data = new int[size];
        }

        //末尾添加元素，自低向上建堆,时间复杂度O( n*log(n) )
        public static  MinHeap buildHeap(int[] nums){
            MinHeap heap = new MinHeap(nums.length);

            for (int i = 0; i < nums.length; i++) {
                heap.data[i] = nums[i];
                heap.shiftUp(i);
            }
            return heap;
        }

        //从非叶子节点开始，从上到下建堆,时间复杂度O( n )
        public static  MinHeap buildHeap2(int[] nums){
            MinHeap heap = new MinHeap(nums.length);
            for (int i = 0; i < nums.length; i++) {
                heap.data[i] = nums[i];
            }
            for (int i = (nums.length/2 -1); i >=0 ; i--) {
                heap.shiftDown(i);
            }
            return heap;
        }


        /** 新增，自低向上调整  */
        public void shiftUp(int i){
            if(i == 0){
                return;
            }
            int parentIndex = (i - 1)/2;
            while (parentIndex>=0){
                if(data[parentIndex] > data[i]){
                    swap(parentIndex,i);
                    i = parentIndex;
                    parentIndex = (i - 1)/2;
                }else {
                    break;
                }
            }
        }


        /** 删除新增，自顶向下调整  */
        public void shiftDown(int i){
            boolean done = false;
            int temp = 0;
            int leftIndex = i * 2 +1;
            while (leftIndex <= size && !done) {
                //左节点
                if(data[leftIndex] < data[i]){
                    temp = leftIndex;
                }else {
                    temp = i;
                }
                //右节点
                int rightIndex = leftIndex + 1;
                if((rightIndex) <= size){
                    if(data[rightIndex] < data[temp]){
                        temp = rightIndex;
                    }
                }
                if(temp != i){
                    swap(temp,i);
                    i = temp;
                    leftIndex = i * 2 +1;
                }else {
                    done = true;
                }

            }
        }

        private void swap(int temp, int i) {
            int t = data[temp];
            data[temp] = data[i];
            data[i] = t;
        }

    }

}

