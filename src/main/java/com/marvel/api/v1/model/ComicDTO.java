package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ComicDTO {

    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Past
    private String modified;

    @NotBlank
    private String format;

    @Min(1)
    @Max(1000)
    private Integer pageCount;

    private Byte[] thumbnail;
    private Byte[] fullImage;
    private List<ComicDateDTO> dates = new ArrayList<>();
    private List<ComicPriceDTO> prices = new ArrayList<>();
    private Set<MarvelCharacterDTO> marvelCharacters = new HashSet<>();
}


