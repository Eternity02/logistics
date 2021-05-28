package com.example.api.repository;

import com.example.api.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {

    @Modifying
    @Transactional
    @Query("update Sale s set s.driving = ?1 where s.id = ?2")
    void updateDriving(boolean driving, String id);
}
