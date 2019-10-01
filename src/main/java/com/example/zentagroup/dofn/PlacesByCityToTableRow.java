package com.example.zentagroup.dofn;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import com.google.api.services.bigquery.model.TableRow;

public class PlacesByCityToTableRow extends DoFn<KV<String, Long>, TableRow> {

	private static final long serialVersionUID = 4754448600737259901L;

	@ProcessElement
	public void processElement(ProcessContext c) {
		TableRow row = new TableRow();
		if (c.element() != null) {
			row.set("city", c.element().getKey());
			row.set("count", c.element().getValue());
		}
		c.output(row);
	}

}
