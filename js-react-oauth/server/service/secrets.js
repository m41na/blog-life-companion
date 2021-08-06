const fs = require('fs')

const envVariables = [
    'CLIENT_NAME', 
    'CLIENT_ID', 
    'CLIENT_SECRET', 
    'AUTHORIZATION_ENDPOINT',
    'INTROSPECTION_ENDPOINT',
    'TOKEN_ENDPOINT', 
    'OAUTH_SCROP', 
    'CALLBACK_URL', 
    'RESOURCE_SERVER_URL'
]

function initSecrets() {
    if (process.env.NODE_ENV !== 'production') {
        const filepath = './server/.local'
        if (fs.existsSync(filepath)) {
            const data = fs.readFileSync(filepath, 'utf-8');
            if (data) {
                const pattern = /^(.+)?=(.*)$/
                const secrets = data.split('\n').reduce((acc, line) => {
                    if (pattern.test(line)) {
                        const match = pattern.exec(line)
                        const secret = match[1]
                        const value = match[2]
                        console.log('key', secret, 'value', value)
                        acc[secret] = value
                    }
                    return acc;
                }, {})
                global.secrets = Object.freeze(secrets)
            }
        }
    }
    else {
        let secrets = {}
        envVariables.forEach(variable => secrets[variable] = process.env[variable])
        global.secrets = Object.freeze(secrets)
        console.log(secrets)
    }
}

module.exports = initSecrets()