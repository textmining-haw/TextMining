POET - Public Opinion Extraction Team 
=======================================

University of Applied Science Hamburg
---------------------------------------

### This project is a maven project.

Get required dependencies:

> $ maven install

### The project is separated in two parts:
>1) Classification with Naive Bayes.<br>
>2) Classification with Support Vector Machines.

### To Run the LibSVM-TestApplication:
>There is a bash script located in the root directory of the maven project called RunTestApplicationLibSVM.sh <br><br>
>params: <br>
>*-s start-val*   <br> Can be used to set the value starting iteration (iteration value is used for naming \*.log file) <br><br>
>*-e end-val*    <br> value to end iteration (for each value in sequence start to end a complete svm classification is executed)<br><br>
>*-d yes-or-no*   <br>  yes/no to enable/disable debug mode    <br>

