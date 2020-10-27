package com.split.coffeebot.web.rest;

import com.split.coffeebot.domain.Drink;
import com.split.coffeebot.domain.enumeration.DrinkSize;
import com.split.coffeebot.repository.DrinkRepository;
import com.split.coffeebot.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.split.client.SplitClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * CoffeeBotResource controller
 */
@RestController
@RequestMapping("/api/coffee-bot")
public class CoffeeBotResource {

    private final Logger log = LoggerFactory.getLogger(CoffeeBotResource.class);

    SplitClient splitClient;
    DrinkRepository drinkRepository;

    public CoffeeBotResource(SplitClient splitClient, DrinkRepository drinkRepository) {
        this.splitClient = splitClient;
        this.drinkRepository = drinkRepository;
    }

    private Drink makeDrink(String name, DrinkSize size, Integer caffeineMg, Integer price) {
        Drink drink = new Drink();
        drink.setCaffeineMilligrams(caffeineMg);
        drink.setName(name);
        drink.setSize(size);
        drink.setPriceDollars(price);
        return drink;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        drinkRepository.save(makeDrink("Water", DrinkSize.Small, 0, 1));
        drinkRepository.save(makeDrink("Soda", DrinkSize.Medium, 30, 3));
        drinkRepository.save(makeDrink("Coffee", DrinkSize.XLarge, 50, 5));
        drinkRepository.save(makeDrink("Coffee", DrinkSize.Small, 30, 3));
        drinkRepository.save(makeDrink("Coffee", DrinkSize.Medium, 40, 3));
        drinkRepository.save(makeDrink("Latte", DrinkSize.Large, 100, 8));
        drinkRepository.save(makeDrink("Latte", DrinkSize.Small, 80, 6));
        drinkRepository.save(makeDrink("Latte", DrinkSize.Medium, 60, 5));
    }

    /**
    * GET listDrinks
    */
    @GetMapping("/list-drinks")
    public List<Drink> listDrinks() {

        Optional<String> userName = SecurityUtils.getCurrentUserLogin();

        String treatment = splitClient.getTreatment(userName.get(),"drink-types");

        if (treatment.equals("on")) {
            return drinkRepository.findAll();
        }
        else {
            return drinkRepository.findByNameNotIn(Arrays.asList("Latte", "Soda"));
        }

    }

}
