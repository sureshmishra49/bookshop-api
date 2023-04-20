package com.online.bookshop.domain;

import com.online.bookshop.util.JSONFormatter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BooksModel {
	
	/**
	 * Returns a JSON string corresponding to object state
	 *
	 * @return JSON representation
	 */
	public String toJSON() {
		return JSONFormatter.toJSON(this);
	}

	@Override
	public String toString() {
		return toJSON();
	}
}
