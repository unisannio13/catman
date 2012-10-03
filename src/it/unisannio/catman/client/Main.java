package it.unisannio.catman.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class Main implements EntryPoint {

	@Override
	public void onModuleLoad() {
		initNavigation();
		initMenu();	
	}
	
	private void initMenu() {
		RootPanel root = RootPanel.get("navigation");
		StringBuffer buf = new StringBuffer("<ul id=\"navigation\">");
		for(Unit u : Application.get().getUnits()) {
			buf
				.append("<li><a href=\"#\" title=\"")
				.append(u.getName())
				.append("\">")
				.append(u.getIcon().getCharacter())
				.append("</a></li>");
		}
		buf.append("</ul>");
		root.add(new HTML(buf.toString()));
	}

	private void initNavigation() {
		Application app = Application.get();
		EventBus eventBus = app.getEventBus();
        PlaceController placeController = app.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper masterActivityMapper = new MasterActivityMapper();
        ActivityManager masterActivityManager = new ActivityManager(masterActivityMapper, eventBus);
        
        SimplePanel master = new SimplePanel();
        RootPanel.get("master").add(master);
        masterActivityManager.setDisplay(master);
        
        ActivityMapper detailActivityMapper = new DetailActivityMapper();
        ActivityManager detailActivityManager = new ActivityManager(detailActivityMapper, eventBus);
        
        SimplePanel detail = new SimplePanel();
        RootPanel.get("detail").add(detail);
        detailActivityManager.setDisplay(detail);

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        MainPlaceHistoryMapper historyMapper = GWT.create(MainPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, new UnitPlace(app.getUnits().peek())); // FIXME

        
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
	}
}
