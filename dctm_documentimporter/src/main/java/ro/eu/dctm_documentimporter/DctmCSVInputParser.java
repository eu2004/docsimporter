package ro.eu.dctm_documentimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.eu.documentimporter.inputprocessor.CSVInputParser;
import ro.eu.documentimporter.inputprocessor.CSVRowParserCallback;
import ro.eu.documentimporter.inputprocessor.RepositoryDocumentConvertor;

@Component
public class DctmCSVInputParser extends CSVInputParser {
	@Autowired
	private DctmCSVRowParserCallback csvRowParserCallback;
	@Autowired
	private DctmRepositoryDocumentConvertor repositoryDocumentConvertor;

	@Override
	public CSVRowParserCallback getRowParserCallback() {
		return csvRowParserCallback;
	}

	@Override
	public RepositoryDocumentConvertor getRepositoryDocumentConvertor() {
		return repositoryDocumentConvertor;
	}

}
