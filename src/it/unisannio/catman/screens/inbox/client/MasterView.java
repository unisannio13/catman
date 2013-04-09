package it.unisannio.catman.screens.inbox.client;

import it.unisannio.catman.common.client.widget.MasterItemListPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MasterView extends Composite implements Inbox.Master.View {
	interface Presenter {}

	private static MasterViewUiBinder uiBinder = GWT
			.create(MasterViewUiBinder.class);

	interface MasterViewUiBinder extends UiBinder<Widget, MasterView> {
	}
	
	@UiField SimplePanel northPanel;
	@UiField SimplePanel southPanel;
	@UiField MasterItemListPanel masterItemList;

	public MasterView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//FIXME Questa soluzione necessita la conoscenza a priori dei pannelli in pixel.
		//FIXME Forse non serve controllare getOffsetHeight!=0 dato che probabilmente non lo è mai
		int northPanelHeight = northPanel.getOffsetHeight()!=0?northPanel.getOffsetHeight():24;
		int southPanelHeight = southPanel.getOffsetHeight()!=0?southPanel.getOffsetHeight():24;
		masterItemList.setHeight((Window.getClientHeight() - northPanelHeight - southPanelHeight)+"px");		
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int height = event.getHeight();
				masterItemList.setHeight((height - northPanel.getOffsetHeight() - southPanel.getOffsetHeight()) + "px");
				//Window.alert(height + " -n:"+northPanel.getOffsetHeight()+ " -s:"+southPanel.getOffsetHeight());
			}
		});
	}

}
