package org.thechance.service_identity.domain.usecases.permission

import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.gateway.PermissionGateway

class UpdatePermissionUseCaseImp(private val permissionGateway: PermissionGateway) :
    UpdatePermissionUseCase {
    override suspend fun invoke(id: String, permission: Permission): Boolean {
        return permissionGateway.updatePermission(id, permission)
    }
}