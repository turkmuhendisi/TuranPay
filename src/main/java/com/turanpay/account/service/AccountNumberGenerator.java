package com.turanpay.account.service;

import com.turanpay.account.model.Sequence;
import com.turanpay.account.repository.SequenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountNumberGenerator {

    private final SequenceRepository sequenceRepository;
    public AccountNumberGenerator(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Transactional
    public synchronized String generateAccountNumber() {
        Sequence sequence = sequenceRepository.findByType("ACCOUNT_NUMBER");
        if (sequence == null) {
            sequence = new Sequence("ACCOUNT_NUMBER", 1000108081016259L);
        }
        sequence.setLastValue(sequence.getLastValue() + 1);
        sequenceRepository.save(sequence);
        return String.valueOf(sequence.getLastValue());
    }
}
