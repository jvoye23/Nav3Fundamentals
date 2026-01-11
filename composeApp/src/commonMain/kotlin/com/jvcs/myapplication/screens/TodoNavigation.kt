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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.jvcs.myapplication.navigation.Navigator
import com.jvcs.myapplication.navigation.Route
import com.jvcs.myapplication.navigation.TOP_LEVEL_DESTINATIONS
import com.jvcs.myapplication.navigation.TodoNavigationBar
import com.jvcs.myapplication.navigation.rememberNavigationState
import com.jvcs.myapplication.navigation.toEntries
import com.jvcs.myapplication.scenes.ListDetailScene
import com.jvcs.myapplication.scenes.rememberListDetailSceneStrategy

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
            sceneStrategy = rememberListDetailSceneStrategy(),
            entries = todoNavigationState.toEntries(
                entryProvider {
                    entry<Route.Todo.TodoList>(
                        metadata = ListDetailScene.listPane()
                    ) {
                        // A ViewModel is bound to the first entry of a feature
                        TodoListScreen(
                            onTodoClick = { todo ->
                                navigator.navigate(Route.Todo.TodoDetail(todo))
                            }
                        )
                    }
                    entry<Route.Todo.TodoDetail>(
                        metadata = ListDetailScene.detailPane()
                    ) { key ->
                        TodoDetailScreen(
                            todo = key.todo
                        )
                    }
                    entry<Route.Todo.TodoFavorites>(
                        metadata = ListDetailScene.listPane()
                    ) {
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
