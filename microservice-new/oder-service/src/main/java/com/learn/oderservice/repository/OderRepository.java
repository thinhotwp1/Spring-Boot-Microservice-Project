package com.learn.oderservice.repository;

import com.learn.oderservice.model.Oder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OderRepository extends JpaRepository<Oder,Long> {
}
