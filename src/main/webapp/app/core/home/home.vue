<template>
    <div class="container">
        <div class="home row justify-content-md-center">
            <div class="col-md-6">
                <div class="title">
                    <h1 class="display-4">CoffeeBot!</h1>
                    <p class="lead">Drink coffee!</p>
                    <p>{{username ? username : "anonymous"}}, treatment: {{treatment}}</p>
                </div>

                <div v-for="drink in drinks">
                    <b-button variant="outline-primary" class="menu-button" @click="currentOrder=drink">{{drink.name}} {{drink.size}} - ${{drink.priceDollars}}</b-button>
                </div>

                <div v-if="currentOrder !== null">

                    <div v-if="treatment==='on'">
                        <b-button v-if="!withCream" variant="success" class="menu-button" @click="withCream=true">Add Cream - FREE</b-button>
                        <b-button v-if="withCream" variant="danger" class="menu-button" @click="withCream=false">Remove Cream</b-button>
                    </div>

                    <div class="card order">
                        <h4>Current order:</h4>
                        <div>
                            {{currentOrder.name}} {{currentOrder.size}} {{withCream ? "(w/ cream)" : ""}} - ${{currentOrder.priceDollars}}
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>

<style>
    .menu-button {
        margin-bottom: 10px;
        width: 100%;
    }
    .order {
        text-align: center;
        padding:20px;
    }
    .title h1, .title p {
        text-align: center;
    }
</style>
