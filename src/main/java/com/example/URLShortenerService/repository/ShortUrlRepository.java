package com.example.URLShortenerService.repository;

import com.example.URLShortenerService.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);

    Optional<ShortUrl> findByOriginalUrl(String originalUrl);
}
