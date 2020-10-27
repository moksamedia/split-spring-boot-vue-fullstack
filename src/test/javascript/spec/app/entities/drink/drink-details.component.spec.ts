/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DrinkDetailComponent from '@/entities/drink/drink-details.vue';
import DrinkClass from '@/entities/drink/drink-details.component';
import DrinkService from '@/entities/drink/drink.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Drink Management Detail Component', () => {
    let wrapper: Wrapper<DrinkClass>;
    let comp: DrinkClass;
    let drinkServiceStub: SinonStubbedInstance<DrinkService>;

    beforeEach(() => {
      drinkServiceStub = sinon.createStubInstance<DrinkService>(DrinkService);

      wrapper = shallowMount<DrinkClass>(DrinkDetailComponent, { store, localVue, provide: { drinkService: () => drinkServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDrink = { id: 123 };
        drinkServiceStub.find.resolves(foundDrink);

        // WHEN
        comp.retrieveDrink(123);
        await comp.$nextTick();

        // THEN
        expect(comp.drink).toBe(foundDrink);
      });
    });
  });
});
