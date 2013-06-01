package it.unisannio.catman.screens.inbox.client;

import it.unisannio.catman.common.client.App;
import it.unisannio.catman.common.client.DataStore;
import it.unisannio.catman.common.client.ScreenActivity;
import it.unisannio.catman.domain.equipment.client.WarehouseProxy;
import it.unisannio.catman.domain.equipment.client.WarehouseRequest;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DialogBox;

public class MasterActivity extends ScreenActivity implements Inbox.Master {
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	//	Path p = getPath();
	//	Intent in = new Intent("inbox").withParams(String.valueOf(p.size() + 1));
		//panel.setWidget(new Hyperlink(p.toString(), pathTo(in).getToken()));
		panel.setWidget(new MasterView2(this));
	}
	

	@Override
	public void openNewDialog() {
		DataStore ds = App.getInstance().getDataStore();
		WarehouseRequest wr = ds.warehouses();
		WarehouseProxy wp = wr.create(WarehouseProxy.class);
		new WarehouseEditor(wp,wr);
		
	}

}
