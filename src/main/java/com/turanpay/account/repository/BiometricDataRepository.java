package com.turanpay.account.repository;

import com.turanpay.account.model.BiometricData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometricDataRepository extends JpaRepository<BiometricData, String> {
    BiometricData findBiometricDataByCustomerId(String customerId);
}
