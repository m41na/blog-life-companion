import React from 'react'
import {
  Switch,
  Route,
  Redirect
} from "react-router-dom";
import SignIn from './SignIn';
import Pricing from './Pricing';

export default function App({ app: { signedIn, startRoute }, onSignIn }) {

  return (
    <Switch>
      <Route path="/login">
        {signedIn ? <Redirect to="/" /> : <SignIn onSignIn={onSignIn} />}
      </Route>
      <Route path="/">
        {!signedIn ? <Redirect to={startRoute} /> : <Pricing />}
      </Route>
    </Switch>
  );
}