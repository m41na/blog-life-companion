const router = require('express').Router()
const {
    plusHandler,
    minusHandler,
    timesHandler,
    divideHandler
} = require('../handler')

router.get('/plus/:left/:right', plusHandler);
router.get('/minus/:left/:right', minusHandler);
router.get('/times/:left/:right', timesHandler);
router.get('/divide/:left/:right', divideHandler);

module.exports = router