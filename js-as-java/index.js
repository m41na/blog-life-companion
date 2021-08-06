const express = require('express')
const router = require('./src/routes')

const app = express()
app.use(router)

const port = 3003
app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
