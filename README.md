[![Java CI with Maven](https://github.com/NHL-S-Vigmo/Api/actions/workflows/maven.yml/badge.svg)](https://github.com/NHL-S-Vigmo/Api/actions/workflows/maven.yml)
[![Latest release](https://badgen.net/github/release/Naereen/Strapdown.js)](https://github.com/NHL-S-Vigmo/Api/releases)
[![Website vigmo.serverict.nl](https://img.shields.io/website-up-down-green-red/https/vigmo.serverict.nl.svg)](https://vigmo.serverict.nl/)

# Vigmo Dashboard
*Project for Advanced Java Minor, made by Students*  

_For a deployment guide, check our wiki: [Vigmo Wiki](https://github.com/NHL-S-Vigmo/docs/wiki)_

## How to install
* Create a database using the script **vigmo.sql** in the root of the repository.
* Now update the **application.properties** file with credentials to your database like so: 
    ```properties
    database.user=[USERNAME]
    database.pass=[PASSWORD]
    database.url=jdbc:mysql://[ADDRESS]:[PORT]/[DB_NAME]
    database.driver=com.mysql.jdbc.Driver
    ```
* If you are not using mysql as your db driver, change those values for your respective db type.
* Now configure the project to run in a webserver that is capable of running java web applications. *During development Tomcat **9.0.55** and **9.0.56** was used*

* Create default user if you have an empty user table. The username is `admin` and password is `changeme`.
    ```mysql
    INSERT INTO `users` (`id`, `username`, `password`, `enabled`, `role`, `pfp_location`) VALUES (NULL, 'admin', '$2a$10$6WuZxvmGulNvJTPqhSSwGuPUYfniQqb5t4J0zn.DQPY0CII2kkYwq', '1', 'ROLE_ADMIN', '/ava/avatar.jpg')
    ```

## Available endpoints
These endpoints are available on the API.

* availabilities
* consultation_hours
* files
* logs
* media_slides
* rss_slides
* screens
* slideshows
* slideshow_variables
* test_slides
* users

## Api documentation by Swagger
This Api project uses swagger to further document actions on endpoints and data models that were used.
If you have completed the setup of the project, visting the landings page would direct you to the location of Swagger.

