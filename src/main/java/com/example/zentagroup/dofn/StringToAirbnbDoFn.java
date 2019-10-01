package com.example.zentagroup.dofn;

import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.zentagroup.domain.Airbnb;

public class StringToAirbnbDoFn extends DoFn<String, Airbnb> {

	private static final long serialVersionUID = -329953228316710344L;

	private static final Logger LOG = LoggerFactory.getLogger(StringToAirbnbDoFn.class);

	@ProcessElement
	public void processElement(ProcessContext c, OutputReceiver<Airbnb> out) {
		
		try {
			if (c.element() != null) {
				String[] splitted = c.element().split(",");
				Airbnb airbnb = new Airbnb();
				airbnb.setId(Integer.valueOf(splitted[0]));
				airbnb.setLast_scraped(splitted[1]);
				airbnb.setHost_name(splitted[2]);
				airbnb.setHost_since(splitted[3]);
				airbnb.setCity(splitted[4]);
				airbnb.setProperty_type(splitted[5]);
				airbnb.setRoom_type(splitted[6]);
				airbnb.setReview_scores_rating(
						splitted.length > 6 && splitted[7] != null ? Integer.valueOf(splitted[7]) : 0);
				out.output(airbnb);
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}
}
