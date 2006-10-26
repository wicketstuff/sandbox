package org.stickywicket.renderer;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.stickywicket.misc.StickyWicketSession;

import wicket.model.LoadableDetachableModel;

public class NodeModel extends LoadableDetachableModel<Node> {
	private final String path;

	public NodeModel(Node node) {
		super(node);
		try {
			this.path = node.getPath();
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Node load() {
		try {
			return (Node) StickyWicketSession.get().getJcrSession().getItem(
					path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
