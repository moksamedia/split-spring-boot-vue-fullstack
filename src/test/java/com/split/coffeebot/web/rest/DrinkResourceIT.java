package com.split.coffeebot.web.rest;

import com.split.coffeebot.CoffeebotApp;
import com.split.coffeebot.domain.Drink;
import com.split.coffeebot.repository.DrinkRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.split.coffeebot.domain.enumeration.DrinkSize;
/**
 * Integration tests for the {@link DrinkResource} REST controller.
 */
@SpringBootTest(classes = CoffeebotApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DrinkResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DrinkSize DEFAULT_SIZE = DrinkSize.Small;
    private static final DrinkSize UPDATED_SIZE = DrinkSize.Medium;

    private static final Integer DEFAULT_CAFFEINE_MILLIGRAMS = 1;
    private static final Integer UPDATED_CAFFEINE_MILLIGRAMS = 2;

    private static final Integer DEFAULT_PRICE_DOLLARS = 1;
    private static final Integer UPDATED_PRICE_DOLLARS = 2;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrinkMockMvc;

    private Drink drink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drink createEntity(EntityManager em) {
        Drink drink = new Drink()
            .name(DEFAULT_NAME)
            .size(DEFAULT_SIZE)
            .caffeineMilligrams(DEFAULT_CAFFEINE_MILLIGRAMS)
            .priceDollars(DEFAULT_PRICE_DOLLARS);
        return drink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drink createUpdatedEntity(EntityManager em) {
        Drink drink = new Drink()
            .name(UPDATED_NAME)
            .size(UPDATED_SIZE)
            .caffeineMilligrams(UPDATED_CAFFEINE_MILLIGRAMS)
            .priceDollars(UPDATED_PRICE_DOLLARS);
        return drink;
    }

    @BeforeEach
    public void initTest() {
        drink = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrink() throws Exception {
        int databaseSizeBeforeCreate = drinkRepository.findAll().size();
        // Create the Drink
        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isCreated());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeCreate + 1);
        Drink testDrink = drinkList.get(drinkList.size() - 1);
        assertThat(testDrink.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrink.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testDrink.getCaffeineMilligrams()).isEqualTo(DEFAULT_CAFFEINE_MILLIGRAMS);
        assertThat(testDrink.getPriceDollars()).isEqualTo(DEFAULT_PRICE_DOLLARS);
    }

    @Test
    @Transactional
    public void createDrinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drinkRepository.findAll().size();

        // Create the Drink with an existing ID
        drink.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinkRepository.findAll().size();
        // set the field null
        drink.setName(null);

        // Create the Drink, which fails.


        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinkRepository.findAll().size();
        // set the field null
        drink.setSize(null);

        // Create the Drink, which fails.


        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaffeineMilligramsIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinkRepository.findAll().size();
        // set the field null
        drink.setCaffeineMilligrams(null);

        // Create the Drink, which fails.


        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceDollarsIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinkRepository.findAll().size();
        // set the field null
        drink.setPriceDollars(null);

        // Create the Drink, which fails.


        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrinks() throws Exception {
        // Initialize the database
        drinkRepository.saveAndFlush(drink);

        // Get all the drinkList
        restDrinkMockMvc.perform(get("/api/drinks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drink.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].caffeineMilligrams").value(hasItem(DEFAULT_CAFFEINE_MILLIGRAMS)))
            .andExpect(jsonPath("$.[*].priceDollars").value(hasItem(DEFAULT_PRICE_DOLLARS)));
    }
    
    @Test
    @Transactional
    public void getDrink() throws Exception {
        // Initialize the database
        drinkRepository.saveAndFlush(drink);

        // Get the drink
        restDrinkMockMvc.perform(get("/api/drinks/{id}", drink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drink.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.caffeineMilligrams").value(DEFAULT_CAFFEINE_MILLIGRAMS))
            .andExpect(jsonPath("$.priceDollars").value(DEFAULT_PRICE_DOLLARS));
    }
    @Test
    @Transactional
    public void getNonExistingDrink() throws Exception {
        // Get the drink
        restDrinkMockMvc.perform(get("/api/drinks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrink() throws Exception {
        // Initialize the database
        drinkRepository.saveAndFlush(drink);

        int databaseSizeBeforeUpdate = drinkRepository.findAll().size();

        // Update the drink
        Drink updatedDrink = drinkRepository.findById(drink.getId()).get();
        // Disconnect from session so that the updates on updatedDrink are not directly saved in db
        em.detach(updatedDrink);
        updatedDrink
            .name(UPDATED_NAME)
            .size(UPDATED_SIZE)
            .caffeineMilligrams(UPDATED_CAFFEINE_MILLIGRAMS)
            .priceDollars(UPDATED_PRICE_DOLLARS);

        restDrinkMockMvc.perform(put("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrink)))
            .andExpect(status().isOk());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeUpdate);
        Drink testDrink = drinkList.get(drinkList.size() - 1);
        assertThat(testDrink.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrink.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testDrink.getCaffeineMilligrams()).isEqualTo(UPDATED_CAFFEINE_MILLIGRAMS);
        assertThat(testDrink.getPriceDollars()).isEqualTo(UPDATED_PRICE_DOLLARS);
    }

    @Test
    @Transactional
    public void updateNonExistingDrink() throws Exception {
        int databaseSizeBeforeUpdate = drinkRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrinkMockMvc.perform(put("/api/drinks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrink() throws Exception {
        // Initialize the database
        drinkRepository.saveAndFlush(drink);

        int databaseSizeBeforeDelete = drinkRepository.findAll().size();

        // Delete the drink
        restDrinkMockMvc.perform(delete("/api/drinks/{id}", drink.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
