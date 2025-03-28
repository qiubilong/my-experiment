package org.example.web.dao.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author chenxuegui
 * @since 2025/2/25
 */
@Document("citys")  //对应emp集合中的一个文档
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id   //映射文档中的_id
    private Integer id;

    @Field
    private String state; //州

    @Field
    private String city;//城市

    @Field
    private int pop; //人口

    @Field
    private List<Double> loc; //城市某地方

}
