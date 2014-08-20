package com.ucac.lucene.Config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Configer {
	private static Directory directory;
	private static Analyzer analyzer;
	private static IndexWriter indexWriter;
	static {
		try {
		    URL a  =  Configer.class.getClassLoader().getResource("");
	         File file =null;
		    try {
		    	  file  = new File( a.toURI());
			 
			} catch (URISyntaxException e) {
			 	e.printStackTrace();
			}
			 
			if (!file.exists()) {
				file.mkdir();
			}
			File  file2  = new File(file, "resource"); 
			if (!file2.exists()) {
				file2.mkdir();
			}
			directory = FSDirectory.open(file2);
			analyzer = new IKAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					Version.LUCENE_CURRENT, analyzer);
			indexWriter = new IndexWriter(directory, indexWriterConfig);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Directory getDirectory() {
		return directory;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static IndexWriter getIndexWriter() {
		return indexWriter;
	}
}
