package com.nhlstenden.student.vigmo.models;

import lombok.*;

import javax.persistence.*;

@Table(name = "screens")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "location", length = 50)
    private String location;

    @Column(name = "auth_key", nullable = false)
    private String authKey;
}
