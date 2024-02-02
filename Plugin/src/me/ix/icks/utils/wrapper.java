package me.ix.icks.utils;

public class wrapper {
	
	static String plugin_name = "icksGenerals";
	static String plugin_version = "1.0";
	static String plugin_creator = "ix";
	static String server_name = "ix's Server";
	
	public static String getServerName() {
		return server_name;
	}
	
	public static String getName() {
		return plugin_name;
	}
	
	public static String getVersion() {
		return plugin_version;
	}
	
	public static String getCreator() {
		return plugin_creator;
	}
	
	public static String getStartText() {
		return "[" + getName() + "] ";
	}
}
