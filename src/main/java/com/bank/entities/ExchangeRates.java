package com.bank.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Exchange_rates")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class ExchangeRates {

    @Id
    @GeneratedValue
    private Long id;

    private Double usdToEuro;

    private Double usdToUah;

    private Double eurToUah;

}
