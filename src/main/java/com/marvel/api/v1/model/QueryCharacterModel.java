package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryCharacterModel {

    private Integer numberPage;
    private Integer pageSize;
    private Long comicId;
    private String modifiedFrom;
    private String modifiedTo;
    private String orderBy;

}
