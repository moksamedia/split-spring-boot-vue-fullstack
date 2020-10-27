package com.split.coffeebot.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.split.coffeebot.web.rest.TestUtil;

public class DrinkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drink.class);
        Drink drink1 = new Drink();
        drink1.setId(1L);
        Drink drink2 = new Drink();
        drink2.setId(drink1.getId());
        assertThat(drink1).isEqualTo(drink2);
        drink2.setId(2L);
        assertThat(drink1).isNotEqualTo(drink2);
        drink1.setId(null);
        assertThat(drink1).isNotEqualTo(drink2);
    }
}
