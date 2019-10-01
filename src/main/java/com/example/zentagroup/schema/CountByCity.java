package com.example.zentagroup.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

public class CountByCity implements Serializable {

	private static final long serialVersionUID = 1601757304994515676L;

	public static TableSchema getTableSchema() {
		List<TableFieldSchema> fields = new ArrayList<>();
		fields.add(new TableFieldSchema().setName("city").setType("STRING"));
		fields.add(new TableFieldSchema().setName("count").setType("INTEGER"));
		return new TableSchema().setFields(fields);
	}

}
