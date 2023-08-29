package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.TaxiResource
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.util.APIs
import java.util.Locale
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import okhttp3.internal.platform.Jdk9Platform.Companion.isAvailable
import org.thechance.api_gateway.data.utils.ErrorHandler

@Single(binds = [ITaxiGateway::class])
class TaxiGateway(
    client: HttpClient,
    attributes: Attributes,

    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), ITaxiGateway {
    override suspend fun getAllTaxi(
        permissions: List<Int>,
        locale: Locale,
        page: Int, limit: Int
    ): List<TaxiResource> {
        if (!permissions.contains(ADMIN_PERMISSION)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(
                    listOf(8000),
                    locale
                )
            )
        }

        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            get("/taxi") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    override suspend fun getTaxiById(
        id: String,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/taxi/$id")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun createTaxi(
        taxi: TaxiResource,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        if (!permissions.contains(ADMIN_PERMISSION)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(listOf(8000), locale)
            )
        }
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {

            post("/taxi") {
                body = Json.encodeToString(
                    TaxiResource.serializer(),
                    TaxiResource(
                        plateNumber = taxi.plateNumber,
                        color = taxi.color,
                        type = taxi.type,
                        driverId = taxi.driverId,
                        isAvailable = taxi.isAvailable,
                        seats = taxi.seats
                    )

                )
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun editTaxi(
        id: String,
        taxi: TaxiResource,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        if (!permissions.contains(ADMIN_PERMISSION)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(listOf(8000), locale)
            )
        }
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/taxi/$id") {

                body = Json.encodeToString(
                    TaxiResource.serializer(),
                    TaxiResource(
                        id = taxi.id,
                        plateNumber = taxi.plateNumber,
                        color = taxi.color,
                        type = taxi.type,
                        driverId = taxi.driverId,
                        isAvailable = taxi.isAvailable,
                        seats = taxi.seats
                    )
                )
            }
        }
    }

    override suspend fun deleteTaxi(
        id: String,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        if (!permissions.contains(ADMIN_PERMISSION)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(listOf(8000), locale)
            )
        }
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            delete("/taxi/$id")
        }
    }
}