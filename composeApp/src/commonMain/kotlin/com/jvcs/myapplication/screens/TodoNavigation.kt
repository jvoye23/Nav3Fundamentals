package com.jvcs.myapplication.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.jvcs.myapplication.navigation.Route
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun TodoNavigation(
    modifier: Modifier = Modifier,
) {
    val todoBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Todo.TodoList::class, Route.Todo.TodoList.serializer())
                    subclass(Route.Todo.TodoDetail::class, Route.Todo.TodoDetail.serializer())
                }
            }
        },

        Route.Todo.TodoList
    )
    NavDisplay(
        backStack = todoBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()

        ),
        entryProvider = entryProvider {
            entry<Route.Todo.TodoList> {
                // A ViewModel is bound to the first entry of a feature
                TodoListScreen(
                    onTodoClick = { todo ->
                        todoBackStack.add(Route.Todo.TodoDetail(todo))
                    }

                )
            }
            entry<Route.Todo.TodoDetail> { key ->
                TodoDetailScreen(
                    todo = key.todo
                )
            }
        }
    )
}
