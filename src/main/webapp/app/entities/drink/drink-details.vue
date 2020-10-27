<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="drink">
                <h2 class="jh-entity-heading"><span>Drink</span> {{drink.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Name</span>
                    </dt>
                    <dd>
                        <span>{{drink.name}}</span>
                    </dd>
                    <dt>
                        <span>Size</span>
                    </dt>
                    <dd>
                        <span>{{drink.size}}</span>
                    </dd>
                    <dt>
                        <span>Caffeine Milligrams</span>
                    </dt>
                    <dd>
                        <span>{{drink.caffeineMilligrams}}</span>
                    </dd>
                    <dt>
                        <span>Price Dollars</span>
                    </dt>
                    <dd>
                        <span>{{drink.priceDollars}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="drink.id" :to="{name: 'DrinkEdit', params: {drinkId: drink.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./drink-details.component.ts">
</script>
