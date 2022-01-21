package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Table(name = "text_slides")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TextSlide extends Slide {
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;
}
