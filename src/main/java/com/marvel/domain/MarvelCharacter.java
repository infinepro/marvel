package com.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class MarvelCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime modified;

    @Lob
    private Byte[] thumbnail;

    @Lob
    private Byte[] fullImage;

    @ManyToMany(mappedBy = "marvelCharacters",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    private Set<Comic> comics = new HashSet<>();

    public void setComic(Comic comic) {
        if (comic != null) {
            this.comics.add(comic);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarvelCharacter)) return false;
        MarvelCharacter character = (MarvelCharacter) o;
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
                ", comicList=" + comics +
                '}';
    }
}
