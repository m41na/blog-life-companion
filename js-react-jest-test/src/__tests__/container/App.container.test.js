import { act } from '@testing-library/react-hooks'
import AppContainer from '../../container/App.container';

describe('Testing rendering of App container component', () => {

    test('AppContainer renders without a blow up', async () => {
        let component;
        await act(async () => {
            component = <AppContainer />
        })

        expect(component.props).toMatchObject({})
    })
})