package it.unisannio.catman.screens.inbox.client.widget;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import it.unisannio.catman.common.client.cell.AbstractCellAdapter;
import it.unisannio.catman.domain.workflow.client.DossierProxy;

public class DossierCellAdapter extends AbstractCellAdapter<DossierProxy>{

	@Override
	public SafeHtml getWest(DossierProxy d) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<img src='/prova.jpg' width='32px' height='32px' />");
		return sb.toSafeHtml();
	}

	@Override
	public SafeHtml getNorth(DossierProxy d) {
		return new SafeHtmlBuilder().appendEscaped("Mock").toSafeHtml();
	}

	@Override
	public SafeHtml getSouth(DossierProxy d) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<input type='checkbox'/>");
		sb.appendEscaped("mock");
		sb.appendHtmlConstant("&nbsp;-&nbsp;");
		sb.appendEscaped("mock");
		return sb.toSafeHtml();
	}

	@Override
	public SafeHtml getEast(DossierProxy d) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("<input type='checkbox'" + (d.isSelected()?"checked='checked'":"") + "/>");
		return sb.toSafeHtml();
	}
}