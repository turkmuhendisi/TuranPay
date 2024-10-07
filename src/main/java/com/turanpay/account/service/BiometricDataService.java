package com.turanpay.account.service;

import com.turanpay.account.model.BiometricData;
import com.turanpay.account.repository.BiometricDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BiometricDataService {
    private final BiometricDataRepository biometricDataRepository;

    public BiometricDataService(BiometricDataRepository biometricDataRepository) {
        this.biometricDataRepository = biometricDataRepository;
    }

    public Optional<BiometricData> getCustomerPhotoImageForCustomerId(String customerId) {
        return  biometricDataRepository.findBiometricDataByCustomerId(customerId);
    }

    public void saveImageUrlToDatabase(String customerId, String imageUrl) {
        BiometricData biometricData = new BiometricData(
                imageUrl,
                customerId
        );
        biometricDataRepository.save(biometricData);
    }
}
