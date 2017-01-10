package ro.eu.documentimporter.inputprocessor;

import java.util.Map;

import ro.eu.documentimporter.repository.RepositoryException;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;

public interface RepositoryMetadataConvertor {

	public Map<String, RepositoryMetadata> convert(Map<String, String> csvRow) throws RepositoryException;

}