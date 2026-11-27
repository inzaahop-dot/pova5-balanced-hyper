# POVA5 Balanced Hyper — Architecture

Status: DESIGN_ONLY

## 1. Components

### Android application
Native companion application, planned in Kotlin.
Responsibilities:
- Settings and configuration UI
- Theme selection and design previews
- User-accessible controls
- Diagnostics that do not require elevated privileges

The app must remain useful without root access.

### Systemless integration
A separate optional component.
Its purpose is to provide only those system-level visual adaptations
whose compatibility with TECNO HiOS 14 has been demonstrated.

No implementation mechanism is approved yet.

### Design assets
`assets/design/` is the visual source of truth for the current concept.
Assets must be adapted for Android rather than copied blindly.

## 2. Boundaries

- No boot, init_boot, vbmeta, kernel or partition modifications.
- No replacement of system APKs.
- No disabling Verified Boot, SELinux, thermal or security protections.
- No root-detection or integrity-check bypasses.
- No installation on the phone during the design phase.
- Every future integration must have a documented disable and rollback path.

## 3. Development order

1. Confirm build environment and Android project configuration.
2. Build the standalone application shell.
3. Port approved design assets into native Android resources.
4. Test the app independently of root.
5. Research HiOS compatibility using documented evidence.
6. Consider optional systemless integration only after the project gates pass.

## 4. Unknowns

Android SDK, Gradle version, minimum supported API, root-manager integration
and available HiOS overlay targets are not yet verified.
