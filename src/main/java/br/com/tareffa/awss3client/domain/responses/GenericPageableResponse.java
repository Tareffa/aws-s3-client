package br.com.tareffa.awss3client.domain.responses;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GenericPageableResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("records")
    private List<T> records;

    @JsonProperty("pageInfo")
    private PageInfoResponseObject pageInfo;

    public GenericPageableResponse(Page<T> page) {
        this.records = page.getContent();
        this.pageInfo = new PageInfoResponseObject(page);
    }

}