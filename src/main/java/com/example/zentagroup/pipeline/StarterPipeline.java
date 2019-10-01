/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.zentagroup.pipeline;

import static org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.Top;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.zentagroup.options.ExamplePipelineOptions;
import com.example.zentagroup.schema.CountByCity;
import com.example.zentagroup.transform.AirbnbToKVByCityTransform;
import com.example.zentagroup.transform.PlacebyCityToBigQueryTransform;
import com.example.zentagroup.transform.PlacebyCityToStringMostTransform;
import com.example.zentagroup.transform.StringToAirbnbTransform;
import com.example.zentagroup.utils.Constants;

/**
 * A starter example for writing Beam programs.
 *
 * <p>
 * The example takes two strings, converts them to their upper-case
 * representation and logs them.
 *
 * <p>
 * To run this starter example locally using DirectRunner, just execute it
 * without any additional parameters from your favorite development environment.
 *
 * <p>
 * To run this starter example using managed resource in Google Cloud Platform,
 * you should specify the following command-line options:
 * --project=<YOUR_PROJECT_ID>
 * --stagingLocation=<STAGING_LOCATION_IN_CLOUD_STORAGE> --runner=DataflowRunner
 */
public class StarterPipeline {
	private static final Logger LOG = LoggerFactory.getLogger(StarterPipeline.class);

	public static void main(String[] args) {
		LOG.info(Constants.START_PIPELINE);

		ExamplePipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
				.as(ExamplePipelineOptions.class);

		Pipeline p = Pipeline.create(options);

		PCollection<KV<String, Long>> placesByCity = p
				.apply(Constants.READ_CSV, TextIO.read().from(options.getInputFile()))
				.apply(Constants.REMOVE_HEADER,
						Filter.by((String row) -> !((row.startsWith("dwid") || row.startsWith("\"dwid\"")
								|| row.startsWith("id")))))
				.apply(Constants.STRING_TO_AIRBNB, new StringToAirbnbTransform())
				.apply(Constants.TRANSFORM_KV_CITY, new AirbnbToKVByCityTransform())
				.apply(Constants.COUNT_BY_CITY, Count.perKey());

		placesByCity
				.apply(Constants.MOST + Constants.NUMBER_RANK,
						Top.<KV<String, Long>, KV.OrderByValue<String, Long>>of(Constants.NUMBER_RANK,
								new KV.OrderByValue<String, Long>()).withoutDefaults())
				.apply(Constants.MOST + Constants.PLACEBYCITY_TO_STRING, new PlacebyCityToStringMostTransform())
				.apply(Constants.WRITE_OUTPUT, TextIO.write().to(options.getOutputBestFile())
						.withSuffix(Constants.EXTENSION_CSV).withoutSharding());

		placesByCity.apply(Constants.PLACEBYCITY_TO_STRING, new PlacebyCityToBigQueryTransform()).apply(
				Constants.PLACEBYCITY_TO_BQ,
				BigQueryIO.writeTableRows().to(options.getOutputBigQueryTable())
						.withSchema(CountByCity.getTableSchema()).withCreateDisposition(CREATE_IF_NEEDED)
						.withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND).withoutValidation());

		p.run().waitUntilFinish();

		LOG.info(Constants.END_PIPELINE);
	}
}
