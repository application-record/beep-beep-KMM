package di

import data.gateway.remote.BpLocationDataSource
import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.FakeRemoteGateway
import domain.gateway.IBpLocationDataSource
import domain.gateway.IFakeRemoteGateway
import data.gateway.remote.IdentityRemoteGateway
import domain.gateway.remote.IFakeRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::BpLocationDataSource) { bind<IBpLocationDataSource>() }
    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
}