package com.turanpay.account.service;

import com.turanpay.account.dto.BiometricDataRequest;
import com.turanpay.account.model.BiometricData;
import com.turanpay.account.repository.BiometricDataRepository;
import org.springframework.stereotype.Service;

@Service
public class BiometricDataService {
    private final BiometricDataRepository biometricDataRepository;

    public BiometricDataService(BiometricDataRepository biometricDataRepository) {
        this.biometricDataRepository = biometricDataRepository;
    }

    public BiometricData getFaceImageForCustomer(String customerId) {
        return  biometricDataRepository.findBiometricDataByCustomerId(customerId);
    }
}
