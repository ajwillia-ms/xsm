package com.rectang.xsm.pages.cms;

import org.jdom.Element;

import java.util.List;
import java.util.Vector;

import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.basic.Label;

/**
 * The list of available page links for WYSIWYG editing in CMS
 *
 * @author Andrew Williams
 * @version $Id: LinkList.java 802 2009-05-16 17:25:24Z andy $
 * @since 2.0
 *
 * @plexus.component role="org.apache.wicket.Page" role-hint="page-linklist"
 */
public class LinkList extends DocumentPage {
  public void layout() {
    List files = new Vector();

    if (getDoc() != null) {
      Element rootElem = getDoc().getContentElement();
      if (rootElem != null) {
        Element links = rootElem.getChild("files");
        if (links != null) {
          files = links.getChildren("file");
        }
      }
    }

    add(new ListView("links", files) {
      protected void populateItem(ListItem listItem) {
        Element next = (Element) listItem.getModelObject();
        String path = getDocumentPage().getPath() + "/_files/" + next.getChildText("path");
        String caption = next.getChildText("caption");
        if (caption == null || caption.equals(""))
          caption = next.getChildText("path");

        String line = "  [\"" + caption + "\", \"" + getXSMSession().getSite().getPrefixUrl() + path + "\"]";
        if (listItem.getIndex() < ((List)listItem.getParent().getModelObject()).size() - 1)
          line += ",\n";

        listItem.add(new Label("link", line).setEscapeModelStrings(false).setRenderBodyOnly(true));
        listItem.setRenderBodyOnly(true);
      }
      
    }.setRenderBodyOnly(true));
  }
}
