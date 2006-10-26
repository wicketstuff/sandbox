package org.stickywicket.renderer;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import wicket.MarkupContainer;
import wicket.extensions.markup.html.repeater.RepeatingView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class ContainerRenderer extends Panel<Node> {

	public ContainerRenderer(MarkupContainer parent, String id,
			IModel<Node> model) {
		super(parent, id, model);
		setRenderBodyOnly(true);
		RepeatingView repeater = new RepeatingView(this, "repeater");

		NodeIterator children;
		try {
			children = getModelObject().getNodes();
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
		while (children.hasNext()) {
			final Node child = children.nextNode();
			RendererFactory.newRenderer(repeater, repeater.newChildId(),
					new NodeModel(child));
		}

	}

}
