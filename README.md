The project has been created with a microservices structure to aid with future scalability and the addition of new feature.
Our microservices include:

- place-Service
- Favourites-Service
- Ratings-Service
- User-Service
- Review-Service

## Getting started

### Running The Capri App
<br>
2. Create mySQL database at the standard localhost port 3306 called "placeapp"

```
Or use the below commands to Set up database and the same login details as us to save changing this in all microservices 

CREATE DATABASE placeapp;
CREATE USER 'ironhack-user1'@'localhost' IDENTIFIED BY 'ironhack-user1';
GRANT ALL PRIVILEGES ON *.* TO 'ironhack-user1'@'localhost';
FLUSH PRIVILEGES;

```

3. Set Up & Run The Microservices - (Best Running Order - > Discovery, Gateway, place, User, Favourite, Rating)
```
=== Using Intellij ===
Run Each Microservice Application
```

4. Load Project in Visual Studio enter the following into terminal:

  ```
  cd .\capri-place-app-frontend\
  npm install 
  ng serve 
  ```

5. Enjoy at http://localhost:4200/

## API Routes

### place-service
Generates, stores and updates SalesRep objects

| Endpoint                   | Method | Description          | Path Params
|:---------------------------| :--- |:---------------------| :--- 
| /api/v1/places             | `GET` | Get all places       | None
| /api/v1/categories         | `GET` | Get all categories   | None
| /api/v1/places/{id}        | `GET` | Get place by id      | `id=[Long]`
| /api/v1/places/add         | `POST` | Add new place        | None
| /api/v1/places/edit        | `PUT` | Update place name    |  `placeDTO=[placeDTO]`
| /api/v1/places/delete/{id} | `DELETE` | Delete place         | `id=[Long]`
| /api/v1/places/user/{id}   | `GET` | Get place By UserId  | `id=[Long]`

### user-service
Generates, stores and updates SalesRep objects

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /api/v1/users | `GET` | Get all users | None
| /api/v1/users/{id} | `GET` | Get User by id | `id=[Long]`
| /api/v1/users/{id}/verify | `GET` | Return Boolean for verification | `id=[Long]`
| /api/v1/users/email/{email} | `GET` | Get User by email | `email=[String]`
| /api/v1/users/add | `POST` | Add new User | None
| /api/v1/users/edit | `PUT` | Update User name |  `userDTO=[userDTO]`
| /api/v1/users/delete/{id} | `DELETE` | Delete User | `id=[Long]`
| /api/v1/users/user/{id} | `DELETE` | Get User By UserId | `id=[Long]`
| /api/v1/users/profile | `GET` | Return User Profile | None

### rating-service
Generates, stores and updates SalesRep objects

| Endpoint                             | Method | Description                                        | Path Params
|:-------------------------------------| :--- |:---------------------------------------------------| :--- 
| /api/v1/ratings                      | `GET` | Get all ratings                                    | None
| /api/v1/ratings/{userId}             | `GET` | Get Rating by userId                               | `userId=[Long]`
| /api/v1/ratings/{placeId}/{userId}   | `GET` | Boolean Returned - user has Previously rated place | `placeId=[Long]` `userId=[Long]` 
| /api/v1/ratings/place/{placeId}      | `GET` | Return average rating for place                    | `placeId=[Long]`
| /api/v1/ratings/top10places          | `GET` | Get top10 rated places                             | None
| /api/v1/ratings/top10places/{userId} | `GET` | Get top10 places Rated By User                     | `userId=[Long]`
| /api/v1/ratings/rateplace            | `PUT` | Rate place                                         | `ratingDTO=[ratingDTO]`
| /api/v1/ratings/usersrating          | `PUT` | Gets users rating if previously rated place        | `ratingDTO=[ratingDTO]`

### favourite-service
Generates, stores and updates SalesRep objects

| Endpoint                             | Method | Description                                      | Path Params
|:-------------------------------------| :--- |:-------------------------------------------------| :--- 
| /api/v1/favourites                   | `GET` | Get all favourites                               | None
| /api/v1/favourites/{id}              | `GET` | Get Favourite by id                              | `id=[Long]`
| /api/v1/favourites/userid/{id}       | `GET` | Get All placeDTO's By UserId                     | `id=[Long]`
| /api/v1/favourites/placeisfavourited | `PUT` | Boolean Returned - If User Has Favourited Or Not | `favouriteDTO=[favouriteDTO]`
| /api/v1/favourites/add               | `POST` | Add to Favourites                                | `favouriteDTO=[favouriteDTO]`
| /api/v1/favourites/remove            | `PUT` | Remove From Favourites                           | `favouriteDTO=[favouriteDTO]`
| /api/v1/favourites/top10             | `PUT` | Returns top 10 most favourited placeDTO          | None

### review-service
Generates, stores and updates SalesRep objects

| Endpoint                                    | Method | Description                              | Path Params
|:--------------------------------------------| :--- |:-----------------------------------------| :--- 
| /api/v1/reviews/place/{placeId}             | `GET` | Get ReviewResponses by placeId           | `placeId=[Long]`
| /api/v1/reviews/user/{userId}               | `GET` | Get ReviewResponses by userId            | `userId=[Long]`
| /api/v1/reviews/{id}                        | `GET` | Get Review by Id                         | `id=[Long]`
| /api/v1/reviews/add                         | `POST` | Add Review                               | `reviewDTO=[ReviewDTO]`
| /api/v1/reviews/edit                        | `PUT` | Edit Review                              | `reviewDTO=[ReviewDTO]`
| /api/v1/reviews/delete/{id}                 | `PUT` | Delete Review                            | `id=[Long]`
| /api/v1/reviews/reviewed/{userId}/{placeId} | `GET` | Boolean returned for previously reviewed | `userId=[Long]` `placeId=[Long]`