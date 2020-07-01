package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MarvelCharacterDTO {

    private Long id;

    @NotBlank(message = "You must specify a name")
    private String name;

    @NotBlank(message = "You must specify a discription")
    private String description;

    private String modified;
    private Byte[] thumbnail;
    private Byte[] fullImage;

    @Size(min = 1)
    private List<Long> comicsId;

}
