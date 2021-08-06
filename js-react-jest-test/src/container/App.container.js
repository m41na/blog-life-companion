import React, { useState } from 'react'
import App from '../App'

const initialState = {
    signedIn: false,
    startRoute: '/login'
}

export function handleSignIn(login) {
    console.log('login email is', login.email)
}

export default function AppContainer() {
    const [app, setApp] = useState(initialState)

    const onSignIn = login => {
        setApp({ ...app, signedIn: login.email })
        handleSignIn(login)
    }

    return <App app={app} onSignIn={onSignIn} />
}

