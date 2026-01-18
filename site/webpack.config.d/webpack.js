config.resolve.modules.push("kotlin");
if (config.devServer) {
    config.devServer.client = {
        overlay: false
    };
    config.devServer.hot = true;
    config.devServer.open = false;
    config.devServer.port = 3000;
    config.devServer.historyApiFallback = true;
    config.devtool = 'eval-cheap-source-map';
} else {
    config.devtool = undefined;
}

// disable bundle size warning
config.performance = {
    assetFilter: function (assetFilename) {
        return !assetFilename.endsWith('.js');
    },
};

config.resolve.fallback = {
    ...config.resolve.fallback,
    fs: false, // kottage
    os: false // kottage
}
config.externals = {
    ...config.externals,
    "better-sqlite3": "better-sqlite3" // kottage
}