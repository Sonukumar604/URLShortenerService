package com.example.URLShortenerService.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequest {

    @NotBlank(message = "url must not be blank")
    @URL(message = "must be a valid URL")
    private String url;
}
