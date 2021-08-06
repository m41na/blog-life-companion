const { plus, minus, times, divide } = require('../../src/service/calc')

describe('Testing simple arithmetic ops', () => {

    test('plus should yield the sum of two numbers', () => {
        const sum = plus(3, 4)
        expect(sum).toBe(7)
    })

    test('minus should yield the differenrce bwteen two numbers', () => {
        const diff = minus(3, 4)
        expect(diff).toBe(-1)
    })

    test('times should yield the product of two numbers', () => {
        const product = times(3, 4)
        expect(product).toBe(12)
    })

    test('divide should yield the quotient of two numbers', () => {
        const quot = divide(3, 4)
        expect(quot).toBe(0.75)
    })
})