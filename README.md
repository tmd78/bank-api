# An API to Interact With Bank Accounts

I created this project to investigate concurrency in a Spring controller. I wrote an article with my findings that can be found [here](https://medium.com/@toby.draper/concurrency-in-java-2fb38fc8345e).

## Running the Application

There's some simple setup to perform if you'd like to run the application yourself.

### Install MySQL

[Install](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/) MySQL if not already installed.

### Import the Database

[Import](https://stackoverflow.com/a/15885375) the database using the MySQL Workbench GUI. The "Self-Contained File" can be found [here](https://github.com/tmd78/bank-api/blob/main/mysql/import-bank.sql).

### Override Configuration Properties

I use IntelliJ IDEA to run the application. After you've cloned the project, you can [import](https://www.jetbrains.com/guide/java/tutorials/working-with-maven/importing-a-project/) it as a Maven project to IntelliJ.

A run configuration should be created automatically during import; you'll need to edit this configuration. Start by [exposing](https://www.jetbrains.com/help/idea/run-debug-configuration-spring-boot.html#modify-options) the "Override configuration properties" options. Then create entries for the properties below:

```properties
spring.datasource.url=your-mysql-instance (e.g. jdbc:mysql://localhost:3306/bank)
spring.datasource.username=your-mysql-username
spring.datasource.password=your-mysql-password
```

You should be able to run the application successfully now.

## Interact With the Application Using Postman

[Import](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/) the collection and environment (found in this [folder](https://github.com/tmd78/bank-api/tree/main/postman)) into your Postman application.

This collection allows you to utilize the application's CRUD operations on the database you imported.
