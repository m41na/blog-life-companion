const jwt_decode = require('jwt-decode');

const ACCESS_TOKENS_KEY = 'accessTokens';

const extractCookies = (req) => {
    const rawCookies = req.headers.cookie.split('; ');
    const parsedCookies = {};
    rawCookies.forEach(rawCookie => {
        const parsedCookie = rawCookie.split('=');
        parsedCookies[parsedCookie[0]] = parsedCookie[1];
    });
    return parsedCookies;
};

const saveTokens = (tokens, res) => {
    if (tokens && tokens.access_token) {
        const oneDayInSeconds = 24 * 60 * 60;
        res.cookie(ACCESS_TOKENS_KEY, tokens, {
            maxAge: oneDayInSeconds,
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production' ? true : false
        });
    }
}

const decodeTokens = (tokens) => {
    if (tokens && tokens.access_token) {
        const { access_token } = tokens;
        return jwt_decode(access_token)
    }
    return null
}

const useTokens = (req, res, next) => {
    const tokens = extractCookies(req)[ACCESS_TOKENS_KEY]

    if (tokens && tokens.access_token) {
        res.locals = tokens;
    }
    next()
}

const clearTokens = (req, res, next) => {
    const tokens = extractCookies(req)[ACCESS_TOKENS_KEY]

    if (tokens && tokens.access_token) {
        res.clearCookie(ACCESS_TOKENS_KEY)
    }
    next()
}

module.exports = {
    useTokens,
    saveTokens,
    clearTokens,
    decodeTokens,
}