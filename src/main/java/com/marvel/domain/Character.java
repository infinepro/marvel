package com.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Character {

    @Id
    private Long id;
    private String name;
    private String description;
    private Date modified;
    private Byte[] thumbnail;
    private Byte[] fullImage;

    @DBRef
    private List<Comic> comicList = new ArrayList<>();

    public void addComic(Comic comic) {
        if (comic != null) {
            this.comicList.add(comic);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
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
