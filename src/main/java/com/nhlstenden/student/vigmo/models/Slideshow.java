package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "slideshows")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Slideshow implements EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "slideshow", fetch = FetchType.EAGER)
    private List<Slide> slideList = new ArrayList<>(0);

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
