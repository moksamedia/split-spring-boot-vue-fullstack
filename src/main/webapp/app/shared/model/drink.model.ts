export const enum DrinkSize {
  Small = 'Small',
  Medium = 'Medium',
  Large = 'Large',
  XLarge = 'XLarge',
  XXLarge = 'XXLarge',
}

export interface IDrink {
  id?: number;
  name?: string;
  size?: DrinkSize;
  caffeineMilligrams?: number;
  priceDollars?: number;
}

export class Drink implements IDrink {
  constructor(
    public id?: number,
    public name?: string,
    public size?: DrinkSize,
    public caffeineMilligrams?: number,
    public priceDollars?: number
  ) {}
}
