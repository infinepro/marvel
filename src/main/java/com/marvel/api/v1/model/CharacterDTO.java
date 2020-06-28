package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CharacterDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @Past
    private Date modified;

    private Byte[] thumbnail;
    private Byte[] fullImage;
    private Set<ComicDTO> comicList = new HashSet<>();

}
