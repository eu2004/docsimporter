package ro.eu.dctm_documentimporter;

import java.util.Map;

import org.springframework.stereotype.Component;

import ro.eu.documentimporter.inputprocessor.RepositoryMetadataConvertor;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

@Component
public class DctmRepositoryMetadataConvertor implements RepositoryMetadataConvertor {

	@Override
	public Map<String, RepositoryMetadata> convert(Map<String, Integer> headerMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
