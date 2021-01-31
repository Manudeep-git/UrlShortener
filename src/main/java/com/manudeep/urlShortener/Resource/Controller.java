package com.manudeep.urlShortener.Resource;

import com.manudeep.urlShortener.models.URLShortenRequest;
import com.manudeep.urlShortener.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    URLShortenerService urlShortenerService;

    @PostMapping("/longurl/")
    ResponseEntity shortenUrl(@RequestBody URLShortenRequest urlShortenRequest){
        //return  null;
        return  ResponseEntity.ok(urlShortenerService.shorten(urlShortenRequest.getUrl()));
    }

    @GetMapping("{shorturl}")
    ResponseEntity getShortUrl(@PathVariable("shorturl") String shorturl){
        try {
            return ResponseEntity.ok(urlShortenerService.get(shorturl));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/shorturl/expiration")
    ResponseEntity expire(){
        urlShortenerService.expire();
        return ResponseEntity.ok().build();
    }
}
