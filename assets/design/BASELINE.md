# POVA5 Balanced Hyper — BASELINE v1

Статус: DESIGN BASELINE. Это визуальная основа проекта, не готовая системная оболочка.

## Источники
- `prototype/index.html` — структура прототипа.
- `prototype/styles.css` — оформление.
- `prototype/app.js` — демонстрационные взаимодействия.
- `tokens/tokens.json` — дизайн-токены.

## Дизайн-токены
```json
{
  "$schema": "https://design-tokens.github.io/community-group/format/",
  "name": "Balanced Hyper",
  "version": "0.1.0",
  "description": "Original design tokens for the Balanced Hyper theme (HyperOS-inspired, not copied). DESIGN_PROPOSAL; unverified on device.",
  "themes": {
    "light": {
      "color": {
        "bg": {
          "canvas": "#F5F6F8",
          "surface": "#FFFFFF",
          "surface-elevated": "#FFFFFF",
          "surface-subtle": "#F0F1F3"
        },
        "text": {
          "primary": "#1A1C1F",
          "secondary": "#5B5F66",
          "tertiary": "#8A8F98",
          "disabled": "#B6BBC3"
        },
        "divider": "#E3E5E9",
        "accent": {
          "primary": "#2E6BB5",
          "primary-container": "#DCE6F5"
        },
        "state": {
          "success": "#2E8B57",
          "warning": "#B87A1A",
          "error": "#C0392B",
          "info": "#2E6BB5"
        }
      }
    },
    "dark": {
      "color": {
        "bg": {
          "canvas": "#0E1013",
          "surface": "#16181C",
          "surface-elevated": "#1E2025",
          "surface-subtle": "#14161A"
        },
        "text": {
          "primary": "#E6E8EC",
          "secondary": "#A9AEB6",
          "tertiary": "#7A7F87",
          "disabled": "#555A62"
        },
        "divider": "#2A2D33",
        "accent": {
          "primary": "#6FA0E6",
          "primary-container": "#1F2F4A"
        },
        "state": {
          "success": "#5FBF8A",
          "warning": "#D9A04A",
          "error": "#E06B5F",
          "info": "#6FA0E6"
        }
      }
    }
  },
  "typography": {
    "fontFamily": "system",
    "scale": {
      "xs": {
        "size": 11,
        "lineHeight": 16
      },
      "sm": {
        "size": 12,
        "lineHeight": 18
      },
      "md": {
        "size": 14,
        "lineHeight": 20
      },
      "lg": {
        "size": 16,
        "lineHeight": 24
      },
      "xl": {
        "size": 20,
        "lineHeight": 28
      },
      "2xl": {
        "size": 24,
        "lineHeight": 32
      },
      "3xl": {
        "size": 30,
        "lineHeight": 38
      }
    },
    "weights": [
      400,
      500,
      600
    ]
  },
  "spacing": {
    "0": 0,
    "1": 4,
    "2": 8,
    "3": 12,
    "4": 16,
    "5": 20,
    "6": 24,
    "8": 32,
    "10": 40,
    "12": 48
  },
  "radius": {
    "xs": 4,
    "sm": 8,
    "md": 12,
    "lg": 16,
    "xl": 24,
    "full": 9999
  },
  "elevation": {
    "0": 0,
    "1": 2,
    "2": 4,
    "3": 8
  },
  "motion": {
    "duration": {
      "fast": 120,
      "normal": 200,
      "slow": 320
    },
    "easing": {
      "standard": "cubic-bezier(0.2, 0, 0, 1)",
      "emphasized": "cubic-bezier(0.05, 0.7, 0.1, 1)",
      "decelerate": "cubic-bezier(0, 0, 0.2, 1)"
    }
  }
}
```

## Главный экран
Существующий HTML-прототип принят за визуальную основу. Его разметка и оформление не изменяются этим документом.

## Каталог SVG
- `svg/control-center/brightness-slider.svg`
- `svg/control-center/panel-header.svg`
- `svg/control-center/tile-active.svg`
- `svg/control-center/tile-inactive.svg`
- `svg/control-center/volume-panel-dark.svg`
- `svg/control-center/volume-panel-muted-dark.svg`
- `svg/control-center/volume-panel-muted.svg`
- `svg/control-center/volume-panel.svg`
- `svg/control-center/volume-slider-dark.svg`
- `svg/control-center/volume-slider.svg`
- `svg/home/app-icon-tile.svg`
- `svg/home/page-indicator.svg`
- `svg/home/search-pill.svg`
- `svg/home/widget-container.svg`
- `svg/icons/airplane.svg`
- `svg/icons/auto-rotate-dark.svg`
- `svg/icons/auto-rotate.svg`
- `svg/icons/battery-dark.svg`
- `svg/icons/battery.svg`
- `svg/icons/bluetooth.svg`
- `svg/icons/flashlight.svg`
- `svg/icons/location-dark.svg`
- `svg/icons/location.svg`
- `svg/icons/nfc-dark.svg`
- `svg/icons/nfc.svg`
- `svg/icons/power-saver-dark.svg`
- `svg/icons/power-saver.svg`
- `svg/icons/sound-dark.svg`
- `svg/icons/sound.svg`
- `svg/icons/volume-dark.svg`
- `svg/icons/volume-mute-dark.svg`
- `svg/icons/volume-mute.svg`
- `svg/icons/volume.svg`
- `svg/icons/wifi.svg`
- `svg/notifications/notification-card-dark.svg`
- `svg/notifications/notification-card.svg`
- `svg/notifications/notification-close-dark.svg`
- `svg/notifications/notification-close.svg`
- `svg/notifications/notification-expand-dark.svg`
- `svg/notifications/notification-expand.svg`
- `svg/notifications/notification-icon-dark.svg`
- `svg/notifications/notification-icon.svg`
- `svg/recents/close-all-dark.svg`
- `svg/recents/close-all.svg`
- `svg/recents/recent-card-dark.svg`
- `svg/recents/recent-card.svg`
- `svg/settings/section-card-dark.svg`
- `svg/settings/section-card.svg`
- `svg/settings/settings-about-dark.svg`
- `svg/settings/settings-about.svg`
- `svg/settings/settings-apps-dark.svg`
- `svg/settings/settings-apps.svg`
- `svg/settings/settings-battery-dark.svg`
- `svg/settings/settings-battery.svg`
- `svg/settings/settings-display-dark.svg`
- `svg/settings/settings-display.svg`
- `svg/settings/settings-security-dark.svg`
- `svg/settings/settings-security.svg`
- `svg/settings/settings-storage-dark.svg`
- `svg/settings/settings-storage.svg`

## Ограничения и незавершённые работы
- Прототип работает как локальная HTML-демонстрация.
- Переключатели не управляют настоящими функциями Android.
- Совместимость с HiOS 14 и системными механизмами не подтверждена.
- Установка системного модуля не выполнялась.
- Остальные экраны и их поведение требуют отдельной проверки.

## Правило развития
Сохранять утверждённый главный экран. Новые элементы добавлять постепенно, не переписывая работающие части без подтверждённой причины.
