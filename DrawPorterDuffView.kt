package com.elyeproj.porterduff

import android.content.Context
import android.graphics.*
import android.os.AsyncTask
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.io.InputStream
import java.net.URL

open class DrawPorterDuffView @JvmOverloads constructor(
    context: Context,
    var attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val fullRect by lazy { Rect(0, 0, width, height) }
    private val paintDst = Paint()
    private val paintSrc = Paint()
    private var resourceImageSrc = R.drawable.pigeon
    private var resourceImageDst= R.drawable.balloon
    private var bitmapDestination:Bitmap?=null

    fun loadBitmap(url :String){
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.draw_porter_duff_attributes, 0, 0)
            resourceImageSrc = typedArray.getResourceId(
                R.styleable.draw_porter_duff_attributes_draw_porter_duff_image_src, R.drawable.pigeon)
            resourceImageDst = typedArray.getResourceId(
                R.styleable.draw_porter_duff_attributes_draw_porter_duff_image_dst, R.drawable.balloon)

            typedArray.recycle()
        }
         val task=BackgroundTask(url)
        bitmapDestination=task.execute().get() as Bitmap
    }

    fun loadBitmap(url :Bitmap){
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.draw_porter_duff_attributes, 0, 0)
            resourceImageSrc = typedArray.getResourceId(
                R.styleable.draw_porter_duff_attributes_draw_porter_duff_image_src, R.drawable.pigeon)
            resourceImageDst = typedArray.getResourceId(
                R.styleable.draw_porter_duff_attributes_draw_porter_duff_image_dst, R.drawable.balloon)
            typedArray.recycle()
        }
        bitmapDestination=url
    }

    private val bitmapSource by lazy { BitmapFactory.decodeResource(resources, resourceImageSrc) }

    var mode: PorterDuff.Mode = PorterDuff.Mode.MULTIPLY
        set(value) {
            field = value
            paintSrc.xfermode = PorterDuffXfermode(mode)
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return
        canvas.drawBitmap(bitmapDestination, null, fullRect, paintDst)
        canvas.drawBitmap(bitmapSource, null, fullRect, paintSrc)

    }

    private class BackgroundTask(val url:String)  : AsyncTask<String, Bitmap?, Bitmap>() {

        override fun onPostExecute(result: Bitmap) {
            Log.e("DrawPorterDuffView","----BackgroundTask")
            myMethod(result)
        }

        override fun doInBackground(vararg p0: String): Bitmap {
            var theBitmap:Bitmap?=null
            try{
                val stringUrl: String = url
                val inputStream: InputStream = URL(stringUrl).openStream()
                theBitmap = BitmapFactory.decodeStream(inputStream) }
            catch (e: Exception) {
                Log.e("Exception :", e.message!!)
            }
            catch (e: InterruptedException) {
                Log.e("doInBackground :", e.message!!)
            }
            return theBitmap!!

        }

        fun myMethod(myValue: Bitmap): Bitmap {
            return myValue
        }

    }

}
