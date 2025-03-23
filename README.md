# Anagram-Service Overview

This is a spring-boot 3 backend application. It is a multi maven modules application. The app has 3 rest controllers ;
Word controller for handling basic dictionary operations, Activities controller for handling audits on the dictionary and then
the Anagram controller for handling anagram algorithm.

The app has an embedded H2 database. This is where is persist the original dictionary when the app starts up. 
An important note; a word is treated as an object which has wordText and then effectivity which includes effective dates. 
This concept of effectivity is crucial because it makes it easy for the app to track for new words and words that get removed. 

The app also has caching. This is to help to improve the speed of execution when the anagram algorithm is running. The latest dictionary 
view gets cached. However, if there is any changes to the dictionary, that cache is evicted to ensure that the algorithm is always 
processing on the latest and correct dictionary data.

The app also has pagination and sorting. This is to make it easy for the frontend app to display the dictionary in a paginated manner because
if pagination was not implemented. It would have been difficult for frontend to handle large volume of words from the dictionary. 

The app has unit tests which help to improve robustness and correctness. The app also has JaCoCo which ensures that the test coverage
is above 80%. 

The app also have swagger which is available here :
http://localhost:8080/anagram-service/rest/swagger-ui/index.html#

The H2 database is available here :
username is user and password is also user
http://localhost:8080/anagram-service/h2-console

The path to the dictionary is configured on the properties file. This is an absolute path to the dictionary. 

Moreover, the app also has logging to track various activities happening across the app.


