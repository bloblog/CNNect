import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  assetsInclude: ["**/*.PNG"],
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/daum': {
        target: 'https://dic.daum.net',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/daum/, '')
      },
      '/naverapi': {
        target: 'https://clovaspeech-gw.ncloud.com',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/naverapi/, '')
      },
    },
  },
})