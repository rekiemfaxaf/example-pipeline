package com.example.zentagroup.dofn;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import com.example.zentagroup.domain.Airbnb;

public class AirbnbToKVByCityDoFn extends DoFn<Airbnb, KV<String, Airbnb>> {

	private static final long serialVersionUID = -5984365511750727366L;

	@ProcessElement
	public void processElement(ProcessContext c, OutputReceiver<KV<String, Airbnb>> out) {
		c.output(KV.of(c.element().getCity(), c.element()));
	}

}
