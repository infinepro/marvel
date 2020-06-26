package com.marvel.dto;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class ComicDTO {

    private Long Id;
    private String title;
    private String description;
    private Date modified;
    private String format;
    private Short pageCount;
    private List<ComicDateDTO> dates;
    private List<ComicPriceDTO> prices;
    private Byte[] thumbnail;
    private Byte[] fullImage;
    private Set<CharacterDTO> characters;

}


