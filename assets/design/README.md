# Balanced Hyper Design Asset Pack (v2)

Original (non-Xiaomi) design asset pack for the home screen, control center, notifications,
settings, volume panel and recents. All artwork is original vector work; no copied
Xiaomi/HyperOS assets. Tokens and values are `DESIGN_PROPOSAL` (unverified on device)
per C3.

## Layout

```
assets/design/
├── README.md
├── tokens/
│   ├── tokens.json   # Combined light/dark design tokens (W3C-style)
│   ├── light.css     # CSS custom properties, light theme
│   └── dark.css      # CSS custom properties, dark theme
└── svg/
    ├── home/         # Home screen surfaces
    ├── control-center/ # Control center / quick settings surfaces
    ├── icons/        # 24x24 toggle glyphs
    ├── notifications/ # Notification cards and icons
    ├── settings/     # Settings category icons and section cards
    └── recents/      # Recent-app cards and close-all control
```

## Assets and dimensions

Light theme files use light token hex values; dark variants use the `-dark` suffix and
dark token hex values. Icons are single 24x24 glyphs recolored at integration time where
needed; surfaces, cards and panels ship explicit light/dark pairs.

### Home

| File | Canvas (px) | Purpose |
|---|---|---|
| svg/home/app-icon-tile.svg | 56 x 56 | Rounded app icon container (radius 16) |
| svg/home/page-indicator.svg | 80 x 12 | Pagination dots (one active) |
| svg/home/widget-container.svg | 320 x 160 | Widget container (radius 24) |
| svg/home/search-pill.svg | 320 x 48 | Home search bar (pill, radius 24) |

### Control center

| File | Canvas (px) | Purpose |
|---|---|---|
| svg/control-center/tile-active.svg | 80 x 80 | Quick settings tile, active (radius 16) |
| svg/control-center/tile-inactive.svg | 80 x 80 | Quick settings tile, inactive (radius 16) |
| svg/control-center/brightness-slider.svg | 320 x 48 | Brightness slider track + thumb |
| svg/control-center/panel-header.svg | 360 x 56 | Control center top header bar (radius 24) |
| svg/control-center/volume-slider.svg (+ -dark) | 320 x 48 | Volume slider track + thumb |
| svg/control-center/volume-panel.svg (+ -dark) | 320 x 96 | Volume panel with slider and mute control |
| svg/control-center/volume-panel-muted.svg (+ -dark) | 320 x 96 | Volume panel in muted state |

### Icons (24 x 24)

| File | Purpose |
|---|---|
| svg/icons/wifi.svg (+ -dark for new glyphs) | Wi-Fi toggle glyph |
| svg/icons/bluetooth.svg | Bluetooth toggle glyph |
| svg/icons/flashlight.svg | Flashlight toggle glyph |
| svg/icons/airplane.svg | Airplane mode toggle glyph |
| svg/icons/battery.svg (+ -dark) | Battery toggle glyph |
| svg/icons/power-saver.svg (+ -dark) | Power saver toggle glyph |
| svg/icons/location.svg (+ -dark) | Location toggle glyph |
| svg/icons/sound.svg (+ -dark) | Sound toggle glyph |
| svg/icons/nfc.svg (+ -dark) | NFC toggle glyph |
| svg/icons/auto-rotate.svg (+ -dark) | Auto-rotate toggle glyph |
| svg/icons/volume.svg (+ -dark) | Volume glyph |
| svg/icons/volume-mute.svg (+ -dark) | Volume mute glyph |

Note: the four original icons (wifi, bluetooth, flashlight, airplane) are preserved unchanged;
new icons follow the same 2px-stroke, 24x24 convention and ship `-dark` variants.

### Notifications

| File | Canvas (px) | Purpose |
|---|---|---|
| svg/notifications/notification-card.svg (+ -dark) | 360 x 88 | Notification card surface |
| svg/notifications/notification-icon.svg (+ -dark) | 24 x 24 | Notification bell icon |
| svg/notifications/notification-close.svg (+ -dark) | 24 x 24 | Notification close action |
| svg/notifications/notification-expand.svg (+ -dark) | 24 x 24 | Notification expand action |

### Settings

| File | Canvas (px) | Purpose |
|---|---|---|
| svg/settings/section-card.svg (+ -dark) | 360 x 72 | Settings section card surface |
| svg/settings/settings-display.svg (+ -dark) | 24 x 24 | Display category icon |
| svg/settings/settings-battery.svg (+ -dark) | 24 x 24 | Battery category icon |
| svg/settings/settings-apps.svg (+ -dark) | 24 x 24 | Apps category icon |
| svg/settings/settings-security.svg (+ -dark) | 24 x 24 | Security category icon |
| svg/settings/settings-storage.svg (+ -dark) | 24 x 24 | Storage category icon |
| svg/settings/settings-about.svg (+ -dark) | 24 x 24 | About category icon |

### Recents

| File | Canvas (px) | Purpose |
|---|---|---|
| svg/recents/recent-card.svg (+ -dark) | 160 x 280 | Recent app card surface |
| svg/recents/close-all.svg (+ -dark) | 48 x 48 | Close-all recent apps control |

## Interactive prototype (v3)

A self-contained, offline browser prototype lives at `prototype/index.html` with local
`prototype/styles.css` and `prototype/app.js`. It demonstrates Light/Dark toggle, home screen,
control center open/close, Wi-Fi/Bluetooth/NFC/power-saver demo toggles, brightness and
volume sliders, notification shade/cards, Settings screen and recent-app cards using the
existing SVG assets and tokens. All toggles are labeled as visual demos only and do not
control Android settings or device functions. No remote resources or external libraries.

## Design tokens

Colors, typography (sp), spacing (4dp grid), radii, elevation, motion. Light theme uses
`values`; dark theme uses `values-night` qualifiers conceptually. Semantic state colors
are provided for both themes. See `tokens/tokens.json`.

## Integration notes

- SVGs are scalable; viewBox matches canvas size. Prefer `currentColor` overrides at
  integration time where a host theme requires recoloring; base fills use token hex.
- Keep assets vector-only; no raster fallbacks or embedded bitmaps.
- Dark variants follow the `-dark` suffix naming and use dark token hex values.
- Do not treat these as shippable system resources: RRO targets, launcher support and
  SystemUI ownership are unverified (C1/C2/C4 pending). Apply only after gates G1-G4.
- Icons are 24x24 with 2px strokes; scale with `width`/`height` or CSS.
- Tiles are 80x80 to match a 4-column control center grid at typical densities.
- Cards use 16-24 dp radii per Balanced Hyper radius tokens.
- No changes to phone partitions, boot images, security settings, or thermal protections.
- No commits or pushes performed; work confined to `assets/design/`.

## Design baseline
- [BASELINE v1](BASELINE.md)
