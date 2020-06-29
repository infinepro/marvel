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
@Entity
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime modified;
    private String format;
    private Integer pageCount;

    @Lob
    private Byte[] thumbnail;

    @Lob
    private Byte[] fullImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comic")
    private List<ComicDate> dates = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comic")
    private List<ComicPrice> prices = new ArrayList<>();

    @ManyToMany(mappedBy = "comicList")
    private List<Character> characters = new ArrayList<>();

    public void addComicDate(ComicDate comicDate) {
        if (comicDate != null) {
            this.dates.add(comicDate);
        }
    }

    public void addComicPrice(ComicPrice comicPrice) {
        if (comicPrice != null) {
            this.prices.add(comicPrice);
        }
    }

    public void addCharacter(Character character) {
        if (character != null) {
            this.characters.add(character);
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
                ", characters=" + characters +
                '}';
    }
}


