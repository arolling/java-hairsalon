# Epicodus Hair Salon

#### _A hair salon management app, February 26, 2016_

#### By _**Abigail Rolling**_

## Description

This website would allow a salon owner to manage a database of stylists and clients, allowing additions, edits, and deletions. When a stylist with active clients is deleted, the user is prompted to reassign or delete those clients.

## Setup/Installation Requirements

Clone this repository:
```
$ cd ~/Desktop
$ git clone https://github.com/arolling/java-hairsalon.git
$ cd java-hairsalon
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `hair_salon` database:
```
$ psql
$ CREATE DATABASE hair_salon;
$ psql hair_salon < hair_salon.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```

## Support and contact details

[E-mail me](mailto:arolling@gmail.com) with any comments or concerns.

## Technologies Used

* Java
* junit
* Gradle
* Spark
* fluentlenium
* PostgreSql
* Bootstrap

### License

Licensed under the GPL.

Copyright (c) 2016 **Abigail Rolling**
