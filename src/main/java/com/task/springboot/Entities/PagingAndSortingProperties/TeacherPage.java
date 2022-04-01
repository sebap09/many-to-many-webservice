package com.task.springboot.Entities.PagingAndSortingProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class TeacherPage  {
    private int pageNumber=0;
    private int pageSize=5;
    private Sort.Direction sortDirection=Sort.Direction.ASC;
    private String sortBy="lastName";
    //ALL DEFAULT VALUES

    public Pageable getPageRequestOf(){
        return PageRequest.of(pageNumber,pageSize,sortDirection,sortBy);
    }

}
