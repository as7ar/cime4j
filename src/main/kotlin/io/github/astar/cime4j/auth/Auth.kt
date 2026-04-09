package io.github.astar.cime4j.auth

abstract class Auth {
    abstract val type: AuthType
    abstract fun getAuth(): String
}