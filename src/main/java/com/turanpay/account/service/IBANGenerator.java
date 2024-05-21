package com.turanpay.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
public class IBANGenerator {
    private final String COUNTRY_CODE = "TR";
    private final String BANK_CODE = "00571";
    private final char NATIONAL_CONTROL_CODE = '0';

    @Transactional
    public synchronized String generateIban(String accountNumber) {
        String basicIban = BANK_CODE + NATIONAL_CONTROL_CODE + accountNumber;
        String numericIban = convertToNumericString(basicIban);

        BigInteger numericIbanBigInt = new BigInteger(numericIban);
        BigInteger checkDigits = BigInteger.valueOf(98).subtract(numericIbanBigInt.mod(BigInteger.valueOf(97)));

        return COUNTRY_CODE + String.format("%02d", checkDigits) + basicIban;
    }

    private String convertToNumericString(String stringIban) {
        StringBuilder numericString = new StringBuilder();

        for (char ch : stringIban.toCharArray()) {
            if (Character.isDigit(ch)) {
                numericString.append(ch);
            } else {
                numericString.append(Character.getNumericValue(ch));
            }
        }

        return numericString.toString();
    }
}
