package com.turanpay.account.repository;

import com.turanpay.account.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {
    Sequence findByType(String type);
}
