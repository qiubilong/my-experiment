package org.example.剑指.从1到n出现1的次数;

/**
 * @author chenxuegui
 * @since 2024/5/15
 *
 * https://blog.csdn.net/whl_program/article/details/119861231
 */
public class Solution {

    /** 遍历每个数字，判断1的位数 */
    public int count1(int n){
        int count = 0;
        for (int i = 1; i <= n; i++) {
            int count1 = count1Do(i);
            if(count1>0){
                System.out.println(i);
            }
            count += count1;
        }
        System.out.println("--------");
        return count;
    }
    private int count1Do(int n){

        int count = 0;
        while (n>0){
            int i = n%10;
            if(i == 1){
                count++;
            }
            n = n/10;
        }
        return count;
    }



    public int count1_数学规律(int n){
        int count = 0;
        //位数
        int base = 1;
        while (n >= base){

            int high = n / base / 10;
            int cur = n / base % 10 ;
            int low = n % base;

            if(cur == 1 ){
                count += high * base + (low +1);
            }else if(cur > 1 ){
                count += (high +1) * base;
            }else if(cur == 0 ){
                count += high * base;
            }

            base = base * 10;
        }

        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.count1(12));
        System.out.println("--------");
        System.out.println(solution.count1_数学规律(12));
    }
}
