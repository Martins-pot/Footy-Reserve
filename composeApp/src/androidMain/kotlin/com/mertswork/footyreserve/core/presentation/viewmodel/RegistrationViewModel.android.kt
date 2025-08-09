package com.mertswork.footyreserve.core.presentation.viewmodel

import android.util.Patterns

actual fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
