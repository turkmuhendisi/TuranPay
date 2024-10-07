package com.turanpay.account.controller;

import com.turanpay.account.config.FTPUtils;
import com.turanpay.account.dto.FaceVerificationRequest;
import com.turanpay.account.model.BiometricData;
import com.turanpay.account.repository.BiometricDataRepository;
import com.turanpay.account.service.BiometricDataService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


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

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("customerId") String customerId) {

        String ftpDir = "/tpay_face_images";

        try {
            // Geçici dosyayı oluştur
            File tempFile = File.createTempFile(customerId, image.getOriginalFilename());
            image.transferTo(tempFile);

            // FTP'ye yükle
            FTPUtils.uploadFileToFtp(
                    tempFile,
                    ftpDir,
                    customerId
            );
            // Geçici dosyayı sil
            tempFile.delete();
            // URL'yi veritabanına kaydet
            String imageUrl = "http://localhost/tpay_face_images/" + customerId + image.getOriginalFilename();
            biometricDataService.saveImageUrlToDatabase(customerId, imageUrl);

            return ResponseEntity.ok("URL başarıyla kaydedildi: " + imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Dosya yükleme başarısız oldu");
        }
    }

    @PostMapping("/validate-face")
    public ResponseEntity<String> validateFace(@RequestBody FaceVerificationRequest request) {
        Optional<BiometricData> faceImageOptional = biometricDataService.getCustomerPhotoImageForCustomerId(request.getCustomerId());

        if (faceImageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BiometricData faceImage = faceImageOptional.get();

        // Verileri JSON formatında göndermek için bir Map oluştur
        Map<String, Object> data = new HashMap<>();
        data.put("referenceImage", faceImage.getFaceImage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(
                    "http://10.196.136.255:8000/process-image",
                    entity,
                    String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during image processing: " + e.getMessage());
        }

        return response;
    }
}
