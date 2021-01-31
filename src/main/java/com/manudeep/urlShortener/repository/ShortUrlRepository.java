package com.manudeep.urlShortener.repository;

import com.manudeep.urlShortener.Entities.ShortURL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortURL, Long> {
    //Get short url if the url is not expired
    Optional<ShortURL> findByIdAndExpired(Long id, Boolean expired);
}
