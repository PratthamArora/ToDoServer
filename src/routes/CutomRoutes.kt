package com.pratthamarora.routes

import com.pratthamarora.utils.Constant
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location

@KtorExperimentalLocationsAPI
@Location(Constant.USER_LOGIN)
class UserLoginRoute

@KtorExperimentalLocationsAPI
@Location(Constant.USER_CREATE)
class UserCreateRoute