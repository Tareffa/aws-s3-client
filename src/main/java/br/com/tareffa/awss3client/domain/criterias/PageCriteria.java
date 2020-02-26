package br.com.tareffa.awss3client.domain.criterias;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer pageIndex = 0;

    public Integer pageSize = 10;

    public String sortBy;

    public String sortOrder;

    public static class Order {
        public static final String ASC = "asc";
        public static final String DESC = "desc";
    }

    public static Pageable getPageRequest(PageCriteria searchCriteria) {
        return PageRequest.of(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), getSort(searchCriteria));
    }

    public static Sort getSort(PageCriteria searchCriteria) {
        Sort sort = Sort.unsorted();
        if (searchCriteria.getSortOrder() != null && searchCriteria.getSortBy() != null) {
            sort = Sort.by(searchCriteria.getSortBy());
            if (searchCriteria.getSortOrder().equals(PageCriteria.Order.ASC)) {
                sort = sort.ascending();
            } else if (searchCriteria.getSortOrder().equals(PageCriteria.Order.DESC)) {
                sort = sort.descending();
            }
        }
        return sort;
    }

}