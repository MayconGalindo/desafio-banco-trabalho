package com.banco;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see com.banco.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {

    @Override
    public void init() {
        super.init();
    }
    
    @Override
    public Class<? extends WebPage> getHomePage() {
        return TelaLogin.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return AutenticarLogin.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return TelaLogin.class;
    }
}
