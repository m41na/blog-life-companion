const axios = require('axios');

async function plus(left, right) {
    const sum = await axios.get(`http://localhost:8080/plus/${left}/${right}`)
    .then(res => res.data).catch(err => ({error: err.message}))
    return sum;
}

async function minus(left, right) {
    const diff = await axios.get(`http://localhost:8080/minus/${left}/${right}`)
    .then(res => res.data).catch(err => ({error: err.message}))
    return diff;
}

async function times(left, right) {
    const prod = await axios.get(`http://localhost:8080/times/${left}/${right}`)
    .then(res => res.data).catch(err => ({error: err.message}))
    return prod;
}

async function divide(left, right) {
    const quot = await axios.get(`http://localhost:8080/divide/${left}/${right}`)
    .then(res => res.data).catch(err => ({error: err.message}))
    return quot;
}

module.exports = {
    plus,
    minus,
    times,
    divide
}