package com.grit

import android.app.Application
import com.grit.data.local.GritDatabase
import com.grit.data.repository.FocusRepository
import com.grit.data.repository.SocialRepository

class GritApplication : Application() {
    val database by lazy { GritDatabase.getDatabase(this) }
    val focusRepository by lazy {
        FocusRepository(database.focusSessionDao(), database.distractionEventDao())
    }
    val socialRepository by lazy {
        SocialRepository(database.socialActivityDao())
    }
}
