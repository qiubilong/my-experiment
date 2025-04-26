package org.example.web.dao.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author chenxuegui
 * @since 2025/3/14
 */
@Document("LanguageBR")
@Data
@NoArgsConstructor
public class LanguageBR {

    @Id   //映射文档中的_id
    private String id;

    //private String app;

    private String key;

    private String en_US;

    private String pt_BR;

    public LanguageBR(String key, String en_US) {
        this.key = key;
        this.en_US = en_US;
        this.pt_BR = "";
    }
}
