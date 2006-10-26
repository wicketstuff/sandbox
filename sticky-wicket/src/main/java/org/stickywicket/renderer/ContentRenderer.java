package org.stickywicket.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;

import org.stickywicket.misc.StickyWicketSession;

import wicket.MarkupContainer;
import wicket.extensions.markup.html.repeater.RepeatingView;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class ContentRenderer extends Panel<Node> {

	public ContentRenderer(MarkupContainer parent, String id,
			IModel<Node> model) {
		super(parent, id, model);
		setRenderBodyOnly(true);
		RepeatingView repeater = new RepeatingView(this, "repeater");

		for (Chunk chunk : getTemplateChunks()) {
			if (chunk instanceof MarkupChunk) {
				new Label(repeater, repeater.newChildId(),
						((MarkupChunk) chunk).getMarkup())
						.setRenderBodyOnly(true).setEscapeModelStrings(false);
			} else if (chunk instanceof ContainerChunk) {
				final String containerName = ((ContainerChunk) chunk)
						.getContainerName();
				Node containerNode;
				try {
					containerNode = getModelObject().getNode(containerName);
					new ContainerRenderer(repeater, repeater.newChildId(),
							new NodeModel(containerNode));
				} catch (PathNotFoundException e) {
					throw new RuntimeException(e);
				} catch (RepositoryException e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new RuntimeException("wtf");
			}
		}

	}

	private static final String idmarker = "sticky:id=\"";

	private List<Chunk> getTemplateChunks() {
		try {
			List<Chunk> chunks = new ArrayList<Chunk>();

			Node node = getModelObject();

			String content = node.getProperty("content").getValue().getString();

			int pos = 0;

			while (true) {
				int containerStart = content.indexOf("<sticky:container ", pos);

				if (containerStart < 0) {
					chunks.add(new MarkupChunk(content.substring(pos)));
					break;
				}

				chunks.add(new MarkupChunk(content.substring(pos,
						containerStart)));

				int idStart = content.indexOf(idmarker, containerStart);
				int idEnd = content.indexOf("\"", idStart + idmarker.length());

				String id = content.substring(idStart + idmarker.length(),
						idEnd);
				chunks.add(new ContainerChunk(id));

				int containerEnd = content.indexOf("/>", containerStart);

				pos = containerEnd + 2;
			}

			return chunks;

		} catch (ValueFormatException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (PathNotFoundException e) {
			throw new RuntimeException(e);
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
	}

	private static class Chunk {
	}

	private static class MarkupChunk extends Chunk {
		private final String markup;

		public MarkupChunk(String markup) {
			this.markup = markup;
		}

		public String getMarkup() {
			return markup;
		}
	}

	private static class ContainerChunk extends Chunk {
		private final String containerName;

		public ContainerChunk(String name) {
			this.containerName = name;
		}

		public String getContainerName() {
			return containerName;
		}

	}

}
