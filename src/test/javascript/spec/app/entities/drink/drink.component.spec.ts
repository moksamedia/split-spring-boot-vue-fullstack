/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DrinkComponent from '@/entities/drink/drink.vue';
import DrinkClass from '@/entities/drink/drink.component';
import DrinkService from '@/entities/drink/drink.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Drink Management Component', () => {
    let wrapper: Wrapper<DrinkClass>;
    let comp: DrinkClass;
    let drinkServiceStub: SinonStubbedInstance<DrinkService>;

    beforeEach(() => {
      drinkServiceStub = sinon.createStubInstance<DrinkService>(DrinkService);
      drinkServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DrinkClass>(DrinkComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          drinkService: () => drinkServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      drinkServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDrinks();
      await comp.$nextTick();

      // THEN
      expect(drinkServiceStub.retrieve.called).toBeTruthy();
      expect(comp.drinks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      drinkServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDrink();
      await comp.$nextTick();

      // THEN
      expect(drinkServiceStub.delete.called).toBeTruthy();
      expect(drinkServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
