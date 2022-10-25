import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// import path from "path";

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [vue()],
	server: {
		port: 3002,
	},
	resolve: {
		alias: {
			// "@/": `${path.resolve(__dirname, "src")}/`,
		},
	},	
	// define: {
	// 	"process.env": process.env,
	// },
});