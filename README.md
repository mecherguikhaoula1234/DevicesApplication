# DevicesApplication

I created a mock server to get devices from URL : https://526fe7b9-9969-4f9d-b3e5-c04c7fbd4ae0.mock.pstmn.io/home/getDevices
which returns the informations of devices as the json in the description below:
devices: [
{
“Id”: “1234”,
“Type”: “Sensor”,
“Price”: 20,
“Currency”: “USD”,
“isFavorite”: false,
“imageUrl: “”,
“Title”: “Test Sensor”,
“Description: “”
},
{
“Id”: “1235”,
“Type”: “Thermostat”,
“Price”: 25,
“Currency”: “USD”,
“isFavorite”: false,
“imageUrl: “”,
“Title”: “Test Thermostat”,
“Description: “”
}
]

I used :

Architecture MVVM: 

Retrofit:  to manage the call of the API

Hilt- Dagger: for the dependency injection

Coroutines: to write async code and operators

Glide : to load an image

viewbinding:  to more easily write code that interacts with views.

Junit4 :for unit test
