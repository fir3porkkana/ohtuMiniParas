Feature: User can add cover picture for each book in list

    Scenario: user can add image file for book in list
       Given command add book cover is selected
       When  image file is selected and added
       When  book in list is selected
       Then  system will respond with image of book in user interface
