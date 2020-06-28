package com.marvel.api.v1.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class DataContainerModel<T> {

    private Integer numberPage;
    private Integer pageSize;
    private Integer count;
    private List<T> results = new ArrayList<>();

}
