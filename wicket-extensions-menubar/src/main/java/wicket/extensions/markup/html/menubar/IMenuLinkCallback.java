package wicket.extensions.markup.html.menubar;

import java.io.Serializable;

import wicket.ajax.AjaxRequestTarget;

public interface IMenuLinkCallback extends Serializable
{
    void onClick(AjaxRequestTarget target);
}
