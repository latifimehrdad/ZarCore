package com.zar.core.tools

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import com.zar.core.R
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class BiometricTools @Inject constructor(
    @ApplicationContext private val context: Context) {

    //---------------------------------------------------------------------------------------------- checkDeviceHasBiometric
    fun checkDeviceHasBiometric(biometricPrompt : BiometricPrompt) : String? {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricDialog(biometricPrompt)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                return context.getString(R.string.biometricNoHardware)
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                context.startActivity(enrollIntent)
                return null
            }
            else -> {
                return context.getString(R.string.biometricNoHardware)
            }
        }
        return null
    }
    //---------------------------------------------------------------------------------------------- checkDeviceHasBiometric



    //---------------------------------------------------------------------------------------------- showBiometricDialog
    private fun showBiometricDialog(biometricPrompt : BiometricPrompt) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("ورود با اثر انگشت")
            .setNegativeButtonText("انصراف")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
    //---------------------------------------------------------------------------------------------- showBiometricDialog


}