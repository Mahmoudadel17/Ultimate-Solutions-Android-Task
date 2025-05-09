package com.example.ultimate.di

import android.app.Activity
import android.app.Application
import android.os.Handler
import android.os.Looper
import com.example.ultimate.utils.SessionManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {}
