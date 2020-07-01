package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryComicModel {

    private Integer numberPage;
    private Integer pageSize;
    private String title;
    private String dateFrom;
    private String dateTo;
    private String orderBy;

}
