package com.dev.kotlinweekly.models

import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest

class KotlinWeeklyRequest(name: String) : ServiceRequest(DataSource.KOTLIN_WEEKLY, name) {
    var latestIssue = 0
}