package com.ahrorovkspace.codebeyondearth.app.navigation


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahrorovkspace.codebeyondearth.R
import com.ahrorovkspace.codebeyondearth.app.navigation.components.CBEBottomBar
import com.ahrorovkspace.codebeyondearth.app.theme.AntiFlashWhite
import com.ahrorovkspace.codebeyondearth.core.Constants
import com.ahrorovkspace.codebeyondearth.core.Routes
import com.ahrorovkspace.codebeyondearth.core.doesScreenHaveBottomBar
import com.ahrorovkspace.codebeyondearth.core.doesScreenHaveMenu
import com.ahrorovkspace.codebeyondearth.core.doesScreenHaveTopBar
import com.ahrorovkspace.codebeyondearth.core.getTopBarTitle
import com.ahrorovkspace.codebeyondearth.presentation.applicationScreen.ApplicationEvent
import com.ahrorovkspace.codebeyondearth.presentation.applicationScreen.ApplicationScreen
import com.ahrorovkspace.codebeyondearth.presentation.applicationScreen.ApplicationViewModel
import com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen.AuthorizationEvent
import com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen.AuthorizationScreen
import com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen.AuthorizationViewModel
import com.ahrorovkspace.codebeyondearth.presentation.brieflyDescScreen.BrieflyDescEvent
import com.ahrorovkspace.codebeyondearth.presentation.brieflyDescScreen.BrieflyDescScreen
import com.ahrorovkspace.codebeyondearth.presentation.brieflyDescScreen.BrieflyDescViewModel
import com.ahrorovkspace.codebeyondearth.presentation.chatsScreen.ChatsEvent
import com.ahrorovkspace.codebeyondearth.presentation.chatsScreen.ChatsScreen
import com.ahrorovkspace.codebeyondearth.presentation.chatsScreen.ChatsViewModel
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomIconButton
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomSearchTextField
import com.ahrorovkspace.codebeyondearth.presentation.mainScreen.MainEvent
import com.ahrorovkspace.codebeyondearth.presentation.mainScreen.MainScreen
import com.ahrorovkspace.codebeyondearth.presentation.mainScreen.MainViewModel
import com.ahrorovkspace.codebeyondearth.presentation.myApplicationScreen.MyApplicationEvent
import com.ahrorovkspace.codebeyondearth.presentation.myApplicationScreen.MyApplicationScreen
import com.ahrorovkspace.codebeyondearth.presentation.myApplicationScreen.MyApplicationViewModel
import com.ahrorovkspace.codebeyondearth.presentation.profileScreen.ProfileEvent
import com.ahrorovkspace.codebeyondearth.presentation.profileScreen.ProfileScreen
import com.ahrorovkspace.codebeyondearth.presentation.profileScreen.ProfileViewModel
import com.ahrorovkspace.codebeyondearth.presentation.registratrionScreen.RegistrationEvent
import com.ahrorovkspace.codebeyondearth.presentation.registratrionScreen.RegistrationScreen
import com.ahrorovkspace.codebeyondearth.presentation.registratrionScreen.RegistrationViewModel
import com.ahrorovkspace.codebeyondearth.presentation.settingsScreen.SettingsScreen
import com.ahrorovkspace.codebeyondearth.presentation.settingsScreen.SettingsViewModel
import com.ahrorovkspace.codebeyondearth.presentation.splashScreen.SplashEvent
import com.ahrorovkspace.codebeyondearth.presentation.splashScreen.SplashScreen
import com.ahrorovkspace.codebeyondearth.presentation.splashScreen.SplashViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val searchState = mutableStateOf("")
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    navState: NavigationState,
    onEvent: (NavigationEvent) -> Unit
) {
    searchState.value = navState.searchState
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

    Scaffold(
        contentColor = AntiFlashWhite,
        topBar = {
            if (doesScreenHaveTopBar(currentScreen)) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(AntiFlashWhite),
                    title = {
                        when (currentScreen) {
                            Routes.MainScreen.route -> {
                                CustomSearchTextField(
                                    value = navState.searchState,
                                    onValueChange = {
                                        onEvent(
                                            NavigationEvent.OnSearchStateChange(
                                                it
                                            )
                                        )
                                    }, placeholder = "Search project"
                                )
                            }

                            else -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = getTopBarTitle(currentScreen),
                                        color = MaterialTheme.colors.onBackground
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        if (doesScreenHaveMenu(currentScreen)) {
                            CustomIconButton(icon = Icons.Filled.Menu) {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        } else {
                            CustomIconButton(icon = Icons.Filled.ArrowBackIos) {
                                navController.popBackStack()
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (doesScreenHaveBottomBar(currentScreen)) {
                CBEBottomBar(navController)
            }
        }
    ) { itt ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(itt)) {
            Image(painter = painterResource(id = R.drawable.orig), contentDescription = "smedium")
        }
        NavHost(navController = navController, startDestination = Routes.MainScreen.route) {
            composable(Routes.SplashScreen.route) {
                val viewModel = hiltViewModel<SplashViewModel>()
                val state = viewModel.state.collectAsState()
                scope.launch(Dispatchers.IO) {
                    delay(5000)
                    viewModel.onEvent(
                        SplashEvent.OnStateRegisteredChange(
                            true
                        )
                    )
                }
                SplashScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            SplashEvent.GoToMainScreen -> {
                                navController.navigate(Routes.MainScreen.route) {
                                    popUpTo(Routes.SplashScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            SplashEvent.GoToAuthScreen -> {
                                navController.navigate(Routes.AuthorizationScreen.route) {
                                    popUpTo(Routes.SplashScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    }
                )
            }
            composable(Routes.RegistrationScreen.route) {
                val viewModel = hiltViewModel<RegistrationViewModel>()
                val state = viewModel.state.collectAsState()

                RegistrationScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            RegistrationEvent.GoToSignUp -> {
                                navController.navigate(Routes.AuthorizationScreen.route) {
                                    popUpTo(Routes.RegistrationScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            RegistrationEvent.GoToMainScreen -> {
                                navController.navigate(Routes.MainScreen.route) {
                                    popUpTo(Routes.RegistrationScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
            composable(Routes.AuthorizationScreen.route) {
                val viewModel = hiltViewModel<AuthorizationViewModel>()
                val state = viewModel.state.collectAsState()
                AuthorizationScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            AuthorizationEvent.GoToSignUp -> {
                                navController.navigate(Routes.RegistrationScreen.route)
                            }

                            AuthorizationEvent.GoToMainScreen -> {
                                navController.navigate(Routes.MainScreen.route) {
                                    popUpTo(Routes.AuthorizationScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    }
                )
            }
            composable(Routes.SettingsScreen.route) {
                val viewModel = hiltViewModel<SettingsViewModel>()
                val state = viewModel.state.collectAsState()
                SettingsScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            else -> Unit
                        }

                    }
                )
            }
            composable(Routes.MainScreen.route) {
                val viewModel = hiltViewModel<MainViewModel>()
                val state = viewModel.state.collectAsState()
                viewModel.onEvent(MainEvent.OnSearchProjectChange(searchState.value))
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(MainEvent.GetCategories)
                    viewModel.onEvent(MainEvent.GetProjectsByCategories)
                }
                MainScreen(
                    state = state.value,
                    scaffoldState = scaffoldState,
                    onEvent = { event ->
                        when (event) {
                            MainEvent.GoToAuthorization -> {
                                navController.navigate(Routes.AuthorizationScreen.route)
                            }

                            MainEvent.Logout -> {
                                navController.navigate(Routes.AuthorizationScreen.route) {
                                    popUpTo(Routes.MainScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            MainEvent.GoToMyApplication -> {
                                navController.navigate(Routes.MyApplicationScreen.route)
                            }

                            is MainEvent.GoToApplication -> {
                                navController.navigate(
                                    Routes.ApplicationScreen.route.replace(
                                        "{${Constants.PROJECT_ID_ARG}}",
                                        "${event.id}",
                                    )
                                )
                            }

                            is MainEvent.GoToBrieflyDesc -> {
                                navController.navigate(
                                    Routes.BrieflyDescScreen.route.replace(
                                        "{${Constants.PROJECT_ID_ARG}}",
                                        "${event.id}"
                                    )
                                )
                            }

                            MainEvent.GoToProfile -> {
                                navController.navigate(Routes.ProfileScreen.route)
                            }

                            MainEvent.GoToRegistration -> {
                                navController.navigate(Routes.RegistrationScreen.route)
                            }

                            MainEvent.GoToSettings -> {
                                navController.navigate(Routes.SettingsScreen.route)
                            }

                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    }
                )
            }
            composable(Routes.ChatScreen.route) {
                val viewModel = hiltViewModel<ChatsViewModel>()
                val state = viewModel.state.collectAsState()
                ChatsScreen(
                    state = state.value,
                    scaffoldState = scaffoldState,
                    onEvent = { event ->
                        when (event) {
                            ChatsEvent.Logout -> {
                                navController.navigate(Routes.AuthorizationScreen.route) {
                                    popUpTo(Routes.ChatScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            ChatsEvent.GoToAuthorization -> {
                                navController.navigate(Routes.AuthorizationScreen.route)
                            }

                            ChatsEvent.GoToProfile -> {
                                navController.navigate(Routes.ProfileScreen.route)
                            }

                            ChatsEvent.GoToRegistration -> {
                                navController.navigate(Routes.RegistrationScreen.route)
                            }

                            ChatsEvent.GoToMyApplication -> {
                                navController.navigate(Routes.MyApplicationScreen.route)
                            }

                            ChatsEvent.GoToSettings -> {
                                navController.navigate(Routes.SettingsScreen.route)
                            }

                            else -> Unit
                        }
                    }
                )
            }
            composable(Routes.ProfileScreen.route) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(ProfileEvent.GetProfileInfo)
                }
                ProfileScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
            composable(
                Routes.ApplicationScreen.route, arguments = listOf(
                    navArgument(Constants.PROJECT_ID_ARG) {
                        type = NavType.IntType
                    })
            ) { backstackEntry ->
                val projectId = backstackEntry.arguments?.getInt(Constants.PROJECT_ID_ARG) ?: -1
                val viewModel = hiltViewModel<ApplicationViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(ApplicationEvent.OnProjectIdChange(projectId))
                }
                ApplicationScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            ApplicationEvent.GoToMain -> {
                                navController.popBackStack()
                            }

                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
            composable(
                Routes.BrieflyDescScreen.route, arguments = listOf(
                    navArgument(Constants.PROJECT_ID_ARG) {
                        type = NavType.IntType
                    })
            ) { backstackEntry ->
                val projectId = backstackEntry.arguments?.getInt(Constants.PROJECT_ID_ARG) ?: -1
                val viewModel = hiltViewModel<BrieflyDescViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(BrieflyDescEvent.OnProjectIdChange(projectId))
                }
                BrieflyDescScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
            composable(
                Routes.MyApplicationScreen.route
            ) {
                val viewModel = hiltViewModel<MyApplicationViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(MyApplicationEvent.GetMyApplication)
                }
                MyApplicationScreen(
                    state = state.value,
                    onEvent = { event ->
                        when (event) {
                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
        }
    }
}