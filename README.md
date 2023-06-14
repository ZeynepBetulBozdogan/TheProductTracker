# **ProductTracker**

It is an android application that tracks products.

This repo has an android application that was developed using Java Programming Language.

Authentication is made by jwt token in asp.net web API.

The application allows users to get product information by scanning and typing the barcode or item ID.
It notifies the user by coloring the table green and okay sound when it finds the item. Otherwise, it warns the user with coloring the table red and wrong sound.
The ProductTracker has 3 main page:

1. Login Screen
2. Application Functions Screen(Other functions will be added. It is in phase of development)
3. Product Information Page

![Screenshot_15](https://user-images.githubusercontent.com/22201810/214413677-94b402a7-f84e-46cd-96a3-2d72c518cc00.png)



## **Installation**

First, clone the project.

``` git
git clone https://github.com/ZeynepBetulBozdogan/ProductTrack.git
````

## **Usage**

After you clone the project, open project on Android Studio.

**Change Base Url**
```` Java
OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logger).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://yourwebapi:44327/api/").client(client).build();
`````
```` Java
Request request = new Request.Builder()
                        .url("http://yourwebapi/api/Get")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization","Bearer "+ token)
                        .build();
`````
## **Contributing**
Pull requests are accepted. For major changes, please first open a thread to discuss what you have changed.
## **License**
[MIT](https://choosealicense.com/licenses/mit/)
