let AppV4 = Java.type("works.hop.calc.svc.AppV4")
let app = new AppV4()
//initialize app
app.initServer();
app.initContextHandler();

//add handler
app.handle("/", function (req, resp)  {
    resp.setStatus(200);
    let out;
    try {
        out = resp.getWriter();
        out.println(Date.now());
    } catch (e) {
        e.printStackTrace();
    }
});

//start the server
app.start();