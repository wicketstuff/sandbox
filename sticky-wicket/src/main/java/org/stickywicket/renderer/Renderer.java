package org.stickywicket.renderer;

import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stickywicket.jcr.JcrUtil;
import org.stickywicket.page.StickyPage;

import wicket.PageParameters;

public class Renderer extends StickyPage {
	private final Logger log = LoggerFactory.getLogger(Renderer.class);

	public Renderer(PageParameters params) {
		String path = "";
		int idx = 0;
		while (params.containsKey(String.valueOf(idx))) {
			path = path + "/";
			path = path + params.getString(String.valueOf(idx));
			idx++;
		}

		log.info("rendering path: " + path);

		Node node = JcrUtil.getNode(path);

		RendererFactory.newRenderer(this, "renderer", new NodeModel(node));
	}
}
