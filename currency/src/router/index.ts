import { createRouter, createWebHistory } from "vue-router";

import routes from "./routes";

const routerHistory = createWebHistory();

const router = createRouter({
	history: routerHistory,
	routes: routes,
	strict: true,
	sensitive: true,
	scrollBehavior(to, from, savedPosition) {
		// if (to.name !== from.name) document.getElementById("app").scrollIntoView();
	},
});

export default router;
