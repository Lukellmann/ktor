/*
 * Copyright 2014-2023 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.server.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import kotlin.jvm.*

/**
 * Builds a route to match the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * route(Regex("/(?<number>\\d+)")) {
 *     get("/hello") {
 *         val number = call.parameters["number"]
 *         ...
 *     }
 * }
 * ```
 */
@KtorDsl
public fun Route.route(path: Regex, build: Route.() -> Unit): Route = createRouteFromRegexPath(path).apply(build)

/**
 * Builds a route to match the specified HTTP [method] and regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * route(Regex("/(?<name>.+)/hello"), HttpMethod.Get) {
 *     handle {
 *         val name = call.parameters["name"]
 *         ...
 *     }
 * }
 * ```
 */
@KtorDsl
public fun Route.route(path: Regex, method: HttpMethod, build: Route.() -> Unit): Route {
    val selector = HttpMethodRouteSelector(method)
    return createRouteFromRegexPath(path).createChild(selector).apply(build)
}

/**
 * Builds a route to match `GET` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * get(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.get(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Get) { handle(body) }
}

/**
 * Builds a route to match `POST` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * post(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.post(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Post) { handle(body) }
}

/**
 * Builds a route to match `POST` requests with the specified regex [path] receiving a request body as content of the [R] type.
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * post<String>(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
@JvmName("postTypedPath")
public inline fun <reified R : Any> Route.post(
    path: Regex,
    crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(R) -> Unit
): Route = post(path) {
    body(call.receive())
}

/**
 * Builds a route to match `HEAD` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * head(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.head(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Head) { handle(body) }
}

/**
 * Builds a route to match `PUT` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * put(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.put(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Put) { handle(body) }
}

/**
 * Builds a route to match `PUT` requests with the specified regex [path] receiving a request body as content of the [R] type.
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * put<String>(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
@JvmName("putTypedPath")
public inline fun <reified R : Any> Route.put(
    path: Regex,
    crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(R) -> Unit
): Route = put(path) {
    body(call.receive())
}

/**
 * Builds a route to match `PATCH` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * patch(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.patch(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Patch) { handle(body) }
}

/**
 * Builds a route to match `PATCH` requests with the specified regex [path] receiving a request body as content of the [R] type.
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * patch<String>(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
@JvmName("patchTypedPath")
public inline fun <reified R : Any> Route.patch(
    path: Regex,
    crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(R) -> Unit
): Route = patch(path) {
    body(call.receive())
}

/**
 * Builds a route to match `DELETE` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * delete(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.delete(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Delete) { handle(body) }
}

/**
 * Builds a route to match `OPTIONS` requests with the specified regex [path].
 * Named parameters from regex can be accessed via [ApplicationCall.parameters].
 *
 * Example:
 * ```
 * options(Regex("/(?<name>.+)/hello")) {
 *     val name = call.parameters["name"]
 *     ...
 * }
 * ```
 */
@KtorDsl
public fun Route.options(path: Regex, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return route(path, HttpMethod.Options) { handle(body) }
}

private fun Route.createRouteFromRegexPath(regex: Regex): Route {
    return this.createChild(PathSegmentRegexRouteSelector(regex))
}

public class PathSegmentRegexRouteSelector(private val regex: Regex) : RouteSelector() {

    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation {
        val prefix = if (regex.pattern.startsWith('/')) "/" else ""
        val postfix = if (regex.pattern.endsWith('/')) "/" else ""
        val pathSegments = context.segments.drop(segmentIndex).joinToString("/", prefix, postfix)
        val result = regex.find(pathSegments) ?: return RouteSelectorEvaluation.Failed

        val segmentIncrement = result.value.length.let { consumedLength ->
            if (pathSegments.length == consumedLength) {
                context.segments.size - segmentIndex
            } else if (pathSegments[consumedLength] == '/') {
                val segments = result.value.substring(0, consumedLength)
                val count = segments.count { it == '/' }
                if (prefix == "/") count else count + 1
            } else {
                return RouteSelectorEvaluation.Failed
            }
        }

        val groups = result.groups as MatchNamedGroupCollection
        val parameters = Parameters.build {
            GROUP_NAME_MATCHER.findAll(regex.pattern).forEach { matchResult ->
                val (name) = matchResult.destructured
                val value = groups[name]?.value ?: ""
                append(name, value)
            }
        }
        return RouteSelectorEvaluation.Success(
            quality = RouteSelectorEvaluation.qualityQueryParameter,
            parameters = parameters,
            segmentIncrement = segmentIncrement
        )
    }

    override fun toString(): String = "Regex(${regex.pattern})"

    public companion object {
        private val GROUP_NAME_MATCHER = Regex("\\(\\?<(\\p{Alpha}\\p{Alnum}*)>[^)]*\\)")
    }
}
