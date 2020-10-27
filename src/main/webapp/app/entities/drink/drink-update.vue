<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="coffeebotApp.drink.home.createOrEditLabel">Create or edit a Drink</h2>
                <div>
                    <div class="form-group" v-if="drink.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="drink.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="drink-name">Name</label>
                        <input type="text" class="form-control" name="name" id="drink-name"
                            :class="{'valid': !$v.drink.name.$invalid, 'invalid': $v.drink.name.$invalid }" v-model="$v.drink.name.$model"  required/>
                        <div v-if="$v.drink.name.$anyDirty && $v.drink.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.drink.name.required">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="drink-size">Size</label>
                        <select class="form-control" name="size" :class="{'valid': !$v.drink.size.$invalid, 'invalid': $v.drink.size.$invalid }" v-model="$v.drink.size.$model" id="drink-size"  required>
                            <option value="Small" >Small</option>
                            <option value="Medium" >Medium</option>
                            <option value="Large" >Large</option>
                            <option value="XLarge" >XLarge</option>
                            <option value="XXLarge" >XXLarge</option>
                        </select>
                        <div v-if="$v.drink.size.$anyDirty && $v.drink.size.$invalid">
                            <small class="form-text text-danger" v-if="!$v.drink.size.required">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="drink-caffeineMilligrams">Caffeine Milligrams</label>
                        <input type="number" class="form-control" name="caffeineMilligrams" id="drink-caffeineMilligrams"
                            :class="{'valid': !$v.drink.caffeineMilligrams.$invalid, 'invalid': $v.drink.caffeineMilligrams.$invalid }" v-model.number="$v.drink.caffeineMilligrams.$model"  required/>
                        <div v-if="$v.drink.caffeineMilligrams.$anyDirty && $v.drink.caffeineMilligrams.$invalid">
                            <small class="form-text text-danger" v-if="!$v.drink.caffeineMilligrams.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.drink.caffeineMilligrams.numeric">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="drink-priceDollars">Price Dollars</label>
                        <input type="number" class="form-control" name="priceDollars" id="drink-priceDollars"
                            :class="{'valid': !$v.drink.priceDollars.$invalid, 'invalid': $v.drink.priceDollars.$invalid }" v-model.number="$v.drink.priceDollars.$model"  required/>
                        <div v-if="$v.drink.priceDollars.$anyDirty && $v.drink.priceDollars.$invalid">
                            <small class="form-text text-danger" v-if="!$v.drink.priceDollars.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.drink.priceDollars.numeric">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.drink.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./drink-update.component.ts">
</script>
