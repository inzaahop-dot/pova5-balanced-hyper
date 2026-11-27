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

    private var currentPage = "home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        showHome()
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (currentPage != "home") {
            showHome()
        } else {
            super.onBackPressed()
        }
    }

    private fun showHome() {
        currentPage = "home"
        val root = container()

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
        quickRow1.addView(actionTile("◉", "Подключения", "Wi-Fi · Bluetooth") {
            showSection("connections")
        }, weightParams())
        quickRow1.addView(spaceWidth())
        quickRow1.addView(actionTile("☼", "Экран", "Яркость и дисплей") {
            showSection("display")
        }, weightParams())
        root.addView(quickRow1)

        val quickRow2 = row().apply { setPadding(0, dp(10), 0, 0) }
        quickRow2.addView(actionTile("◐", "Оформление", "Цвета и темы") {
            showSection("appearance")
        }, weightParams())
        quickRow2.addView(spaceWidth())
        quickRow2.addView(actionTile("⌁", "Анимации", "Эффекты интерфейса") {
            showSection("animations")
        }, weightParams())
        root.addView(quickRow2)

        root.addView(sectionTitle("Разделы"))
        addNavigationCard(root, "Внешний вид", "Цвета, иконки и оформление", "appearance")
        addNavigationCard(root, "Экран и анимации", "Переходы и визуальные эффекты", "display")
        addNavigationCard(root, "О приложении", "Информация о Balanced Hyper", "about")

        root.addView(label("DESIGN PREVIEW · 0.1.1", 10f, secondary, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(18), 0, 0)
        })

        setContentView(makeScroll(root))
    }

    private fun showSection(page: String) {
        currentPage = page
        val root = container()
        val title: String
        val subtitle: String
        val items: List<Pair<String, String>>

        when (page) {
            "connections" -> {
                title = "Подключения"
                subtitle = "Быстрый доступ к сетевым функциям."
                items = listOf(
                    "Wi-Fi" to "Управление подключением будет добавлено отдельно.",
                    "Bluetooth" to "Состояние и управление Bluetooth.",
                    "Мобильная сеть" to "Информация о мобильном подключении."
                )
            }
            "display" -> {
                title = "Экран"
                subtitle = "Настройки отображения и визуального поведения."
                items = listOf(
                    "Яркость" to "Элемент управления яркостью пока находится в разработке.",
                    "Частота обновления" to "Информация о режимах экрана устройства.",
                    "Экран блокировки" to "Будущая настройка внешнего вида экрана блокировки."
                )
            }
            "appearance" -> {
                title = "Внешний вид"
                subtitle = "Визуальная система Balanced Hyper."
                items = listOf(
                    "Цветовая палитра" to "Основные цвета, акценты и поверхности интерфейса.",
                    "Темы" to "Подготовка светлой и тёмной тем оформления.",
                    "Иконки" to "Единый стиль значков приложения."
                )
            }
            "animations" -> {
                title = "Анимации"
                subtitle = "Плавность и визуальные переходы."
                items = listOf(
                    "Открытие экранов" to "Переходы между разделами приложения.",
                    "Нажатия" to "Визуальная обратная связь при взаимодействии.",
                    "Плавность" to "Настройки анимаций внутри Balanced Hyper."
                )
            }
            else -> {
                title = "О приложении"
                subtitle = "Информация о проекте."
                items = listOf(
                    "Balanced Hyper" to "Нативное приложение-компаньон проекта для TECNO POVA 5.",
                    "Текущий этап" to "Разработка основы приложения и отдельных экранов.",
                    "Важно" to "Приложение пока не заменяет системный интерфейс HiOS и не меняет системные настройки."
                )
            }
        }

        val header = row().apply { gravity = Gravity.CENTER_VERTICAL }
        val back = label("‹", 32f, accent, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, 0, dp(14), 0)
            isClickable = true
            isFocusable = true
            setOnClickListener { showHome() }
        }
        header.addView(back)
        header.addView(label(title, 21f, primary, true))
        root.addView(header, margins(bottom = 20))

        root.addView(label(title, 25f, primary, true))
        root.addView(label(subtitle, 13f, secondary).apply {
            setPadding(0, dp(7), 0, dp(20))
        })

        items.forEach { item ->
            val info = card(surface, 18).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(17), dp(16), dp(17), dp(16))
                layoutParams = margins(bottom = 11)
            }
            info.addView(label(item.first, 16f, primary, true))
            info.addView(label(item.second, 12f, secondary).apply {
                setPadding(0, dp(7), 0, 0)
            })
            root.addView(info)
        }

        root.addView(label("Раздел находится в разработке", 11f, secondary).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(18), 0, dp(10))
        })

        setContentView(makeScroll(root))
    }

    private fun addNavigationCard(
        root: LinearLayout,
        title: String,
        detail: String,
        page: String
    ) {
        val tile = card(surface, 18).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(17), dp(15), dp(17), dp(15))
            layoutParams = margins(bottom = 10)
            isClickable = true
            isFocusable = true
            setOnClickListener { showSection(page) }
        }
        tile.addView(label(title, 16f, primary, true))
        tile.addView(label(detail, 12f, secondary).apply {
            setPadding(0, dp(5), 0, 0)
        })
        root.addView(tile)
    }

    private fun container() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(22), dp(24), dp(22), dp(28))
        setBackgroundColor(bg)
    }

    private fun makeScroll(root: LinearLayout) = ScrollView(this).apply {
        setBackgroundColor(bg)
        isFillViewport = true
        addView(root)
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

    private fun actionTile(
        symbol: String,
        title: String,
        detail: String,
        onClick: () -> Unit
    ) = card(surfaceAlt, 20).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(15), dp(15), dp(12), dp(15))
        isClickable = true
        isFocusable = true
        setOnClickListener { onClick() }
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
}
