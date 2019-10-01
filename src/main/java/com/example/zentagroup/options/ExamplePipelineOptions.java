package com.example.zentagroup.options;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface ExamplePipelineOptions extends PipelineOptions {

	@Description("Output best file on storage gcp")
	@Default.String("gs://staging-dataflow-pipe/output")
	String getOutput();

	void setOutput(String value);

	@Description("Input file on storage gcp")
	@Default.String("gs://staging-dataflow-pipe/input/listingshorted.csv")
	String getInputFile();

	void setInputFile(String value);

	@Description("Project ID")
	@Default.String("gitlabtestenviroment")
	String getProjectId();

	@Description("Output best file on storage gcp")
	@Default.String("gs://staging-dataflow-pipe/output/output_best_airbnb.csv")
	String getOutputBestFile();

	void setOutputBestFile(String value);

	@Description("Output Bigquery")
	@Default.String("airbnb.countbycity")
	String getOutputBigQueryTable();

	void setOutputBigQueryTable(String value);

	void setProjectId(String value);

	@Description("Temp Location")
	@Default.String("gs://staging-dataflow-pipe/output")
	String getTempLocation();

	void setTempLocation(String value);
}
