package com.zar.core.tools

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
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
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricDialog(biometricPrompt)
                null
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                context.getString(R.string.biometricNoHardware)
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                context.getString(R.string.biometricErrorNoneEnrolled)
            }

            else -> {
                context.getString(R.string.biometricNoHardware)
            }
        }
    }
    //---------------------------------------------------------------------------------------------- checkDeviceHasBiometric



    //---------------------------------------------------------------------------------------------- showBiometricDialog
    private fun showBiometricDialog(biometricPrompt : BiometricPrompt) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("ورود از طریق بایومتریک")
            .setNegativeButtonText("انصراف")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
    //---------------------------------------------------------------------------------------------- showBiometricDialog


}