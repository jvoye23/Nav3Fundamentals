package com.jvcs.myapplication.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object Auth: Route{
        @Serializable
        data object Login: Route

        @Serializable
        data object Register: Route
    }

    @Serializable
    data object Todo: Route {
        @Serializable
        data object TodoList: Route

        @Serializable
        data object TodoFavorites: Route

        @Serializable
        data class TodoDetail(val todo: String): Route

        @Serializable
        data object Settings: Route
    }
}