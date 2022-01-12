package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Table(name = "rss_slides")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RssSlide extends Slide {
    @Column(name = "url", nullable = false, length = 220)
    private String url;

    @Column(name = "title_tag", length = 50)
    private String titleTag;

    @Column(name = "description_tag", length = 50)
    private String descriptionTag;

    @Column(name = "author_tag", length = 50)
    private String authorTag;

    @Column(name = "category_tag", length = 50)
    private String categoryTag;

    @Column(name = "image_tag", length = 50)
    private String imageTag;
}
