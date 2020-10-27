<template>
    <div>
        <h2 id="page-heading">
            <span id="drink-heading">Drinks</span>
            <router-link :to="{name: 'DrinkCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-drink">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new Drink
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && drinks && drinks.length === 0">
            <span>No drinks found</span>
        </div>
        <div class="table-responsive" v-if="drinks && drinks.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Name</span></th>
                    <th><span>Size</span></th>
                    <th><span>Caffeine Milligrams</span></th>
                    <th><span>Price Dollars</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="drink in drinks"
                    :key="drink.id">
                    <td>
                        <router-link :to="{name: 'DrinkView', params: {drinkId: drink.id}}">{{drink.id}}</router-link>
                    </td>
                    <td>{{drink.name}}</td>
                    <td>{{drink.size}}</td>
                    <td>{{drink.caffeineMilligrams}}</td>
                    <td>{{drink.priceDollars}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DrinkView', params: {drinkId: drink.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'DrinkEdit', params: {drinkId: drink.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(drink)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="coffeebotApp.drink.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-drink-heading">Are you sure you want to delete this Drink?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-drink" v-on:click="removeDrink()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./drink.component.ts">
</script>
