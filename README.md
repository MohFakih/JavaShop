# JavaShop
This is the EECE 350 (Computer Networks) project I individually worked on during the Spring 2020 semester.

# Description

We were required to present a Client/Server application written in Java where the Server is multithreaded to handle many incoming connections.
I took my project many steps further: It is a fully featured shop with DB handling (MySQL), SMTP verification for the emails, and dynamic stock manipulation (admin can restock and add items while server is running). There is sufficient encryption for the serialized communication aswell as for the password storing on the database. None of the extra features were actually required.

# What I learned

The entire project was done in-between exams, so I only had 4 days to implement all of the features.
This was the first fully-fledged project of this size, and I insisted on exploring new grounds that I haven't touched or seen before: it was my first time implementing some form of design pattern on this scale, and also the first time I extensively refactored my code for maintainability and reusability.
There are many things I might have done differently looking back at them, and I will definitely return to them some day: Mainly, the redundancy of some classes and their implementations. I didn't efficiently chose the specific designs since I didn't take any software engineering or software design courses yet. All my choices were made given my limited knowledge in the field that comes from brief videos/articles.

Working on this project and implementing way-more-than-needed features in a very-short-timeframe was actually fun and I liked the challenge of it.

# Technologies used:

- Java as the main project language
- Java Swing for the UI
- JDBC for database connectivity
- MySQL for the Database itself
- AWS/PHP/SES for email verification
