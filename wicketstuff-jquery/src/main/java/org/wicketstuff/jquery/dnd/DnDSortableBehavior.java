/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jquery.dnd;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.IBehaviorListener;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.jquery.JQueryInterfaceBehavior;
import org.wicketstuff.jquery.Options;

// TODO: disable callback to serverside if clientsideonly
@SuppressWarnings("serial")
class DnDSortableBehavior extends JQueryInterfaceBehavior implements IBehaviorListener {
    // create a reference to the base javascript file.
    // we use JavascriptResourceReference so that the included file will have
    // its comments stripped and gzipped.
    private static final JavascriptResourceReference DNDSORTABLEBEHAVIOR_JS = new JavascriptResourceReference(DnDSortableBehavior.class, DnDSortableBehavior.class.getSimpleName() + ".js");

    protected Options options_;

    protected ArrayList<MarkupContainer> containers_;

    public DnDSortableBehavior() throws Exception {
        this(null);
    }

    /**
     * Create a DnDSortableBehavior with default options override.
     * <ul>
     * <li> options include every optionsof the js component (see http://interface.eyecon.ro/docs/sort for the
     * base list of options).</li>
     * <li> "containerclass" : the CSS' class of every container to be sortable (default is bind component (handler) + "_dndContainer"</li>
     * <li> "startOnLoad" : boolean, true => sortable feature is started on page load (default) else, the client side must call the JSFunctionName4Start.</li>
     * <ul>
     *
     * @param options
     * @see http://interface.eyecon.ro/docs/sort
     */
    public DnDSortableBehavior(Options options) throws Exception {
        super();
        if (options == null) {
            options = new Options();
        }
        options_ = options;
        options_.set("accept", "dndItem", false)
            .set("helperclass", "sortHelper", false)
            .set("activeclass", "sortableactive", false)
            .set("hoverclass", "sortablehover", false)
            //.set("handle", ".dndItem", false)
            .set("tolerance", "pointer", false)
            .set("startOnLoad", Boolean.TRUE, false)
        ;
        containers_ = new ArrayList<MarkupContainer>();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavascriptReference(DNDSORTABLEBEHAVIOR_JS);
        response.renderString(getHead());
    }

    private CharSequence getHead() {
        try {
            // load the css template we created form the res package
            PackagedTextTemplate template = new PackagedTextTemplate(DnDSortableBehavior.class, DnDSortableBehavior.class.getSimpleName() + "-head.tmpl");
            // create a variable subsitution map
            CharSequence itemSelector = "." + options_.get("accept");
            CharSequence handleSelector = (CharSequence)options_.get("handle");
            if (handleSelector == null) {
                //only for CSS
                handleSelector = itemSelector;
            }
            HashMap<String, CharSequence> params = new HashMap<String, CharSequence>();
            params.put("containerSelector", "."+ getContainerCSSClass());
            params.put("helperclass", options_.get("helperclass", "").toString());
            params.put("handleSelector", handleSelector);
            params.put("itemSelector", itemSelector);
            params.put("options", options_.toString(true));
            params.put("callbackUrl", getCallbackUrl());
            params.put("dndHandlerStart", getJSFunctionName4Start());
            params.put("dndHandlerStop", getJSFunctionName4Stop());
            params.put("startOnLoad", options_.get("startOnLoad", "true").toString());
            // perform subsitution and return the result
            CharSequence back = template.asString(params);
            return back;
        } catch (RuntimeException exc) {
            throw exc;
        } catch (Exception exc) {
            throw new RuntimeException("wrap: " + exc.getMessage(), exc);
        }
    }

    private CharSequence getContainerCSSClass() throws Exception {
        CharSequence back = (CharSequence)options_.get("containerclass", null);
        if (back == null) {
            back = getComponent().getId() + "_dndContainer";
        }
        return back;
    }

    /**
     * @return the name of the javascript function to start the behavior on client side.
     */
    public CharSequence getJSFunctionName4Start() {
        return getComponent().getId() + "_dndStart";
    }

    /**
     * @return the name of the javascript function to stop the behavior on client side.
     */
    public CharSequence getJSFunctionName4Stop() {
        return getComponent().getId() + "_dndStop";
    }

