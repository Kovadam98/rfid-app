const { createProxyMiddleware } = require('http-proxy-middleware');
module.exports = function(app) {
    app.use('/order', createProxyMiddleware({ target: 'http://service-order:8080', changeOrigin: true }));
    app.use('/images', createProxyMiddleware({ target: 'http://service-resource:8080', changeOrigin: true }));
};

