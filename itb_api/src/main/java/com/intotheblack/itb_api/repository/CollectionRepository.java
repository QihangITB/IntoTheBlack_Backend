package com.intotheblack.itb_api.repository;

import com.intotheblack.itb_api.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

}