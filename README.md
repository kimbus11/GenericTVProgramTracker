# GenericTVProgramTracker
generic command line based Program tracker using csv database


In order to import MAL into the database:
1. Go to edit profile > list and set the list to only show episodes 
2. Copy the watching table into a text editor and find "edit - more\n" and replace with "" (nothing), do the same for "airing" and "+"
3. Copy that into calc/exel, make sure it is seperated by tabs and "/" in the import wizard
4. Check if any of the cells got pushed around by special characters in titles and clean those that did, eg. "fate/zero" would become "fate" "zero" in two seperate cells
5. Entries that dont have any watched episodes are marked with "-", change this to 0
6. Entries that dont have a total episode count are marked with "-", change this to the total episodes
7. The entries should be in the order title, current episode, total episodes, airing state
8. Mark entries that are airing with "AIRING" in the last collumn, and those that are not with "AIRED"
9. Save as a CSV file, Field deliminated by "," and String deliminated by """
10. Save CSV file in same location as the Program root, calling the file "watching.csv"
11. Repeat for Completed, making sure that the collumn names are properly followed as MAL displays a complete Program as just "12" instead of "12/12"
12. Save as "complete.csv"


The process for other websites will be similar, just make sure that the csv files have the right name, (watching.csv, complete.csv) and that the Programs are in the right format (title, episode number, total episodes, airing state as true or false) and, if the title includeds commas, make sure the whole title is enclosed in quotes.
