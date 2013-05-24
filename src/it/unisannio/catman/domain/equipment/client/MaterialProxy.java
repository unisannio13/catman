package it.unisannio.catman.domain.equipment.client;

import it.unisannio.catman.domain.equipment.Material;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(Material.class)
public interface MaterialProxy extends EntityProxy {
	String getName();
}
