package me.ix.icks.utils;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

public class Hashmaps {
	
	private final static Map<UUID, UUID> tpas = Maps.newHashMap();
	
	public static Map<UUID, UUID> getTpas() {
		return tpas;
	}
}
