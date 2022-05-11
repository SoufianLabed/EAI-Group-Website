# EAI GROUP WebSite

EAI Group want a e-commerce application where user can buy some product from reseller.


### Endpoints


* `GET /getUser/{username}` : Retrieve User by Username
* `POST /signup` : Create User
* `PUT /updateUser` : Update existing User 
* `DELETE /deleteUser` : Delete existing User 

### Required

Before launch the project, please make sure you have executed the SQL script named "EAI-Group.sql". This script generates the tables and inserts the initial data into the database.

After that, don't forget to replace Postgres DB properties in application.properties :
* `spring.datasource.url`
* `spring.datasource.username`
* `spring.datasource.password`
