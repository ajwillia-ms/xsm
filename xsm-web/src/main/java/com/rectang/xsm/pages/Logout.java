package com.rectang.xsm.pages;

import com.rectang.xsm.UserData;
import org.apache.wicket.PageParameters;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Andrew Williams
 * @version $Id: Logout.java 806 2010-05-26 21:55:04Z andy $
 * @since 2.0
 */
public class Logout extends XSMPage {
  public Logout(PageParameters parameters) {
    super(parameters);
  }

  public void layout() {
    /* userdata can be null if we just restarted, quietly pretend that we
     * logged out ;) */
    if (getXSMSession().getUser() != null) {
      UserData user = getXSMSession().getUser();
      getXSMSession().setUser(null);

      info("User " + user.getUsername() + " logged out of site " + user.getSite().getId() + ".");
      getXSMSession().setUser(null);
      getXSMSession().setSite(user.getSite());

      getResponse().redirect(user.getSite().getRootUrl());
    }

    super.layout();
  }
}
