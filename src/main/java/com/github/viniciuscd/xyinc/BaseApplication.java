package com.github.viniciuscd.xyinc;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.github.viniciuscd.xyinc.endpoint.BuscaCepEndpoint;

@ApplicationPath("")
public class BaseApplication extends Application {

    /*
     * (non-Javadoc)
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(BuscaCepEndpoint.class);
    }
}
