package com.parser.scraper.repository;

import com.parser.scraper.model.MarketPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MarketPlaceRepository extends CrudRepository<MarketPlace, Long> {

    @Override
    List<MarketPlace> findAll();

}
