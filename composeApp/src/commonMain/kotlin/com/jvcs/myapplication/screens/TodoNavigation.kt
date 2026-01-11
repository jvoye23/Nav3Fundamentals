package com.jvcs.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.jvcs.myapplication.auth.LoginScreen
import com.jvcs.myapplication.auth.LoginViewModel
import com.jvcs.myapplication.auth.RegisterScreen
import com.jvcs.myapplication.auth.RegisterViewModel
import com.jvcs.myapplication.auth.SharedAuthViewModel
import com.jvcs.myapplication.navigation.Navigator
import com.jvcs.myapplication.navigation.Route
import com.jvcs.myapplication.navigation.TOP_LEVEL_DESTINATIONS
import com.jvcs.myapplication.navigation.TodoNavigationBar
import com.jvcs.myapplication.navigation.rememberNavigationState
import com.jvcs.myapplication.navigation.toEntries
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun TodoNavigation(
    modifier: Modifier = Modifier,
) {
    val todoNavigationState = rememberNavigationState(
        startRoute = Route.Todo.TodoList,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(todoNavigationState)
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            TodoNavigationBar(
                selectedKey = todoNavigationState.topLevelRoute,
                onSelectKey = {
                    navigator.navigate(route = it)
                }
            )
        }
    ) {
        innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            entries = todoNavigationState.toEntries(
                entryProvider {
                    entry<Route.Todo.TodoList> {
                        // A ViewModel is bound to the first entry of a feature
                        TodoListScreen(
                            onTodoClick = { todo ->
                                navigator.navigate(Route.Todo.TodoDetail(todo))
                            }
                        )
                    }
                    entry<Route.Todo.TodoDetail> { key ->
                        TodoDetailScreen(
                            todo = key.todo
                        )
                    }
                    entry<Route.Todo.TodoFavorites> {
                        TodoListScreen(
                            onTodoClick = { todo ->
                                navigator.navigate(Route.Todo.TodoDetail(todo))
                            }
                        )
                    }
                    entry<Route.Todo.Settings> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Settings")
                        }
                    }
                }
            )
        )

    }


}
