export class Favourite {
    public get userId(): number {
        return this._userId;
    }
    public set userId(value: number) {
        this._userId = value;
    }

    public get placeId(): number {
        return this._placeId;
    }
    public set placeId(value: number) {
        this._placeId = value;
    }
    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }

    constructor(private _id: number,
        private _placeId: number,
        private _userId: number) { };


}

export interface FavouriteDTO {
    id?: number;
    placeId: number;
    userId: number;
}