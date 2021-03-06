package it.unisannio.catman.screens.warehouse.client;

import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import it.unisannio.catman.common.client.App;
import it.unisannio.catman.common.client.DataStore;
import it.unisannio.catman.common.client.ErrorHandler;
import it.unisannio.catman.common.client.Intent;
import it.unisannio.catman.common.client.ScreenActivity;
import it.unisannio.catman.domain.equipment.client.StockProxy;
import it.unisannio.catman.domain.equipment.client.WarehouseProxy;
import it.unisannio.catman.screens.warehouse.client.queries.StockQuery;

public class WarehousePresenter implements Warehouse.Presenter{
	
	private Warehouse.View view;
	private ScreenActivity screenActivity;
	private WarehouseProxy warehouse;
	
	public WarehousePresenter(Warehouse.View v, ScreenActivity activity, Intent intent) {
		this.view = v;
		this.screenActivity = activity;
		
		DataStore dataStore = App.getInstance().getDataStore();
		try{
			EntityProxyId<WarehouseProxy> entityId = dataStore.getProxyId(intent.get(0, ""));
			dataStore.warehouses().find(entityId).fire(new Receiver<WarehouseProxy>() {

				@Override
				public void onSuccess(final WarehouseProxy warehouse) {
					WarehousePresenter.this.warehouse = warehouse;
					view.setWarehouseProxy(warehouse);
					view.setStockProxyQuery(new StockQuery(warehouse,null));
				}

				@Override
				public void onFailure(ServerFailure error) {
					ErrorHandler.handle(error.getMessage()); 
				}
			});
		}catch(IllegalArgumentException e){
			ErrorHandler.handle(); 
		}
	
		
	}

	@Override
	public void goToStockScreen(StockProxy m) {
		String id = App.getInstance().getDataStore().getHistoryToken(m.stableId());
		screenActivity.goTo(new Intent("stock").withParams(id));
	}

	@Override
	public void executeSearch(String searchQuery) {
		view.setStockProxyQuery(new StockQuery(warehouse,searchQuery));
	}
}
