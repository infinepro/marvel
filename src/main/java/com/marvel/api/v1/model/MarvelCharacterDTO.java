package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MarvelCharacterDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @Past
    private String modified;

    private Byte[] thumbnail;
    private Byte[] fullImage;

    @Size(min = 1)
    private List<Long> comicsId;

}
