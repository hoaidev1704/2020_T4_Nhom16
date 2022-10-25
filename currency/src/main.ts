import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import routes from "./router";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
createApp(App).use(routes).use(Antd).mount('#app');