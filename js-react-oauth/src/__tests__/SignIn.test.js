import { render, screen, fireEvent, cleanup } from '@testing-library/react';
import SignIn from '../SignIn';

describe('test rendering sign in page', () => {

  afterEach(cleanup)

  test('renders signin form', () => {
    render(<SignIn />);
    const signInForm = screen.getByTestId("signin-form");
    expect(signInForm).toBeInTheDocument();
  });

  test('entering email address updates the input\'s value', async () => {
    await render(<SignIn />)
    const input = document.querySelector('#email')
    fireEvent.change(input, { target: { value: 'steve@email.com' } })
    expect(input.value).toBe('steve@email.com')
  })

  test('entering password updates the input\'s value', async () => {
    await render(<SignIn />)
    const input = document.querySelector('#password')
    fireEvent.change(input, { target: { value: 'secret' } })
    expect(input.value).toBe('secret')
  })

  test('submiting form without any input values sends the form\'s empty state', async () => {
    let formData = {}
    const onSignIn = (data) => ({...formData, ...data})
    await render(<SignIn onSignIn={onSignIn}/>)
    const button = screen.getByRole('button', {type: 'submit'})
    fireEvent.click(button)
    expect(formData).toMatchObject({})
  })

  test('submiting form after adding input values sends the form\'s completed state', async () => {
    let formData = {}
    const onSignIn = (data) => {
      formData = {...formData, ...data}
    }

    await render(<SignIn onSignIn={onSignIn}/>)
    const button = screen.getByRole('button', {type: 'submit'})

    //now input some form data
    const email = document.querySelector('#email')
    fireEvent.change(email, { target: { value: 'steve@email.com' } })

    const password = document.querySelector('#password')
    fireEvent.change(password, { target: { value: 'secret' } })

    fireEvent.click(button)
    expect(formData).toMatchObject({
      email: email.value,
      password: password.value
    })
  })
})
