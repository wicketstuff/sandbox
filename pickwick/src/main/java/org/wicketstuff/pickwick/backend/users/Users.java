package org.wicketstuff.pickwick.backend.users;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("users")
public class Users extends HashMap<String, UserBean>{}

