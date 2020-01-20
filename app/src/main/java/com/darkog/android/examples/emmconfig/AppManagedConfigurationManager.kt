package com.darkog.android.examples.emmconfig

import android.content.Context
import android.content.RestrictionEntry
import android.content.RestrictionsManager
import android.os.Build
import android.os.Bundle
import android.os.UserManager


class AppManagedConfigurationManager(context: Context) {

    private val context: Context = context.applicationContext
    private val restrictionsManager = context.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager
    private lateinit var entriesMap: Map<String, RestrictionEntry>
    private lateinit var applicationRestrictions: Bundle

    fun updateValues() {
        entriesMap = restrictionsManager.getManifestRestrictions(context.packageName).associateBy { it.key }
        applicationRestrictions = restrictionsManager.applicationRestrictions
    }

    fun isDeviceManaged(): Boolean = !restrictionsManager.applicationRestrictions.isEmpty

    fun isMissingConfigurations(): Boolean {
        if (!KEY_RESTRICTIONS_PENDING_SUPPORTED) {
            restrictionsManager.applicationRestrictions.let {
                if (it.containsKey(UserManager.KEY_RESTRICTIONS_PENDING)) {
                    return it.getBoolean(UserManager.KEY_RESTRICTIONS_PENDING)
                }
            }
        }
        return false
    }

    fun canIncrementNumber() = resolveBoolean(KEY_CAN_INCREMENT_NUMBER)
    fun number() = resolveInt(KEY_NUMBER)
    fun welcomeMessage() = resolveString(KEY_WELCOME_MESSAGE)
    fun username() = resolveString(KEY_USERNAME)
    fun certificateAlias() = resolveString(KEY_CERTIFICATE_ALIAS)
    fun color() = resolveString(KEY_COLORS)
    fun userLevel() = resolveMulti(KEY_USER_ROLE)
    fun secretCode() = resolveString(KEY_SECRET_CODE)
    fun bookmarks(): List<Bookmark> {
        if (!isBundleSupported() || !applicationRestrictions.containsKey(KEY_BOOKMARK_LIST)) {
            return emptyList()
        }


        return applicationRestrictions.getParcelableArray(KEY_BOOKMARK_LIST)
                ?.map { it as Bundle }
                ?.toList()
                ?.map { Bookmark(it.getString(KEY_BOOKMARK_NAME).orEmpty(), it.getString(KEY_BOOKMARK_VALUE).orEmpty()) }
                .orEmpty()
    }

    /**
     * This is just to show the typical full process of extracting a value for a key.
     */
    private fun welcomeMessageInASingleMethod(key: String = KEY_WELCOME_MESSAGE): String {
        val restrictionsManager = context.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager
        val entry = restrictionsManager.getManifestRestrictions(context.packageName).first { key == it.key }
        val applicationRestrictions = restrictionsManager.applicationRestrictions
        return if (applicationRestrictions.containsKey(key)) {
            applicationRestrictions.getString(key).orEmpty()
        } else {
            entry.selectedString
        }
    }

    private fun resolveMulti(key: String) = resolve(key, { it.getStringArrayList(key) }, { it.allSelectedStrings.toList() })
    private fun resolveString(key: String) = resolve(key, { it.getString(key) }, { it.selectedString })
    private fun resolveInt(key: String) = resolve(key, { it.getInt(key) }, { it.intValue })
    private fun resolveBoolean(key: String) = resolve(key, { it.getBoolean(key) }, { it.selectedState })

    private fun <T> resolve(key: String, fromBundle: (bundle: Bundle) -> T, fromEntries: (entry: RestrictionEntry) -> T): T {
        return if (applicationRestrictions.containsKey(key)) {
            applicationRestrictions.get(key) as T
        } else {
            fromEntries(entriesMap.getValue(key))
        }
    }

    companion object {
        private val BUNDLE_SUPPORTED = Build.VERSION.SDK_INT >= 23
        private val KEY_RESTRICTIONS_PENDING_SUPPORTED = Build.VERSION.SDK_INT >= 22

        private const val KEY_CAN_INCREMENT_NUMBER = "can_increment_number"
        private const val KEY_WELCOME_MESSAGE = "welcome_message"
        private const val KEY_NUMBER = "number"
        private const val KEY_COLORS = "colors"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_SECRET_CODE = "secret_code"
        private const val KEY_BOOKMARK_LIST = "bookmark_list"
        private const val KEY_BOOKMARK = "bookmark"
        private const val KEY_BOOKMARK_NAME = "bookmark_name"
        private const val KEY_BOOKMARK_VALUE = "bookmark_value"

        private const val KEY_USERNAME = "username"
        private const val KEY_CERTIFICATE_ALIAS = "certificate_alias"

        fun isBundleSupported(): Boolean {
            return BUNDLE_SUPPORTED
        }

    }
}

