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

	@Description("Id project.")
	@Default.String("gitlabtestenviroment")
	String getProjectId();

	void setProjectId(String value);

}
