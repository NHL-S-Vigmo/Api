package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "logs")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Log implements EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "message", length = 220)
    private String message;

    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
