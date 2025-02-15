package com.intotheblack.itb_api.repository;

import com.intotheblack.itb_api.model.Fragment;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Integer> {

    @Query("SELECT f FROM Fragment f ORDER BY f.orderNumber ASC")
    Optional <List<Fragment>> findAllOrderByOrderNumber();
}