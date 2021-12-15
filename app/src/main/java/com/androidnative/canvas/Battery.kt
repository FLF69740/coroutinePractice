package com.androidnative.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Battery : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mPaintStroke = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 8F
    }

    private val mPaintFill = Paint().apply {
        isAntiAlias = true
        color = Color.rgb(250,0,0)
        style = Paint.Style.FILL
    }

    private var mLevel: Float = 0F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val height = this.height.toFloat()
        val width = this.width.toFloat()

        val p1x = width*(3F/8F)
        val p1y = height*(1F/6F)
        val p2x = width*(5F/8F)
        val p2y = height*(69F/80F)

        val p3x = width*(5F/16F)
        val p3y = height*(5F/44F)
        val p4x = width*(11F/16F)
        val p4y = height*(10F/11F)

        val p5x = width*(7F/16F)
        val p5y = height*(2F/44F)
        val p6x = width*(9F/16F)
        val p6y = height*(3F/44F)

        drawBottle(canvas, p1x, p1y, p2x, p2y)
        drawSkin(canvas, p3x, p3y, p4x, p4y)
        drawDot(canvas, p5x, p5y, p6x, p6y)
        drawLiquid(canvas, p1x, getLiquidLevel(p1y, p2y, mLevel), p2x, p2y)
    }

    private fun drawLiquid(canvas: Canvas?, p1x: Float, p1y: Float, p2x: Float, p2y: Float) {
        canvas?.drawRect(p1x, p1y, p2x, p2y, mPaintFill)
    }

    private fun drawBottle(canvas: Canvas?, p1x: Float, p1y: Float, p2x: Float, p2y: Float){
        mPaintStroke.strokeWidth = 8F
        canvas?.drawRect( p1x - 8F, p1y - 8F, p2x + 8F, p2y + 8F, mPaintStroke)
    }

    private fun drawSkin(canvas: Canvas?, p1x: Float, p1y: Float, p2x: Float, p2y: Float){
        mPaintStroke.strokeWidth = 20F
        canvas?.drawRect(p1x, p1y, p2x, p2y, mPaintStroke)
    }

    private fun drawDot(canvas: Canvas?, p1x: Float, p1y: Float, p2x: Float, p2y: Float){
        canvas?.drawRect(p1x, p1y, p2x, p2y, mPaintStroke)
    }

    private fun getLiquidLevel(p1: Float, p2: Float, ratio: Float): Float = p2 - ((p2 - p1) * ratio / 100)

    /**
     *  ANIMATION
     */

    fun defineLevel(level: Float){
        mLevel = level
        mPaintFill.color = when(level.toInt()) {
            in 1..50 -> Color.rgb(250, defineFirstMiddleGreenColor(level), 0)
            in 51..100 -> Color.rgb(defineSecondMiddleRedColor(level), 150,0)
            else -> Color.rgb(250,0,0)
        }
        invalidate()
    }

    fun getLevel():Float = mLevel

    private fun defineFirstMiddleGreenColor(level: Float): Int{
        return 300 * level.toInt() / 100
    }

    private fun defineSecondMiddleRedColor(level: Float): Int{
        return 500 - (300 * (level.toInt() - 50) / 100)
    }

}