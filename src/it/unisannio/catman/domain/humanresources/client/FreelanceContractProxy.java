package it.unisannio.catman.domain.humanresources.client;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

import it.unisannio.catman.domain.humanresources.FreelanceContract;
import it.unisannio.catman.domain.planning.client.PositionProxy;
import it.unisannio.catman.domain.workflow.client.EventProxy;

@ProxyFor(FreelanceContract.class)
public interface FreelanceContractProxy extends ContractProxy {
	EventProxy getEvent();
	
	void setPosition(PositionProxy p);
	PositionProxy getPosition();
}
