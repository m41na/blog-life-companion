import { render, screen, cleanup } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from '../App';

describe('Testing rendering of App component', () => {

  afterEach(cleanup)

  test('the landing page is signin form when signedIn is false', async () => {
    const onSignIn = (login) => console.log('login attempt using:', login)
    let state = {
      signedIn: false,
      startRoute: '/login'
    }

    render(
      <MemoryRouter>
        <App app={state} onSignIn={onSignIn} />
      </MemoryRouter>
    );

    const signInForm = screen.getByTestId("signin-form");
    expect(signInForm).toBeInTheDocument();
  })

  test('the landing page is pricing page when signedIn is true', () => {
    const onSignIn = (login) => console.log('login attempt using:', login)
    let state = {
      signedIn: true,
      startRoute: '/login'
    }

    render(
      <MemoryRouter>
        <App app={state} onSignIn={onSignIn} />
      </MemoryRouter>
    );

    const pricingPage = screen.getByTestId("pricing-page");
    expect(pricingPage).toBeInTheDocument();
  });
})