package com.example.zentagroup.transform;

import java.util.List;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import com.example.zentagroup.dofn.PlacebyCityToStringMostDoFn;

public class PlacebyCityToStringMostTransform extends PTransform<PCollection<List<KV<String, Long>>>, PCollection<String>> {

	private static final long serialVersionUID = 7662172896556414561L;

	@Override
	public PCollection<String> expand(PCollection<List<KV<String, Long>>> input) {
		return input.apply(ParDo.of(new PlacebyCityToStringMostDoFn()));
	}
}
