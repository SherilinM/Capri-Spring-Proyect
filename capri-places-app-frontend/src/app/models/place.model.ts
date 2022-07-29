import { FormsModule } from "@angular/forms";
import { Ingredient, IngredientDTO } from "./ingredient.model";

export class Place {

    constructor(
        private _name: string,
        private _authorId: number,
        private _category: string,
        private _location: string,
        private _description: string
    ) { }

    public get category(): string {
        return this._category;
    }
    public set category(value: string) {
        this._category = value;
    }
    public get authorId(): number {
        return this._authorId;
    }
    public set authorId(value: number) {
        this._authorId = value;
    }

    public get name(): string {
        return this._name;
    }
    public set name(value: string) {
        this._name = value;
    }
    public get location(): string {
        return this._location;
    }
    public set location(value: string) {
        this._location = value;
    }

    public get description(): string {
        return this._description;
    }
    public set description(value: string) {
        this._description = value;
    }

    public convertToDTO(): PlaceDTO {
        const placeDTO: PlaceDTO = {
            id: 0,
            name: this.name,
            authorId: this.authorId,
            category: this.category,
            location: this.location,
            description: this.description
        }
        return placeDTO;
    }
}

export interface PlaceDTO {
    id: number,
    name: string,
    authorId: number,
    category: string,
    location: string,
    description: string,
    imageUrl?: string,
    createdDate?: Date,
    editedDate?: Date
}
