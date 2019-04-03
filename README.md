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
This program will save passwords in plain text into the database at this time. Although
developer is aware salting and hashing of passwords is advised, there is insufficient
time to learn to do this properly during this course.
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

### Iteration 3: 
Ability for maintenance-type users to add and delete users.
Ability for maintenance-type users to see all uses.
Ability to change password.
Ability to log in.
Different views for different user-types.
Ability to add experiments to users own list. Ability to see own list and delete from it.
Ability to print experiments from application : the pdf is saved to TryWithKids main folder. 
In Netbeans it can be found in the files-section. The name of the document is named after the experiment
topic (+ .pdf).

## Iteration 4: by 21st of April
Ability to add an evaluation of an experiment
Ability to see average evaluation score given to an experiment
JUnit tests (excluding GUI) at least 70%

## How to install this software
Install Netbeans by following instructions here: http://moocfi.github.io/courses/general/programming/how-to-get-started.html
Install Mongo by following instructions here: https://docs.mongodb.com/manual/administration/install-community/
Find the Java driver mongo-java-driver-3.10.1.jar for mongo at https://mongodb.github.io/mongo-java-driver/ 
and download the driver,  make sure it is in the same folder as the project folders src and target. If it is not, please move it there.
Download the .zip-file containing this program.
From Netbeans, choose "file" -> "import project" -> from ZIP... and choose the zip-file 
containing this file. Choose open.
Once opened, press the green play-button to run the software.

## Default users
This program comes with two default users. This info is also found in the code, so it is HIGHLY advisable
to change the passwords after installation.
User-information for maintenance is:
username : maintenance
default password : main_auth123
User-information for user is:
username: end-user
default password: end_auth987

## Note on security --- IMPORTANT ---
The security of this application is very poor. Default-maintenance passwords are found
in this readme as well as in the code. They are clearly visible. Also all passwords
are saved in plain text (String) at this time. All maintenance-type users can see all 
user passwords that are stored in the same device. From the security -point of view this is
not wise, but from the teacher's point of view, this is almost a must as they need
to help students login very frequently. Passwords should be salted and hashed. 
However, they will not be at this time.

## Known bugs at this time:
- listview scrolls also horizontally. Content should be wrapped.
- user-notification is insufficient in add experiments-view. No notification if experiment 
did not save to database
- in add experiment the user can add more text than the database can handle causing
save to fail
- error messages remain in window until user visits another part of application
- Maintance-user can delete themselves. A change will be coded, so user cannot delete themselves.

## Creator information 
The creator and developer for this project is Satu Korhonen.
This software is being built for a course "Ohjelmistotekniikka" for the University
of Helsinki in the Spring 2019. However, the idea for this has been brewing in Satu's 
mind for a longer period and the experiments come from her blog isbel.org
