package org.example.string;

/**
 * @author chenxuegui
 * @since 2023/10/23
 */
public class String对象 {
    String s0 = "abc";

    public static void main(String[] args) {
        String s = "xyz";
        String s1 = new String("xyz");
        String s2 = new String("xyz").intern();
    }
}
