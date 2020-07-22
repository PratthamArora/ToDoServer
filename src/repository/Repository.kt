package com.pratthamarora.repository

import com.pratthamarora.model.User

interface Repository {

    suspend fun addUser(
        email: String,
        displayName: String,
        passwordHash: String
    ): User?

    suspend fun findUser(userId: Int): User?

    suspend fun findUserByEmail(email: String): User?
}