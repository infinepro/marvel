package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data
@Accessors(chain = true)
public class QueryCharacterModel {

    private String Name;
    private Date modifiedSince;
    private Integer comicId;
    private String orderBy;
    private Integer numberPage;
    private Integer pageSize;

}
