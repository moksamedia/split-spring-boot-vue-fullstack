package com.split.coffeebot.web.rest;

import com.split.coffeebot.domain.Drink;
import com.split.coffeebot.repository.DrinkRepository;
import com.split.coffeebot.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.split.coffeebot.domain.Drink}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DrinkResource {

    private final Logger log = LoggerFactory.getLogger(DrinkResource.class);

    private static final String ENTITY_NAME = "drink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrinkRepository drinkRepository;

    public DrinkResource(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    /**
     * {@code POST  /drinks} : Create a new drink.
     *
     * @param drink the drink to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drink, or with status {@code 400 (Bad Request)} if the drink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drinks")
    public ResponseEntity<Drink> createDrink(@Valid @RequestBody Drink drink) throws URISyntaxException {
        log.debug("REST request to save Drink : {}", drink);
        if (drink.getId() != null) {
            throw new BadRequestAlertException("A new drink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Drink result = drinkRepository.save(drink);
        return ResponseEntity.created(new URI("/api/drinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drinks} : Updates an existing drink.
     *
     * @param drink the drink to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drink,
     * or with status {@code 400 (Bad Request)} if the drink is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drink couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drinks")
    public ResponseEntity<Drink> updateDrink(@Valid @RequestBody Drink drink) throws URISyntaxException {
        log.debug("REST request to update Drink : {}", drink);
        if (drink.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Drink result = drinkRepository.save(drink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, drink.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drinks} : get all the drinks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drinks in body.
     */
    @GetMapping("/drinks")
    public List<Drink> getAllDrinks() {
        log.debug("REST request to get all Drinks");
        return drinkRepository.findAll();
    }

    /**
     * {@code GET  /drinks/:id} : get the "id" drink.
     *
     * @param id the id of the drink to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drink, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drinks/{id}")
    public ResponseEntity<Drink> getDrink(@PathVariable Long id) {
        log.debug("REST request to get Drink : {}", id);
        Optional<Drink> drink = drinkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(drink);
    }

    /**
     * {@code DELETE  /drinks/:id} : delete the "id" drink.
     *
     * @param id the id of the drink to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drinks/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        log.debug("REST request to delete Drink : {}", id);

        drinkRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
