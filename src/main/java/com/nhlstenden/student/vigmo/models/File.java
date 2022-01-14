package com.nhlstenden.student.vigmo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "files")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class File implements EntityId{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_name", nullable = false, length = 220)
    private String fileName;

    @Column(name = "mime_type", nullable = false, length = 50)
    private String mimeType;

    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_key", nullable = false, length = 64)
    private String fileKey;

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}