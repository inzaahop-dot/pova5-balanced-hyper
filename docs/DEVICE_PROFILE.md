# Профиль устройства

Единый источник сведений об устройстве для клеток C1–C4.

## Легенда статусов

| Статус | Значение |
| --- | --- |
| `ASSERTED_BY_USER` | Сообщено пользователем; не проверено репозиторием |
| `OBSERVED_READ_ONLY` | Получено безопасной read-only командой; требуется evidence |
| `DERIVED` | Логически выведено из проверенных полей |
| `UNKNOWN` | Неизвестно; использовать нельзя |
| `OUT_OF_SCOPE` | Проверять и изменять запрещено без отдельного разрешения |

## Идентификация

| Поле | Значение | Статус | Примечание |
| --- | --- | --- | --- |
| Производитель | TECNO | `ASSERTED_BY_USER` | Подтвердить через `ro.product.brand` |
| Модель | `LH7n-H894LM-U-RU` | `ASSERTED_BY_USER` | Подтвердить через `ro.product.model` |
| Кодовое имя | — | `UNKNOWN` | Подлежит уточнению в C1 |
| SoC | `MT6789` | `ASSERTED_BY_USER` | Алиас/маркетинговое имя не фиксировать без проверки |
| ABI | `arm64-v8a` | `ASSERTED_BY_USER` | Подтвердить через `ro.product.cpu.abi` |
| Android | 14 | `ASSERTED_BY_USER` | Подтвердить через `ro.build.version.release` |
| API level | 34 | `ASSERTED_BY_USER` | Подтвердить через `ro.build.version.sdk` |
| Build ID | `250416V782` | `ASSERTED_BY_USER` | Подтвердить через `ro.build.display.id` |
| Fingerprint | — | `UNKNOWN` | Не выводить как факт без проверки |
| Регион/локаль | — | `UNKNOWN` | Суффикс `-RU` не трактовать как подтверждённую локаль |

## Состояние, которое не предполагается

| Область | Статус | Почему важно |
| --- | --- | --- |
| Root-решение | `UNKNOWN` | Определяет, возможен ли systemless-слой вообще |
| Состояние загрузчика | `OUT_OF_SCOPE` | Не проверять и не изменять |
| Verified Boot / SELinux | `OUT_OF_SCOPE` | Не переключать; только читать props при наличии доступа |
| Разделы `/system`, `/vendor`, `/boot` | `OUT_OF_SCOPE` | Не монтировать на запись, не патчить, не прошивать |
| Системные APK | `OUT_OF_SCOPE` | Не заменять, не отключать, не переподписывать |
| Настройки безопасности | `OUT_OF_SCOPE` | Не менять через `settings put`, A11y, device admin и т. п. |
| Установленные обновления OTA | `UNKNOWN` | Может сбросить systemless-модуль |
| Сторонние root-модули | `UNKNOWN` | Источник конфликтов; проверять только списком |

## Разрешённые read-only проверки

Команды ниже — **план**, а не выполненные проверки. Выполнять только при явном разрешении и только в read-only режиме.

```sh
adb devices -l
adb shell getprop ro.product.brand
adb shell getprop ro.product.model
adb shell getprop ro.product.device
adb shell getprop ro.product.cpu.abi
adb shell getprop ro.product.cpu.abilist
adb shell getprop ro.build.version.release
adb shell getprop ro.build.version.sdk
adb shell getprop ro.build.display.id
adb shell getprop ro.build.fingerprint
adb shell getprop ro.boot.bootloader
adb shell getprop ro.boot.verifiedbootstate
adb shell getprop ro.boot.flash.locked
adb shell pm list features
adb shell pm list packages -s
adb shell cmd overlay list
adb shell dumpsys overlay
adb shell wm density
adb shell wm size
```

### Локальный аудит из Termux

Для C1 подготовлен скрипт `tools/device-audit-termux.sh`. Он выполняет только безопасную часть проверок (`getprop`, `wm size`, `wm density`, `cmd overlay list`, версию Magisk) из обычного Termux, из корня репозитория:

```sh
./tools/device-audit-termux.sh
```

Запуск в Ubuntu PRoot не поддерживается. Скрипт не пишет файлы, не использует привилегированные команды и не отправляет данные в сеть. Отсутствующая команда — `NOT_AVAILABLE`, пустое свойство — `UNKNOWN`; перед фиксацией в профиль вывод просматривают вручную.

Правила фиксации:

- Пустое поле не интерпретируется как «разблокировано» или «root есть».
- Для каждого `OBSERVED_READ_ONLY` поля сохраняются команда, дата и краткий результат с маскированием.
- Полный вывод `dumpsys`, `logcat` и содержимое APK в репозиторий не добавляются.

## Явно запрещённые операции

```sh
# Ничего из этого не выполнять в рамках проекта
fastboot flash ...
dd if=... of=...
adb shell su -c 'mount -o rw /system'
adb shell magisk --install-module ...
adb shell pm install ...
adb shell settings put ...
adb shell setprop ...
adb shell setenforce ...
```

## Открытые вопросы для C1

1. Каково фактическое кодовое имя устройства и fingerprint сборки?
2. Есть ли root и через какой менеджер он реализован?
3. Поддерживается ли `cmd overlay` и кто владеет текущими overlay-пакетами?
4. Какой лаунчер используется и поддерживает ли он смену иконок без системных прав?
5. Какие системные компоненты отвечают за статус-бар, шторку и экран блокировки?
6. Как часто приходят OTA и сохраняют ли они модули root-менеджера?
