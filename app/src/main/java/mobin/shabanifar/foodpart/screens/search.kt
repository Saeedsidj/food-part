package mobin.shabanifar.foodpart.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import mobin.shabanifar.foodpart.NavigationBottom

@Composable
fun Search() {
    Text(text = NavigationBottom.Search.name.toString())
}