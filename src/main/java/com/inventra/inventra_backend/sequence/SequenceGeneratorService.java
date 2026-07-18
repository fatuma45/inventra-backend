package com.inventra.inventra_backend.sequence;

import com.inventra.inventra_backend.entity.Sequence;
import com.inventra.inventra_backend.repository.SequenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

    private final SequenceRepository sequenceRepository;

    public SequenceGeneratorService(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Transactional
    public synchronized String generateId(String prefix) {

        Sequence sequence = sequenceRepository.findById(prefix)
                .orElseGet(() -> {
                    Sequence seq = new Sequence();
                    seq.setSequenceName(prefix);
                    seq.setNextValue(1L);
                    return seq;
                });

        Long nextValue = sequence.getNextValue();
        sequence.setNextValue(nextValue + 1);

        sequenceRepository.save(sequence);

        // Generates IDs like USR001, ROL001, PER001...
        return prefix + String.format("%03d", nextValue);
    }
}
