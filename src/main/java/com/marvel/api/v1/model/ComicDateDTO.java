package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ComicDateDTO {

    private String id;

    @NotBlank
    private String type;

    @NotBlank
    @Past
    private String date;
}
