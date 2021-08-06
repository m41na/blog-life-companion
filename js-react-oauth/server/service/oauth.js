const axios = require('axios')
const moment = require('moment');
const jwt_decode = require('jwt-decode');
const randomstring = require('randomstring')
const pkce_challenge = require("pkce-challenge");
const { secrets } = global;

const oauthEndpointUrl = () => {
    const authorizationEndpoint = secrets.AUTHORIZATION_ENDPOINT
    const oauthScope = 'openid+profile+email+repo'
    const clientId = secrets.CLIENT_ID
    const callbackUrl = secrets.CALLBACK_URL
    const { code_verifier, code_challenge } = pkce_challenge();

    const location = `${authorizationEndpoint}`;
    const query = [
        `response_type=code&scope=${oauthScope}`,
        `client_id=${clientId}`,
        `state=${code_verifier}`,
        `nounce=${randomstring.generate(7)}`,
        `redirect_uri=${callbackUrl}`,
        `code_challenge=${code_challenge}`,
        `code_challenge_method=S256`
    ]
    return `${location}?${query.join("&")}`
}

const exchangeCodeForToken = async (code, code_verifier) => {
    const postBody = [
        'grant_type=authorization_code',
        `redirect_uri=${secrets.CALLBACK_URL}`,
        `client_id=${secrets.CLIENT_ID}`,
        `client_secret=${secrets.CLIENT_SECRET}`,
        `code_verifier=${code_verifier}`,
        `code=${code}`
    ]

    return await axios.post(secrets.TOKEN_ENDPOINT, postBody.join("&"), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
    .catch(normalizeError)
}

const validateIdToken = (idToken, nounce) => {
    const decoded = jwt_decode(idToken);
    //validate that 'iss' matches expected oauth issuer
    if (decoded.iss !== secrets.AUTHORIZATION_ENDPOINT) {
        throw Error('Token issuer does not match oauth provider')
    }
    //validate that 'aud' matches 'client-id'
    if (decoded.aud !== secrets.CLIENT_ID) {
        throw Error('Token audience does not match client id')
    }
    //validate that response 'nonce' values matches request 'nonce' value
    if (decoded.nounce !== nounce) {
        throw Error('Token nounce value does not match generated value')
    }
    //validate that request-time is between 'iat' and 'exp'
    const issuedAt = moment(decoded.iat);
    const expiresAt = moment(decoded.exp);
    const now = moment()
    if (now.isBefore(issuedAt) || now.isAfter(expiresAt)) {
        throw Error('Token validity is outside acceptable range')
    }
    return true
}

const refreshAccessToken = async (refresh_token) => {
    const postBody = {
        grant_type: "refresh_token",
        client_id: secrets.CLIENT_ID,
        client_secret: secrets.CLIENT_SECRET,
        refresh_token
    }

    return await axios.post(secrets.TOKEN_ENDPOINT, postBody)
        .catch(normalizeError)
}

const authorizationMetadata = async () => {
    return await axios.get(secrets.AUTHORIZATION_ENDPOINT)
        .catch(normalizeError)
}

const normalizeError = (err) => {
    const reason = err && err.response && err.response.data && err.response.data.errorSummary || err.response.data.error_description || 'not available'
    const status = err && err.response && err.response.status || 500
    return { error: true, message: err && err.response && err.response.data && err.response.data.error || err.message || 'No available', reason, status }
}

const clientName = secrets.CLIENT_NAME
const clientId = secrets.CLIENT_ID
const clientSecret = secrets.CLIENT_SECRET
const authorizationEndpoint = secrets.AUTHORIZATION_ENDPOINT
const introspectionEndpoint = secrets.INTROSPECTION_ENDPOINT
const revokeEndpoint = secrets.REVOKE_ENDPOINT
const tokenEndpoint = secrets.TOKEN_ENDPOINT
const oauthScope = secrets.OAUTH_SCOPE
const callbackUrl = secrets.CALLBACK_URL
const resourceServer = secrets.RESOURCE_SERVER_URL
const currentToken = "some value"

const exampleAuthEndpointUrl = `${authorizationEndpoint}?
response_type=code&
scope=offline_access+repo&
client_id=${clientId}&
state=79ef8f05c2a9fee2307f7868478f5d7c8ae1c3dd2e157ac10d4b0b42&
redirect_uri=${callbackUrl}&
code_challenge=rGAYmn1U9vhvPuqUn_iook83FTCIITLbVFABoPULN9w&
code_challenge_method=S256`

const exampleAuthEndpointWithAdditionalScopeUrl = `${authorizationEndpoint}?
response_type=code&
scope=offline_access+repo+repo:create+repo:delete&
client_id=${clientId}&
state=b6d7d49ff8ad30fe6da252bc5991153268bd19805210278c6c9860da&
redirect_uri=${callbackUrl}&
code_challenge=8W8gRNlujpXFWi6H3qKmg9jbDhtN6r92MAg47A9ZTtI&
code_challenge_method=S256`

const exampleTokenEndpointUrl = `curl -X POST ${tokenEndpoint} \
-d grant_type=authorization_code \
-d redirect_uri=${callbackUrl} \
-d client_id=${clientId} \
-d client_secret=2OiOrV7wU4pEq8ziVFHAcbmC4ra5I8iwEY5ScgOR \
-d code_verifier=b6d7d49ff8ad30fe6da252bc5991153268bd19805210278c6c9860da \
-d code=sSL0TrPPW4wqBPj-VA9L3kKm3ZiZlpNEv_n4lOMRrg4`

const exampleRefreshTokenEndpointUrl = `curl -X POST ${tokenEndpoint} \
-d grant_type=refresh_token \
-d client_id=${clientId} \
-d client_secret=2OiOrV7wU4pEq8ziVFHAcbmC4ra5I8iwEY5ScgOR \
-d refresh_token=akqzn-9dryV6zsLoymMd5K72tv_SN80XJA29kbpRI48`

const exampleIntrospectEndpointUrl = `curl ${introspectionEndpoint} \
-d client_id=${clientId} \
-d client_secret=2OiOrV7wU4pEq8ziVFHAcbmC4ra5I8iwEY5ScgOR \
-d token=${currentToken}`

const spaAuthEndpointUrl = `${authorizationEndpoint}?
response_type=code&
scope=repo&
client_id=0oa1e6ynajwMvuFPf5d7&
state=5e6663713b4b251a42e67f8a11fe6cc49cda4694576055ca02ff9e36&
redirect_uri=${callbackUrl}&
code_challenge=2iD4Nhi2eEDLDPo2UsWgStKLYAwxUAnKNSNx8vgtBSA&
code_challenge_method=S256`

const spaTokenEndpointUrl = `curl -X POST ${tokenEndpoint} \
-d grant_type=authorization_code \
-d redirect_uri=${callbackUrl} \
-d client_id=0oa1e6ynajwMvuFPf5d7 \
-d code_verifier=5e6663713b4b251a42e67f8a11fe6cc49cda4694576055ca02ff9e36 \
-d code=TSHzKqlEB8VrsFVLuIjEavHRZefiRNTOzIFnJey3MNY`

const apiTokenEndpointUrl = `curl -X POST ${tokenEndpoint} \
-d grant_type=client_credentials \
-d client_id=0oa1e77irsZ5IQX8V5d7 \
-d client_secret=ifXm4H5IftpSJH1RvN8ZQWnHRvWps-_l2POxPV4d \
-d scope=repo`

const apiTokenIntrospectionEndpointUrl = `curl ${introspectionEndpoint} \
-d client_id=0oa1e77irsZ5IQX8V5d7 \
-d client_secret=ifXm4H5IftpSJH1RvN8ZQWnHRvWps-_l2POxPV4d \
-d token=${currentToken}`

const apiRevokeTokenEndpointUrl = `curl ${revokeEndpoint} \
-d client_id=0oa1e77irsZ5IQX8V5d7 \
-d client_secret=ifXm4H5IftpSJH1RvN8ZQWnHRvWps-_l2POxPV4d \
-d token=${currentToken}`

const exampleOpenIdEndpointUrl = `${authorizationEndpoint}?
response_type=code&
scope=openid+profile+email&
client_id=${clientId}&
state=9ffb70c4db0b436f4755f6e91859f887de41ca17196881221607cb63&
redirect_uri=${callbackUrl}&
code_challenge=ujhvm6W4J4sAht0kFDkMzYIW1fxOPrPX-GQYyKBuCHQ&
code_challenge_method=S256`

const exampleIdTokenEndpointUrl = `curl -X POST ${tokenEndpoint} \
-d grant_type=authorization_code \
-d redirect_uri=${callbackUrl} \
-d client_id=${clientId} \
-d client_secret=2OiOrV7wU4pEq8ziVFHAcbmC4ra5I8iwEY5ScgOR \
-d code_verifier=9ffb70c4db0b436f4755f6e91859f887de41ca17196881221607cb63 \
-d code=EBwRsXlzmMqAh0W28uEj6YvWwFYdZ69WwYspmBh84mY`


module.exports = {
    oauthEndpointUrl,
    exchangeCodeForToken,
    validateIdToken,
    refreshAccessToken,
    authorizationMetadata,
}