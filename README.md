# PharosTask

**For Libraries**

    I compile Piccaso to load profile image 
    I compile retrofit to send requests to server 
    I compile converter json to can work with json type 
    I compile recycler and card view for layout design 
    
**For Patterns & Code**
    
    I use MVP pattern for make code clear and easy
    I write comments before methods to explain what it do 
    I use singleton design pattern in sharedData class to work with SharedPreferences
    I use sharedpreference to save data of cities in it after convert to json string 
    I use boolean variable active to handle if view still on screen or user move from activity
    
**For Logic**

    I sorted cities in alphabetic order by using collection.sort() {i use it becaues it uses merge sort which is fast and stable} 
    After loading more i sort received data but not sort it with all pervious data i get before  
    In search activity i get data from local to reduce number of requests
   
**For search algorithm**

   1) I sort all data that came from local storage 
   2) I show this data to user before he start searching 
   3) I use linear search because i need to visit all data in array list
   4) In case of user enter query to search 
   
  if user enter any thing not stirng ( numbers , special character or empty string ) then i will back that no results and will not 	loop or search in array..
   if user enter a string then i search under 3 conditions
   
     a) city name contains string that user enter
     b) if city name not contains strig that user enter i remove last character in user's search string text
     c) if b condition not work i remove last two character in user's search string text 
     if these 3 conditions not working then i show that no reuslts found        
