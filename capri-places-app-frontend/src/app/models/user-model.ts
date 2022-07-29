export class User {

    constructor(
        private _name: string,
        private _username: string,
        private _email: string,
        private _location: string,
        private _bio: string,
        private _pictureUrl: string,
        private _role: string[]
    ){};

    public get pictureUrl(): string {
        return this._pictureUrl;
    }
    public set pictureUrl(value: string) {
        this._pictureUrl = value;
    }
    public get bio(): string {
        return this._bio;
    }
    public set bio(value: string) {
        this._bio = value;
    }
    public get location(): string {
        return this._location;
    }
    public set location(value: string) {
        this._location = value;
    }
    public get email(): string {
        return this._email;
    }
    public set email(value: string) {
        this._email = value;
    }
    public get role(): string[] {
        return this._role;
    }
    public set role(value: string[]) {
        this._role = value;
    }
    public get username(): string {
        return this._username;
    }
    public set username(value: string) {
        this._username = value;
    }
    public get name(): string {
        return this._name;
    }
    public set name(value: string) {
        this._name = value;
    }

}

export interface UserDTO {
    id: number;
    name: string;
    username: string;
    email?: string;
    location: string;
    bio: string;
    pictureUrl: string;
    roles: string[];
}