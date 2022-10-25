module.exports = {
	important: true,
	content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}", "./node_modules/tw-elements/dist/js/**/*.js"],
	theme: {
		extend: {
			screens: {
				xs: "375px",
				"2xl": "1440px",
				"3xl": "1640px",
				"4xl": "1920px",
			},
			maxWidth: {
				default: "120rem",
				container: "80rem",
			},
			boxShadow: {},
			transitionProperty: {},
			animation: {
				"spin-rotate": "spin-rotate .45s ease-in-out forwards",
				"transfer-to-right": "transfer-to-right .2s cubic-bezier(.165, .840, .440, 1.000) forwards",
				"show-x": "show-x .4s cubic-bezier(.165, .840, .440, 1.000)",
				"pulse-1": "pulse-1 1.4s cubic-bezier(0.4, 0, 0.6, 1) infinite",
			},
			keyframes: {
				"pulse-1": {
					"50%": {
						opacity: 0.35,
					},
				},
				"spin-rotate": {
					"0%": {
						transform: "translate(0, -60%) scale(1.5) rotate(-270deg)",
					},
					"100%": {
						transform: "translate(0, 0) scale(1) rotate(0deg)",
					},
				},
				"transfer-to-right": {
					"0%": {
						transform: "translate(0, -5%)",
					},
					"50%": {
						transform: "translate(0, 5%) scale(1.05)",
					},
					"100%": {
						transform: "translate(0, 0) scale(1)",
					},
				},
				"show-x": {
					"0%": {
						transform: "scaleX(0.4) scaleY(1)",
					},
					"100%": {
						transform: "scaleX(1) scaleY(1)",
					},
				},
			},
		},
	},
	plugins: [],
};
