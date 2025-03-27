package org.example.springapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class NewsDto {
    private Long id;
    private String title;
    private String text;
    private String category;
    private Instant date;
}
