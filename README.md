# iStore - Java Project

Welcome to iStore, your Java-based store management system. This README provides you with the necessary steps to set up your iStore application, including database setup, initial user logins, and how to run the application.

## Getting Started

Before running the iStore application, you must set up your database environment and ensure your application is correctly configured.

### Database Setup

1. **Create Database**: First, make sure you have a MySQL server (MAMP, WAMP or XAMP) open on port 3306. And create an istore_database.

    ```sql
    CREATE DATABASE istore_database;
    ```

2. **Import Database Schema**: Next, import the `istore_database.sql` file into your `istore` database. This file contains all necessary tables and initial data for the iStore application. Use  a client like phpMyAdmin to import the file.

Your database is ready !

### Configuration

Ensure you have a `.env` file in your project's root directory with the correct database credentials. This file should include the database URL, username, and password.

### Initial User Logins

- **Administrator Account**
    - Email: admin@istore.com
    - Password: admin

- **Employee Account**
    - Email: employee@istore.com
    - Password: employee

Use these credentials to log into the application and access different levels of functionality.

## Running the Application

To run the iStore application, ensure your database is running and you have configured the `.env` file with correct database credentials.

1. Navigate to the `java/com.istore` directory.
2. Run `Application.java`.

This will start the iStore application. Ensure your database server is running and accessible to avoid any connection issues.

## Technical documentation (Javadoc

The technical documentation is available in the `javadoc` directory. Open the `index.html` file in your browser to view the documentation.