package it.unisannio.catman.screens.inbox.client;

import java.util.List;

import it.unisannio.catman.common.client.AbstractQuery;
import it.unisannio.catman.common.client.App;
import it.unisannio.catman.common.client.DataStore;
import it.unisannio.catman.common.client.Query;
import it.unisannio.catman.common.client.QueryDataProvider;
import it.unisannio.catman.common.client.cell.InteractiveCellAdapter;
import it.unisannio.catman.common.client.ui.DataList;
import it.unisannio.catman.domain.workflow.client.CustomerProxy;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Request;

public class MasterView extends Composite {

	private static MasterViewUiBinder uiBinder = GWT.create(MasterViewUiBinder.class);

	interface MasterViewUiBinder extends UiBinder<Widget, MasterView> {
	}

	@UiField Button makeNew;
	
	@UiField DataList<CustomerProxy> dataList;
	
	private Inbox.Master activity;

	public MasterView(Inbox.Master activity) {
		initWidget(uiBinder.createAndBindUi(this));

		this.activity = activity;
		
		dataList.setPageSize(20);
		
		dataList.setCellAdapter(new InteractiveCellAdapter<CustomerProxy>() {

			@Override
			public SafeHtml getNorth(CustomerProxy object) {
				return new SafeHtmlBuilder().appendEscaped(object.getName()).toSafeHtml();
			}

		});
		
		final DataStore store = App.getInstance().getDataStore();
		
		Query<CustomerProxy> query = new AbstractQuery<CustomerProxy>() {

			@Override
			public Request<List<CustomerProxy>> list(int start, int length) {
				return store.customers().listAll(start, length);
			}

			@Override
			public Request<Integer> count() {
				return store.customers().count();
			}
		};
		
		dataList.setDataProvider(new QueryDataProvider<CustomerProxy>(query));
		
		
	}

	@UiHandler("makeNew")
	void handleNew(ClickEvent e) {
		activity.openNewDialog();
	}

	
	@UiHandler("dataList")
	void handleCellClick(ClickEvent e) {
		Window.alert("Hello "+ ((CustomerProxy) e.getSource()).getName());
	}
	
}
