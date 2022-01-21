package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "media_slides")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MediaSlide extends Slide {
    @Column(name = "audio_enabled", nullable = false)
    private Boolean audioEnabled = false;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "resource", nullable = false)
    private String resource;
}
