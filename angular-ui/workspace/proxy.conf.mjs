export default [
  {
    context: [
      '/api/**',
    ],
    target: "http://localhost:8080",
    secure: false,
    changeOrigin: true,
    logLevel: "debug",
  },
];
