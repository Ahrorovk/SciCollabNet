package com.ahrorovkspace.codebeyondearth.presentation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahrorovkspace.codebeyondearth.app.navigation.components.DrawerContent
import com.ahrorovkspace.codebeyondearth.presentation.components.CategoryItem
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomButton
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomProjectItem
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun MainScreen(
    state: MainState,
    scaffoldState: ScaffoldState,
    onEvent: (MainEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(
                openProfileScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToProfile)
                },
                openLoginScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToAuthorization)
                },
                openRegistrationScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToRegistration)
                },
                openSettingsScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToSettings)
                },
                openDonationsLink = { /*TODO*/ },
                openGithubPage = { /*TODO*/ },
                shareApp = { /*TODO*/ },
                sendEmail = { /*TODO*/ },
                openMyApplicationScreen = {
                    onEvent(MainEvent.GoToMyApplication)
                },
                isReg = state.refreshToken.isNotEmpty(),
                logOut = {
                    onEvent(MainEvent.Clean)
                    onEvent(MainEvent.Logout)
                }
            )
        },
        backgroundColor = Color.Transparent
    ) { itt ->
        LazyColumn() {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyRow(modifier = Modifier.fillMaxWidth(0.8f)) {
                        item {
                            Spacer(modifier = Modifier.padding(4.dp))
                            state.parentCategories.forEachIndexed { ind, category ->
                                CategoryItem(
                                    parentName = category,
                                    items = state.childrenCategories[ind],
                                    changedCategories = state.changedCategories,
                                    response = state.categoriesRespState.response != null,
                                ) {
                                    onEvent(MainEvent.OnCategorySelectedChange(it))
                                }
                                Spacer(modifier = Modifier.padding(4.dp))
                            }
                        }
                    }
                    CustomButton(
                        text = "refresh",
                        textSize = 10,
                        isLoading = state.projectByCategoriesRespState.isLoading,
                        width = 80,
                        height = 30
                    ) {
                        onEvent(MainEvent.GetProjectsByCategories)
                    }
                }
            }
            item {
                state.projectByCategoriesRespState.response?.let { item ->
                    item.forEach {
                        if (it.name.toLowerCase(Locale.ROOT)
                                .contains(state.searchState.toLowerCase(Locale.ROOT))
                        ) {
                            CustomProjectItem(projectByCategoriesItem = it,
                                { onEvent(MainEvent.GoToApplication(it)) }) {
                                onEvent(MainEvent.GoToBrieflyDesc(it))
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                    }
                    Spacer(modifier = Modifier.padding(50.dp))
                }
            }
        }
    }
}


