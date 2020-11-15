module.exports = {
  devServer: {
    proxy: {
      '/api/': {
        target: 'http://192.168.8.11:8085/',
        // target: 'http://lw101192.xicp.net:23164/',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api/': ''
        }
      }
    }
  },
  // publicPath: './'
};
