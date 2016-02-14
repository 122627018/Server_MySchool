package bean.lib.format;

import java.util.ArrayList;
import java.util.List;

import bean.lib.BookBorrowedState;

public class LibBorrowState {
	
	private List<BookBorrowedState> columns = new ArrayList<BookBorrowedState>();
	


	public List<BookBorrowedState> getColumns() {
		return columns;
	}

	public void setColumns(List<BookBorrowedState> columns) {
		this.columns = columns;
	}

}
