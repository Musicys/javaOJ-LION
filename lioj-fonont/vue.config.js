const { defineConfig } = require("@vue/cli-service");
const MonacoWebpackPlugin = require("monaco-editor-webpack-plugin");

module.exports = defineConfig({
  transpileDependencies: true, // 是否需要转译依赖


  devServer: {
    proxy: {
      '/api': {
        target: process.env.VUE_APP_URL,
        changeOrigin: true, // 如果目标是https，则需要设置为true
        pathRewrite: { '^/api': '' }, // 重写路径，移除/api前缀
        ws: true, // 支持websocket
        secure: false, // 如果是自签名证书需要设置为false
      },
      // 可以为其他路径添加额外的代理规则
      /*
      '/other': {
        target: 'http://another-backend-server.com',
        changeOrigin: true,
        pathRewrite: {'^/other' : ''},
      }
      */
    }
  },
  chainWebpack(config) {
    // 添加 MoncaoWebpackPlugin
    config.plugin("monaco").use(new MonacoWebpackPlugin());

    // 禁用 TypeScript 类型检查
    config.plugins.delete("fork-ts-checker"); // 移除 fork-ts-checker 插件
  },
});