    @Override
    public final void respond(AjaxRequestTarget target) {
        try {
            Request req = RequestCycle.get().getRequest();
            if (logger().isDebugEnabled()) {
                logger().debug("params : {}", req.getRequestParameters());
            }
            onDnD(target,
                    //req.getParameter("itemId"),
                    req.getParameter("srcContainerId"),
                    Integer.parseInt(req.getParameter("srcPosition")),
                    req.getParameter("destContainerId"),
                    Integer.parseInt(req.getParameter("destPosition"))
            );
        } catch (RuntimeException exc) {
            throw exc;
        } catch (Exception exc) {
            throw new RuntimeException("wrap: " + exc.getMessage(), exc);
        }
    }

    /**
     * Call when a component has been moved on client side.
     * The default implementation log (as debug) the incomming parameters,
     * search the component and forward to onDnD(AjaxRequestTarget target, String itemId, String srcContainerId, int srcPos, String destContainerId, int destPos).
     *
     * @param target a target, provide if a response,
     * @param srcContainer the source container from where item come, (null if not previously registered by via registerContainer(...)).
     * @param srcPos the position/index of item into srcContainer before moving.
     * @param destContainer the destination container where item is, (null if not previously registered by via registerContainer(...)).
     * @param destPos the position/index of item into srcContainer after moving.
     * @throws Exception
     */
    public void onDnD(AjaxRequestTarget target, String srcContainerId, int srcPos, String destContainerId, int destPos) throws Exception {
        if (logger().isDebugEnabled()) {
            logger().debug("srcContainerId={}, srcPos={}, destContainerId={}, destPos={}", new Object[]{
                    srcContainerId,
                    srcPos,
                    destContainerId,
                    destPos
            });
        }
        MarkupContainer srcContainer = null;
        MarkupContainer destContainer = null;
        for(MarkupContainer container : containers_) {
            if ((srcContainerId != null) && srcContainerId.equals(container.getMarkupId())) {
                srcContainer = container;
            }
            if ((destContainerId != null) && destContainerId.equals(container.getMarkupId())) {
                destContainer = container;
            }
            if ((srcContainer != null) && (destContainer != null)) {
                break;
            }
        }
//        if (srcContainer != null) {
//            item = findByMarkupId(srcContainer, itemId);
//        }
//        if ((item == null) && (destContainer != null)) {
//            item = findByMarkupId(destContainer, itemId);
//        }
        boolean updateContainers = onDnD(target, srcContainer, srcPos, destContainer, destPos);
        if (updateContainers && (target != null)) {
            // target is null in testcase
            // (optional) if you need to keep in sync component, markupId on serverside and client side
            target.addComponent(srcContainer);
            if (srcContainer != destContainer) {
                target.addComponent(destContainer);
            }
            target.appendJavascript(getJSFunctionName4Start() + "();");
        }
    }

    /**
     * Call when a component has been moved on client side (to be overwrited).
     * The default implementation log (as debug) the incomming parameters.
     * @param target a target, provide if a response,
     * @param item the moved component(null if not a child (direct or indirect) of srcContainer or destContainer.
     * @param srcContainer the source container from where item come, (null if not previously registered by via registerContainer(...)).
     * @param srcPos the position/index of item into srcContainer before moving.
     * @param destContainer the destination container where item is, (null if not previously registered by via registerContainer(...)).
     * @param destPos the position/index of item into srcContainer after moving.
     * @return false if you don't need to keep in sync component, markupId on serverside and client side,
     *  else return true to send to client side the srcContainer and destContainer and to update the handler (consume more resource, server, network, client).
     * @throws Exception
     */
    public boolean onDnD(AjaxRequestTarget target, MarkupContainer srcContainer, int srcPos, MarkupContainer destContainer, int destPos) throws Exception {
        if (logger().isDebugEnabled()) {
            logger().debug("srcContainer={}, srcPos={}, destContainer={}, destPos={}", new Object[] { srcContainer, srcPos, destContainer, destPos });
        }
        return false;
    }

    /**
     * Register a container as a container for draggable/droppable items.
     * (add the css class and markupId to be find on clientside).
     *
     * @param v the container to register.
     * @return this
     * @throws Exception
     */
    protected DnDSortableBehavior registerContainer(MarkupContainer v) throws Exception {
        v.add(new SimpleAttributeModifier("class", getContainerCSSClass()));
        v.setOutputMarkupId(true);
        containers_.add(v);
        return this;
    }

    /**
     * Register a component as draggable/moveable item.
     * (add the css class and markupId to be find on clientside).
     *
     * @param v the component to register.
     * @return this
     * @throws Exception
     */
    protected DnDSortableBehavior registerItem(Component v) throws Exception {
        v.add(new SimpleAttributeModifier("class", String.valueOf(options_.get("accept"))));
        v.setOutputMarkupId(true);
        return this;
    }



}
