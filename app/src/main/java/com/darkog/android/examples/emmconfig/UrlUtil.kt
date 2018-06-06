package com.darkog.android.examples.emmconfig

import android.net.Uri


fun String.normalizeUrl(): String {
    if (this.isEmpty()) return this
    if (!this.startsWith("http")) {
        return "https://$this"
    } else if (this.startsWith("http://")) {
        return this.replaceFirst("http://", "https://")
    }
    return this
}