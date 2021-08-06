const CLEINT_URL = "http://localhost:3000"
const {
    oauthEndpointUrl,
    exchangeCodeForToken,
    validateIdToken,
    refreshAccessToken,
    authorizationMetadata,
} = require('../service/oauth')
const {saveTokens} = require('../middleware/tokens')

const callback = async (req, res) => {
    const { code, state } = req.query;
    if (code && state) {
        try {
            const response = await exchangeCodeForToken(code, state).catch(err => {
                return { error: true, message: err.message }
            });

            if (!response.error) {
                saveTokens(response.data, res)
                res.status(301).redirect(`${CLEINT_URL}?validated=true`)
            }
            else {
                res.status(response.status).send(response)
            }
        }
        catch (err) {
            res.status(503).send(err.message)
        }
    }
    else {
        res.status(400).send('There is no access code in the oauth response')
    }
}

const oauthEndpointUrlHandler = async (_, res) => {
    const url = oauthEndpointUrl();
    res.send(url)
}

module.exports = {
    callback,
    oauthEndpointUrlHandler,
}