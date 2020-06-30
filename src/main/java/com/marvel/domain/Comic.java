package com.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Entity(name = "comic")
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private String format;
    private Integer pageCount;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime modified;

    @Lob
    private Byte[] thumbnail;

    @Lob
    private Byte[] fullImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comic")
    private List<ComicDate> dates = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comic")
    private List<ComicPrice> prices = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "comic_marvel_characters",
            joinColumns = @JoinColumn(name = "comic_id"),
            inverseJoinColumns = @JoinColumn(name = "marvel_characters_id"))
    private List<MarvelCharacter> marvelCharacters = new ArrayList<>();

    public void setComicDate(ComicDate comicDate) {
        if (comicDate != null) {
            this.dates.add(comicDate);
        }
    }

    public void setComicPrice(ComicPrice comicPrice) {
        if (comicPrice != null) {
            this.prices.add(comicPrice);
        }
    }

    public void setCharacter(MarvelCharacter character) {
        if (character != null) {
            this.marvelCharacters.add(character);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comic)) return false;
        Comic comic = (Comic) o;
        return Objects.equals(getId(), comic.getId()) &&
                Objects.equals(getTitle(), comic.getTitle()) &&
                Objects.equals(getDescription(), comic.getDescription()) &&
                Objects.equals(getModified(), comic.getModified()) &&
                Objects.equals(getFormat(), comic.getFormat()) &&
                Objects.equals(getPageCount(), comic.getPageCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getModified(), getFormat(), getPageCount());
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", modified=" + modified +
                ", format='" + format + '\'' +
                ", pageCount=" + pageCount +
                ", thumbnail=" + Arrays.toString(thumbnail) +
                ", fullImage=" + Arrays.toString(fullImage) +
                ", dates=" + dates +
                ", prices=" + prices +
                ", characters=" + marvelCharacters +
                '}';
    }
}


