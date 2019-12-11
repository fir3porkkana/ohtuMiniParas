Feature: Playback of new audiobook begins at end of previous one

    Scenario: Playback of current audiobook is ongoing 
       Given following audiobook exists
       When  playback of current audiobook ends 
       Then  system will respond with playback of following audiobook
    
    Scenario: Playback of current audiobook is ongoing 
       Given following audiobook does not exist
       When  playback of current audiobook ends 
       Then  system will respond with null
