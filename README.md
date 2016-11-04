# sheffieldDentalCare

HOW TO USE DBController

NOTE: ALWAYS USE db.closeConnection WHEN FINISHED INTERACTING WITH DB.

I made this because it saves us having to write all that shit in the notes every time we want to interact with the DB.
Our team's DB details are stored as attributes at the top of the class.

Construct:

DBController db = new DBController();

Make a CREATE, DROP, INSERT, DELETE or UPDATE statement:

db.update("insert your sql statement here"); 

*this will also return the number of rows that have been updated*

Make a SELECT statement:

ResultSet rs = db.query("insert your sql statement here");

*this returns the rich type java.sql.ResultSet* read how to use this type here: https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
