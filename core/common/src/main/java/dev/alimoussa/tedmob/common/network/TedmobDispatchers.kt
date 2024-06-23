package dev.alimoussa.tedmob.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val playnKeepDispatcher: TedmobDispatchers)

enum class TedmobDispatchers {
    Default,
    MAIN,
    IO,
}