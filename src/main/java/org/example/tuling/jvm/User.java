package org.example.tuling.jvm;


import lombok.Data;

/**
 * @author chenxuegui
 * @since 2024/8/5
 */
@Data
public class User {
    private Long id;
    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void sout(){
        System.out.println("");
    }

}
