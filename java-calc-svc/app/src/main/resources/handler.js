(function handle(req, resp)  {
    resp.setStatus(200);
    let out;
    try {
        out = resp.getWriter();
        out.println(Date.now());
    } catch (e) {
        e.printStackTrace();
    }
});