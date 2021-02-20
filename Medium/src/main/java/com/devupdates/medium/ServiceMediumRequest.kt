package com.devupdates.medium

import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest

class ServiceMediumRequest(type: DataSource,
                           var username: String): ServiceRequest(type)