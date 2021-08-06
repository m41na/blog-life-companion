const express = require('express')
const cors = require('cors')

require('./service/secrets')
const router = require('./routes')
const app = express()
app.use(cors())
app.use(express.json())
app.use('/api', router)

const port = 3003
app.listen(port, () => {
  console.log(`React OAuth server listening at http://localhost:${port}`)
})
