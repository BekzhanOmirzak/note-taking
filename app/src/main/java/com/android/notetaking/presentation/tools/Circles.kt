package com.android.notetaking.presentation.tools

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.android.notetaking.R

class Circles(private val con: Context, private val attr: AttributeSet) : View(con, attr) {

    private var circleEdgeColor: Int = 0
    private var circleRadius = 15f
    private var usedCircleCount = 0
    private var usedCircleColor = 0

    private val circlePaint = Paint()
    private val usedCirclePaint = Paint()

    init {
        val typedArray = con.theme.obtainStyledAttributes(attr, R.styleable.Circles, 0, 0)
        try {
            circleEdgeColor = typedArray.getInteger(R.styleable.Circles_circleEdgeColor, 0)
            usedCircleCount = typedArray.getInteger(R.styleable.Circles_usedCircleCount, 0)
            usedCircleColor = typedArray.getInteger(R.styleable.Circles_usedCircleColor, 0)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        circlePaint.color = circleEdgeColor
        circlePaint.strokeWidth = 4f
        circlePaint.style = Paint.Style.STROKE
        circlePaint.isAntiAlias = true


        usedCirclePaint.color = usedCircleColor
        usedCirclePaint.style = Paint.Style.FILL
        usedCirclePaint.isAntiAlias = true

        drawCircles(canvas)
    }

    private fun drawCircles(canvas: Canvas) {
        for (i in 40..500 step 80) {
            canvas.drawCircle(i.toFloat(), 20f, circleRadius, circlePaint)
        }
    }


}