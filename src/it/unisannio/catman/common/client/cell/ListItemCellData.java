package it.unisannio.catman.common.client.cell;

import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.CellWidget;

/**
 * Used to define &quot;CellData&quot; objects that provides data to be shown in a {@link CellWidget}. <br />
 *  Rendering elements in a {@link Cell} requires a {@link CellWidget} and an object that contains the informations to be shown.
 *  Cell structures like {@link CellList} or {@link CellTree}, instantiate a single {@link CellWidget} object that is recycled to show
 *  data from a {@link List} of &quot;CellData&quot; objects.<br />
 */
public interface ListItemCellData {
	/**
	 * Returns the {@link SafeHtml} to be rendered in the left-aligned div of the Cell  
	 * @return the {@link SafeHtml} to be rendered in the left-aligned div of the Cell
	 */
	SafeHtml getLeftDivHTML();
	
	/**
	 * Returns the {@link String} used as title
	 * @return the {@link String} used as title
	 */
	String getTitle();
	
	/**
	 * Returns the {@link SafeHtml} to be rendered in the div below the title  
	 * @return the {@link SafeHtml} to be rendered in the div below the title
	 */
	SafeHtml getCaptionDivHTML();
	
	/**
	 * Returns the {@link SafeHtml} to be rendered in the right-aligned div of the Cell  
	 * @return the {@link SafeHtml} to be rendered in the right-aligned div of the Cell
	 */
	SafeHtml getRightDivHTML();
	
	/**
	 * Returns the {@link SafeHtml} to be rendered in the top-right-aligned div of the Cell  
	 * @return the {@link SafeHtml} to be rendered in the top-right-aligned div of the Cell
	 */
	SafeHtml getTopRightDivHTML();
	
}
