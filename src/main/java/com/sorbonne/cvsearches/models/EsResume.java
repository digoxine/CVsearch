package com.sorbonne.cvsearches.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

// ça représente un CV en Elastic search
@Document(indexName = "resumes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EsResume {

    public EsResume(
            List<String> competences,
            List<String> mails,
            List<String> hashtags,
            List<String> telephones,
            List<String> liens
    ) {
        this.competences = competences;
        this.mails = mails;
        this.hashtags = hashtags;
        this.telephones = telephones;
        this.liens = liens;
    }

    @Id
    private Long id;

    private String fileName;

    private List<String> competences;

    private List<String> mails;

    private List<String> hashtags;

    private List<String> telephones;

    private List<String> liens;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
