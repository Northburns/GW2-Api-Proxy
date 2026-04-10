package northburns.gw2.site.helper.redux

import arrow.core.Either
import arrow.core.right
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.log.GoonConsole
import northburns.gw2.client.myclient.log.goonConsole
import northburns.gw2.site.actions.GoonAction
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.helper.redux.MiddlewareFactory.middlewareTyped
import northburns.gw2.site.util.BiList
import northburns.gw2.site.util.Differ
import northburns.gw2.site.util.eitherFrom
import org.reduxkotlin.Middleware
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

/**
 * Very much inspired by [Redux Logger](https://github.com/LogRocket/redux-logger)
 */
object ReduxLogger {

    enum class ReduxLoggerLevel(
        val log: (GoonConsole) -> JsConsoleMethod,
    ) {
        LOG(log = { c -> { c.log(*it) } }),
        CONSOLE(log = { c -> { c.log(*it) } }),
        WARN(log = { c -> { c.warn(*it) } }),
        ERROR(log = { c -> { c.error(*it) } }),
        INFO(log = { c -> { c.info(*it) } }),
    }

    fun interface JsConsoleMethod {
        fun log(vararg o: Any?): Unit
    }

    class ReduxLoggerFormatting(
        val levelPrevState: (GoonAction) -> ReduxLoggerLevel? = { ReduxLoggerLevel.LOG },
        val levelAction: (GoonAction) -> ReduxLoggerLevel? = { ReduxLoggerLevel.LOG },
        val levelError: (GoonAction) -> ReduxLoggerLevel? = { ReduxLoggerLevel.ERROR },
        val levelNextState: (GoonAction) -> ReduxLoggerLevel? = { ReduxLoggerLevel.LOG },
        val levelDiff: (GoonAction) -> ReduxLoggerLevel? = { ReduxLoggerLevel.LOG },

        val showTimestamp: Boolean = true,
        val showDuration: Boolean = true,

        val cssTitle: (GoonAction) -> String = { "" },
        val cssPrevState: String = "",
        val cssAction: String = "",
        val cssNextState: String = "",
        val cssError: String = "background-color: #FFAAAA; color: #000000;",

        // Once these are annotated wiht @JsExport, I can just pass them as is
        val transformAction: (GoonAction) -> Any = { it  }, //JSON.parse(Gw2Json.json.encodeToString(it)) },
        val transformState: (GoonState) -> Any = { it }, // JSON.parse(Gw2Json.json.encodeToString(it)) },
        val transformError: (Throwable) -> Any = { it },

        val formatTime: (Instant) -> String = { it.toString() },
        val formatDuration: (Duration) -> String = { it.toString() },
    ) {
        internal fun formatTitle(logEntry: LogEntry): String =
            mutableListOf("%c 🏃 %c ${logEntry.action::class.simpleName}")
                .also { if (showTimestamp) it.add("%c ${formatTime(logEntry.started)}") }
                .also { if (showDuration) it.add("%c ${formatDuration(logEntry.took)}") }
                .joinToString(separator = " ")

        internal fun cssTitle(logEntry: LogEntry): Array<String> =
            mutableListOf(
                "background-color: #DEDEDE; color: black; text-shadow: black 1px 0 10px; font-weight: lighter;", // "base"
                cssTitle(logEntry.action),       // title
            )
                .also { if (showTimestamp) it.add("color: gray; font-weight: lighter;") } // timestamp
                .also { if (showDuration) it.add("color: gray; font-weight: lighter;") } // duration
                .toTypedArray()
    }

    /**
     * Here the internal should actually be private..
     */
    internal class LogEntry(
        val started: Instant,
        val prevState: GoonState,
        val action: GoonAction,
        val nextState: GoonState,
        val collapsed: Boolean,
        val error: Throwable?,
        ended: Instant,
    ) {
        val took: Duration = ended.minus(started)
        var diff: JsonElement? = null
    }

    fun create(
        predicate: (state: GoonState, GoonAction) -> Boolean = { _, _ -> true },
        collapsed: (state: GoonState, action: GoonAction) -> Boolean = { _, _ -> false },

        logger: GoonConsole = goonConsole,
        logErrors: Boolean = true,
        diff: Boolean = true,
        diffPredicate: (state: GoonState, GoonAction) -> Boolean = { _, _ -> true },

        formatting: ReduxLoggerFormatting = ReduxLoggerFormatting(),
    ): Middleware<GoonState> {
        return middlewareTyped { store, next, action: GoonAction ->
            // https://github.com/LogRocket/redux-logger/blob/master/src/index.js

            val prevState = store.state

            if (!predicate(prevState, action)) return@middlewareTyped next(action)

            val started = Clock.System.now()
            val returnedValue: Either<Throwable, Any> =
                if (!logErrors) next(action).right()
                else eitherFrom { next(action) }
            val ended = Clock.System.now()

            val logEntry = LogEntry(
                started = started,
                ended = ended,
                prevState = prevState,
                action = action,
                nextState = store.state,
                collapsed = collapsed(prevState, action),
                error = returnedValue.leftOrNull(),
            )

            if (diff && diffPredicate(store.state, action)) {
                logEntry.diff = Differ.diff(
                    Gw2Json.json.encodeToJsonElement(logEntry.prevState),
                    Gw2Json.json.encodeToJsonElement(logEntry.nextState),
                )
            }

            printLogEntry(logEntry, formatting, logger)

            returnedValue.fold({ throw it }, { it })
        }
    }

    private fun printLogEntry(
        logEntry: LogEntry,
        formatting: ReduxLoggerFormatting,
        logger: GoonConsole = goonConsole,
    ) {
        // https://github.com/LogRocket/redux-logger/blob/master/src/core.js#L39

        val action = logEntry.action
        // val formattedAction = actionTransformer(action)
        val isCollapsed = logEntry.collapsed

        val bl = BiList<String>()

        // TITLE
        if (isCollapsed) logger.groupCollapsed(formatting.formatTitle(logEntry), *formatting.cssTitle(logEntry))
        else logger.group(formatting.formatTitle(logEntry), *formatting.cssTitle(logEntry))

        formatting.levelPrevState(action)?.also { level ->
            level.log(logger).log(
                "%c prev state",
                "${formatting.cssPrevState}; font-weight: bold",
                formatting.transformState(logEntry.prevState),
            )
        }

        formatting.levelAction(action)?.also { level ->
            level.log(logger).log(
                "%c action",
                "${formatting.cssAction}; font-weight: bold",
                formatting.transformAction(logEntry.action),
            )
        }

        logEntry.error?.also { error ->
            formatting.levelError(action)?.also { level ->
                level.log(logger).log(
                    "%c error",
                    "${formatting.cssError}; font-weight: bold",
                    formatting.transformError(error),
                )
            }
        }

        formatting.levelNextState(action)?.also { level ->
            level.log(logger).log(
                "%c next state",
                "${formatting.cssNextState}; font-weight: bold",
                formatting.transformState(logEntry.nextState),
            )
        }

        logEntry.diff?.also { diff ->
            formatting.levelDiff(action)?.also { level ->
                level.log(logger).log(
                    "%c diff",
                    "${formatting.cssNextState}; font-weight: bold",
                    JSON.parse(diff.toString()),
                )
            }
        }


        logger.groupEnd()
    }


}
