package com.split.coffeebot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.split.coffeebot.domain.enumeration.DrinkSize;

/**
 * A Drink.
 */
@Entity
@Table(name = "drink")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Drink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private DrinkSize size;

    @NotNull
    @Column(name = "caffeine_milligrams", nullable = false)
    private Integer caffeineMilligrams;

    @NotNull
    @Column(name = "price_dollars", nullable = false)
    private Integer priceDollars;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Drink name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DrinkSize getSize() {
        return size;
    }

    public Drink size(DrinkSize size) {
        this.size = size;
        return this;
    }

    public void setSize(DrinkSize size) {
        this.size = size;
    }

    public Integer getCaffeineMilligrams() {
        return caffeineMilligrams;
    }

    public Drink caffeineMilligrams(Integer caffeineMilligrams) {
        this.caffeineMilligrams = caffeineMilligrams;
        return this;
    }

    public void setCaffeineMilligrams(Integer caffeineMilligrams) {
        this.caffeineMilligrams = caffeineMilligrams;
    }

    public Integer getPriceDollars() {
        return priceDollars;
    }

    public Drink priceDollars(Integer priceDollars) {
        this.priceDollars = priceDollars;
        return this;
    }

    public void setPriceDollars(Integer priceDollars) {
        this.priceDollars = priceDollars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drink)) {
            return false;
        }
        return id != null && id.equals(((Drink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drink{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", size='" + getSize() + "'" +
            ", caffeineMilligrams=" + getCaffeineMilligrams() +
            ", priceDollars=" + getPriceDollars() +
            "}";
    }
}
