package org.newscatching.newscatching.dao;

public interface DataConverter<Input, Result> {
	public Result ConvertTo(Input input) throws Exception;
}
