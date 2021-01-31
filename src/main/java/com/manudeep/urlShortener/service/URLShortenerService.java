package com.manudeep.urlShortener.service;

public interface URLShortenerService {
    String shorten(String longurl);
    String get(String shortUrl) throws Exception;
    void expire();
}
