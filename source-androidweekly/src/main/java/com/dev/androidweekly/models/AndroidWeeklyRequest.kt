package com.dev.androidweekly.models

import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest

class AndroidWeeklyRequest(name: String) : ServiceRequest(DataSource.ANDROID_WEEKLY, name) {
    var latestIssue = 0
}