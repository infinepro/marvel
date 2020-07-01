package com.marvel.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.marvel.services.DateHelperService.DATE_FORMAT;

@Getter
@Setter
@Accessors(chain = true)
public class MarvelCharacterDTO {

    private Long id;

    @NotBlank(message = "You must specify a name")
    private String name;

    @NotBlank(message = "You must specify a discription")
    private String description;

    @Length(max = 19, min = 19)
    private String modified;
    private Byte[] thumbnail;
    private Byte[] fullImage;

    public MarvelCharacterDTO() {
        this.modified = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
