import React, { useState } from 'react'
import App from '../App'
import axios from 'axios'

const initialState = {
    signedIn: false,
    startRoute: '/login'
}

export default function AppContainer() {
    const [app, setApp] = useState(initialState)

    const onSignIn = async (login) => {
        const url = await axios.get('http://localhost:3003/api/oauth')
        .then(response => {
            return encodeURI(response.data)
        })
        window.location.assign(url);
        //setApp({ ...app, signedIn: login.email })
    }

    return <App app={app} onSignIn={onSignIn} />
}

