let app = new AppV3()
//initialize app
app.initServer();
app.initContextHandler();

//add handler
app.addHandler("/", function (req, resp)  {
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