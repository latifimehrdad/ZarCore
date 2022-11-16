package com.zar.core.tools.manager

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Window
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DialogManager(@ApplicationContext private val context: Context) {

    //---------------------------------------------------------------------------------------------- createDialog
    @Provides
    @Singleton
    fun createDialog(layout: Int, verticalGravity: Int, verticalMargin : Int): Dialog {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 50)
        window!!.setBackgroundDrawable(inset)
        lp.copyFrom(window.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = verticalGravity
        lp.horizontalMargin = 50f
        lp.y = verticalMargin
        window.attributes = lp
        return dialog
    }
    //---------------------------------------------------------------------------------------------- createDialog


}