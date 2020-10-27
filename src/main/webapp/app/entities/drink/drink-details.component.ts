import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDrink } from '@/shared/model/drink.model';
import DrinkService from './drink.service';

@Component
export default class DrinkDetails extends Vue {
  @Inject('drinkService') private drinkService: () => DrinkService;
  public drink: IDrink = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.drinkId) {
        vm.retrieveDrink(to.params.drinkId);
      }
    });
  }

  public retrieveDrink(drinkId) {
    this.drinkService()
      .find(drinkId)
      .then(res => {
        this.drink = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
