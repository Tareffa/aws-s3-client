package br.com.tareffa.awss3client.domain.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResponseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("hasNext")
    private boolean hasNext;

    @JsonProperty("hasPrevious")
    private boolean hasPrevious;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("pageIndex")
    private int pageIndex;

    @JsonProperty("totalPages")
    private int totalPages;
    
    @JsonProperty("totalElements")
    private long totalElements;

    public PageInfoResponseObject(Page<?> page) {
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();

        this.pageSize = page.getSize();
        this.pageIndex = page.getNumber();

        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}