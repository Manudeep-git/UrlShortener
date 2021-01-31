package com.manudeep.urlShortener.repository;

import com.manudeep.urlShortener.Entities.LongURL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongUrlRepository extends CrudRepository<LongURL,Long> {

}
