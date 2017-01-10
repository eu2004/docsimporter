package ro.eu.dctm_documentimporter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.eu.documentimporter.inputprocessor.RepositoryMetadataConvertor;
import ro.eu.documentimporter.repository.model.RepositoryMetadata;
import ro.eu.documentimporter.repository.model.RepositoryMetadataType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDctmApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TestDctmRepositoryMetadataConvertor {
	@Autowired
	private RepositoryMetadataConvertor dctmRepositoryMetadataConvertor;

	@Test
	public void testConvert() throws Exception {
		final Map<String, String> csvRow = new LinkedHashMap<String, String>();
		csvRow.put("acl_domain", "a ");
		csvRow.put("acl_name", "a ");
		csvRow.put("authors", "a ");
		csvRow.put("a_application_type", "a ");
		csvRow.put("a_archive", "a ");
		csvRow.put("a_category", "a ");
		csvRow.put("a_compound_architecture", "a ");
		csvRow.put("a_content_type", "a ");
		csvRow.put("a_controlling_app", "a ");
		csvRow.put("a_effective_date", "a ");
		csvRow.put("a_effective_flag", "a ");
		csvRow.put("a_effective_label", "a ");
		csvRow.put("a_expiration_date", "a ");
		csvRow.put("a_extended_properties", "a ");
		csvRow.put("a_full_text", "a ");
		csvRow.put("a_is_hidden", "a ");
		csvRow.put("a_is_signed", "a ");
		csvRow.put("a_is_template", "a ");
		csvRow.put("a_last_review_date", "a ");
		csvRow.put("a_link_resolved", "a ");
		csvRow.put("a_publish_formats", "a ");
		csvRow.put("a_retention_date", "a ");
		csvRow.put("a_special_app", "a ");
		csvRow.put("a_status", "a ");
		csvRow.put("a_storage_type", "a ");
		csvRow.put("group_name", "a ");
		csvRow.put("group_permit", "a ");
		csvRow.put("i_antecedent_id", "a ");
		csvRow.put("i_branch_cnt", "a ");
		csvRow.put("i_cabinet_id", "a ");
		csvRow.put("i_chronicle_id", "a ");
		csvRow.put("i_contents_id", "a ");
		csvRow.put("i_direct_dsc", "a ");
		csvRow.put("i_folder_id", "a ");
		csvRow.put("i_has_folder", "a ");
		csvRow.put("i_is_deleted", "a ");
		csvRow.put("i_is_reference", "a ");
		csvRow.put("i_is_replica", "a ");
		csvRow.put("i_latest_flag", "a ");
		csvRow.put("i_partition", "a ");
		csvRow.put("i_reference_cnt", "a ");
		csvRow.put("i_retainer_id", "a ");
		csvRow.put("i_retain_until", "a ");
		csvRow.put("i_vstamp", "a ");
		csvRow.put("keywords", "a ");
		csvRow.put("language_code", "a ");
		csvRow.put("log_entry", "a ");
		csvRow.put("object_name", "a ");
		csvRow.put("owner_name", "a ");
		csvRow.put("owner_permit", "a ");
		csvRow.put("resolution_label", "a ");
		csvRow.put("r_access_date", "a ");
		csvRow.put("r_alias_set_id", "a ");
		csvRow.put("r_aspect_name", "a ");
		csvRow.put("r_assembled_from_id", "a ");
		csvRow.put("r_component_label", "a ");
		csvRow.put("r_composite_id", "a ");
		csvRow.put("r_composite_label", "a ");
		csvRow.put("r_content_size", "a ");
		csvRow.put("r_creation_date", "a ");
		csvRow.put("r_creator_name", "a ");
		csvRow.put("r_current_state", "a ");
		csvRow.put("r_frozen_flag", "a ");
		csvRow.put("r_frzn_assembly_cnt", "a ");
		csvRow.put("r_full_content_size", "a ");
		csvRow.put("r_has_events", "a ");
		csvRow.put("r_has_frzn_assembly", "a ");
		csvRow.put("r_immutable_flag", "a ");
		csvRow.put("r_is_public", "a ");
		csvRow.put("r_is_virtual_doc", "a ");
		csvRow.put("r_link_cnt", "a ");
		csvRow.put("r_link_high_cnt", "a ");
		csvRow.put("r_lock_date", "a ");
		csvRow.put("r_lock_machine", "a ");
		csvRow.put("r_lock_owner", "a ");
		csvRow.put("r_modifier", "a ");
		csvRow.put("r_modify_date", "a ");
		csvRow.put("r_object_type", "dm_sysobject");
		csvRow.put("r_order_no", "a ");
		csvRow.put("r_page_cnt", "a ");
		csvRow.put("r_policy_id", "a ");
		csvRow.put("r_resume_state", "a ");
		csvRow.put("r_version_label", "a ");
		csvRow.put("subject", "a ");
		csvRow.put("title", "a ");
		csvRow.put("world_permit", "a ");

		Map<String, RepositoryMetadata> attributesDefinition = dctmRepositoryMetadataConvertor.convert(csvRow);

		Assert.assertTrue(attributesDefinition != null);
		Assert.assertEquals(csvRow.size(), attributesDefinition.size());

		RepositoryMetadataType[] expectedTypes = new RepositoryMetadataType[] { RepositoryMetadataType.STRING,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.DATE,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.DATE,
				RepositoryMetadataType.STRING, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN,
				RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.DATE,
				RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.STRING, RepositoryMetadataType.DATE,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.STRING, RepositoryMetadataType.INTEGER, RepositoryMetadataType.STRING,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.STRING, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.STRING,
				RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN,
				RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.INTEGER,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.STRING, RepositoryMetadataType.DATE,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.STRING, RepositoryMetadataType.DATE,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.DATE, RepositoryMetadataType.STRING,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.INTEGER,
				RepositoryMetadataType.DOUBLE, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN,
				RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.BOOLEAN, RepositoryMetadataType.INTEGER,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.INTEGER, RepositoryMetadataType.DATE,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.DATE, RepositoryMetadataType.STRING, RepositoryMetadataType.INTEGER,
				RepositoryMetadataType.INTEGER, RepositoryMetadataType.STRING, RepositoryMetadataType.INTEGER,
				RepositoryMetadataType.STRING, RepositoryMetadataType.STRING, RepositoryMetadataType.STRING,
				RepositoryMetadataType.INTEGER };

		int index = 0;
		for (Entry<String, RepositoryMetadata> entry : attributesDefinition.entrySet()) {
			Assert.assertEquals(entry.getKey(), expectedTypes[index++], entry.getValue().getType());
		}
	}
}
