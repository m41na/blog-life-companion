function plus(left, right) {
    return Number(left) + Number(right)
}

function minus(left, right) {
    return Number(left) - Number(right)
}

function times(left, right) {
    return Number(left) * Number(right)
}

function divide(left, right) {
    return Number(left) / Number(right)
}

module.exports = {
    plus,
    minus,
    times,
    divide
}