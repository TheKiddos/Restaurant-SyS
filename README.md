# Restaurant-SyS
Semester project for Tishreen university.

# Introduction
This project aims to show various methods of how software can help in managing and delivering a better restaurant experience for both employees and customers alike.

It was created as a part of a 4th year semester project at Tishreen University (Software Engineering and Data Systems Department).

# Structure
We divided the system to 4 main sub-modules:
*   [Manager](Manager): holds the REST API, Telegram Bot, Payroll System and Restaurant Management System.
*   [Recommendation_Engine](Recommendation_Engine): contains a basic food and beverages recommendation algorithm, also contains some experiments with these algorithms in the [Sandbox](Recommendation_Engine/sandbox) folder.
*   [Waiter](Waiter): A Flutter app used to let the customer send orders from to the manager, making the waiter job easier.
*   [Website](Website): Small restaurant website can be used for deliveries, rating food and beverages and reserving tables.

# Requirements
Unfortunately since this project uses multiple languages and frameworks, there are many requirements for each.
Most of them can be seen in a sub-module setting file (eg: [Maven POM for Manager](Manager/pom.xml))  and [pubspec file for flutter](Waiter/pubspec.yaml).
So we are not going to list the requirements here.

# Notes
You can run each sub-module separately you only need to create the database (you can create it using laravel migrations or with the provided [MySQL script](schema.sql)). so you need to install MySQL and configure the database connection settings for Manager, Website and Recommendation Engine.
Or you can install Wampserver and create the database on it's MySQL server.

The Waiter module depends on the [REST API](Manager/src/main/java/org/thekiddos/manager/api) so you need to edit the IP of the REST API to yours.

two files are missing from this repo for security reasons:
*   auth.dart located in Waiter/lib/auth.dart which contains REST API authentication configurations and the website IP. So you need to create it:

```dart
import 'dart:convert';
import 'dart:io';

const String website = //website_url;
const String _username = //rest_username;
const String _password = //rest_password;
const String manager_api = //rest_url;
final String _basicAuth =
    'Basic ' + base64Encode(utf8.encode('$_username:$_password'));

final headers = {HttpHeaders.authorizationHeader: _basicAuth};
final jsonHeaders = {
  HttpHeaders.authorizationHeader: _basicAuth,
  "content-type" : "application/json",
  "accept" : "application/json",
};
```

*   application.yml located in Manager/src/main/resources/application.yml which contains Telegram BOT Token and Bot Email account settings:

```yaml
bot:
  token: your_token_here
  username: bot_name_here


spring:
  mail:
    host: smtp.gmail.com
    port: 587
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
    username: your_bot_email_here
    password: your_bot_password_here
};
```

# Credits & References:
[How to build a Restaurant Recommendation Engine (Part-1)](https://medium.com/analytics-vidhya/how-to-build-a-restaurant-recommendation-engine-part-1-21aadb5dac6e)

[Recommendation based on Food Review](https://www.kaggle.com/saurav9786/recommendation-based-on-food-review)

[Baeldung | Java, Spring and Web Development tutorials](https://www.baeldung.com)

[Spring Framework 5 - Beginner to Guru By John Thompson](https://courses.springframework.guru/p/spring-framework-5-begginer-to-guru)

[Agile Software Development, Principles, Patterns, and Practices: Pearson New International Edition By Robert C. Martin](https://www.amazon.com/Software-Development-Principles-Patterns-Practices/dp/B00EKYL83K)

[Clean Code: A Handbook of Agile Software Craftsmanship By Robert C. Martin](https://www.amazon.com/Robert-C-Martin/dp/0132350882/ref=pd_lpo_14_t_1/137-1734554-1112703?_encoding=UTF8&pd_rd_i=0132350882&pd_rd_r=4d40075e-61bf-42be-8a8f-60643a8b3a47&pd_rd_w=rSC8a&pd_rd_wg=OCNhp&pf_rd_p=7b36d496-f366-4631-94d3-61b87b52511b&pf_rd_r=HN23GGB2KHY7XWS2YPD5&psc=1&refRID=HN23GGB2KHY7XWS2YPD5)

####Icons made by:
[iconixar](https://www.flaticon.com/authors/iconixar)
[Freepik](https://www.flaticon.com/authors/freepik)
[Icongeek26](https://www.flaticon.com/authors/icongeek26)
[Flaticon](https://www.flaticon.com)