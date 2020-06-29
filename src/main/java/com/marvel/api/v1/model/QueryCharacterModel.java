package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryCharacterModel {

    private Long id;
    private String Name;
    private String modifiedSince;
    private Long comicId;

    //for sort
    private String orderBy;
    private Integer numberPage;
    private Integer pageSize;

}
