module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}"
  ],
  theme: {
    extend: {
      spacing: {
        '3/4': '75%'
      }
    },
  },
  plugins: [
    require("@tailwindcss/forms")({
      strategy: 'class',
    }),
  ],
}
