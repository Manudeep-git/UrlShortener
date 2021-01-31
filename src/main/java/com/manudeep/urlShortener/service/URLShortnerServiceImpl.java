package com.manudeep.urlShortener.service;


import com.manudeep.urlShortener.Entities.LongURL;
import com.manudeep.urlShortener.Entities.ShortURL;
import com.manudeep.urlShortener.repository.LongUrlRepository;
import com.manudeep.urlShortener.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class URLShortnerServiceImpl implements URLShortenerService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;
    @Autowired
    private LongUrlRepository longUrlRepository;


    @Override
    public String shorten(String longUrlString) {
        ShortURL shortURL =  ShortURL.builder()
                .domain("localhost:8080")
                .protocol("http")
                .expired(false)
                .createdAt(LocalDateTime.now())
                .build();

        LongURL longURL = LongURL.builder()
                .url(longUrlString)
                .createdAt(LocalDateTime.now())
                .shortURL(shortURL).build();

        shortURL.setLongURL(longURL);

        longURL = longUrlRepository.save(longURL);

        //build tiny url
        URI uri = URI.create(shortURL.getProtocol()+"://"+shortURL.getDomain()+"/"+getShortenedPath(longURL.getShortURL().getId()));
        return uri.toString();
    }

    private String getShortenedPath(Long Id) {
        String path = Base64.getEncoder().encodeToString(String.valueOf(Id).getBytes()); // shortening algorithm , encryption logic
                                                                                            // for id
        return path;
    }

    @Override
    public String get(String shortUrlRequest) throws Exception {
        Long id = 0l;
        try {
            id = decodePath(shortUrlRequest);
        }
        catch (Exception e){
            throw  new Exception("Invalid shortURL");
        }
        ShortURL shortURL = shortUrlRepository.findByIdAndExpired(id,false)
                .orElseThrow(()->  new Exception("shortURL doesn't exist or expired"));
        return shortURL.getLongURL().getUrl();
    }

    @Override
    public void expire() {
        Iterable<ShortURL> shortURLIterable = shortUrlRepository.findAll();
        shortURLIterable.forEach(shortURL -> {
            if(!shortURL.getExpired()){
                if(shortURL.getCreatedAt().isBefore(LocalDateTime.now().minusSeconds(10))){
                    shortURL.setExpired(true);
                    shortUrlRepository.save(shortURL);
                }
            }
        });
    }

    private Long decodePath(String shortUrl) {
        byte[] bytes = Base64.getDecoder().decode(shortUrl);
        String str = new String(bytes, StandardCharsets.UTF_8);
        return Long.parseLong(str);
    }
}
