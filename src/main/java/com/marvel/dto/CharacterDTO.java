package com.marvel.dto;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

public class CharacterDTO {


    private Long id;
    private String name;
    private String description;
    private Date modified;
    private Byte[] thumbnail;
    private Byte[] fullImage;
    private Set<ComicDTO> comicList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterDTO)) return false;
        CharacterDTO character = (CharacterDTO) o;
        return Objects.equals(id, character.id) &&
                Objects.equals(name, character.name) &&
                Objects.equals(description, character.description) &&
                Objects.equals(modified, character.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, modified);
    }
}
