const MiniCssExtractPlugin = require('mini-css-extract-plugin')

config.plugins.push(new MiniCssExtractPlugin())
config.module.rules.push({
    test: /\.css$/,
    use: [
        MiniCssExtractPlugin.loader,
        'css-loader',
        '@tailwindcss/webpack'
    ]
});

/*
config.module.rules.push({
    test: /\.css$/,
    exclude: /node_modules/,
    use: [
      {
        loader: 'style-loader',
      },
      {
        loader: 'css-loader',
        options: {
          importLoaders: 1,
        }
      },
      {
        loader: 'postcss-loader',
        options: {
            postcssOptions: {
                plugins: [
                    [
                        "@tailwindcss/postcss",
                        {
                            // tailwindcss-postcss options here
                        }
                    ]
                ]
            }
        }
      }
    ]
});
*/