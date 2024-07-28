# JSteam
Jsteam application is a game portal application that provides a list of games provided. Every game that is in the Jsteam application can be given a review by the user. Jsteam also provides an authentication feature that provides register and login features. Jsteam also provides company location via google map.

## Technologies
1. Programming Language: Java
2. IDE Tools: Android Studio
3. Database: SQLite
4. API: Google Map API

Github Link: iBRabbit/JSteam (github.com)

## Features
Landing Page

![image](https://github.com/user-attachments/assets/ddfb39ef-bf69-4052-8499-924e8a099c89)
This is the first page when starting the app. There are two buttons: Register and Login.

Register/Login page

![image](https://github.com/user-attachments/assets/5aeb2c35-b1d4-497e-91c0-a0e9fd93fc2d)
On this page, user can register by put their credentials into the form, and login afterwards.

OTP Verification

![image](https://github.com/user-attachments/assets/7b3833ac-cd7f-45d0-9a88-3b64c2d353b9)
After login, users now will see the OTP (One Time Password) page. Using the phone number on the database, the application will send the verification code to the user’s phone number’s message. If the message doesn’t appear, user can press the “Resend OTP” above the button. After the user receives a message (in the second picture), the user now enters the OTP code. If the OTP isn’t matched, it will show an error message. If it’s matched, the user can now access the home page login using the data the user input.


Game Section

![image](https://github.com/user-attachments/assets/15ca9a6b-d10c-4d4d-ad22-1198912cd068)
![image](https://github.com/user-attachments/assets/6e9fbd95-9d64-4282-ad0b-e70af705d7b4)
Game sections, where user can see all the games and its information . User can give their own review on each game.

Reviews Section

![image](https://github.com/user-attachments/assets/c4bd9552-fc5d-4fe5-8cce-e6442a29d689)
On review section, user can see their own reviews. User can update or delete the given review.

About us Section
![image](https://github.com/user-attachments/assets/75e89c4f-b146-4b1c-8e4f-329c027119be)
In this section, the user can see the JSteam’s company location. Using Google Maps API, the application shows the location of the company. The user can zoom the map so the user will be able to see the exact location
