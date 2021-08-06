const router = require('express').Router()
const {
    callback,
    oauthEndpointUrlHandler,
} = require('../handler')

router.get('/authorization-code/callback', callback);
router.get('/oauth', oauthEndpointUrlHandler);

module.exports = router