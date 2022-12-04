package com.zar.core.tools

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import com.zar.core.R
import com.zar.core.enums.EnumErrorType
import com.zar.core.tools.api.interfaces.RemoteErrorEmitter
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class BiometricTools @Inject constructor(
    @ApplicationContext private val context: Context,
    private val emitter: RemoteErrorEmitter) {

    //---------------------------------------------------------------------------------------------- checkDeviceHasBiometric
    fun checkDeviceHasBiometric(biometricPrompt : BiometricPrompt){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
            return

        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricDialog(biometricPrompt)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                emitter.onError(
                    EnumErrorType.UNKNOWN,
                    context.getString(R.string.biometricNoHardware)
                )
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                context.startActivity(enrollIntent)
            }
            else -> {

            }
        }
    }
    //---------------------------------------------------------------------------------------------- checkDeviceHasBiometric



    //---------------------------------------------------------------------------------------------- showBiometricDialog
    private fun showBiometricDialog(biometricPrompt : BiometricPrompt) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("ورود با اثر انگشت")
            .setSubtitle("نرم افزار جامع گروه صنعتی زر")
            .setNegativeButtonText("انصراف")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
    //---------------------------------------------------------------------------------------------- showBiometricDialog


}