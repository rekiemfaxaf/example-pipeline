package com.example.zentagroup.transform;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import com.example.zentagroup.dofn.StringToAirbnbDoFn;
import com.example.zentagroup.domain.Airbnb;

public class StringToAirbnbTransform extends PTransform<PCollection<String>, PCollection<Airbnb>> {

	private static final long serialVersionUID = 7168065003052771260L;

	@Override
	public PCollection<Airbnb> expand(PCollection<String> input) {
		return input.apply(ParDo.of(new StringToAirbnbDoFn()));
	}

}
