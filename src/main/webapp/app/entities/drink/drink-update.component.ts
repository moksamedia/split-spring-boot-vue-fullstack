import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IDrink, Drink } from '@/shared/model/drink.model';
import DrinkService from './drink.service';

const validations: any = {
  drink: {
    name: {
      required,
    },
    size: {
      required,
    },
    caffeineMilligrams: {
      required,
      numeric,
    },
    priceDollars: {
      required,
      numeric,
    },
  },
};

@Component({
  validations,
})
export default class DrinkUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('drinkService') private drinkService: () => DrinkService;
  public drink: IDrink = new Drink();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.drinkId) {
        vm.retrieveDrink(to.params.drinkId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.drink.id) {
      this.drinkService()
        .update(this.drink)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Drink is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.drinkService()
        .create(this.drink)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Drink is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDrink(drinkId): void {
    this.drinkService()
      .find(drinkId)
      .then(res => {
        this.drink = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
