package com.example.zentagroup.transform;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import com.example.zentagroup.dofn.AirbnbToKVByCityDoFn;
import com.example.zentagroup.domain.Airbnb;

public class AirbnbToKVByCityTransform extends PTransform<PCollection<Airbnb>, PCollection<KV<String, Airbnb>>> {

	private static final long serialVersionUID = 4049058816324045661L;

	@Override
	public PCollection<KV<String, Airbnb>> expand(PCollection<Airbnb> input) {
		return input.apply(ParDo.of(new AirbnbToKVByCityDoFn()));
	}

}
