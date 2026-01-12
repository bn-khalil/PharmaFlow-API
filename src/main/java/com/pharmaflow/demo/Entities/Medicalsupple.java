package com.pharmaflow.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "medicalsupples")
public class Medicalsupple extends Product{
    private long size;
}
