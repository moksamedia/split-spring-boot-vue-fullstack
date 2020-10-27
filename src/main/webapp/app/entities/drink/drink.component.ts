import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDrink } from '@/shared/model/drink.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DrinkService from './drink.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Drink extends mixins(AlertMixin) {
  @Inject('drinkService') private drinkService: () => DrinkService;
  private removeId: number = null;

  public drinks: IDrink[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDrinks();
  }

  public clear(): void {
    this.retrieveAllDrinks();
  }

  public retrieveAllDrinks(): void {
    this.isFetching = true;

    this.drinkService()
      .retrieve()
      .then(
        res => {
          this.drinks = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDrink): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDrink(): void {
    this.drinkService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Drink is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllDrinks();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
