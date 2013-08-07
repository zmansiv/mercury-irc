package com.mercuryirc.client.callback;

import com.mercuryirc.network.callback.CallbackAdapter;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

public class Script extends CallbackAdapter {

	private final ScriptingContainer ruby;

	public Script(String name) {
		this.ruby = new ScriptingContainer(LocalVariableBehavior.TRANSIENT);
	}

}