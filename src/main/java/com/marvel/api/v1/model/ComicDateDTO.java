package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 19, min = 19)
    private String date;
}
