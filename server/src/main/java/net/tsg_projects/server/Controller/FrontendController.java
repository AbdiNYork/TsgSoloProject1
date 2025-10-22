package net.tsg_projects.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    // This matches any route that doesn't contain a period (to avoid static files like .js, .css)
    @RequestMapping(value = {"/{path:^(?!api|static|.*\\..*).*$}", "/{path:^(?!api|static|.*\\..*).*$}/**"})
    public String forward() {
        return "forward:/index.html";
    }
}
