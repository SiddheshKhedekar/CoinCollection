package com.siddhesh.coincollection.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.siddhesh.coincollection.model.Currency;
import jakarta.persistence.*;

@Entity
@Table(name = "denominations")
public class Denomination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String series;

    @Column(name = "\"value\"")
    private Double value;

    public Denomination() {
    }

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    @JsonBackReference
    private Currency currency;

    public Denomination(Long id, String series, Double value, String imageUrl, Currency currency) {
        this.id = id;
        this.series = series;
        this.value = value;
        this.imageUrl = imageUrl;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
