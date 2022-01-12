package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

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
