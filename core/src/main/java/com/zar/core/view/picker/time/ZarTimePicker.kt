package com.zar.core.view.picker.time

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.zar.core.R
import com.zar.core.view.picker.time.Utils.Companion.angleBetweenVectors
import com.zar.core.view.picker.time.Utils.Companion.angleToMins
import com.zar.core.view.picker.time.Utils.Companion.snapMinutes
import com.zar.core.view.picker.time.Utils.Companion.to_0_720
import java.time.LocalTime
import java.util.*
import kotlin.math.*


class ZarTimePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    init {
        init(context, attrs)
    }

    enum class PickerMode {
        TIME,
        DEPARTURE,
        RETURN,
        RETURNING
    }

    private lateinit var progressPaint: Paint
    private lateinit var progressBackgroundPaint: Paint
    private var progressTopBlurPaint: Paint? = null
    private var progressBottomBlurPaint: Paint? = null
    private lateinit var divisionPaint: Paint
    private lateinit var textPaint: Paint
    private var divisionOffset = 0
    private var labelOffset = 0
    private var divisionLength = 0
    private var divisionWidth = 0
    private val hourLabels = listOf(12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    private lateinit var circleBounds: RectF
    private var radius: Float = 0F
    private var center = Point(0, 0)
    private var progressBottomShadowSize = 0
    private var progressTopShadowSize = 0
    private var strokeBottomShadowColor = Color.TRANSPARENT
    private var strokeTopShadowColor = Color.TRANSPARENT
    private var labelColor = Color.WHITE
    private lateinit var timeLayout: View
    private lateinit var departureLayout: View
    private lateinit var returnLayout: View
    private var departureAngle = 30.0
    private var returnAngle = 225.0
    private var draggingDeparture = false
    private var draggingReturn = false
    private val stepMinutes = 15
    private val textRect = Rect()
    private var timeLayoutId = 0
    private var departureLayoutId = 0
    private var returnLayoutId = 0

    var listener: ((departureTime: LocalTime, returnTime: LocalTime) -> Unit)? = null


    fun getDepartureTime() = computeDepartureTime()

    fun getReturnTime() = computeReturnTime()

    fun setTime(departureTime: LocalTime, returnTime: LocalTime, pickerMode: PickerMode) {
        departureAngle = Utils.minutesToAngle(departureTime.hour * 60 + departureTime.minute)
        returnAngle = Utils.minutesToAngle(returnTime.hour * 60 + returnTime.minute)
        Companion.pickerMode = pickerMode
        if (pickerMode == PickerMode.TIME) {
            removeView(departureLayout)
            departureLayout = timeLayout
            addView(departureLayout)
        }
        invalidate()
        notifyChanges()
    }



    private fun init(context: Context, attrs: AttributeSet?) {

        divisionOffset = dp2px(DEFAULT_DIVISION_OFFSET_DP)
        divisionLength = dp2px(DEFAULT_DIVISION_LENGTH_DP)
        divisionWidth = dp2px(DEFAULT_DIVISION_WIDTH_DP)
        labelOffset = dp2px(DEFAULT_LABEL_OFFSET_DP)
        var progressColor = Color.WHITE
        var progressBackgroundColor = Color.parseColor(DEFAULT_PROGRESS_BACKGROUND_COLOR)
        var divisionColor = Color.parseColor(DEFAULT_PROGRESS_BACKGROUND_COLOR)
        var progressStrokeWidth = dp2px(DEFAULT_STROKE_WIDTH_DP)
        var progressBgStrokeWidth = dp2px(DEFAULT_STROKE_WIDTH_DP)
        var progressStrokeCap: Paint.Cap = Paint.Cap.ROUND

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.TimePicker)

            timeLayoutId = a.getResourceId(R.styleable.TimePicker_timeLayoutId, 0)
            departureLayoutId = a.getResourceId(R.styleable.TimePicker_departureLayoutId, 0)
            returnLayoutId = a.getResourceId(R.styleable.TimePicker_returnLayoutId, 0)

            progressColor = a.getColor(R.styleable.TimePicker_progressColor, progressColor)
            progressBackgroundColor =
                a.getColor(
                    R.styleable.TimePicker_progressBackgroundColor,
                    progressBackgroundColor
                )
            divisionColor = a.getColor(R.styleable.TimePicker_divisionColor, divisionColor)
            progressStrokeWidth =
                a.getDimensionPixelSize(
                    R.styleable.TimePicker_progressStrokeWidth,
                    progressStrokeWidth
                )
            progressBottomShadowSize =
                a.getDimensionPixelSize(R.styleable.TimePicker_strokeBottomShadowRadius, 0)
            progressTopShadowSize =
                a.getDimensionPixelSize(R.styleable.TimePicker_strokeTopShadowRadius, 0)
            progressBgStrokeWidth =
                a.getDimensionPixelSize(
                    R.styleable.TimePicker_progressBgStrokeWidth,
                    progressStrokeWidth
                )
            strokeBottomShadowColor =
                a.getColor(R.styleable.TimePicker_strokeBottomShadowColor, progressColor)
            strokeTopShadowColor =
                a.getColor(R.styleable.TimePicker_strokeTopShadowColor, progressColor)
            labelColor = a.getColor(R.styleable.TimePicker_labelColor, progressColor)
            labelColor = a.getColor(R.styleable.TimePicker_labelColor, progressColor)

            progressStrokeCap = Paint.Cap.ROUND

            a.recycle()
        }

        progressPaint = Paint()
        progressPaint.strokeCap = progressStrokeCap
        progressPaint.strokeWidth = progressStrokeWidth.toFloat()
        progressPaint.style = Paint.Style.STROKE
        progressPaint.color = progressColor
        progressPaint.isAntiAlias = true

        progressBackgroundPaint = Paint()
        progressBackgroundPaint.style = Paint.Style.STROKE
        progressBackgroundPaint.strokeWidth = progressBgStrokeWidth.toFloat()
        progressBackgroundPaint.color = progressBackgroundColor
        progressBackgroundPaint.isAntiAlias = true

        if (progressTopShadowSize > 0) {
            progressTopBlurPaint = Paint()
            progressTopBlurPaint!!.strokeCap = Paint.Cap.ROUND
            progressTopBlurPaint!!.strokeWidth =
                BLUR_STROKE_RATIO * (progressTopShadowSize + progressStrokeWidth)
            progressTopBlurPaint!!.style = Paint.Style.STROKE
            progressTopBlurPaint!!.isAntiAlias = true
            val topBlurRadius = BLUR_RADIUS_RATIO * (progressTopShadowSize + progressBgStrokeWidth)
            progressTopBlurPaint!!.maskFilter =
                BlurMaskFilter(topBlurRadius, BlurMaskFilter.Blur.NORMAL)
            progressTopBlurPaint!!.color = strokeTopShadowColor
        }

        if (progressBottomShadowSize > 0) {
            progressBottomBlurPaint = Paint(0)
            progressBottomBlurPaint!!.strokeCap = Paint.Cap.ROUND
            progressBottomBlurPaint!!.strokeWidth =
                BLUR_STROKE_RATIO * (progressBottomShadowSize + progressStrokeWidth)
            progressBottomBlurPaint!!.style = Paint.Style.STROKE
            progressBottomBlurPaint!!.isAntiAlias = true
            val bottomBlurRadius =
                BLUR_RADIUS_RATIO * (progressBottomShadowSize + progressBgStrokeWidth)
            progressBottomBlurPaint!!.maskFilter =
                BlurMaskFilter(bottomBlurRadius, BlurMaskFilter.Blur.NORMAL)
            progressBottomBlurPaint!!.color = strokeBottomShadowColor
        }

        divisionPaint = Paint(0)
        divisionPaint.strokeCap = Paint.Cap.BUTT
        divisionPaint.strokeWidth = divisionWidth.toFloat()
        divisionPaint.color = divisionColor
        divisionPaint.style = Paint.Style.STROKE
        divisionPaint.isAntiAlias = true

        textPaint = Paint()
        textPaint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, SCALE_LABEL_TEXT_SIZE,
            resources.displayMetrics
        )
        textPaint.color = labelColor

        val inflater = LayoutInflater.from(context)
        timeLayout = inflater.inflate(timeLayoutId, this, false)
        departureLayout = inflater.inflate(departureLayoutId, this, false)
        returnLayout = inflater.inflate(returnLayoutId, this, false)

        when(pickerMode) {
            PickerMode.DEPARTURE,
            PickerMode.TIME-> addView(departureLayout)
            PickerMode.RETURN -> addView(returnLayout)
            PickerMode.RETURNING -> {
                addView(departureLayout)
                addView(returnLayout)
            }
        }

        circleBounds = RectF()

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val smallestSide = measuredWidth.coerceAtMost(measuredHeight)
        setMeasuredDimension(smallestSide, smallestSide)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        calculateBounds(w, h)
        requestLayout()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        when(pickerMode) {
            PickerMode.DEPARTURE,
            PickerMode.TIME-> layoutView(departureLayout, departureAngle)
            PickerMode.RETURN -> layoutView(returnLayout, returnAngle)
            PickerMode.RETURNING -> {
                layoutView(departureLayout, departureAngle)
                layoutView(returnLayout, returnAngle)
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isTouchOnView(departureLayout, ev)) {
                draggingDeparture = true
                return true
            }
            if (isTouchOnView(returnLayout, ev)) {
                draggingReturn = true
                return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val touchAngleRad = atan2(center.y - y, x - center.x).toDouble()
                if (draggingDeparture) {
                    val departureAngleRad = Math.toRadians(departureAngle)
                    val diff = Math.toDegrees(angleBetweenVectors(departureAngleRad, touchAngleRad))
                    departureAngle = to_0_720(departureAngle + diff)
                    requestLayout()
                    notifyChanges()
                    return true
                } else if (draggingReturn) {
                    val returnAngleRad = Math.toRadians(returnAngle)
                    val diff = Math.toDegrees(angleBetweenVectors(returnAngleRad, touchAngleRad))
                    returnAngle = to_0_720(returnAngle + diff)
                    requestLayout()
                    notifyChanges()
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                draggingDeparture = false
                draggingReturn = false
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun onDraw(canvas: Canvas) {
        drawProgressBackground(canvas)
        drawProgress(canvas)
        drawDivisions(canvas)
    }

    private fun notifyChanges() {
        val computeBedTime = computeDepartureTime()
        val computeReturnTime = computeReturnTime()
        listener?.invoke(computeBedTime, computeReturnTime)
    }

    private fun computeDepartureTime(): LocalTime {
        val departureMin = snapMinutes(angleToMins(departureAngle), stepMinutes)
        return LocalTime.of((departureMin / 60) % 24, departureMin % 60)
    }

    private fun computeReturnTime(): LocalTime {
        val returnMin = snapMinutes(angleToMins(returnAngle), stepMinutes)
        return LocalTime.of((returnMin / 60) % 24, returnMin % 60)
    }

    private fun layoutView(view: View, angle: Double) {
        val measuredWidth = view.measuredWidth
        val measuredHeight = view.measuredHeight
        val halfWidth = measuredWidth / 2
        val halfHeight = measuredHeight / 2
        val parentCenterX = width / 2
        val parentCenterY = height / 2
        val centerX = (parentCenterX + radius * cos(Math.toRadians(angle))).toInt()
        val centerY = (parentCenterY - radius * sin(Math.toRadians(angle))).toInt()
        view.layout(
            (centerX - halfWidth),
            centerY - halfHeight,
            centerX + halfWidth,
            centerY + halfHeight
        )
    }

    private fun calculateBounds(w: Int, h: Int) {

        val maxChildWidth = max(departureLayout.measuredWidth, returnLayout.measuredWidth)
        val maxChildHeight = max(departureLayout.measuredHeight, returnLayout.measuredHeight)


        val maxChildSize = max(maxChildWidth, maxChildHeight)
        val offset = abs(progressBackgroundPaint.strokeWidth / 2 - maxChildSize / 2)
        val width = w - paddingStart - paddingEnd - maxChildSize - offset
        val height = h - paddingTop - paddingBottom - maxChildSize - offset

        radius = min(width, height) / 2F
        center = Point(w / 2, h / 2)

        circleBounds.left = center.x - radius
        circleBounds.top = center.y - radius
        circleBounds.right = center.x + radius
        circleBounds.bottom = center.y + radius
    }

    private fun isTouchOnView(view: View, ev: MotionEvent): Boolean {
        return (ev.x > view.left && ev.x < view.right
                && ev.y > view.top && ev.y < view.bottom)
    }


    private fun drawProgressBackground(canvas: Canvas) {
        canvas.drawArc(
            circleBounds, ANGLE_START_PROGRESS_BACKGROUND.toFloat(),
            ANGLE_END_PROGRESS_BACKGROUND.toFloat(),
            false, progressBackgroundPaint
        )
    }

    private fun drawProgress(canvas: Canvas) {
        if (pickerMode == PickerMode.RETURNING) {
            val startAngle = -departureAngle.toFloat()
            val sweep = Utils.to_0_360(departureAngle - returnAngle).toFloat()
            progressBottomBlurPaint?.let {
                canvas.drawArc(circleBounds, startAngle, sweep, false, it)
            }
            progressTopBlurPaint?.let {
                canvas.drawArc(circleBounds, startAngle, sweep, false, it)
            }
            canvas.drawArc(circleBounds, startAngle, sweep, false, progressPaint)
        }
    }

    private fun drawDivisions(canvas: Canvas) {
        val divisionAngle = 360 / hourLabels.size
        hourLabels.forEachIndexed { index, value ->
            val angle = (divisionAngle * index) - 90
            val radians = Math.toRadians(angle.toDouble())
            val bgStrokeWidth = progressBackgroundPaint.strokeWidth
            val startX = center.x + (radius - bgStrokeWidth / 2 - divisionOffset) * cos(radians)
            val endX =
                center.x + (radius - bgStrokeWidth / 2 - divisionOffset - divisionLength) * cos(
                    radians
                )
            val startY = center.y + (radius - bgStrokeWidth / 2 - divisionOffset) * sin(radians)
            val endY =
                center.y + (radius - bgStrokeWidth / 2 - divisionOffset - divisionLength) * sin(
                    radians
                )
            canvas.drawLine(
                startX.toFloat(),
                startY.toFloat(),
                endX.toFloat(),
                endY.toFloat(),
                divisionPaint
            )

            val customTypeface = resources.getFont(R.font.kalameh_medium)
            val tmp = value.toString()
            textPaint.getTextBounds(tmp, 0, tmp.length, textRect)
            textPaint.typeface = customTypeface
            val x =
                center.x + (radius - bgStrokeWidth / 2 - labelOffset) * cos(radians) - textRect.width() / 2
            val y =
                (center.y + (radius - bgStrokeWidth / 2 - labelOffset) * sin(radians) + textRect.height() / 2)
            canvas.drawText(tmp, x.toFloat(), y.toFloat(), textPaint)
        }
    }

    private fun dp2px(dp: Float): Int {
        val metrics = resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
    }

    companion object {
        var pickerMode = PickerMode.RETURNING
        private const val ANGLE_START_PROGRESS_BACKGROUND = 0
        private const val ANGLE_END_PROGRESS_BACKGROUND = 360
        private const val DEFAULT_STROKE_WIDTH_DP = 8F
        private const val DEFAULT_DIVISION_LENGTH_DP = 8F
        private const val DEFAULT_DIVISION_OFFSET_DP = 12F
        private const val DEFAULT_LABEL_OFFSET_DP = 36F
        private const val DEFAULT_DIVISION_WIDTH_DP = 2F
        private const val SCALE_LABEL_TEXT_SIZE = 13F
        private const val DEFAULT_PROGRESS_BACKGROUND_COLOR = "#e0e0e0"
        private const val BLUR_STROKE_RATIO = 3 / 8F
        private const val BLUR_RADIUS_RATIO = 1 / 4F
    }
}
