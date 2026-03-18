package io.github.astar.cime4j.auth

abstract class Auth {
    abstract val type: String
    abstract fun getAuth(): String
}