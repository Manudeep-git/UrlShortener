package com.manudeep.urlShortener.Entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LongURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String url;
    @Column(insertable = false,updatable = false) // prevents insert and update at database level
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL)
    private ShortURL shortURL;

}
