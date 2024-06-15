package com.manoj.matrixgesturedetectors

import android.graphics.Matrix
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import kotlin.math.atan2
import kotlin.math.sqrt

class MatrixGestureDetector(private val mListener: OnMatrixChangeListener?) {
    interface OnMatrixChangeListener {
        fun onMatrixChanged(matrix: Matrix?)
    }



    private var mode = NONE
    private val mMatrixValues = FloatArray(9)
    private val mCurrentMatrix = Matrix()
    private val mStartMatrix = Matrix()
    private val mStartPoint = PointF()
    private var mStartDist = 0f
    private var mStartAngle = 0f
    private val midPoint = PointF()
    fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    mode = DRAG
                    mStartPoint[event.x] = event.y
                    mStartMatrix.set(mCurrentMatrix)
                    logMatrix("ACTION_DOWN", mStartMatrix)
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    mode = ZOOM
                    mStartDist = distance(event)
                    mStartAngle = rotation(event)
                    if (mStartDist > 10f) {
                        mStartMatrix.set(mCurrentMatrix)
                        midPoint(midPoint, event)
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (mode == DRAG) {
                        val dx = event.x - mStartPoint.x
                        val dy = event.y - mStartPoint.y
                        mCurrentMatrix.set(mStartMatrix)
                        mCurrentMatrix.postTranslate(dx, dy)
                        notifyMatrixChange()
                    } else if (mode == ZOOM) {
                        val newDist = distance(event)
                        if (newDist > 10f) {
                            mCurrentMatrix.set(mStartMatrix)
                            val scale = newDist / mStartDist
                            mCurrentMatrix.postScale(scale, scale, midPoint.x, midPoint.y)
                        }
                        val newAngle = rotation(event)
                        val deltaAngle = newAngle - mStartAngle
                        mCurrentMatrix.postRotate(deltaAngle, midPoint.x, midPoint.y)
                        notifyMatrixChange()
                    }
                    logMatrix("ACTION_MOVE", mCurrentMatrix)
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mode = NONE
                    logMatrix("ACTION_UP/ACTION_POINTER_UP", mCurrentMatrix)
                }
            }


        return true
    }

    private fun notifyMatrixChange() {
        mListener?.onMatrixChanged(mCurrentMatrix)
    }

    private fun distance(event: MotionEvent): Float {
        val dx = event.getX(0) - event.getX(1)
        val dy = event.getY(0) - event.getY(1)
        return sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    private fun rotation(event: MotionEvent): Float {
        val deltaX = (event.getX(0) - event.getX(1)).toDouble()
        val deltaY = (event.getY(0) - event.getY(1)).toDouble()
        val radians = atan2(deltaY, deltaX)
        return Math.toDegrees(radians).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }

    fun setMatrix(matrix: Matrix?) {
        mCurrentMatrix.set(matrix)
    }

    private fun logMatrix(tag: String, matrix: Matrix) {
        matrix.getValues(mMatrixValues)
        Log.d("MatrixGestureDetector", tag + " - Matrix: " + mMatrixValues.contentToString())
    }

    companion object {
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2

    }

}
