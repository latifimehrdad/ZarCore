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
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DialogManager @Inject constructor(@ApplicationContext private val context: Context) {

    //---------------------------------------------------------------------------------------------- createDialogHeightWrapContent
    fun createDialogHeightWrapContent(
        layout: Int,
        verticalGravity: Int,
        verticalMargin: Int
    ): Dialog {
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
    //---------------------------------------------------------------------------------------------- createDialogHeightWrapContent


    //---------------------------------------------------------------------------------------------- createDialogHeightMatchParent
    fun createDialogHeightMatchParent(layout: Int): Dialog {
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
        lp.horizontalMargin = 50f
        lp.verticalMargin = 50f
        window.attributes = lp
        return dialog
    }
    //---------------------------------------------------------------------------------------------- createDialogHeightMatchParent


}