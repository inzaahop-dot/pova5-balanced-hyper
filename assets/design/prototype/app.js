/* Balanced Hyper Prototype v3 - local JS (visual demo only) */
(function () {
  'use strict';

  const root = document.documentElement;
  const phone = document.getElementById('phone');

  // ---------- Theme ----------
  const THEME_KEY = 'bh-proto-theme';
  function getPreferredTheme() {
    try { return localStorage.getItem(THEME_KEY); } catch (e) { return null; }
    return null;
  }
  function applyTheme(theme) {
    root.setAttribute('data-theme', theme);
    document.querySelector('meta[name="theme-color"]')?.setAttribute('content', theme === 'dark' ? '#0E1013' : '#F5F6F8');
    // swap icons that have -dark variants
    document.querySelectorAll('img[data-dark-src]').forEach((img) => {
      const base = img.getAttribute('data-base-src');
      const dark = img.getAttribute('data-dark-src');
      img.setAttribute('src', theme === 'dark' ? dark : base);
    });
    // handle close-all icon
    const closeAll = document.getElementById('closeAllIcon');
    if (closeAll) {
      closeAll.setAttribute('src', theme === 'dark'
        ? '../svg/recents/close-all-dark.svg'
        : '../svg/recents/close-all.svg');
    }
    // notification card/icon/close/expand
    document.querySelectorAll('img[data-dark-swap]').forEach((img) => {
      const base = img.getAttribute('data-base');
      const dark = img.getAttribute('data-dark');
      img.setAttribute('src', theme === 'dark' ? dark : base);
    });
    // status icons
    const wifi = document.querySelector('img[data-icon="wifi"]');
    if (wifi) wifi.classList.toggle('icon-no-dark', theme === 'dark');
    const battery = document.querySelector('img[data-icon="battery"]');
    if (battery) {
      battery.setAttribute('src', theme === 'dark'
        ? '../svg/icons/battery-dark.svg'
        : '../svg/icons/battery.svg');
    }
  }
  function toggleTheme() {
    const cur = root.getAttribute('data-theme') || 'light';
    const next = cur === 'light' ? 'dark' : 'light';
    applyTheme(next);
    try { localStorage.setItem(THEME_KEY, next); } catch (e) {}
  }

  const saved = getPreferredTheme();
  applyTheme(saved === 'dark' ? 'dark' : 'light');

  document.getElementById('themeToggleSettings')?.addEventListener('click', toggleTheme);
  document.getElementById('themeToggleCC')?.addEventListener('click', toggleTheme);

  // ---------- Time ----------
  function updateTime() {
    const d = new Date();
    const h = String(d.getHours()).padStart(2, '0');
    const m = String(d.getMinutes()).padStart(2, '0');
    const el = document.getElementById('statusTime');
    if (el) el.textContent = h + ':' + m;
  }
  updateTime();
  setInterval(updateTime, 30000);

  // ---------- Apps ----------
  const apps = [
    { id: 'settings', label: 'Settings', icon: '../svg/settings/settings-display.svg', dark: '../svg/settings/settings-display-dark.svg' },
    { id: 'camera', label: 'Camera', icon: '../svg/home/app-icon-tile.svg' },
    { id: 'photos', label: 'Photos', icon: '../svg/home/app-icon-tile.svg' },
    { id: 'browser', label: 'Browser', icon: '../svg/home/app-icon-tile.svg' },
    { id: 'messages', label: 'Messages', icon: '../svg/home/app-icon-tile.svg' },
    { id: 'phone', label: 'Phone', icon: '../svg/home/app-icon-tile.svg' },
    { id: 'mail', label: 'Mail', icon: '../svg/home/app-icon-tile.svg' },
    { id: 'music', label: 'Music', icon: '../svg/home/app-icon-tile.svg' },
  ];
  const grid = document.getElementById('appGrid');
  apps.forEach((a) => {
    const btn = document.createElement('button');
    btn.className = 'app-tile';
    btn.type = 'button';
    btn.setAttribute('data-app', a.id);
    const img = document.createElement('img');
    img.src = a.icon;
    if (a.dark) { img.setAttribute('data-base', a.icon); img.setAttribute('data-dark', a.dark); img.setAttribute('data-dark-swap', ''); }
    btn.appendChild(img);
    const span = document.createElement('span');
    span.textContent = a.label;
    btn.appendChild(span);
    grid.appendChild(btn);
  });

  // app open handling
  function openApp(id) {
    if (id === 'settings') openSettings();
  }
  document.addEventListener('click', (e) => {
    const t = e.target.closest('[data-app]');
    if (t) openApp(t.getAttribute('data-app'));
  });

  // ---------- Settings ----------
  const settingsItems = [
    { icon: 'settings-display', label: 'Display', desc: 'Theme, brightness' },
    { icon: 'settings-battery', label: 'Battery', desc: 'Power saver (demo)' },
    { icon: 'settings-apps', label: 'Apps', desc: 'Manage apps' },
    { icon: 'settings-security', label: 'Security', desc: 'Lock, privacy' },
    { icon: 'settings-storage', label: 'Storage', desc: 'Usage' },
    { icon: 'settings-about', label: 'About', desc: 'Version info' },
  ];
  const settingsList = document.getElementById('settingsList');
  settingsItems.forEach((s) => {
    const row = document.createElement('div');
    row.className = 'settings-row';
    const img = document.createElement('img');
    const base = '../svg/settings/' + s.icon + '.svg';
    const dark = '../svg/settings/' + s.icon + '-dark.svg';
    img.src = root.getAttribute('data-theme') === 'dark' ? dark : base;
    img.setAttribute('data-base', base);
    img.setAttribute('data-dark', dark);
    img.setAttribute('data-dark-swap', '');
    row.appendChild(img);
    const lbl = document.createElement('span');
    lbl.className = 'label';
    lbl.textContent = s.label;
    row.appendChild(lbl);
    const chev = document.createElement('span');
    chev.className = 'chev';
    chev.textContent = '›';
    row.appendChild(chev);
    settingsList.appendChild(row);
  });

  const homeScreen = document.getElementById('homeScreen');
  const settingsScreen = document.getElementById('settingsScreen');
  function openSettings() { homeScreen.hidden = true; settingsScreen.hidden = false; }
  function closeSettings() { settingsScreen.hidden = true; homeScreen.hidden = false; }
  document.getElementById('settingsBack')?.addEventListener('click', closeSettings);

  // ---------- Overlays ----------
  function showOverlay(id) {
    const el = document.getElementById(id);
    if (!el) return;
    el.hidden = false;
    el.setAttribute('aria-hidden', 'false');
  }
  function hideOverlay(id) {
    const el = document.getElementById(id);
    if (!el) return;
    el.hidden = true;
    el.setAttribute('aria-hidden', 'true');
  }
  document.getElementById('openCC')?.addEventListener('click', () => showOverlay('controlCenter'));
  document.getElementById('openNotif')?.addEventListener('click', () => showOverlay('notificationShade'));
  document.getElementById('openRecents')?.addEventListener('click', () => showOverlay('recentsOverlay'));
  document.getElementById('openVolume')?.addEventListener('click', () => showOverlay('volumePanel'));
  document.querySelectorAll('.shade-backdrop').forEach((b) => {
    b.addEventListener('click', () => {
      const close = b.getAttribute('data-close');
      if (close === 'notifications') hideOverlay('notificationShade');
      if (close === 'control') hideOverlay('controlCenter');
      if (close === 'recents') hideOverlay('recentsOverlay');
      if (close === 'volume') hideOverlay('volumePanel');
    });
  });

  document.querySelectorAll('.close-btn[data-close]').forEach((btn) => {
    btn.addEventListener('click', () => {
      const close = btn.getAttribute('data-close');
      if (close === 'notifications') hideOverlay('notificationShade');
      if (close === 'control') hideOverlay('controlCenter');
      if (close === 'recents') hideOverlay('recentsOverlay');
      if (close === 'volume') hideOverlay('volumePanel');
    });
  });

  // swipe down from top to open notification shade; swipe up to close
  let touchStartY = null;
  phone.addEventListener('touchstart', (e) => { touchStartY = e.touches[0].clientY; }, { passive: true });
  phone.addEventListener('touchend', (e) => {
    if (touchStartY === null) return;
    const endY = e.changedTouches[0].clientY;
    const dy = endY - touchStartY;
    if (touchStartY < 60 && dy > 60) showOverlay('notificationShade');
    if (dy < -60) { hideOverlay('notificationShade'); hideOverlay('controlCenter'); hideOverlay('recentsOverlay'); hideOverlay('volumePanel'); }
    touchStartY = null;
  }, { passive: true });

  // ---------- Control center toggles ----------
  const toggles = [
    { id: 'wifi', label: 'Wi-Fi', icon: 'wifi', iconDark: null, demo: true },
    { id: 'bluetooth', label: 'Bluetooth', icon: 'bluetooth', iconDark: null, demo: true },
    { id: 'nfc', label: 'NFC', icon: 'nfc', iconDark: 'nfc-dark', demo: true },
    { id: 'power-saver', label: 'Power saver', icon: 'power-saver', iconDark: 'power-saver-dark', demo: true },
    { id: 'flashlight', label: 'Flashlight', icon: 'flashlight', iconDark: null, demo: true },
    { id: 'airplane', label: 'Airplane', icon: 'airplane', iconDark: null, demo: true },
    { id: 'location', label: 'Location', icon: 'location', iconDark: 'location-dark', demo: true },
    { id: 'auto-rotate', label: 'Auto-rotate', icon: 'auto-rotate', iconDark: 'auto-rotate-dark', demo: true },
  ];
  const state = {};
  toggles.forEach((t) => { state[t.id] = (t.id === 'wifi' || t.id === 'bluetooth'); });

  const ccGrid = document.getElementById('ccGrid');
  toggles.forEach((t) => {
    const btn = document.createElement('button');
    btn.className = 'tile' + (state[t.id] ? ' active' : '');
    btn.type = 'button';
    btn.setAttribute('data-toggle', t.id);
    btn.setAttribute('aria-pressed', String(state[t.id]));
    btn.setAttribute('aria-label', t.label + ' (visual demo toggle)');

    const bg = document.createElement('img');
    bg.className = 'tile-bg';
    bg.src = state[t.id]
      ? '../svg/control-center/tile-active.svg'
      : '../svg/control-center/tile-inactive.svg';
    btn.appendChild(bg);

    // icon overlay
    const icon = document.createElement('img');
    const baseIcon = '../svg/icons/' + t.icon + '.svg';
    const darkIcon = t.iconDark ? '../svg/icons/' + t.iconDark + '.svg' : null;
    icon.src = (root.getAttribute('data-theme') === 'dark' && darkIcon) ? darkIcon : baseIcon;
    if (darkIcon) { icon.setAttribute('data-base', baseIcon); icon.setAttribute('data-dark', darkIcon); icon.setAttribute('data-dark-swap', ''); }
    else { icon.classList.add('icon-no-dark'); }
    icon.style.position = 'absolute';
    icon.style.top = '18px';
    icon.style.left = '28px';
    icon.style.width = '24px';
    icon.style.height = '24px';
    btn.style.position = 'relative';
    btn.appendChild(icon);

    const lbl = document.createElement('span');
    lbl.className = 'tile-label';
    lbl.textContent = t.label;
    btn.appendChild(lbl);

    btn.addEventListener('click', () => {
      state[t.id] = !state[t.id];
      btn.classList.toggle('active', state[t.id]);
      btn.setAttribute('aria-pressed', String(state[t.id]));
      bg.src = state[t.id]
        ? '../svg/control-center/tile-active.svg'
        : '../svg/control-center/tile-inactive.svg';
    });

    ccGrid.appendChild(btn);
  });

  // ---------- Notifications ----------
  const notifications = [
    { title: 'Balanced Hyper', body: 'Visual demo notification card.', icon: '../svg/notifications/notification-icon.svg', iconDark: '../svg/notifications/notification-icon-dark.svg' },
    { title: 'System', body: 'All toggles are visual demos only.', icon: '../svg/notifications/notification-icon.svg', iconDark: '../svg/notifications/notification-icon-dark.svg' },
  ];
  const nList = document.getElementById('notificationList');
  function renderNotifications() {
    nList.innerHTML = '';
    notifications.forEach((n, idx) => {
      const card = document.createElement('div');
      card.className = 'notification-card';
      const cardBg = document.createElement('img');
      cardBg.className = 'card-bg';
      const baseCard = '../svg/notifications/notification-card.svg';
      const darkCard = '../svg/notifications/notification-card-dark.svg';
      cardBg.src = root.getAttribute('data-theme') === 'dark' ? darkCard : baseCard;
      cardBg.setAttribute('data-base', baseCard);
      cardBg.setAttribute('data-dark', darkCard);
      cardBg.setAttribute('data-dark-swap', '');
      card.appendChild(cardBg);

      const content = document.createElement('div');
      content.className = 'card-content';
      const ic = document.createElement('img');
      ic.className = 'n-icon';
      ic.src = root.getAttribute('data-theme') === 'dark' ? n.iconDark : n.icon;
      ic.setAttribute('data-base', n.icon);
      ic.setAttribute('data-dark', n.iconDark);
      ic.setAttribute('data-dark-swap', '');
      content.appendChild(ic);

      const text = document.createElement('div');
      text.className = 'n-text';
      const title = document.createElement('div');
      title.className = 'n-title';
      title.textContent = n.title;
      text.appendChild(title);
      const body = document.createElement('div');
      body.className = 'n-body';
      body.textContent = n.body;
      text.appendChild(body);
      const actions = document.createElement('div');
      actions.className = 'n-actions';
      const closeBtn = document.createElement('button');
      closeBtn.className = 'n-btn';
      closeBtn.type = 'button';
      closeBtn.setAttribute('aria-label', 'Dismiss notification (visual demo)');
      const closeImg = document.createElement('img');
      const closeBase = '../svg/notifications/notification-close.svg';
      const closeDark = '../svg/notifications/notification-close-dark.svg';
      closeImg.src = root.getAttribute('data-theme') === 'dark' ? closeDark : closeBase;
      closeImg.setAttribute('data-base', closeBase);
      closeImg.setAttribute('data-dark', closeDark);
      closeImg.setAttribute('data-dark-swap', '');
      closeBtn.appendChild(closeImg);
      closeBtn.addEventListener('click', () => { card.remove(); });
      const expBtn = document.createElement('button');
      expBtn.className = 'n-btn';
      expBtn.type = 'button';
      expBtn.setAttribute('aria-label', 'Expand notification (visual demo)');
      const expImg = document.createElement('img');
      const expBase = '../svg/notifications/notification-expand.svg';
      const expDark = '../svg/notifications/notification-expand-dark.svg';
      expImg.src = root.getAttribute('data-theme') === 'dark' ? expDark : expBase;
      expImg.setAttribute('data-base', expBase);
      expImg.setAttribute('data-dark', expDark);
      expImg.setAttribute('data-dark-swap', '');
      expBtn.appendChild(expImg);
      expBtn.setAttribute('aria-expanded', 'false');
      expBtn.addEventListener('click', () => {
        const expanded = card.classList.toggle('expanded');
        expBtn.setAttribute('aria-expanded', String(expanded));
        expImg.style.transform = expanded ? 'rotate(180deg)' : '';
      });
      actions.appendChild(closeBtn);
      actions.appendChild(expBtn);
      text.appendChild(actions);
      content.appendChild(text);
      card.appendChild(content);
      nList.appendChild(card);
    });
  }
  renderNotifications();

  // ---------- Recents ----------
  const recents = [
    { label: 'Browser' }, { label: 'Settings' }, { label: 'Photos' },
  ];
  const rList = document.getElementById('recentsList');
  recents.forEach((r) => {
    const card = document.createElement('div');
    card.className = 'recent-card';
    const bg = document.createElement('img');
    bg.className = 'card-bg';
    const base = '../svg/recents/recent-card.svg';
    const dark = '../svg/recents/recent-card-dark.svg';
    bg.src = root.getAttribute('data-theme') === 'dark' ? dark : base;
    bg.setAttribute('data-base', base);
    bg.setAttribute('data-dark', dark);
    bg.setAttribute('data-dark-swap', '');
    card.appendChild(bg);
    const lbl = document.createElement('div');
    lbl.className = 'card-label';
    lbl.textContent = r.label;
    card.appendChild(lbl);
    rList.appendChild(card);
  });
  document.getElementById('closeAllIcon')?.addEventListener('click', () => {
    rList.innerHTML = '';
  });

  // ---------- Sliders (visual only) ----------
  // no device control; values are in-memory only
  document.getElementById('brightnessSlider')?.addEventListener('input', () => {});
  document.getElementById('volumeSlider')?.addEventListener('input', () => {});
  document.getElementById('volumePanelSlider')?.addEventListener('input', () => {});

  // Volume panel mute toggle (visual demo)
  const muteToggle = document.getElementById('muteToggle');
  const volumePanelImg = document.getElementById('volumePanelImg');
  let muted = false;
  muteToggle?.addEventListener('click', () => {
    muted = !muted;
    if (muteToggle) muteToggle.textContent = muted ? 'Unmute' : 'Mute';
    const theme = root.getAttribute('data-theme') || 'light';
    const panelBase = muted ? '../svg/control-center/volume-panel-muted.svg' : '../svg/control-center/volume-panel.svg';
    const panelDark = muted ? '../svg/control-center/volume-panel-muted-dark.svg' : '../svg/control-center/volume-panel-dark.svg';
    if (volumePanelImg) {
      volumePanelImg.setAttribute('data-base', panelBase);
      volumePanelImg.setAttribute('data-dark', panelDark);
      volumePanelImg.setAttribute('src', theme === 'dark' ? panelDark : panelBase);
    }
    const volumeIcon = document.querySelector('.volume-panel-card .volume-icon');
    if (volumeIcon) {
      const iconBase = muted ? '../svg/icons/volume-mute.svg' : '../svg/icons/volume.svg';
      const iconDark = muted ? '../svg/icons/volume-mute-dark.svg' : '../svg/icons/volume-dark.svg';
      volumeIcon.setAttribute('data-base', iconBase);
      volumeIcon.setAttribute('data-dark', iconDark);
      volumeIcon.setAttribute('src', theme === 'dark' ? iconDark : iconBase);
    }
  });

  // Re-sync theme-dependent assets created after the initial applyTheme() call.
  applyTheme(root.getAttribute('data-theme') || 'light');

  // ---------- Ensure dark swap on theme change re-rend not needed; applyTheme handles data-dark-swap ----------
})();
