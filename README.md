# Welcome to TryWithKids

## What is this program about
This is a fairly simple program to browse, add, edit, search and delete scientific 
experiments with kids. It will have separate GUIs for maintenance that can do all
the above-mentioned things (f.ex. for parents or teachers). Maintenance-type user
can also add users and delete users. One maintenance-type user will come with the 
software, including password, which can be changed.
User-type can  browse and search for experiments (f.ex. for children or parents if someone 
else adds experiments for them). The idea is that a teacher can add his/her students
that can then perform the experiments as groups or individuals. Or parent can add their
children so they can browse the experiments on their own and add interesting experiments
to their personal lists to conduct when they so choose. Users can also change their
password.
Some experiments come with the software.
There will be a way to log into the program (not there yet).
This program will save passwords in plain text into the database at this time. If
time allows, salts and hashing will be developed, but not in the third iteration.
The database-solution is MongoDB. 

## Environment
This program has been written with Netbeans version 8.2. and java version 1.8.0_181. 
Mongo java-driver version is 3.10.1 and Morphia version is 1.4.0. 
MongoDB is 4.0 Community edition.
The computer operating system used in development is MacOS Mojave.

## Development 

### Iteration 1:
Currently the GUI (and program) works for add and browse all. 
It is possible to add to database and to find all from database and clear database.
No separation of user profiles yet.
Four experiments come with the software at this time.

### Iteration 2:
GUI (and program) works for delete, update and search.
It is possible to delete an experiment from the database (from gui), update an experiment and search
by subject, by maximum desired duration or both.
These features are tested with JUnit.

### Iteration 3: by 7th April
Ability to add and delete users.
Ability to change password.
Ability to log in.
Different views for different user-types.

## How to install this software
Install Netbeans by following instructions here: http://moocfi.github.io/courses/general/programming/how-to-get-started.html
Install Mongo by following instructions here: https://docs.mongodb.com/manual/administration/install-community/
Find the Java driver mongo-java-driver-3.10.1.jar for mongo at https://mongodb.github.io/mongo-java-driver/ 
and download the driver,  make sure it is in the same folder as the project folders src and target. If it is not, please move it there.
Download the .zip-file containing this program.
From Netbeans, choose "file" -> "import project" -> from ZIP... and choose the zip-file 
containing this file. Choose open.
Once opened, press the green play-button to run the software.

## Maintenance user
User-information for maintenance is:
username : maintenance
default password : main_auth123
This password can be changed. It should definately be changed on first login.

## Creator information 
The creator and developer for this project is Satu Korhonen.
This software is being built for a course "Ohjelmistotekniikka" for the University
of Helsinki in the Spring 2019. However, the idea for this has been brewing in Satu's 
mind for a longer period and the experiments come from her blog isbel.org
