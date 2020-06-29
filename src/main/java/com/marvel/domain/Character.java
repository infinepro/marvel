package com.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Long modified;
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

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", modified=" + modified +
                ", thumbnail=" + Arrays.toString(thumbnail) +
                ", fullImage=" + Arrays.toString(fullImage) +
                ", comicList=" + comicList +
                '}';
    }
}
