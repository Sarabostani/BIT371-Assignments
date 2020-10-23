## PART 1

1. Create an empty project
1. Add dependencies to module's build.gradle file
    
       def nav_version = "2.3.1"

       // Java language implementation
       implementation "androidx.navigation:navigation-fragment:$nav_version"
       
       implementation "androidx.navigation:navigation-ui:$nav_version"

1. Add the 6 fragments (destinations) to be used for the navigation componenti
  
       MainFragment
       SpecifyAmountFragment
       ViewBalanceFragment
       ViewTransactionFragment
       ChooseRecipientFragment
       ConfirmationFragment

   >(delete all of the boiler plate code in the each java file except for onViewCreated)

1. copy over the layout contents for each fragment from the repo
1. Create a navigation component by going over res -> New Android Resource -> Drop down resource type : navigaton
1. Add the fragments to the navigation compnent and organize them
1. Write up the code for navigation for each button following the lecture's notes
1. Add animations:
   
   a. Add a folder to res folder called anim
   
   b. Right click and add 4 animation files.

         slide_in_left
         slide_in_right
         slide_out_right
         slide_out_left

   c. Copy and paste the content of each animation .xml file from the repo

1. Add the animations to each action in the navigator as explained in the lecture
            
            app:popEnterAnim="@anim/slide_in_left"
            app:popExitAnim="@anim/slide_out_right"
            app:enterAnim="@anim/slide_in_right"
            app:exitAnim="@anim/slide_out_left"
   
    >delete each animation and observe the effect to understand the difference between animation types
1. Set the back stack pop behavior for specify amount to confirmation action to main fragment and let the popup to be inclusive
    
       app:popUpTo="@id/mainFragment"
       app:popUpToInclusive="false" (optional)
