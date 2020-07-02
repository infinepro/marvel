package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.marvel.services.DateHelperService.DATE_FORMAT;
import static com.marvel.services.DateHelperService.DATE_FORMAT_LENGTH;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ComicDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Length(max = 19, min = 19)
    private String modified;

    @NotBlank
    private String format;

    @Min(1)
    @Max(1000)
    private Integer pageCount;

    private String thumbnailImageName;
    private String fullImageName;
    private List<ComicDateDTO> dates = new ArrayList<>();
    private List<ComicPriceDTO> prices = new ArrayList<>();
    private List<MarvelCharacterDTO> marvelCharacters = new ArrayList<>();
}


