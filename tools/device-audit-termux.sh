#!/data/data/com.termux/files/usr/bin/bash
# C1-DEVICE-AUDIT: read-only device facts from plain Termux.
# No writes, no network, no privileged commands.

TERMUX_PREFIX="/data/data/com.termux/files/usr"

if [ "${PREFIX:-}" != "$TERMUX_PREFIX" ]; then
    printf '%s\n' "ERROR: run in plain Termux (PREFIX=$TERMUX_PREFIX), not Ubuntu PRoot." >&2
    exit 1
fi

find_cmd() {
    name=$1
    if command -v "$name" >/dev/null 2>&1; then
        command -v "$name"
        return 0
    fi
    for candidate in "/system/bin/$name" "/system/xbin/$name"; do
        if [ -x "$candidate" ]; then
            printf '%s\n' "$candidate"
            return 0
        fi
    done
    return 1
}

GETPROP=$(find_cmd getprop || true)
WM=$(find_cmd wm || true)
CMD=$(find_cmd cmd || true)
MAGISK=$(find_cmd magisk || true)

print_prop() {
    label=$1
    key=$2
    if [ -z "$GETPROP" ]; then
        printf '%s: NOT_AVAILABLE\n' "$label"
        return
    fi
    value=$("$GETPROP" "$key" 2>/dev/null)
    if [ -z "$value" ]; then
        value="UNKNOWN"
    fi
    printf '%s: %s\n' "$label" "$value"
}

printf '%s\n' "=== C1 device audit (Termux, read-only) ==="
print_prop "brand" "ro.product.brand"
print_prop "model" "ro.product.model"
print_prop "device" "ro.product.device"
print_prop "abi" "ro.product.cpu.abi"
print_prop "android_version" "ro.build.version.release"
print_prop "api_level" "ro.build.version.sdk"
print_prop "build_display_id" "ro.build.display.id"
print_prop "fingerprint" "ro.build.fingerprint"

if [ -z "$WM" ]; then
    printf 'wm_size: NOT_AVAILABLE\n'
    printf 'wm_density: NOT_AVAILABLE\n'
else
    wm_size=$("$WM" size 2>/dev/null)
    if [ -z "$wm_size" ]; then
        wm_size="NOT_AVAILABLE"
    fi
    printf 'wm_size:\n%s\n' "$wm_size"

    wm_density=$("$WM" density 2>/dev/null)
    if [ -z "$wm_density" ]; then
        wm_density="NOT_AVAILABLE"
    fi
    printf 'wm_density:\n%s\n' "$wm_density"
fi

if [ -z "$CMD" ]; then
    printf 'cmd_overlay_list: NOT_AVAILABLE\n'
else
    overlays=$("$CMD" overlay list 2>/dev/null)
    if [ -z "$overlays" ]; then
        overlays="NOT_AVAILABLE"
    fi
    printf 'cmd_overlay_list:\n%s\n' "$overlays"
fi

if [ -z "$MAGISK" ]; then
    printf 'magisk_version: NOT_AVAILABLE\n'
else
    magisk_version=$("$MAGISK" -v 2>/dev/null)
    if [ -z "$magisk_version" ]; then
        magisk_version="NOT_AVAILABLE"
    fi
    printf 'magisk_version: %s\n' "$magisk_version"
fi
