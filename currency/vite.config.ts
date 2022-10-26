import { defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
// import path from "path";

// https://vitejs.dev/config/


export default ({ mode }) => {
    // import.meta.env.VITE_NAME available here with: process.env.VITE_NAME
    // import.meta.env.VITE_PORT available here with: process.env.VITE_PORT
    return defineConfig({
        plugins: [vue()],
        server: {
            port: 3032,
        },
    });
}

