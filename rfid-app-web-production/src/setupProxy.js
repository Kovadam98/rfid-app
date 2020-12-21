const { createProxyMiddleware } = require('http-proxy-middleware');
module.exports = function(app) {
    app.use('/production', createProxyMiddleware({ target: 'http://service-production:8080', changeOrigin: true }));
    app.use('/subscribe', createProxyMiddleware({ target: 'http://service-production:8080', changeOrigin: true }));
    app.use('/images', createProxyMiddleware({ target: 'http://service-resource:8080', changeOrigin: true }));
};

