/** @type {import('tailwindcss').Config} */
export default {
  purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
	darkMode: false, // or 'media' or 'class'
  content: [],
  theme: {
    extend: {
      colors : {
        'theme-red' : '#CC0000',
        'theme-blue' : '#006FE8',
        'theme-navy' : '#0F1B4F',
        'theme-redbrown' : '#80112F'
      }
    },
  },
  plugins: [],
}

