/////////////////////////////////////////////////////////////////////////////
//
// Project   ProjectForge
//
// Copyright 2001-2009, Micromata GmbH, Kai Reinhard
//           All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.plugins.teamcal.dialog;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.projectforge.web.dialog.PFDialog;
import org.projectforge.web.wicket.components.SingleButtonPanel;

import de.micromata.wicket.ajax.AjaxCallback;

/**
 * @author Johannes Unterstein (j.unterstein@micromata.de)
 * 
 */
public abstract class TeamCalNameDialog extends PFDialog
{
  private static final long serialVersionUID = 8687197318833240410L;

  private Content content;

  /**
   * @param id
   * @param titleModel
   * @param filter
   */
  public TeamCalNameDialog(final String id, final IModel<String> titleModel)
  {
    super(id, titleModel);
  }

  /**
   * @see org.projectforge.web.dialog.PFDialog#onInitialize()
   */
  @Override
  protected void onInitialize()
  {
    super.onInitialize();
    final AjaxCallback okCallback = new AjaxCallback() {
      private static final long serialVersionUID = 7224559508934430123L;

      @Override
      public void callback(final AjaxRequestTarget target)
      {
        onOk(target, content.name);
      }
    };
    appendNewAjaxActionButton(okCallback, getString("save"), content.form, SingleButtonPanel.DEFAULT_SUBMIT);
  }

  /**
   * @param target
   * @param name
   */
  protected abstract void onOk(AjaxRequestTarget target, String name);

  /**
   * @see org.projectforge.web.dialog.PFDialog#getDialogContent(java.lang.String)
   */
  @Override
  protected Component getDialogContent(final String wicketId)
  {
    content = new Content(wicketId);
    return content;
  }

  /**
   * Inner class to represent the actual dialog content
   * 
   */
  private class Content extends Panel
  {
    private static final long serialVersionUID = -135497846745050310L;

    private Form<Void> form;

    private String name;

    /**
     * @param id
     */
    public Content(final String id)
    {
      super(id);
    }

    /**
     * @see org.apache.wicket.Component#onInitialize()
     */
    @Override
    protected void onInitialize()
    {
      super.onInitialize();
      form = new Form<Void>("form");
      add(form);
      form.add(new TextField<String>("name", new PropertyModel<String>(this, "name")));
    }
  }
}