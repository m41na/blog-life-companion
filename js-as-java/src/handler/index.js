const { plus, minus, times, divide } = require('../service/calcapi')

const plusHandler = async (req, res) => {
    const { left, right } = req.params;
    try {
        const sum = await plus(left, right)
        console.log(`${left} + ${right} = ${sum}`)
        res.send({ data: sum })
    }
    catch (err) {
        res.status(503).send(err.message)
    }
}

const minusHandler = async (req, res) => {
    const { left, right } = req.params;
    try {
        const diff = await minus(left, right)
        console.log(`${left} - ${right} = ${diff}`)
        res.send({ data: diff })
    }
    catch (err) {
        res.status(503).send(err.message)
    }
}

const timesHandler = async (req, res) => {
    const { left, right } = req.params;
    try {
        const mult = await times(left, right)
        console.log(`${left} * ${right} = ${mult}`)
        res.send({ data: mult })
    }
    catch (err) {
        res.status(503).send(err.message)
    }
}

const divideHandler = async (req, res) => {
    const { left, right } = req.params;
    try {
        const quot = await divide(left, right)
        console.log(`${left} / ${right} = ${quot}`)
        res.send({ data: quot })
    }
    catch (err) {
        res.status(503).send(err.message)
    }
}

module.exports = {
    plusHandler,
    minusHandler,
    timesHandler,
    divideHandler
}