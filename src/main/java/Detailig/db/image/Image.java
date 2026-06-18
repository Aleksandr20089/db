package Detailig.db.image;

import Detailig.db.entiti.Service;
import Detailig.db.entiti.Worker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String originalFileName;
    private Long size;
    private String contentType;

    @Lob
    @JsonIgnore
    private byte[] bytes;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;
}
