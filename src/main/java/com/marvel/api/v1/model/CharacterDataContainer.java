package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CharacterDataContainer<T> {

    private Integer offset;
    private Integer limit;
    private Integer count;
    private List<T> results;

}
