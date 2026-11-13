# Product REST API

A Spring Boot REST API for creating, listing, retrieving, updating, and deleting products. Product data is stored in an in-memory H2 database and is cleared when the application restarts. Three sample products are loaded at startup.

## Run the application

Requires Java 21. From the project root, run:

```bash
./mvnw spring-boot:run
```

On Windows, run `mvnw.cmd spring-boot:run`.

## Try the API with Swagger UI

1. Start the application.
2. Open [Swagger UI](http://localhost:8080/swagger-ui.html).
3. Expand an operation under **Products** and select **Try it out**.
4. Enter any required values or request body, then select **Execute** to send the request and view its response.

The raw OpenAPI specification is available at [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

### Suggested CRUD walkthrough

Use the following operations in order. At startup, sample products are available at IDs `1` through `3`; the new product in this walkthrough receives ID `4`.

1. **GET `/products`** - View the three sample products.
2. **GET `/products/1`** - Retrieve the wireless mouse sample.
3. **POST `/products`** - Create a product with this request body:

   ```json
   {
     "name": "Notebook",
     "description": "A lined notebook"
   }
   ```

   A successful request returns `201 Created`, the product, and a `Location` header.

4. **GET `/products/4`** - Retrieve the newly created product.
5. **PUT `/products/4`** - Replace its name and description. Use the same JSON fields as the create request.
6. **DELETE `/products/4`** - Delete the product. A successful request returns `204 No Content`.

Errors return Problem Details JSON with `title`, `status`, and `detail` fields. For example, requesting a product ID that does not exist returns `404 Not Found`.
