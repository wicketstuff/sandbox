package org.wicketstuff.pickwick.bean;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("users")
public class Users extends HashMap<String, User>{}

