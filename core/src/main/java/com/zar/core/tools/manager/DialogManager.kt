package com.zar.core.tools.manager

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Window
import android.view.WindowManager

class DialogManager {

    //---------------------------------------------------------------------------------------------- createDialogHeightWrapContent
    fun createDialogHeightWrapContent(
        context: Context,
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
    fun createDialogHeightMatchParent(context: Context, layout: Int, margin: Float): Dialog {
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
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.horizontalMargin = 50f
        lp.verticalMargin = margin
        window.attributes = lp
        return dialog
    }
    //---------------------------------------------------------------------------------------------- createDialogHeightMatchParent


}