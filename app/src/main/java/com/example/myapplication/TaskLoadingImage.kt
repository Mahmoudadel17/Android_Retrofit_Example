package com.example.myapplication

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

//"https://www.kasandbox.org/programming-images/avatars/marcimus.png"
@Composable
fun ListImages() {
    LazyColumn(state = rememberLazyListState()){
        items(10){
            LoadingImage(link = "https://images.unsplash.com/photo-1614288532696-203f89dc0db4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1827&q=80" )
            LoadingImage(link = "https://www.pakainfo.com/wp-content/uploads/2021/09/sample-image-url-for-testing-768x432.jpg")
        }
    }
}
@Composable
fun LoadingImage(link:String) {
    AsyncImage(
        model = link,
        contentDescription = "Translated description of what the image contains",
        error = painterResource(id = R.drawable.ph),
        placeholder = painterResource(id = R.drawable.ph),
    )

}