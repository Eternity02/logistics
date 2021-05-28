// vue.config.js for less-loader@6.0.0
module.exports = {
    css: {
        loaderOptions: {
            less: {
                lessOptions: {
                    // If you are using less-loader@5 please spread the lessOptions to options directly
                    modifyVars: {
                        'primary-color': '#7546C9',
                        'link-color': '#7546C9',
                        'border-radius-base': '4px',
                        'btn-primary-color': '#7546C9',
                        'btn-border-radius-base': '8px',
                    },
                    javascriptEnabled: true,
                },
            },
        },
    },
};
