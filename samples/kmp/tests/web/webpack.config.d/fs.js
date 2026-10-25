config.resolve = {
    fallback: {
        fs: false,
        path: false,
        crypto: false,
        os: false,
    },
},
config.devServer = {
    ...config.devServer,
    historyApiFallback: true,
};
