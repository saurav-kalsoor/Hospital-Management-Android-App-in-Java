<h1>Hospital-Management-Android-App-in-Java</h1>

This is a simple Hospital Management App of a hospital. The Features of this App are as follows:<br />
1.Sign In using your mobile number and get OTP – this feature is done with the help of Google’s Firebase Authentication.<br />
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101052494-adb68880-35ac-11eb-9822-0727c74176e4.jpeg" width="300" height="600">
</p>

2.Create Your Profile for once i.e. patient’s profile – this data will be stored in Firebase Firestore.

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101055052-92994800-35af-11eb-991b-1ac001d983ba.jpeg" width="300" height="600">
</p>

3.The MainActivity of this App has a Navigation Menu Drawer and many fragments such as

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101055411-e146e200-35af-11eb-9978-15113b9c4836.jpeg" width="300" height="600">
</p>

i)Home Fragment<br />
ii)Appointment Fragment<br />
iii)Prescription Fragment <br />
iv)Doctors Fragment<br />
v)Medical Records Fragment <br />
vi)Upload Documents Fragment <br />
vii)Edit Profile Fragment <br />
viii)Blog<br />
ix)Contact Us<br />
x)About Developer <br />
xi)Log Out<br />
<br /><br />

i)Home Fragment – This fragment has got 5 custom buttons.

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101056233-c6c13880-35b0-11eb-88f4-379eda7bbaa3.jpeg" width="300" height="800">
</p>

A)Call Ambulance – on click it opens a fragment with a button where on clicking it , it calls the ambulance.<br />



<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101056514-10118800-35b1-11eb-89b1-f212047551df.jpeg" width="300" height="600">
</p>

B)Book An Appointment – opens book appointment fragment.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101056819-5f57b880-35b1-11eb-99aa-25f536f65854.jpeg" width="300" height="600">
</p>

C)Find Doctors – opens Doctors Fragment.<br />
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101056977-8ca46680-35b1-11eb-87db-9455c5d89466.jpeg" width="300" height="600">
</p>

D)View Prescriptions – opens prescription fragment.<br />
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057127-b78eba80-35b1-11eb-88a9-4f786200bce5.jpeg" width="300" height="600">
</p>

E)Medical Records – opens medical record fragment.<br />
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057430-076d8180-35b2-11eb-817b-8f04ce64a6b2.jpeg" width="300" height="600">
</p>
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057441-089eae80-35b2-11eb-8147-c2ca4c1bf9eb.jpeg" width="300" height="600">
</p>

ii)Appointment Fragment – this fragment contains all the appointments booked by you and it contains a floating action button ,where on pressing it you can book a new appointment. And all this data is stored in Firestore.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057793-734fea00-35b2-11eb-9f78-939cdd3666be.jpeg" width="300" height="600">
</p>
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057796-73e88080-35b2-11eb-8eb1-52732d64096d.jpeg" width="300" height="600">
</p>

iii)Prescription Fragment – this fragment contains all the prescriptions given to you by your doctor and you call also add new prescriptions. And all this data is in Firestore.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101058054-bf029380-35b2-11eb-831a-d26b0f755f62.jpeg" width="300" height="600">
</p>
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101058056-bf9b2a00-35b2-11eb-9f1b-f836a490a1b1.jpeg" width="300" height="600">
</p>

iv)Doctor Fragment – this fragment contains the list of doctors working in this hospital with their names, qualifications and specified fields.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101056977-8ca46680-35b1-11eb-87db-9455c5d89466.jpeg" width="300" height="600">
</p>

v)Medical Record Fragment – this fragment contains all the medical records such as x-ray images or some other images and you can also add new records.<br/>

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057430-076d8180-35b2-11eb-817b-8f04ce64a6b2.jpeg" width="300" height="600">
</p>
<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101057441-089eae80-35b2-11eb-8147-c2ca4c1bf9eb.jpeg" width="300" height="600">
</p>

vi)Upload Document Fragment – this fragment lets you add new medical record files. All this data is stored in Firebase Storage.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101058495-30dadd00-35b3-11eb-98e9-9aaa16e225e4.jpeg" width="300" height="600">
</p>

vii)Edit Profile Fragment – this fragment lets you edit your profile. <br/>

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101058663-5ec02180-35b3-11eb-97e6-a953aa41107e.jpeg" width="300" height="600">
</p>

Viii)Blog – this takes you to the hospital’s blog page.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101058788-8a430c00-35b3-11eb-9dcc-255065250904.jpeg" width="300" height="600">
</p>

ix)Contact us – this takes you to the hospital’s contact us page.<br />

<p align="center">
<img src="https://user-images.githubusercontent.com/54215324/101058892-af377f00-35b3-11eb-825a-2e934c90b715.jpeg" width="300" height="600">
</p>

xi)Log out – it logs out the current user.
