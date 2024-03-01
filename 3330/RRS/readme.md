<!-- insert image here-->


## Running The Project
To run this project open a terminal and type the following command to initialize the db
```
sqlite3 rrs.db
```

Next to load the data and run all queries in the requirements run the command:
```
.read queries.sql
```



Here is the output when running the queries.sql
![Output of the following 2 commands](./Screenshot%202023-03-10%20at%208.31.13%20PM.png)


## ER Diagram
![Output of the following 2 commands](./Screenshot%202023-03-10%20at%208.38.15%20PM.png)

# Tools Used
No special tools were used. Just plain sqlite

### Loading the Data
To load the data we simply used the import method built into sqlite
For the Passenger table however, we opted to use one big insert command inorder to properly set the passengers date of birth as a date type. This would of course be replaced with some sort of script for larget datasets.

# HONOR CODE

I pledge, on my honor, to uphold UT Arlington's tradition of academic integrity, a tradition that values hard work and honest effort in the pursuit of academic excellence.

I promise that I will submit only work that I personally create or that I contribute to group collaborations, and I will appropriately reference any work from other sources. I will follow the highest standards of integrity and uphold the spirit of the Honor Code.

### Contributers
* Rodrigo Munoz
* Max
* Chris
