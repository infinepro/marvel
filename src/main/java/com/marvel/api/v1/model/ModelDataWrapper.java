package com.marvel.api.v1.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ModelDataWrapper<T> {

    private Integer code;
    private String status;
    private ModelDataContainer<T> data;

    public ModelDataWrapper() {
        this.code = 200;
        this.status = "all right";
    }
}
