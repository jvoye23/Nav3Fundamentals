package com.jvcs.myapplication.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.jvcs.myapplication.auth.AuthNavigation
import com.jvcs.myapplication.screens.TodoDetailScreen
import com.jvcs.myapplication.screens.TodoListScreen
import com.jvcs.myapplication.screens.TodoNavigation
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    // The rootBackStack with its NavDisplay will only navigate between features and not single screens
    // The initial screens will be handled in different backStack for each feature
    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Auth::class, Route.Auth.serializer())
                    subclass(Route.Todo::class, Route.Todo.serializer())
                }
            }
        },
        // if (isLoggedIn) Route.Todo else Route.Auth
        Route.Auth
    )
    NavDisplay(
        modifier = modifier,
        backStack = rootBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()

        ),
        entryProvider = entryProvider {
            entry<Route.Auth> {
                AuthNavigation(
                    onLogin = {
                        rootBackStack.remove(Route.Auth)
                        rootBackStack.add(Route.Todo)
                    }
                )
            }
            entry<Route.Todo> {
                TodoNavigation()
            }
        }
    )
}