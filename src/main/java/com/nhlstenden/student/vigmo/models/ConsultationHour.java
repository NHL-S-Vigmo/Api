package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "consultation_hours")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConsultationHour implements EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", length = 220)
    private String description;

    @Column(name = "week_day", nullable = false, columnDefinition = "ENUM('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY')")
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
