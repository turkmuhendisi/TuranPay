package com.turanpay.account.controller;

import com.turanpay.account.dto.BiometricDataRequest;
import com.turanpay.account.model.BiometricData;
import com.turanpay.account.repository.BiometricDataRepository;
import com.turanpay.account.service.BiometricDataService;
import com.turanpay.account.service.CustomerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class BiometricDataController {
    private final BiometricDataService biometricDataService;
    private final BiometricDataRepository biometricDataRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public BiometricDataController(BiometricDataService biometricDataService, BiometricDataRepository biometricDataRepository) {
        this.biometricDataService = biometricDataService;
        this.biometricDataRepository = biometricDataRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadBiometricData(@RequestBody BiometricDataRequest request) {
        try {
            BiometricData biometricData = new BiometricData(request.getMobileCam(), request.getCustomerId());
            biometricDataRepository.save(biometricData);
            return ResponseEntity.ok("Biometric data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving biometric data: " + e.getMessage());
        }
    }

    @PostMapping("/validate-face")
    public ResponseEntity<String> validateFace(@RequestBody BiometricDataRequest request) {

        String faceImage = String.valueOf(biometricDataService.getFaceImageForCustomer(request.getCustomerId()));

        Map<String, String> data = new HashMap<>();
        data.put("mobileCam", request.getMobileCam());
        data.put("faceImage", faceImage);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(data, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8000/process-image",
                entity,
                String.class);

        return response;
    }
}
