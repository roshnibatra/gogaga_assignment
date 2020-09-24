# GoGaga Dating App

### Project Images
![When internet issue exist](https://user-images.githubusercontent.com/66513552/94200018-5b375c80-fed7-11ea-87cd-0384675f5619.png)
![Profile page with slider images](https://user-images.githubusercontent.com/66513552/94200448-0fd17e00-fed8-11ea-84a8-11b5ceda7fc3.png)
![Loading Progress bar on pagination](https://user-images.githubusercontent.com/66513552/94200028-5f637a00-fed7-11ea-8ef1-8ffa448e6da2.png)


### Libraries used in the project
- retrofit2
- Constraintlayout
- lombok
- recyclerview
- viewpager2
- roundedimageview
- okhttp3:logging-interceptor
- androidx.navigation (On the project generation)

### Things implemented in the project
- Navigation graph.
- Retrofit library to make https call and collect data from the server.
- Fragments and its lifecycle.
- Recyclerview to display list of incoming data in scrollable format.
- Custom background for textView.
- Viewpager for image scrolling.
- lombak to create getter and setter for java classes.
- Runnable to auto slide images after X seconds.
- RecyclerView OnScrollListner to auto fetch the data on the last record.
- Pagination to load data in chunks and improve UI experience.
- ProgessBar
- SnackBar to inform user about the error.
- Transition animation on image to improve UX.

### Things which can be improved.
- Use of ViewModels and Room to store the fetched data. This will reduce the number of API calls to the server and improve the performance of the app.
- Better animations.
- More details on profile page.
- Instagram Login to collect more images.
- Facebook login to connect with friends*.
