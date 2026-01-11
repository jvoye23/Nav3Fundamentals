package com.jvcs.myapplication.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(val state: NavigationState) {

    fun navigate(route: NavKey) {
        // Check if the route is part of Top Level Destinations / BottomBar Items
        if(route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            // the backStack of the current Top Level route
            // if it exists we push it onto the stack
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} does not exist")

        val currentRoute = currentStack.last()

        if(currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()

        }
    }
}