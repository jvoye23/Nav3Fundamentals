package com.jvcs.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform