const customTitlebar = require('custom-electron-titlebar');

new customTitlebar.Titlebar({
	title: "Fuck",
	app: "Fuck",
	theme: {
		/* Title */
		barTheme: 'dark', // light, dark
		barHeight: '22px', // Change this value if you set 'titleBarStyle' to 'hiddenInset'
		winBarHeight: '28px',
		barColor: '#fff',
		barTitleColor: 'inherit',
		barBackgroundColor: '#FF0000',
		barShowBorder: false,
		barBorderBottom: '1px solid #000',
		// should the icon be shown in the center of the toolbar on Mac/Linux apps alongside the app or title property
		showIconDarLin: true,
	
		/* Menu */
		menuStyle: 'horizontal', // horizontal, vertical
		menuDimItems: true,
		menuDimOpacity: 0.6,
		menuDisabledOpacity: 0.3,
		menuWidth: 240,
		menuBackgroundColor: '#fff',
		menuItemTextColor: '#fff',
		menuItemHoverBackground: 'rgba(255,255,255,0.3)',
		menuActiveTextColor: '#24292e',
		menuTextHighlightColor: '#fff',
		menuHighlightColor: '#0372ef',
		accentStatusIcon: false,
		menuSubLabelHeaders: true,
		menuSubLabelColor: '#6a737d',
		menuAcceleratorColor: '#6a737d',
		menuShowBoxShadow: true,
		menuSeparatorColor: '#e1e4e8',
		menuBoxShadow: '0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24)',
	
		/* Menu Overlay */
		menuOverlayBackground: 'black',
		menuOverlayOpacity: 0.4,
	
		/* WindowControls */
		windowControlsColor: '#fff',
		windowCloseHover: '#fff',
		windowCloseBackground: '#e81123',
		windowCloseActive: '#0000FF',
		windowDefaultBackground: 'rgba(255,255,255,0.3)',
		windowDefaultActive: 'rgba(255,255,255,0.2)',
	}
});