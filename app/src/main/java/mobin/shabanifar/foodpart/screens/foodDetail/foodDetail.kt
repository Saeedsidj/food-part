package mobin.shabanifar.foodpart.screens.foodDetail

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.NavigationBottom
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.detailList
import mobin.shabanifar.foodpart.ui.theme.FoodPartTheme
import mobin.shabanifar.foodpart.ui.theme.green
import mobin.shabanifar.foodpart.ui.theme.shapes


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FoodDetail(navController: NavHostController, isLogin: Boolean) {
    val tabData = listOf(
        "مواد اولیه",
        "طرز تهیه",
        "اطلاعات بیشتر"
    )
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var showMenu by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { tabData.size }
    val tabIndex = pagerState.currentPage
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var textReportState by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(0.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(236.dp)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(R.string.reportTitle),
                    style = MaterialTheme.typography.h3
                )
                OutlinedTextField(
                    value = textReportState,
                    onValueChange = { newString -> textReportState = newString },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)
                        .height(100.dp)
                        .background(MaterialTheme.colors.surface, MaterialTheme.shapes.medium)
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    shape = shapes.medium,
                    placeholder = { Text(text = "ایتجا بنویسید . . .") },
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary, MaterialTheme.shapes.small),
                    onClick = {
                        coroutineScope.launch {
                            modalSheetState.hide()
                            Toast.makeText(context, "گزارش شما ثبت شد", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                ) {
                    Text(
                        text = "ثبت",
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    ) {
        FoodPartTheme(
        ) {
            Scaffold(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                scaffoldState = scaffoldState,
                snackbarHost = {
                    CustomSnackbarHost(
                        snackbarHostState = snackbarHostState,
                        snackbarBackgroundColor = MaterialTheme.colors.surface,
                        actionColor = MaterialTheme.colors.primary
                    )
                },
                topBar = {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground,
                        elevation = 0.dp
                    ) {
                        IconButton(onClick = {
                            navController.navigate(NavigationBottom.Category.route) {
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                painterResource(R.drawable.ic_back),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                        Text(
                            text = stringResource(R.string.food_info),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            modifier = Modifier.clip(MaterialTheme.shapes.medium),
                            onClick = { showMenu = !showMenu }) {
                            Icon(
                                painterResource(R.drawable.more),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onBackground
                            )
                            DropDownMenu(
                                showMenu,
                                isLogin,
                                coroutineScope,
                                modalSheetState,
                                snackbarHostState
                            ) {
                                showMenu = it
                            }
                        }
                    }
                }
            ) {
                MainScreen(
                    it,
                    scrollState,
                    navController,
                    tabIndex,
                    pagerState,
                    tabData,
                    coroutineScope
                )
            }

        }
    }
}