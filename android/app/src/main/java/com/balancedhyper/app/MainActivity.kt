package com.balancedhyper.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.rgb(10, 15, 25)
    private val surface = Color.rgb(23, 31, 45)
    private val surfaceAlt = Color.rgb(31, 43, 61)
    private val accent = Color.rgb(91, 166, 255)
    private val primary = Color.WHITE
    private val secondary = Color.rgb(157, 177, 203)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg

        val scroll = ScrollView(this).apply {
            setBackgroundColor(bg)
            isFillViewport = true
        }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(28))
            setBackgroundColor(bg)
        }
        scroll.addView(root)

        val header = row()
        header.addView(label("BALANCED HYPER", 12f, accent, true), weightParams())
        header.addView(label("POVA 5", 12f, secondary, true))
        root.addView(header)

        root.addView(label("Твой телефон. Твой стиль.", 23f, primary, true).apply {
            setPadding(0, dp(26), 0, dp(4))
        })
        root.addView(label("Пространство Balanced Hyper", 13f, secondary))

        val clockCard = card(surface, 26).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(18), dp(20), dp(18))
            layoutParams = margins(top = 22, bottom = 16)
        }
        clockCard.addView(label("BALANCED HYPER", 10f, secondary, true))
        clockCard.addView(TextClock(this).apply {
            format12Hour = "h:mm"
            format24Hour = "HH:mm"
            textSize = 48f
            setTextColor(primary)
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            setPadding(0, dp(3), 0, 0)
        })
        clockCard.addView(label("Панель быстрого доступа и настройки", 12f, secondary))
        root.addView(clockCard)

        root.addView(sectionTitle("Быстрые действия"))
        val quickRow1 = row()
        quickRow1.addView(actionTile("◉", "Подключения", "Wi-Fi · Bluetooth"), weightParams())
        quickRow1.addView(spaceWidth())
        quickRow1.addView(actionTile("☼", "Яркость", "Экран и подсветка"), weightParams())
        root.addView(quickRow1)

        val quickRow2 = row().apply { setPadding(0, dp(10), 0, 0) }
        quickRow2.addView(actionTile("◐", "Оформление", "Светлая и тёмная темы"), weightParams())
        quickRow2.addView(spaceWidth())
        quickRow2.addView(actionTile("⌁", "Анимации", "Плавность интерфейса"), weightParams())
        root.addView(quickRow2)

        root.addView(sectionTitle("Разделы"))
        listOf(
            "Внешний вид" to "Цвета, иконки и оформление",
            "Экран и анимации" to "Переходы и визуальные эффекты",
            "О приложении" to "Версия и информация"
        ).forEach { item ->
            val tile = card(surface, 18).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(17), dp(15), dp(17), dp(15))
                layoutParams = margins(bottom = 10)
                isClickable = true
                isFocusable = true
                setOnClickListener { showMessage(item.first) }
            }
            tile.addView(label(item.first, 16f, primary, true))
            tile.addView(label(item.second, 12f, secondary).apply {
                setPadding(0, dp(5), 0, 0)
            })
            root.addView(tile)
        }

        root.addView(label("DESIGN PREVIEW · 0.1.1", 10f, secondary, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(18), 0, 0)
        })
        setContentView(scroll)
    }

    private fun row() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    private fun label(text: String, size: Float, color: Int, bold: Boolean = false) =
        TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(typeface, Typeface.BOLD)
        }

    private fun card(color: Int, radius: Int) = LinearLayout(this).apply {
        background = GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
        elevation = dp(2).toFloat()
    }

    private fun actionTile(symbol: String, title: String, detail: String) =
        card(surfaceAlt, 20).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(15), dp(15), dp(12), dp(15))
            isClickable = true
            isFocusable = true
            setOnClickListener { showMessage(title) }
            addView(label(symbol, 25f, accent, true))
            addView(label(title, 14f, primary, true).apply {
                setPadding(0, dp(12), 0, dp(3))
            })
            addView(label(detail, 10f, secondary))
        }

    private fun sectionTitle(text: String) = label(text, 17f, primary, true).apply {
        setPadding(0, dp(24), 0, dp(12))
    }

    private fun weightParams() =
        LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

    private fun spaceWidth() = View(this).apply {
        layoutParams = LinearLayout.LayoutParams(dp(10), 1)
    }

    private fun margins(top: Int = 0, bottom: Int = 0) =
        LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = dp(top)
            bottomMargin = dp(bottom)
        }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun showMessage(title: String) {
        Toast.makeText(this, "$title · скоро", Toast.LENGTH_SHORT).show()
    }
}
