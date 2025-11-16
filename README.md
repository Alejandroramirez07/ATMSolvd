ATM Simulator

A Java-based ATM simulation system built using MyBatis, JavaFX, and modern SOLID principles.
The application provides both a console and graphical (JavaFX) interface for performing ATM operations such as authentication, balance inquiry, deposits, and withdrawals.

Features

User Authentication using account number and PIN.

View Account Balance, Deposit, and Withdraw functions.

Greedy Cash Dispensing Algorithm ensures minimum banknotes are dispensed.

Data Persistence managed via MyBatis ORM with a MySQL database.

JavaFX UI for an interactive ATM screen experience.

SOLID Principles applied throughout the design.

Design Patterns implemented:

Facade Pattern for unified ATM operation handling.

Strategy Pattern for flexible cash dispensing algorithms.

Factory Pattern for creating DAO instances dynamically.

Project Structure

Prerequisites

-Java 17 or later
-Maven 3.8+
-MySQL Server 8.0+
-MyBatis 3.5+
-JavaFX SDK 21+

To run: mvn javafx:run

