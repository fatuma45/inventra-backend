package com.inventra.inventra_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sequences")
public class Sequence {

    @Id
    @Column(length = 50)
    private String sequenceName;

    @Column(nullable = false)
    private Long nextValue;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Long getNextValue() {
        return nextValue;
    }

    public void setNextValue(Long nextValue) {
        this.nextValue = nextValue;
    }
}