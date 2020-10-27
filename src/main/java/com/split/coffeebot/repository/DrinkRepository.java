package com.split.coffeebot.repository;

import com.split.coffeebot.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data  repository for the Drink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findByNameNotIn(Collection<String> names);
}
