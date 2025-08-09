package com.mertswork.footyreserve.core.presentation.viewmodel

import platform.Foundation.NSPredicate

actual fun isValidEmail(email: String): Boolean {
    val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
    val predicate = NSPredicate.predicateWithFormat("SELF MATCHES %@", emailRegex)
    return predicate.evaluateWithObject(email)
}