package com.turanpay.account.repository;

import com.turanpay.account.model.BiometricData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BiometricDataRepository extends JpaRepository<BiometricData, String> {
    Optional<BiometricData> findBiometricDataByCustomerId(String customerId);
}
