export interface ReviewDTO {
    id?: number;
    title: string;
    content: string;
    placeId: number;
    userId: number;
    ratingId: number;
    createdDate?: Date;
    editedDate?: Date;
}

export interface ReviewResponse {
    id: number;
    title: string;
    content: string;
    email: string;
    name: string;
    userId: number;
    createdDate: Date;
    editedDate: Date;
    rating: number;
    ratingId: number;
}