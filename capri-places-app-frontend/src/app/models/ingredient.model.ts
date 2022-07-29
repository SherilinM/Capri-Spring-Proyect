export class Ingredient {

    constructor(
        private _name: string,
        private _quantity: number,
        private _measurement: string,
    ) { }

    public get measurement(): string {
        return this._measurement;
    }
    public set measurement(value: string) {
        this._measurement = value;
    }
    public get quantity(): number {
        return this._quantity;
    }
    public set quantity(value: number) {
        this._quantity = value;
    }
    public get name(): string {
        return this._name;
    }
    public set name(value: string) {
        this._name = value;
    }

    public convertToDTO(): IngredientDTO {
        const ingredientDTO: IngredientDTO = {
            name: this.name,
            quantity: this.quantity,
            measurement: this.measurement
        }
        return ingredientDTO;
    }

}

export interface IngredientDTO {
    placeId?: any;
    name: string;
    quantity: number;
    measurement: string;
}