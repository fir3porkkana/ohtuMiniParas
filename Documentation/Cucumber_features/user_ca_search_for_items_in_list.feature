Feature: User can search for items in list

    Scenario: user can find items in list with correct search word 
       Given command search books is selected
       When  search word "sinuhe" is entered
       Then  system will respond with "Sinuhe egyptiläinen by: Mika Waltari"

    
    Scenario: user can not find items in list with incorrect search word
       Given command search books is selected
       When  username "qwerty" is entered
       Then  system will respond with null
