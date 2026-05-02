package com.example.URLShortenerService.controller;

import com.example.URLShortenerService.entity.ShortUrl;
import com.example.URLShortenerService.model.ShortenUrlRequest;
import com.example.URLShortenerService.model.ShortenUrlResponse;
import com.example.URLShortenerService.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final UrlShortenerService shortenerService;

    public HomeController(UrlShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping("shorten")
    public String shorten(@Valid @ModelAttribute ShortenUrlRequest request, Model model) {
        try {
            ShortUrl shortUrl = shortenerService.createShortUrl(request.getUrl());

            ShortenUrlResponse response = new ShortenUrlResponse();
            response.setShortCode(shortUrl.getShortCode());
            response.setShortUrl("http://localhost:8081/" + shortUrl.getShortCode());
            response.setOriginalUrl(shortUrl.getOriginalUrl());
            response.setCreatedAt(shortUrl.getCreatedAt());
            response.setExpiresAt(shortUrl.getExpiresAt());

            model.addAttribute("response", response);
            return "result";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }
    }

    @GetMapping("{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode) {
        Optional<ShortUrl> shortUrl = shortenerService.getByShortCode(shortCode);
        if (shortUrl.isPresent()) {
            return new RedirectView(shortUrl.get().getOriginalUrl());
        }
        return new RedirectView("/");
    }
}
