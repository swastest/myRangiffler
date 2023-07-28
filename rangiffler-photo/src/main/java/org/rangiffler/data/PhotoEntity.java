package org.rangiffler.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "photos")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Column(nullable = true)
    private String description;
    @Column(columnDefinition = "bytea")
    private byte[] photo;

}
