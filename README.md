Title: D308 Mobile Application | Vacation and Excursion Scheduling App.

Purpose:
    The purpose of this app is to provide a simple and efficient way for users
to select and enter in their own Vacation locations along with it's associated start and end date.
users will also be able to select Excursions for each Vacation. The App will send notifications 
when the start dates have begun as well as the end dates of the vacation. 

users will have the ability to share the vacation details with others. 


_________________________________________________________________



B.  Create an Android mobile application, compatible with Android 8.0 and higher. The application 
must use the Room Framework as an abstraction layer over the local SQLite database to save the data. 
The application must include the following functional requirements:

1.  Create a user option to enter, update and delete vacations.
- Users have the ability via the Vacation drop down menu to enter, update, and delete vacations. 
a.  Allow the user to add as many vacations as desired.
- Users may use the floating action button to add more vacations. 
b.  Implement validation so that a vacation cannot be deleted if excursions are associated with it.
- Validations have been programmed into the app to prevent the deletion of a vacation when an excursion
Exists on it. 

Changes: Included CRUD options for vacations. Added delete validation warning.   | Files: VacationDetails.java    | lines: Save/update = 211-236, create new vacation = 78-83. 

_________________________________________________________________


2.  Include the following details for each vacation:

•  title (e.g., Bermuda Trip, Spring Break, London Trip)

•  hotel or other place where you will be staying

•  start date

•  end date

- All options are available for users to fill out. 
- Please note that I included validation into the date picker so not notifications were needed
since users are not allowed to pick dates where end starts before the start date and visa versa. 



Task B2
Changes:  Added layout and logic for Vacation title, hotel, start, and end date.  | 
Files:  VacationDetails.java, activity_excursion_details.xml   | 
lines: VacationDetails.java = 50-58, 70-91, 80, 227, 236 / activity_vacation_details.xml= 48-54, 
66-72, 83-89, 102-108

_________________________________________________________________


3.  Include features that allow the user to do the following for each vacation:

a.  Display a detailed view of the vacation, including all vacation details. This view can also be 
used to add and update the vacation information.
- By clicking on enter from the home page and then clicking onto a vacation in the list 
(you may need to created the vacation first), you'll be able to see all the details of the vacation. 
b.  Enter, edit, and delete vacation information.
- Changing an already created vacation will UPDATE it. 
- You can also delete the vacation so long as there are no attached excursions.
- This can be done by using the save and delete features in the drop down menu. 
c.  Include validation that the input dates are formatted correctly.
- If you choose a start date after the end date, the end date will be auto updated to match the start date.
d.  Include validation that the vacation end date is after the start date.
- The datepicker will not allow you to choose an end date before the start date.
e.  Include an alert that the user can set which will trigger on the start and end date, displaying 
the vacation title and whether it is starting or ending.
- Using the drop down menu, you can set an alert that will go off the day the vacation starts and ends. 

f.  Include sharing features so the user can share all the vacation details via a sharing feature 
(either e-mail, clipboard or SMS) that automatically populates with the vacation details.
- Sharing features allow copy to clipboard, email, SMS etc. 
- This can be done by using the drop down menu in excursion details and selecting the "Share" option. 

g.  Display a list of excursions associated with each vacation.
- When adding Excursions to a vacation and saving them via the menu option "save excursion", you will 
then be able to see the excursion in the vacation Details page. 

h.  Add, update, and delete as many excursions as needed.
- Adding, updating, deleting to many excursions is supported using the Floating action button to add
and the menu options to save, update, and delete. 





Task B3
Changes: Included CRUD, Notify, Validation, and share features   | Files: VacationDetails.java, 
myReceiver.java, menu_vacation_details.xml    | lines: menu_vacation_details = 320-356 / 
myReceiver.java = All lines / VacationDetails.java = notify: 320-356, Share: 357 - 381 / Validation = 97-138

_________________________________________________________________


4.  Include the following details for each excursion:

•  The excursion title (e.g., Snorkeling, Hiking, Bus Tour, Cooking Lesson)
- Feature added. Access by clicking the floating action button in the vacations details page. 
•  The excursion date
- Exursion datepicker will only allow users to choose dates that are between the vacation start and end dates. 

Task B4
Changes:  Added excursion title and datepicker options. Fully functional.   | 
Files: ExcursionDetails.java, activity_excursion_details.xml    | 
lines: activity_excursion_details.xml = Title: 23-30 , Date : 34-46 / 
ExcursionDetails.java = Title = 53-54, 167, 171. Date = 59-60, 71-106, 107-114


_________________________________________________________________

5.  Include features that allow the user to do the following for each excursion:

a.  Display a detailed view of the excursion, including the title, and date
- Detail viewholder is added to Vacation details page. 
b.  Enter, edit, and delete excursion information.
- users can click on excursion and then use the menu options/ edit name/datepicker options to change
save, and delete excursions. 
c.  Include validation that the input dates are formatted correctly.
- Validation is forced by users having to choose a date via the date picker dialog. 
d.  Include an alert that the user can set that will trigger on the excursion date, stating the 
excursion title.
- Excursion notification is specific to the excursion start time and will notify user of the 
- excursion name and that it's starting on the day of the excursion. 

e.  Include validation that the excursion date is during the associated vacation.
- Date picker will not allow users to pick a date outside of the vacation start and end date.
- I included a feature that greys out the dates if they are not within the min/max(start/end) of the date

Task B5
Changes: Added viewholder in vacations page for excursions. Options to update, save, and delete excursions added. 
Validation enforcing date format via datepicker. Alert of excursion name on the start date. 
validation enforced on excursion so it can ONLY select a date within vacation start and end date. 
| Files: VacationDetails.java, ExcursionDetails.java    | lines:  vacation Details = 135-145. excursion details = 77-111, 153-214

_________________________________________________________________