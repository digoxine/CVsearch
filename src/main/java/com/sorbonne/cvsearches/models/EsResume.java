package com.sorbonne.cvsearches.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

// ça représente un CV en Elastic search
@Document(indexName = "resume")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EsResume {

    // just for test
    public EsResume(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private Long id;

    private String name;

    private int age;
}
