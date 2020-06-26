package com.marvel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ComicDTO {

    private String Id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Past
    private Date modified;

    @NotBlank
    private String format;

    @Min(1)
    @Max(1000)
    private Short pageCount;

    private Byte[] thumbnail;
    private Byte[] fullImage;
    private List<ComicDateDTO> dates = new ArrayList<>();
    private List<ComicPriceDTO> prices = new ArrayList<>();
}


