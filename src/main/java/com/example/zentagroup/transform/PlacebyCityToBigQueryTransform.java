package com.example.zentagroup.transform;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import com.example.zentagroup.dofn.PlacesByCityToTableRow;
import com.google.api.services.bigquery.model.TableRow;

public class PlacebyCityToBigQueryTransform extends PTransform<PCollection<KV<String, Long>>, PCollection<TableRow>> {

	private static final long serialVersionUID = 7662172896556414561L;

	@Override
	public PCollection<TableRow> expand(PCollection<KV<String, Long>> input) {
		return input.apply(ParDo.of(new PlacesByCityToTableRow()));
	}
}
