package org.example.base.异常;

/**
 * @author chenxuegui
 * @since 2024/6/19
 */
public class TryFinallyTest {


    public static void test(int i){
        try {
            if(i ==10){
                int j  = i / 0;
            }

        }finally {

        }
        System.out.println("i am here");
    }

    public static void main(String[] args) {
        test(11);
        test(10);
    }
}
