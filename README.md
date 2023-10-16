# FilmLand
Filmland backend application

These are all of FilmLands categories.

<img width="251" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/9c53b446-13f6-4643-af5c-84a30daa59f5">

@PostMapping
/auth/signup
Creates a user password is hashed in the database (also initiliazes all userCategories for the created user)

<img width="193" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/272818a2-c7e5-4df0-8c46-58f324d04225">

@PostMapping
/auth/login
Returns a jwt token, this token is used to get authorisation to all the other endpoints.

<img width="481" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/178c98dd-2a91-4bee-b75b-eb72bd534fc5">

@GetMapping
/api/category/user
Gets all UserCategories from the signed in user.

<img width="257" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/4f483eec-b095-4a51-ba72-15ef375215f5">

@PostMapping
/api/category/subscribe
Subscribes the loggedIn user to an available category, if the user is already subscribed to a category an exception will be thrown.

<img width="467" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/8b2c795f-3edb-43ce-8478-d225438a3c53">

A subscribed category has a startDate, while the availableCategory startDate is null.

USER_CATEGORY table

<img width="376" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/4258f159-a217-48df-8bc2-f46315d0c8b4">

@PostMapping
/api/category/share
Subscribes the logged in user to another account, (for this application an agreement from the other user isn't necessary)

<img width="563" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/f458816b-d9ca-4d11-9299-3cba1119ee3b">

If you look at the USER_CATEGORY table you can see that there is an is_shared BIT(1) (boolean) column, this column is created to
prevent users from sharing their subscription more than twice.

The real relationship between 2 users sharing a subscription is stored in the database.
Imagine one subscriber decides to cancel their shared subscription, then it should be traceable which other user the subscription was shared with.
Only then can the shared subscription be removed properly.

<img width="284" alt="image" src="https://github.com/wil004/FilmLand/assets/79720969/927624a2-520d-4f1c-b015-80309a95062a">

/api/category/user/payment/start-process
This is an endpoint to manually start the application's payment process.
This endpoint is only created for test purposes, since there is a scheduler class created to call the paymentService methods this
endpoint is redundant.

PaymentService:
The paymentService calculates payments for all subscriptions.
A customers first month subscription is free.
Since there are 3 categories it would be inconvenient to charge bills on 3 different times of the month,
that's why the payments for all users will be processed the 1st of each month.

New subscription
This makes it more complex if a user subscirbed on let's say the 15th of the month.
On the first payment the customer will be charged based on the amount of days he was subscribed till the 1st of the next month.
Example: Customer started subscription at 10 march (1st month free) his paid subscription starts 10 april + 21 days = 1 may.
The customer will be priced for 21 days subscription.

Shared subscription
Shared subscriptions will be charged half the price, but the availableContent will also be halved.
There are to ways to make this calculation 
1: check if the UserCategory has a isShared with true.
2: Check if an existing sharedSubscription row exists in the database and use it's data to make the calculations.
I chose the 2nd option, since you can implement extra functionality when upgrading this application 
(e.g. automatically delete shared subscriptions for both users if 1 cancelled and charge full price for the remaining user).

Also if the a Shared subscription customer is new he will be charged based on the amount of subscribed days divided by 2.


Note:
I added some prefilled data for the H2 database.
All userPasswords are "hallo"
