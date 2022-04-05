package com.elyeproj.porterduff

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CompoundButton
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*
import android.os.StrictMode




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val draw_porter_duff=findViewById<DrawPorterDuffView>(R.id.draw_porter_duff)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            draw_porter_duff.mode = PorterDuff.Mode.MULTIPLY
            draw_porter_duff.loadBitmap("https://media.gamtoss.com/web/uploads/images/team/-1646915230.png")

        }
    }
   
}
