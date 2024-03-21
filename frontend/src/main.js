import './assets/main.css'
import 'tailwindcss/tailwind.css';
import '@/assets/tailwind.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persistedstate';
import App from './App.vue'
import router from './router'
import './index.css'

const app = createApp(App)

app.use(router)
// pinia
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(pinia);

app.mount('#app')
