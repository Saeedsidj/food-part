package mobin.shabanifar.foodpart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.fakeFoods


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search() {
    val keyboardController = LocalSoftwareKeyboardController.current
    var textField by remember { mutableStateOf(TextFieldValue("")) }
    val borderSurface = MaterialTheme.colors.surface
    val borderOnBack = MaterialTheme.colors.onBackground
    var isWrite by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.what_are_you_looking_for),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h2
                    )
                },
                backgroundColor = MaterialTheme.colors.background
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TextField(
                modifier = Modifier
                    .padding(16.dp)
                    //.clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    //.background(MaterialTheme.colors.surface)
                    .border(
                        1.dp,
                        if (isWrite) borderOnBack else borderSurface,
                        MaterialTheme.shapes.medium
                    ),
                value = textField,
                shape = MaterialTheme.shapes.medium,
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,

                colors = TextFieldDefaults.textFieldColors(
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,// خط زیر تکست فیلد وقتی روش کلیک بشه
                    unfocusedIndicatorColor = Color.Transparent, // خط زیر تکست فیلد وقتی روش کلیک نشده هنوز
                    backgroundColor = MaterialTheme.colors.surface,
                    cursorColor= Color.Yellow, // رنگ کرسر


                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.write_here),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                onValueChange = {
                    textField = it
                    isWrite = textField != TextFieldValue("")
                },
                trailingIcon = {
                    if (isWrite) {
                        IconButton(onClick = {
                            textField = TextFieldValue("")
                            isWrite = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                tint = MaterialTheme.colors.onBackground,
                            )
                        }
                    }
                }
            )
            if (isWrite) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(R.string.search_result, textField.text),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            SearchedItems(textField.text)
        }
    }
}


@Composable
fun SearchedItems(searchValue: String) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        val foundItems = fakeFoods.filter { items ->
            items.name == searchValue
        }
        items(foundItems) {
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { /*TODO*/ }
            ) {
                Image(
                    painter = painterResource(id = it.image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(width = 240.dp, height = 85.dp)
                )

                Text(
                    text = it.name,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp)
                )
                Text(
                    //text = "زمان : " + "${it.time}" + " دقیقه",
                    text = stringResource(id = R.string.food_time, it.time),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }
    }

}