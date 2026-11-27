package com.balancedhyper.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.rgb(16, 23, 34))
        }

        val title = TextView(this).apply {
            text = "Balanced Hyper"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "POVA 5 · Native Android foundation"
            textSize = 14f
            setTextColor(Color.rgb(160, 180, 205))
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 0)
        }

        root.addView(title)
        root.addView(subtitle)
        setContentView(root)
    }
}
