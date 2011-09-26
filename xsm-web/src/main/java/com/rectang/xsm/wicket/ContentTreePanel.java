package com.rectang.xsm.wicket;

import com.rectang.xsm.site.HierarchicalPage;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;
import org.codehaus.plexus.wicket.PlexusPageFactory;

import com.rectang.xsm.site.Page;
import com.rectang.xsm.XSM;

/**
 * A simple tree renderer
 *
 * @author Andrew Williams
 * @version $Id: ContentTreePanel.java 831 2011-09-25 12:59:18Z andy $
 * @since 1.0
 */
public class ContentTreePanel extends Panel {
  public ContentTreePanel(String id, HierarchicalPage rootPage, final String current,
                          final String viewType) {
    super(id);

    add(new ListView("pages", rootPage.getSubPages()) {

      protected void populateItem(ListItem listItem) {
        Page page = (Page) listItem.getModelObject();
        Class linkClass =
            ((PlexusPageFactory) getSession().getPageFactory())
                .getPageClass(page.getType() + "-" + viewType);

        PageParameters params = new PageParameters();
        params.add("page", page.getPath());
        listItem.add(new Image("page-icon", new ResourceReference(XSM.class,
            "icons/" + page.getIcon())));
        BookmarkablePageLink link = new BookmarkablePageLink("page", linkClass, params);
        listItem.add(link);

        String title = page.getTitle();
        if (page.getHidden()) {
          title = "(" + title + ")";
        }
        link.add(new Label("page-label", title));

        if (page.getPath().equals(current)) {
          listItem.add( new AttributeModifier( "class", new Model() {
            public Object getObject() {
              return "xsm_menu_item xsm_menu_item_selected";
            }
          }));
        }

        if (page instanceof HierarchicalPage
            && ((HierarchicalPage) page).getSubPages().size() > 0) {
          listItem.add(new ContentTreePanel("subpages", (HierarchicalPage) page,
              current, viewType));
        } else {
          listItem.add(new WebMarkupContainer("subpages").setVisible(false));
        }
      }
    });
  }
}
