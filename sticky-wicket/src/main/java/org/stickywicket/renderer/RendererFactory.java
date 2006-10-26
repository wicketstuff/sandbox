package org.stickywicket.renderer;

import java.lang.reflect.Constructor;

import javax.jcr.Node;

import org.stickywicket.misc.StickyWicketSession;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

public class RendererFactory {
	private RendererFactory() {

	}

	private static final Class[] constructorArgs = new Class[] {
			MarkupContainer.class, String.class, IModel.class };

	public static Component newRenderer(MarkupContainer parent, String string,
			IModel<Node> nodeModel) {
		Node node = nodeModel.getObject();
		try {
			String type = node.getProperty("type").getString();

			if (NodeType.CONTENT.toString().equals(type)) {
				Class clazz = ContentRenderer.class;
				Constructor constructor = clazz.getConstructor(constructorArgs);
				Object[] args = new Object[] { parent, string, nodeModel };
				return (Component) constructor.newInstance(args);
			} else if (NodeType.CONTENT_REFERENCE.toString().equals(type)) {
				String path = node.getProperty("ref").getString();
				Node ref = StickyWicketSession.get().getJcrSession()
						.getRootNode().getNode(path.substring(1));
				Class clazz = ContentRenderer.class;
				Constructor constructor = clazz.getConstructor(constructorArgs);
				Object[] args = new Object[] { parent, string,
						new NodeModel(ref) };
				return (Component) constructor.newInstance(args);
			} else {
				throw new RuntimeException("unknown node type [["
						+ type.toString() + "]");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
