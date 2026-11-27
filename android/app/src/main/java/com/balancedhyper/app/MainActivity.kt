package com.balancedhyper.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextClock
import android.widget.TextView

class MainActivity : Activity() {
    private val prefs by lazy { getSharedPreferences("balanced_hyper", MODE_PRIVATE) }
    private val light get() = prefs.getBoolean("light_theme", false)
    private val animations get() = prefs.getBoolean("animations", true)

    private val bg get() = if (light) Color.rgb(241, 245, 251) else Color.rgb(10, 15, 25)
    private val surface get() = if (light) Color.WHITE else Color.rgb(23, 31, 45)
    private val surfaceAlt get() = if (light) Color.rgb(230, 237, 247) else Color.rgb(31, 43, 61)
    private val accent = Color.rgb(65, 137, 235)
    private val primary get() = if (light) Color.rgb(22, 31, 45) else Color.WHITE
    private val secondary get() = if (light) Color.rgb(91, 108, 130) else Color.rgb(157, 177, 203)

    private var currentPage = "home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (currentPage != "home") showHome() else super.onBackPressed()
    }

    private fun updateSystemBars() {
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility =
            if (light) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else 0
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

        val clock = card(surface, 26).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(18), dp(20), dp(18))
            layoutParams = margins(top = 22, bottom = 16)
        }
        clock.addView(label("BALANCED HYPER", 10f, secondary, true))
        clock.addView(TextClock(this).apply {
            format12Hour = "h:mm"
            format24Hour = "HH:mm"
            textSize = 48f
            setTextColor(primary)
            setPadding(0, dp(3), 0, 0)
        })
        clock.addView(label("Панель быстрого доступа и настройки", 12f, secondary))
        root.addView(clock)

        root.addView(sectionTitle("Быстрые действия"))
        val first = row()
        first.addView(actionTile("◉", "Подключения", "Wi-Fi · Bluetooth") {
            showSection("connections")
        }, weightParams())
        first.addView(spaceWidth())
        first.addView(actionTile("☼", "Экран", "Яркость и дисплей") {
            showSection("display")
        }, weightParams())
        root.addView(first)

        val second = row().apply { setPadding(0, dp(10), 0, 0) }
        second.addView(actionTile("◐", "Оформление", "Цвета и темы") {
            showSection("appearance")
        }, weightParams())
        second.addView(spaceWidth())
        second.addView(actionTile("⌁", "Анимации", "Эффекты интерфейса") {
            showSection("animations")
        }, weightParams())
        root.addView(second)

        root.addView(sectionTitle("Разделы"))
        addNavigationCard(root, "Внешний вид", "Цвета, иконки и оформление", "appearance")
        addNavigationCard(root, "Экран и анимации", "Переходы и визуальные эффекты", "display")
        addNavigationCard(root, "О приложении", "Информация о Balanced Hyper", "about")
        root.addView(label("DESIGN PREVIEW · 0.1.1", 10f, secondary, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(18), 0, 0)
        })
        display(root)
    }

    private fun showSection(page: String) {
        currentPage = page
        if (page == "appearance") {
            showAppearance()
            return
        }
        if (page == "animations") {
            showAnimations()
            return
        }

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
                title = "Экран и анимации"
                subtitle = "Настройки отображения Balanced Hyper."
                items = listOf(
                    "Яркость" to "Элемент управления яркостью пока в разработке.",
                    "Частота обновления" to "Информация о режимах экрана устройства.",
                    "Экран блокировки" to "Будущая настройка внешнего вида экрана блокировки."
                )
            }
            else -> {
                title = "О приложении"
                subtitle = "Информация о проекте."
                items = listOf(
                    "Balanced Hyper" to "Нативное приложение-компаньон для TECNO POVA 5.",
                    "Текущий этап" to "Разработка основы приложения и отдельных экранов.",
                    "Важно" to "Приложение пока не заменяет системный интерфейс HiOS и не меняет системные настройки."
                )
            }
        }

        addPageHeader(root, title)
        root.addView(label(subtitle, 13f, secondary).apply {
            setPadding(0, dp(7), 0, dp(20))
        })
        items.forEach { addInfoCard(root, it.first, it.second) }
        display(root)
    }

    private fun showAppearance() {
        val root = container()
        addPageHeader(root, "Внешний вид")
        root.addView(label("Настрой внешний вид самого приложения.", 13f, secondary).apply {
            setPadding(0, dp(7), 0, dp(20))
        })

        val themeCard = card(surface, 18).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(17), dp(16), dp(17), dp(16))
            layoutParams = margins(bottom = 12)
            isClickable = true
            isFocusable = true
            setOnClickListener {
                prefs.edit().putBoolean("light_theme", !light).apply()
                showAppearance()
            }
        }
        themeCard.addView(label("Тема оформления", 16f, primary, true))
        themeCard.addView(label(
            if (light) "Светлая · нажми, чтобы включить тёмную" else "Тёмная · нажми, чтобы включить светлую",
            12f, secondary
        ).apply { setPadding(0, dp(7), 0, 0) })
        root.addView(themeCard)

        addInfoCard(root, "Цветовая палитра", "Акцентный цвет Balanced Hyper пока фиксированный.")
        addInfoCard(root, "Иконки", "Единый набор значков будет добавлен в следующем этапе.")
        display(root)
    }

    private fun showAnimations() {
        val root = container()
        addPageHeader(root, "Анимации")
        root.addView(label("Управление переходами внутри Balanced Hyper.", 13f, secondary).apply {
            setPadding(0, dp(7), 0, dp(20))
        })

        val setting = card(surface, 18).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(17), dp(14), dp(12), dp(14))
            layoutParams = margins(bottom = 12)
        }
        val textColumn = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        textColumn.addView(label("Анимации экранов", 16f, primary, true))
        textColumn.addView(label("Плавное появление разделов", 12f, secondary).apply {
            setPadding(0, dp(5), 0, 0)
        })
        setting.addView(textColumn, LinearLayout.LayoutParams(0, -2, 1f))
        setting.addView(Switch(this).apply {
            isChecked = animations
            setOnCheckedChangeListener { _, checked ->
                prefs.edit().putBoolean("animations", checked).apply()
            }
        })
        root.addView(setting)

        addInfoCard(root, "Область действия", "Настройка влияет только на переходы внутри приложения и не меняет системные анимации Android.")
        display(root)
    }

    private fun addPageHeader(root: LinearLayout, title: String) {
        val header = row().apply { gravity = Gravity.CENTER_VERTICAL }
        header.addView(label("‹", 32f, accent, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, 0, dp(14), 0)
            isClickable = true
            isFocusable = true
            setOnClickListener { showHome() }
        })
        header.addView(label(title, 21f, primary, true))
        root.addView(header, margins(bottom = 20))
        root.addView(label(title, 25f, primary, true))
    }

    private fun addInfoCard(root: LinearLayout, title: String, detail: String) {
        val info = card(surface, 18).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(17), dp(16), dp(17), dp(16))
            layoutParams = margins(bottom = 11)
        }
        info.addView(label(title, 16f, primary, true))
        info.addView(label(detail, 12f, secondary).apply {
            setPadding(0, dp(7), 0, 0)
        })
        root.addView(info)
    }

    private fun addNavigationCard(
        root: LinearLayout, title: String, detail: String, page: String
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

    private fun display(root: LinearLayout) {
        updateSystemBars()
        val scroll = ScrollView(this).apply {
            setBackgroundColor(bg)
            isFillViewport = true
            addView(root)
        }
        setContentView(scroll)
        if (animations) {
            scroll.startAnimation(AlphaAnimation(0.75f, 1f).apply { duration = 160 })
        }
    }

    private fun container() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(22), dp(24), dp(22), dp(28))
        setBackgroundColor(bg)
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
        symbol: String, title: String, detail: String, onClick: () -> Unit
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
