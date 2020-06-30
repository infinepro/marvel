package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryCharacterModel {
/*
    private final String DEFAULT_ORDER_BY = "name";
    private final String START_TIME = "1900-01-01 00:00:00";
    private final String END_TIME = "2900-01-01 00:00:00";
    private final Integer DEFAULT_PAGE_SIZE = 25;
    private final Integer DEFAULT_NUMBER_PAGE = 0;*/

    private Long comicId;
    private Integer numberPage;
    private Integer pageSize;
    private String modifiedFrom;
    private String modifiedTo;
    private String orderBy;

    public QueryCharacterModel() {
        setDefaultData();
    }

    private void setDefaultData() {
       /* this.setPageSize(DEFAULT_PAGE_SIZE);
        this.setNumberPage(DEFAULT_NUMBER_PAGE);
        this.setModifiedFrom(START_TIME);
        this.setModifiedTo(END_TIME);
        this.setOrderBy(DEFAULT_ORDER_BY);*/
    }
}
