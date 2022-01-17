package com.nhlstenden.student.vigmo.models;

import lombok.*;

import javax.persistence.*;

@Generated
@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 220)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "pfp_location", length = 220)
    private String pfpLocation;

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
