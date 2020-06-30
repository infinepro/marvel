package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryCharacterModel {

    private String modifiedFrom;
    private String modifiedTo;
    private Long comicId;

    //for sort
    private String orderBy;
    private Integer numberPage;
    private Integer pageSize;

}
