package com.example.URLShortenerService.service;

import com.example.URLShortenerService.entity.ShortUrl;
import com.example.URLShortenerService.repository.ShortUrlRepository;
import com.example.URLShortenerService.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final ShortCodeGenerator codeGenerator;
    private static final int MAX_GENERATION_ATTEMPTS = 10;

    public UrlShortenerService(ShortUrlRepository repository, ShortCodeGenerator codeGenerator) {
        this.repository = repository;
        this.codeGenerator = codeGenerator;
    }

    @Transactional
    public ShortUrl createShortUrl(String originalUrl) {
        String normalized = originalUrl == null ? null : originalUrl.trim();
        if (normalized == null || normalized.isEmpty()) {
            throw new IllegalArgumentException("originalUrl must not be empty");
        }

        // Return existing if present
        Optional<ShortUrl> existing = repository.findByOriginalUrl(normalized);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Generate unique short code (handle collisions)
        String shortCode = null;
        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            String candidate = codeGenerator.generate();
            if (!repository.findByShortCode(candidate).isPresent()) {
                shortCode = candidate;
                break;
            }
        }

        if (shortCode == null) {
            throw new IllegalStateException("Failed to generate unique short code after " + MAX_GENERATION_ATTEMPTS + " attempts");
        }

        ShortUrl entity = new ShortUrl();
        entity.setShortCode(shortCode);
        entity.setOriginalUrl(normalized);

        return repository.save(entity);
    }

    @Transactional
    public Optional<ShortUrl> getByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode);
    }
}
