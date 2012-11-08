package com.leman.core.data.dictionar.dbunit;

import java.io.File;
import java.text.MessageFormat;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class TestHelper {

	protected IDataSet getUser()
		throws Exception {
		return buildDataSet("USER.xml");
	}
	
	private static IDataSet buildDataSet(String filename) {
		try {
			final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);
			return builder.build(new File(TestHelper.class.getClassLoader().getResource(filename)
					.getFile()));
		} catch (Exception e) {
			throw new IllegalStateException(MessageFormat.format(
					"Error while building a dataset from file \"{0}\"", filename), e);
		}
	}

	protected void disableReferentialIntegrity(final IDatabaseConnection conn) {
		try {
			conn.getConnection().prepareStatement("set referential_integrity FALSE").execute();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}	
}