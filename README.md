# payment project

This is an application to schedule payments. It's built using [QuarkusIO](https://github.com/quarkusio).

# How does it work?

You need to add a new credit card, and use it to schedule your payment.

# How to run it

 - Set env var like this: `export SEND_GRID_API_KEY=YOUR_API_KEY`
 - `mvn clean install`
   - With the generated jar: `java -jar target/payment-1.0.0-SNAPSHOT-runner.jar`
   - Docker
     - `docker build --build-arg ENV_SEND_GRID_API_KEY=$SEND_GRID_API_KEY -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus .`
     - `docker run -i --rm -p 8080:8080 -e TZ=America/Sao_Paulo quarkus/quarkus`
     
# How to use it

Here you have to add a credit card and then schedule a paymet with the credit card you added

 - Add a new credit card
   - Send a `POST` request to `http://localhost:8080/credit-cards`, with a `JSON` like this in the body:
     ```
     {
     	"cardNumber": 12341234,
     	"securityCode": 123,
     	"ownerName": "user",
     	"emissionDate": "2020-03-12",
     	"validThru": "2020-03-12"
     }
     ```
 - Schedule a payment using this card:
   - Send a `POST` request to `http://localhost:8080/payments`:
     ```
     {
     	"productId": "Id",
     	"description": "Eletric guitaar iqwen the blue color",
     	"dueDate": "2020-03-19 00:50:35",
     	"customerMail": "email@email.com",
     	"amount": 50,
     	"currency": "BLR",
     	"creditCard":12341234
     }
     ```

 - If you inserted some wrong value in the credit card, you can delete it:
   - Send a `DELETE` request to `http://localhost:8080/credit-cards/{CARD-NUMBER-HERE}`

 - You can verify your credit card data accessing this URL: `http://localhost:8080/credit-cards/{CARD-NUMBER-HERE}`
 
 - Once you have schedule your payment, you will receive and email with the result of success or failure. The email will give you the payment ID, which you can see accessing `http://localhost:8080/payments/{PAYMENT_ID_HERE}`
